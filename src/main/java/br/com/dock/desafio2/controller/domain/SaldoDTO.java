package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * {@code DTO} para encapsular o valor do saldo disponivel do cliente.
 *
 * @author matramos
 * @since 1.0
 */
public final class SaldoDTO implements Serializable {

	private static final long serialVersionUID = 7995208740946258985L;
	private BigDecimal saldo;

	/**
	 * Construtor padrão.
	 */
	public SaldoDTO() {
	}

	/**
	 * Construtor padrão.
	 */
	public SaldoDTO(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(saldo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SaldoDTO other = (SaldoDTO) obj;
		return Objects.equals(saldo, other.saldo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("SaldoDTO [saldo=");
		builder.append(saldo);
		builder.append("]");
		return builder.toString();
	}

}
