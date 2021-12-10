package br.com.dock.desafio2.service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.domain.Conta;

/**
 * Classe responsavel pelos testes dos recursos de {@link ContaServiceImpl}.
 *
 * @author matramos
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts = {"/create-tables.sql", "/fill-tables.sql"}),
	@Sql(scripts = {"/drop-tables.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@ActiveProfiles("test")
final class ContaServiceImplTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContaServiceImplTest.class);

	@Autowired
	private ContaService contaService;

	/**
	 * Setup inicial.
	 */
	@BeforeEach
	public void init() {
    	NovaContaDTO novaConta = new NovaContaDTO();
    	novaConta.setCpf("87304306076");
    	novaConta.setTipoConta(Integer.valueOf(5));

    	BigDecimal valorOperacao = new BigDecimal("99999.00000");
    	novaConta.setLimiteSaqueDiario(valorOperacao);

    	this.contaService.criarConta(novaConta);

    	OperacaoDTO operacaoDeposito = new OperacaoDTO(valorOperacao);
    	this.contaService.incrementarSaldo(NumberUtils.INTEGER_ONE, operacaoDeposito);
	}

	/**
	 * Testa o método {@link ContaServiceImpl#decrementarSaldo(Integer, OperacaoDTO, BigDecimal)}.</p>
	 *
	 * Este método realiza o teste da concorrencia de execuçao de saques para um usuario. Quando houver
	 * a tentativa de saque no mesmo instante, por motivos de segurança e controle do saldo devemos
	 * retornar um erro / alerta para o usuario.
	 *
	 * @throws InterruptedException
	 */
	@Test
	void saqueConcorrente() throws InterruptedException {
		Integer idConta = NumberUtils.INTEGER_ONE;

		BigDecimal valorOperacao = new BigDecimal("100.00000");
		OperacaoDTO operacao = new OperacaoDTO(valorOperacao);

		this.contaService.decrementarSaldo(idConta, operacao, BigDecimal.ZERO);

		Conta conta = this.contaService.getContaPorId(NumberUtils.INTEGER_ONE);
		Assertions.assertEquals(NumberUtils.INTEGER_TWO, conta.getVersao());

		final ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 2)
			.forEach(t -> {
				executor.execute(() -> {
					try {
						this.contaService.decrementarSaldo(idConta, operacao, BigDecimal.ZERO);
					} catch (ObjectOptimisticLockingFailureException e) {
						// Ignore
						LOGGER.info("ObjectOptimisticLockingFailureException, operacao de saque ignorada ...");
					}
				});
			});

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

		// Somente uma das duas tentativas de saques anteriores deve ser efetivada, entao
		// o próximo valor deve ser 3
        
        conta = this.contaService.getContaPorId(NumberUtils.INTEGER_ONE);
        Assertions.assertEquals(Integer.valueOf(3), conta.getVersao());
	}

}
