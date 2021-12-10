package br.com.dock.desafio2.service;

import org.springframework.stereotype.Component;

import br.com.dock.desafio2.domain.Pessoa;
import br.com.dock.desafio2.repository.PessoaRepository;

/**
 * Implementaçao de {@link PessoaService} para abstrair a gestão dos
 * dados de {@link Pessoa}.
 *
 * @author matramos
 * @since 1.0.0
 */
@Component
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	/**
	 * Construtor padrão.
	 *
	 * @param pessoaRepository the pessoaRepository to set
	 */
	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pessoa getPessoaPorCpf(String cpf) {
		return this.pessoaRepository.getPessoaPorCpf(cpf);
	}

}
