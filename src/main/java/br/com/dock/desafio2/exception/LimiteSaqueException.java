package br.com.dock.desafio2.exception;

import java.math.BigDecimal;

/**
 * {@code Exception} para identificar quando um cliente atingir
 * o limite de saque diario.
 *
 * @author matramos
 * @since 1.0
 */
public final class LimiteSaqueException extends RuntimeException {

	private static final long serialVersionUID = 3172377711844559375L;
	private final BigDecimal valor;

	/**
	 * Construtor padrão.
	 *
	 * @param valor
	 */
	public LimiteSaqueException(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

}
