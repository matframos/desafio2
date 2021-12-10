package br.com.dock.desafio2.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dock.desafio2.controller.domain.ErrorDTO;
import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.controller.domain.TipoTransacaoDTO;
import br.com.dock.desafio2.controller.domain.TransacaoDTO;
import br.com.dock.desafio2.domain.Conta;
import br.com.dock.desafio2.domain.TipoTransacao;
import br.com.dock.desafio2.domain.Transacao;
import br.com.dock.desafio2.service.ContaService;
import br.com.dock.desafio2.service.TransacaoService;

/**
 * Classe responsavel pelos testes dos recursos de {@link TransacaoController}.
 *
 * @author matramos
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts = {"/create-tables.sql", "/fill-tables.sql"}),
	@Sql(scripts = {"/drop-tables.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@ActiveProfiles("test")
final class TransacaoControllerTest {

	private final String basePath = "/gestao/v1/transacoes";
	private URI uriDeposito;
	private URI uriSaque;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoService transacaoService;

    /**
     * Setup inicial.
     */
    @BeforeEach
    public void init() {
    	this.uriDeposito = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/deposito")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	this.uriSaque = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/saque")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	NovaContaDTO novaConta = new NovaContaDTO();
    	novaConta.setCpf("87304306076");
    	novaConta.setLimiteSaqueDiario(BigDecimal.valueOf(500));
    	novaConta.setTipoConta(NumberUtils.INTEGER_TWO);
    	this.contaService.criarConta(novaConta);
    }

    /**
     * Testa o método {@link TransacaoController#deposito(Integer, br.com.dock.desafio2.controller.domain.OperacaoDTO)}.
     */
    @Test
    void deposito() {
    	BigDecimal valor = new BigDecimal("110.60000");
    	OperacaoDTO operacao = new OperacaoDTO(valor);

    	HttpEntity<OperacaoDTO> request = new HttpEntity<>(operacao);

    	ResponseEntity<String> response = this.restTemplate.postForEntity(this.uriDeposito, request, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Deposito realizado com sucesso", response.getBody());

        Conta conta = this.contaService.getContaPorId(NumberUtils.INTEGER_ONE);
        Assertions.assertNotNull(conta);
        Assertions.assertEquals(new BigDecimal("500.00000"), conta.getLimiteSaqueDiario());
        Assertions.assertEquals(valor, conta.getSaldo());

        BigDecimal valorTotalDia = this.transacaoService.getValorDepositosPorDia(conta.getIdConta(), LocalDate.now());
        Assertions.assertEquals(valor, valorTotalDia);

        Transacao transacao = this.transacaoService.getTransacaoPorId(NumberUtils.INTEGER_ONE);
        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(LocalDate.now(), transacao.getDataTransacao());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, transacao.getIdConta());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, transacao.getIdTransacao());
        Assertions.assertEquals(valor, transacao.getValor());
    }

    /**
     * Testa o método {@link TransacaoController#deposito(Integer, br.com.dock.desafio2.controller.domain.OperacaoDTO)}.</p>
     *
     * Neste método tentamos realizar cinco depositos em sequencia na conta do cliente e avaliamos o
     * total depositado e o saldo final da conta.
     */
    @Test
    void depositoMultiplo() {
    	BigDecimal valor = new BigDecimal("30.00000");
    	OperacaoDTO operacao = new OperacaoDTO(valor);
    	HttpEntity<OperacaoDTO> request = new HttpEntity<>(operacao);

    	IntStream.range(0, 5).forEach(t -> {
    		ResponseEntity<String> response = this.restTemplate.postForEntity(this.uriDeposito, request, String.class);
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    	});

    	BigDecimal valorDia = new BigDecimal("150.00000");

        Conta conta = this.contaService.getContaPorId(NumberUtils.INTEGER_ONE);
        Assertions.assertNotNull(conta);
        Assertions.assertEquals(new BigDecimal("500.00000"), conta.getLimiteSaqueDiario());
        Assertions.assertEquals(valorDia, conta.getSaldo());

        BigDecimal valorTotalDia = this.transacaoService.getValorDepositosPorDia(conta.getIdConta(), LocalDate.now());
        Assertions.assertEquals(valorDia, valorTotalDia);

        Transacao transacao = this.transacaoService.getTransacaoPorId(Integer.valueOf(5));
        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(LocalDate.now(), transacao.getDataTransacao());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, transacao.getIdConta());
        Assertions.assertEquals(valor, transacao.getValor());

        transacao = this.transacaoService.getTransacaoPorId(Integer.valueOf(6));
        Assertions.assertNull(transacao);
    }

    /**
     * Testa o método {@link TransacaoController#saque(Integer, br.com.dock.desafio2.controller.domain.OperacaoDTO)}.<p>
     *
     * Neste método tentamos realizar o saque acima do que temos disponivel no saldo do cliente.
     *
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void saque_SaldoIndisponivel() throws JsonMappingException, JsonProcessingException {
    	BigDecimal valor = new BigDecimal("50.00000");
    	OperacaoDTO operacao = new OperacaoDTO(valor);

    	HttpEntity<OperacaoDTO> request = new HttpEntity<>(operacao);

    	ResponseEntity<String> response = this.restTemplate.postForEntity(uriSaque, request, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        List<ErrorDTO> errors = objectMapper.readValue(response.getBody(), new TypeReference<List<ErrorDTO>>(){});

        Assertions.assertTrue(errors.contains(ErrorDTO.of("400.001", "Saldo indisponivel")));
    }

    /**
     * Testa o método {@link TransacaoController#saque(Integer, br.com.dock.desafio2.controller.domain.OperacaoDTO)}.</p>
     *
     * Inicialmente realizamos a criação da conta com um limite de saque diario de 500.00 e o deposito
     * de 6 valores de 100.00.</p>
     *
     * Posteriormente, para finalizar o teste, tentamos realizar 6 saques de 100.00. Os cinco primeiros devem funcionar normalmente
     * e o sexto, entretanto, deve apresentar um erro de tentativa de saque fora do limite diario.
     *
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void saque() throws JsonMappingException, JsonProcessingException {
    	BigDecimal valor = new BigDecimal("100.00000");
    	OperacaoDTO operacao = new OperacaoDTO(valor);
    	HttpEntity<OperacaoDTO> request = new HttpEntity<>(operacao);

    	IntStream.range(0, 6)
    		.forEach(t -> {
    			this.restTemplate.postForEntity(this.uriDeposito, request, Void.class);
    		});

    	IntStream.range(0, 5)
    		.forEach(t -> {
	    		ResponseEntity<String> response = this.restTemplate.postForEntity(this.uriSaque, request, String.class);
	    		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    		Assertions.assertEquals("Saque realizado com sucesso", response.getBody());
    		});

    	ResponseEntity<String> response = this.restTemplate.postForEntity(uriSaque, request, String.class);
    	Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    	ObjectMapper objectMapper = new ObjectMapper();
    	List<ErrorDTO> errors = objectMapper.readValue(response.getBody(), new TypeReference<List<ErrorDTO>>(){});

        Assertions.assertTrue(errors.contains(ErrorDTO.of("400.001", "Limite de saque diario de 500")));
    }

    /**
     * testa o método {@link TransacaoControllerTest#getTransacoesPorPeriodo()}.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void getTransacoesPorPeriodo_DataNaoInformada() throws JsonMappingException, JsonProcessingException {
    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/extrato")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	ResponseEntity<String> response = this.restTemplate.getForEntity(uri, String.class);
    	Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    	ObjectMapper objectMapper = new ObjectMapper();
    	List<ErrorDTO> errors = objectMapper.readValue(response.getBody(), new TypeReference<List<ErrorDTO>>(){});

    	Assertions.assertTrue(errors.contains(ErrorDTO.of("400.001", "O parametro dataInicial deve ser informado")));
    }

    /**
     * Realiza a formatação do endereço para pesquisa do extrato.
     *
     * @param idConta
     * @param dataInicial
     * @param dataFinal
     * @return endereco extrato
     */
    private URI getURIExtrato(Integer idConta, LocalDate dataInicial, LocalDate dataFinal) {
    	return UriComponentsBuilder.newInstance()
    			.host(this.basePath)
		    	.path("/{idConta}/extrato")
		    	.queryParam("dataInicial", dataInicial)
		    	.queryParam("dataFinal", dataFinal)
		    	.buildAndExpand(idConta)
		    	.toUri();
    }

    /**
     * Testa o método {@link TransacaoControllerTest#getTransacoesPorPeriodo()}.
     *
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void getTransacoesPorPeriodo_ContaNaoEncontrada() throws JsonMappingException, JsonProcessingException {
    	LocalDate dataTransacao = LocalDate.now();

    	URI uri = this.getURIExtrato(NumberUtils.INTEGER_TWO, dataTransacao, dataTransacao);

    	ResponseEntity<String> response = this.restTemplate.getForEntity(uri, String.class);
    	Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    	String mensagem = response.getBody();
    	Assertions.assertEquals("Conta 2 nao encontrada", mensagem);
    }

    /**
     * Testa o método {@link TransacaoControllerTest#getTransacoesPorPeriodo()}.</p>
     */
    @Test
    void getTransacoesPorPeriodo_ResultadoVazio() {
    	LocalDate dataTransacao = LocalDate.now();

    	URI uri = this.getURIExtrato(NumberUtils.INTEGER_ONE, dataTransacao, dataTransacao);

    	ResponseEntity<TransacaoDTO[]> response = this.restTemplate.getForEntity(uri, TransacaoDTO[].class);
    	Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    	TransacaoDTO[] transacoes = response.getBody();
    	Assertions.assertEquals(0, transacoes.length);
    }

    /**
     * Testa o método {@link TransacaoControllerTest#getTransacoesPorPeriodo()}.</p>
     */
    @Test
    void getTransacoesPorPeriodo() {
    	LocalDate dataTransacao = LocalDate.now();
    	this.setupTransacoesPorPeriodo(dataTransacao);

    	URI uri = this.getURIExtrato(NumberUtils.INTEGER_ONE, dataTransacao, dataTransacao);

    	ResponseEntity<TransacaoDTO[]> response = this.restTemplate.getForEntity(uri, TransacaoDTO[].class);
    	Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    	TransacaoDTO[] transacoes = response.getBody();
    	Assertions.assertNotNull(transacoes);
    	Assertions.assertEquals(12, transacoes.length);

    	TransacaoDTO transacao = transacoes[0];
    	Assertions.assertEquals(dataTransacao, transacao.getDataTransacao());
    	Assertions.assertEquals(NumberUtils.INTEGER_ONE, transacao.getIdConta());
    	Assertions.assertEquals(TipoTransacaoDTO.DEPOSITO, transacao.getTipoTransacao());
    }

    /**
     * Realiza uma carga inicial de dados de transacoes para o teste do método {@link #getTransacoesPorPeriodo()}.
     *
     * @param dataFinal
     */
    private void setupTransacoesPorPeriodo(LocalDate dataTransacao) {
    	Transacao transacao = new Transacao();
		transacao.setIdConta(NumberUtils.INTEGER_ONE);
		transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
		transacao.setValor(new BigDecimal("20.00000"));
		transacao.setDataTransacao(dataTransacao);

    	IntStream.range(0, 12)
    		.forEach(t -> {
    			transacao.setIdTransacao(null);
    			this.transacaoService.criarTransacao(transacao);
    		});

    	// Adiciona um registro no dia anterior
    	transacao.setIdTransacao(null);
    	transacao.setDataTransacao(dataTransacao.minusDays(1));
		this.transacaoService.criarTransacao(transacao);

    	// Adiciona um registro no dia posterior
		transacao.setIdTransacao(null);
    	transacao.setDataTransacao(dataTransacao.plusDays(1));
    	this.transacaoService.criarTransacao(transacao);
    }

}
