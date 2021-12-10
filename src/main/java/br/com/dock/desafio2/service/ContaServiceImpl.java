package br.com.dock.desafio2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.domain.Conta;
import br.com.dock.desafio2.exception.ClienteNaoEncontradoException;
import br.com.dock.desafio2.exception.ContaNaoEncontradaException;
import br.com.dock.desafio2.exception.LimiteSaqueException;
import br.com.dock.desafio2.exception.SaldoIndisponivelException;
import br.com.dock.desafio2.repository.ContaRepository;

/**
 * Implementaçao de {@link ContaService} para abstrair a gestão dos
 * dados de {@link Conta}.
 *
 * @author matramos
 * @since 1.0
 */
@Component
public class ContaServiceImpl implements ContaService {

	private PessoaService pessoaService;
	private ContaRepository contaRepository;

	/**
	 * Construtor padrão.
	 *
	 * @param pessoaService the pessoaService to set
	 * @param contaRepository the contaRepository to set
	 */
	public ContaServiceImpl(PessoaService pessoaService,
			ContaRepository contaRepository) {
		this.pessoaService = pessoaService;
		this.contaRepository = contaRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Conta getContaPorId(Integer idConta) {
		return this.getContaPorId(idConta, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Conta getContaPorId(Integer idConta, boolean somenteAtiva) {
		var conta = this.contaRepository.findById(idConta).orElse(null);
		if (somenteAtiva) {
			this.validarContaAtiva(conta, idConta);
		}
		return conta;
	}

	private void validarContaAtiva(Conta conta, Integer idConta) {
		if (Objects.isNull(conta) || Boolean.FALSE.equals(conta.getFlagAtivo())) {
			throw new ContaNaoEncontradaException(idConta);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getSaldoPorIdConta(Integer idConta) {
		var conta = this.getContaPorId(idConta);
		return conta.getSaldo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Conta criarConta(NovaContaDTO novaConta) {
		var cliente = this.pessoaService.getPessoaPorCpf(novaConta.getCpf());

		if (Objects.isNull(cliente)) {
			throw new ClienteNaoEncontradoException(novaConta.getCpf());
		}

		var conta = new Conta();
		conta.setIdPessoa(cliente.getIdPessoa());
		conta.setFlagAtivo(Boolean.TRUE);
		conta.setSaldo(BigDecimal.ZERO);
		conta.setLimiteSaqueDiario(novaConta.getLimiteSaqueDiario());
		conta.setTipoConta(novaConta.getTipoConta());
		conta.setDataCriacao(LocalDate.now());

		return this.contaRepository.save(conta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void inativarConta(Integer idConta) {
		var conta = this.getContaPorId(idConta);
		conta.setFlagAtivo(Boolean.FALSE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void incrementarSaldo(Integer idConta, OperacaoDTO operacao) {
		var conta = this.getContaPorId(idConta);

		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal valor = operacao.getValor();
		BigDecimal novoSaldo = saldoAtual.add(valor);

		conta.setSaldo(novoSaldo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void decrementarSaldo(Integer idConta, OperacaoDTO operacao, BigDecimal valorTotalSaquesDia) {
		var conta = this.getContaPorId(idConta);

		BigDecimal valorSaque = operacao.getValor();

		if (valorSaque.compareTo(conta.getSaldo()) > 0) {
			throw new SaldoIndisponivelException(conta.getSaldo());
		}

		if (Objects.isNull(valorTotalSaquesDia)) {
			valorTotalSaquesDia = BigDecimal.ZERO;
		}

		BigDecimal totalSaquesDia = valorTotalSaquesDia.add(valorSaque);

		if (totalSaquesDia.compareTo(conta.getLimiteSaqueDiario()) > 0) {
			throw new LimiteSaqueException(conta.getLimiteSaqueDiario());
		}

		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal novoSaldo = saldoAtual.subtract(valorSaque);

		conta.setSaldo(novoSaldo);
	}

}
