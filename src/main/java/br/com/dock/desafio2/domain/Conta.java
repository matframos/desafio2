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
import javax.persistence.Version;

/**
 * Entidade que representa a tabela desafio2.conta.
 *
 * @author matramos
 * @since 1.0
 */
@Entity
@Table(schema = "desafio2", name = "conta")
public final class Conta implements Serializable {

	private static final long serialVersionUID = 4514258986378891796L;

	@Id
	@SequenceGenerator(schema = "desafio2", name = "seq_conta", sequenceName = "seq_conta", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta")
    @Column(name = "id_conta", nullable = false, updatable = false, insertable = false)
	private Integer idConta;

	@Column(name = "id_pessoa", nullable = false, updatable = false)
	private Integer idPessoa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", nullable = false, updatable = false, insertable = false)
    private Pessoa pessoa;

	@Column(name = "saldo", nullable = false, updatable = true)
	private BigDecimal saldo;

	@Column(name = "limite_saque_diario", nullable = false, updatable = true)
	private BigDecimal limiteSaqueDiario;

	@Column(name = "flag_ativo", nullable = false, updatable = true)
	private Boolean flagAtivo;

	@Column(name = "tipo_conta", nullable = false, updatable = false)
	private Integer tipoConta;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDate dataCriacao;

    @Version
    private Integer versao;

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
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
	 * @return the flagAtivo
	 */
	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	/**
	 * @param flagAtivo the flagAtivo to set
	 */
	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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
	 * @return the versao
	 */
	public Integer getVersao() {
		return versao;
	}

	/**
	 * @param versao the versao to set
	 */
	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idConta);
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
		Conta other = (Conta) obj;
		return Objects.equals(idConta, other.idConta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("Conta [idConta=");
		builder.append(idConta);
		builder.append(", idPessoa=");
		builder.append(idPessoa);
		builder.append(", pessoa=");
		builder.append(pessoa);
		builder.append(", saldo=");
		builder.append(saldo);
		builder.append(", limiteSaqueDiario=");
		builder.append(limiteSaqueDiario);
		builder.append(", flagAtivo=");
		builder.append(flagAtivo);
		builder.append(", tipoConta=");
		builder.append(tipoConta);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append(", versao=");
		builder.append(versao);
		builder.append("]");
		return builder.toString();
	}

}
