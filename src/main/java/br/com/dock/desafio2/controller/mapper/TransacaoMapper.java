package br.com.dock.desafio2.controller.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.dock.desafio2.controller.domain.TipoTransacaoDTO;
import br.com.dock.desafio2.controller.domain.TransacaoDTO;
import br.com.dock.desafio2.domain.TipoTransacao;
import br.com.dock.desafio2.domain.Transacao;

/**
 * {@code Mapper} para realizar a conversão entre os objetos {@link Transacao} e
 * {@link TransacaoDTO}.
 *
 * @author matramos
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface TransacaoMapper {

	/**
	 * Realiza a conversão de {@link Transacao} para {@link TransacaoDTO}.
	 *
	 * @param transacao
	 * @return {@link TransacaoDTO}
	 */
	@Mapping(source = "tipoTransacao", target = "tipoTransacao", qualifiedByName = "toTipoTransacaoDTO")
	TransacaoDTO transacaoToTransacaoDTO(Transacao transacao);

	/**
	 * Realiza a conversão de {@link Transacao} para {@link TransacaoDTO}.
	 *
	 * @param transacao
	 * @return {@link TransacaoDTO}
	 */
	List<TransacaoDTO> transacaoToTransacaoDTO(List<Transacao> transacao);

	/**
	 * Converte {@link TipoTransacao} para {@link TipoTransacaoDTO}.
	 *
	 * @param tipoTransacao
	 * @return {@link TipoTransacaoDTO}
	 */
    @Named("toTipoTransacaoDTO")
    public static TipoTransacaoDTO toTipoTransacaoDTO(TipoTransacao tipoTransacao) {
        return TipoTransacaoDTO.valueOf(tipoTransacao.name());
    }

}
