package br.com.dock.desafio2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dock.desafio2.domain.Pessoa;

/**
 * Implementaçao de {@link JpaRepository} para gerenciar as informaçoes
 * da entidade {@link Pessoa} - Tabela desafio2.pessoa.
 *
 * @author matramos
 * @since 1.0
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	/**
	 * Retorna uma {@link Pessoa} a partir de um cpf.
	 *
	 * @param cpf
	 * @return {@link Pessoa}
	 */
	@Query(value = "SELECT p FROM Pessoa p WHERE p.cpf = :cpf")
	Pessoa getPessoaPorCpf(String cpf);

}
