package br.com.dock.desafio2.service;

import br.com.dock.desafio2.domain.Pessoa;

/**
 * Interface disponibilizada para o gerenciamento das informaçoes de pessoas.
 *
 * @author matramos
 * @since 1.0
 */
public interface PessoaService {

	/**
	 * Recupera uma {@link Pessoa} a partir do cpf.
	 *
	 * @param cpf
	 * @return {@link Pessoa}
	 */
	Pessoa getPessoaPorCpf(String cpf);

}
