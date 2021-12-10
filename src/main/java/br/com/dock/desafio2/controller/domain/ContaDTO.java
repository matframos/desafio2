package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code DTO} para representar os dados das contas.
 *
 * @author matramos
 * @since 1.0
 */
public final class ContaDTO implements Serializable {

	private static final long serialVersionUID = -346302188980620617L;

	private Integer idConta;
	private Integer idPessoa;
	private BigDecimal saldo;
	private BigDecimal limiteSaqueDiario;
	private Integer tipoConta;
	private LocalDate dataCriacao;

	/**
	 * @return the idConta
	 */
	public Integer getIdConta() {
		return idConta;
	}

	/**
	 * @param idConta the idConta to set
	 */
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

	/**
	 * @return the idPessoa
	 */
	public Integer getIdPessoa() {
		return idPessoa;
	}

	/**
	 * @param idPessoa the idPessoa to set
	 */
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
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
	 * @return the dataCriacao
	 */
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idConta, idPessoa);
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
		ContaDTO other = (ContaDTO) obj;
		return Objects.equals(idConta, other.idConta) && Objects.equals(idPessoa, other.idPessoa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("ContaDTO [idConta=");
		builder.append(idConta);
		builder.append(", idPessoa=");
		builder.append(idPessoa);
		builder.append(", saldo=");
		builder.append(saldo);
		builder.append(", limiteSaqueDiario=");
		builder.append(limiteSaqueDiario);
		builder.append(", tipoConta=");
		builder.append(tipoConta);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append("]");
		return builder.toString();
	}

}
