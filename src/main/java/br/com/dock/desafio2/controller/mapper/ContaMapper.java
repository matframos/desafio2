package br.com.dock.desafio2.controller.mapper;

import org.mapstruct.Mapper;

import br.com.dock.desafio2.controller.domain.ContaDTO;
import br.com.dock.desafio2.domain.Conta;

/**
 * {@code Mapper} para realizar a conversão entre os objetos {@link Conta} e
 * {@link ContaDTO}.
 *
 * @author matramos
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface ContaMapper {

	/**
	 * Realiza a conversao de {@link Conta} para {@link ContaDTO}.
	 *
	 * @param conta
	 * @return {@link ContaDTO}
	 */
	ContaDTO contaToContaDTO(Conta conta);

}
