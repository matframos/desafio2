package br.com.dock.desafio2.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela desafio2.transacao.
 *
 * @author matramos
 * @since 1.0
 */
@Entity
@Table(schema = "desafio2", name = "transacao")
public final class Transacao implements Serializable {

	private static final long serialVersionUID = -8119200533327925938L;

	@Id
	@SequenceGenerator(schema = "desafio2", name = "seq_transacao", sequenceName = "seq_transacao", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transacao")
    @Column(name = "id_transacao", nullable = false, updatable = false, insertable = false)
	private Integer idTransacao;

    @Column(name = "id_conta", nullable = false, updatable = false)
	private Integer idConta;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_conta", nullable = false, updatable = false, insertable = false)
	private Conta conta;

	@Column(name = "valor", nullable = false, updatable = false)
	private BigDecimal valor;

	@Column(name = "data_transacao", nullable = false, updatable = false)
	private LocalDate dataTransacao;

	@Column(name = "id_tipo_transacao", nullable = false, updatable = false)
	private TipoTransacao tipoTransacao;

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
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
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
	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	/**
	 * @param tipoTransacao the tipoTransacao to set
	 */
	public void setTipoTransacao(TipoTransacao tipoTransacao) {
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
		Transacao other = (Transacao) obj;
		return Objects.equals(idTransacao, other.idTransacao);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("Transacao [idTransacao=");
		builder.append(idTransacao);
		builder.append(", idConta=");
		builder.append(idConta);
		builder.append(", conta=");
		builder.append(conta);
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
