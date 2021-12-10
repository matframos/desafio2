package br.com.dock.desafio2.exception;

import java.math.BigDecimal;

/**
 * {@code Exception} para identificar que um cliente não pode realizar
 * um saque pois não há limite.
 *
 * @author matramos
 * @since 1.0.0
 */
public final class SaldoIndisponivelException extends RuntimeException {

	private static final long serialVersionUID = 3485180667367840334L;
	private final BigDecimal limite;

	/**
	 * Construtor padrão.
	 *
	 * @param limite
	 */
	public SaldoIndisponivelException(BigDecimal limite) {
		this.limite = limite;
	}

	/**
	 * @return the limite
	 */
	public BigDecimal getLimite() {
		return limite;
	}

}
