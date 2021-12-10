package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code DTO} para representar os dados das transacoes.
 *
 * @author matramos
 * @since 1.0
 */
public final class TransacaoDTO implements Serializable {

	private static final long serialVersionUID = -7665438708576901388L;

	private Integer idTransacao;
	private Integer idConta;
	private BigDecimal valor;
	private LocalDate dataTransacao;
	private TipoTransacaoDTO tipoTransacao;

	/**
	 * @return the idTransacao
	 */
	public Integer getIdTransacao() {
		return idTransacao;
	}

	/**
	 * @param idTransacao the idTransacao to set
	 */
	public void setIdTransacao(Integer idTransacao) {
		this.idTransacao = idTransacao;
	}

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
	 * @return the dataTransacao
	 */
	public LocalDate getDataTransacao() {
		return dataTransacao;
	}

	/**
	 * @param dataTransacao the dataTransacao to set
	 */
	public void setDataTransacao(LocalDate dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	/**
	 * @return the tipoTransacao
	 */
	public TipoTransacaoDTO getTipoTransacao() {
		return tipoTransacao;
	}

	/**
	 * @param tipoTransacao the tipoTransacao to set
	 */
	public void setTipoTransacao(TipoTransacaoDTO tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idTransacao);
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
		TransacaoDTO other = (TransacaoDTO) obj;
		return Objects.equals(idTransacao, other.idTransacao);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("TransacaoDTO [idTransacao=");
		builder.append(idTransacao);
		builder.append(", idConta=");
		builder.append(idConta);
		builder.append(", valor=");
		builder.append(valor);
		builder.append(", dataTransacao=");
		builder.append(dataTransacao);
		builder.append(", tipoTransacao=");
		builder.append(tipoTransacao);
		builder.append("]");
		return builder.toString();
	}

}
