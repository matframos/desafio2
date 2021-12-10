package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * {@code DTO} para encapsular os dados necessarios para a criação de
 * uma nova conta.
 *
 * @author matramos
 * @since 1.0
 */
public final class NovaContaDTO implements Serializable {

	private static final long serialVersionUID = -5968464765969850634L;

	@NotNull(message = "{error.field.conta.cpf.NotNull}")
	@NotBlank(message = "{error.field.conta.cpf.NotBlank}")
	@Pattern(regexp = "^\\d{11}$", message = "{error.field.conta.cpf.Pattern}")
	private String cpf;

	@NotNull(message = "{error.field.conta.limiteSaqueDiario.NotNull}")
	@Min(value = 0, message = "{error.field.conta.limiteSaqueDiario.Min}")
	private BigDecimal limiteSaqueDiario;

	@NotNull(message = "{error.field.conta.tipoConta.NotNull}")
	@Min(value = 1, message = "{error.field.conta.tipoConta.Min}")
	private Integer tipoConta;

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the limiteSaqueDiario
	 */
	public BigDecimal getLimiteSaqueDiario() {
		return limiteSaqueDiario;
	}

	/**
	 * @param limiteSaqueDiario the limiteSaqueDiario to set
	 */
	public void setLimiteSaqueDiario(BigDecimal limiteSaqueDiario) {
		this.limiteSaqueDiario = limiteSaqueDiario;
	}

	/**
	 * @return the tipoConta
	 */
	public Integer getTipoConta() {
		return tipoConta;
	}

	/**
	 * @param tipoConta the tipoConta to set
	 */
	public void setTipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
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
		NovaContaDTO other = (NovaContaDTO) obj;
		return Objects.equals(cpf, other.cpf);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("NovaContaDTO [cpf=");
		builder.append(cpf);
		builder.append(", limiteSaqueDiario=");
		builder.append(limiteSaqueDiario);
		builder.append(", tipoConta=");
		builder.append(tipoConta);
		builder.append("]");
		return builder.toString();
	}

}
