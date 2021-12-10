package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * {@code DTO} para encapsular o valor das operaçoes de deposito e saque
 * de uma conta.
 *
 * @author matramos
 * @since 1.0.0
 */
public final class OperacaoDTO implements Serializable {

	private static final long serialVersionUID = -338741738242064003L;
	private BigDecimal valor;

	/**
	 * Construtor padrão.
	 */
	public OperacaoDTO() {
	}

	/**
	 * Construtor padrão.
	 */
	public OperacaoDTO(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(valor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		OperacaoDTO other = (OperacaoDTO) obj;
		return Objects.equals(valor, other.valor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("NovoDepositoDTO [valor=");
		builder.append(valor);
		builder.append("]");
		return builder.toString();
	}

}
