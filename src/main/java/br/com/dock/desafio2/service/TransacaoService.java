package br.com.dock.desafio2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.domain.Transacao;

/**
 * Interface disponibilizada para o gerenciamento das informaçoes de pessoas.
 *
 * @author matramos
 * @since 1.0
 */
public interface TransacaoService {

	/**
	 * Recupera uma transação por Id.
	 *
	 * @param idTransacao
	 * @return {@link Transacao}
	 */
	Transacao getTransacaoPorId(Integer idTransacao);

	/**
	 * Retorna o valor total em transaçoes de deposito realizadas por um cliente
	 * em um dia especifico.
	 *
	 * @param idConta
	 * @param data
	 * @return valor total
	 */
	BigDecimal getValorDepositosPorDia(Integer idConta, LocalDate data);

	/**
	 * Retorna o valor total em transaçoes de saque realizadas por um cliente
	 * em um dia especifico.
	 *
	 * @param idConta
	 * @param data
	 * @return valor total
	 */
	BigDecimal getValorSaquesPorDia(Integer idConta, LocalDate data);

	/**
	 * Recupera a lista de transacoes de um cliente em um determinado periodo.
	 *
	 * @param idConta
	 * @param dataInicial
	 * @param dataFinal
	 * @return {@link Transacao}
	 */
	List<Transacao> getTransacoesPorPeriodo(Integer idConta, LocalDate dataInicial, LocalDate dataFinal);

	/**
	 * Realiza o deposito de um valor em uma conta.
	 *
	 * @param transacao
	 */
    Transacao criarTransacao(Transacao transacao);

	/**
	 * Realiza o deposito de um valor em uma conta.
	 *
	 * @param idConta
	 * @param operacao
	 */
    void deposito(Integer idConta, OperacaoDTO operacao);

    /**
     * Realiza o saque de um valor em uma conta.
     *
     * @param idConta
     * @param operacao
     */
    void saque(Integer idConta, OperacaoDTO operacao);

}
