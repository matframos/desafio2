package br.com.dock.desafio2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.domain.TipoTransacao;
import br.com.dock.desafio2.domain.Transacao;
import br.com.dock.desafio2.repository.TransacaoRepository;

/**
 * Implementaçao de {@link TransacaoService} para abstrair a gestão dos
 * dados de {@link Transacao}.
 *
 * @author matramos
 * @since 1.0.0
 */
@Component
public class TransacaoServiceImpl implements TransacaoService {

	private ContaService contaService;
	private TransacaoRepository transacaoRepository;

	/**
	 * Construtor padrão.
	 *
	 * @param contaService the contaService to set
	 * @param transacaoRepository the transacaoRepository to set
	 */
	public TransacaoServiceImpl(ContaService contaService,
			TransacaoRepository transacaoRepository) {
		this.contaService = contaService;
		this.transacaoRepository = transacaoRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transacao getTransacaoPorId(Integer idTransacao) {
		return this.transacaoRepository.findById(idTransacao).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getValorDepositosPorDia(Integer idConta, LocalDate data) {
		return this.transacaoRepository.getValorTransacoesPorDia(idConta, data, TipoTransacao.DEPOSITO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getValorSaquesPorDia(Integer idConta, LocalDate data) {
		return this.transacaoRepository.getValorTransacoesPorDia(idConta, data, TipoTransacao.SAQUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Transacao> getTransacoesPorPeriodo(Integer idConta, LocalDate dataInicial, LocalDate dataFinal) {
		var conta = this.contaService.getContaPorId(idConta);
		return this.transacaoRepository.getTransacoesPorPeriodo(conta.getIdConta(), dataInicial, dataFinal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transacao criarTransacao(Transacao transacao) {
		return this.transacaoRepository.save(transacao);
	}

	private Transacao criarTransacao(Integer idConta, BigDecimal valor, TipoTransacao tipoTransacao) {
		var transacao = new Transacao();
		transacao.setIdConta(idConta);
		transacao.setValor(valor);
		transacao.setDataTransacao(LocalDate.now());
		transacao.setTipoTransacao(tipoTransacao);
		return this.criarTransacao(transacao);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deposito(Integer idConta, OperacaoDTO operacao) {
		this.contaService.incrementarSaldo(idConta, operacao);
		this.criarTransacao(idConta, operacao.getValor(), TipoTransacao.DEPOSITO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saque(Integer idConta, OperacaoDTO operacao) {
		BigDecimal valorTotalSaquesDia = this.getValorSaquesPorDia(idConta, LocalDate.now());

		this.contaService.decrementarSaldo(idConta, operacao, valorTotalSaquesDia);
		this.criarTransacao(idConta, operacao.getValor(), TipoTransacao.SAQUE);
	}

}
