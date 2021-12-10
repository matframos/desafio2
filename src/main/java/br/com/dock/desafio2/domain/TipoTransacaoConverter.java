package br.com.dock.desafio2.domain;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Conversor responsavel por mapear os dados do campo id_tipo_transacao
 * para o {@code Enum} {@link TipoTransacao}.
 *
 * @author matramos
 * @since 1.0.0
 */
@Converter(autoApply = true)
public class TipoTransacaoConverter implements AttributeConverter<TipoTransacao, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer convertToDatabaseColumn(TipoTransacao attribute) {
		return Objects.nonNull(attribute) ? attribute.getIdTipoTransacao() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipoTransacao convertToEntityAttribute(Integer dbData) {
		return TipoTransacao.of(dbData);
	}

}
