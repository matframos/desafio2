package br.com.dock.desafio2.service;

import java.math.BigDecimal;

import br.com.dock.desafio2.controller.domain.ContaDTO;
import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.domain.Conta;

/**
 * Interface disponibilizada para o gerenciamento das informaçoes de contas.
 *
 * @author matramos
 * @since 1.0
 */
public interface ContaService {

	/**
	 * Recupera uma conta por ID, porém somente aquelas que estiverem ativas.
	 *
	 * @param idConta
	 * @return {@link ContaDTO}
	 */
	Conta getContaPorId(Integer idConta);

	/**
	 * Recupera uma conta por ID.
	 *
	 * @param idConta
	 * @return {@link ContaDTO}
	 */
	Conta getContaPorId(Integer idConta, boolean somenteAtiva);

	/**
	 * Recupera o saldo da conta pelo ID da conta.
	 *
	 * @param idConta
	 * @return saldo
	 */
	BigDecimal getSaldoPorIdConta(Integer idConta);

	/**
	 * Cria uma nova conta.
	 *
	 * @param placa
	 * @return {@link ContaDTO}
	 */
	Conta criarConta(NovaContaDTO novaConta);

	/**
	 * Inativa uma conta de cliente.
	 *
	 * @param idConta
	 */
	void inativarConta(Integer idConta);

	/**
	 * Método utilizado para realizar a operação de acrescimo de valores
	 * no saldo do cliente.
	 *
	 * @param idConta
	 * @param operacao
	 */
	void incrementarSaldo(Integer idConta, OperacaoDTO operacao);

	/**
	 * Método utilizado para realizar a operação de decrescimo de valores
	 * no saldo do cliente.
	 *
	 * @param idConta
	 * @param operacao
	 * @param valorTransDia
	 */
	void decrementarSaldo(Integer idConta, OperacaoDTO operacao, BigDecimal valorTransDia);

}
