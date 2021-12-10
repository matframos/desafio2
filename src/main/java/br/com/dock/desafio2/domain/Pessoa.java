package br.com.dock.desafio2.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela desafio2.pessoa.
 *
 * @author matramos
 * @since 1.0
 */
@Entity
@Table(schema = "desafio2", name = "pessoa")
public final class Pessoa implements Serializable {

	private static final long serialVersionUID = -8363270879694778835L;

	@Id
    @Column(name = "id_pessoa", nullable = false, updatable = false)
	private Integer idPessoa;

	@Column(name = "nome", nullable = false, updatable = false)
	private String nome;

	@Column(name = "cpf", nullable = false, updatable = false)
	private String cpf;

	@Column(name = "data_nascimento", nullable = false, updatable = false)
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Conta> contas;

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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

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
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the contas
	 */
	public List<Conta> getContas() {
		return contas;
	}

	/**
	 * @param contas the contas to set
	 */
	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idPessoa);
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
		Pessoa other = (Pessoa) obj;
		return Objects.equals(idPessoa, other.idPessoa);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("Pessoa [idPessoa=");
		builder.append(idPessoa);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", cpf=");
		builder.append(cpf);
		builder.append(", dataNascimento=");
		builder.append(dataNascimento);
		builder.append("]");
		return builder.toString();
	}

}
