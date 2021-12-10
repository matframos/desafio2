package br.com.dock.desafio2.domain;

import java.util.Arrays;

/**
 * {@code Enum} que representa os dados de tipos de transações.
 *
 * @author matramos
 * @since 1.0.0
 */
public enum TipoTransacao {

	DEPOSITO	(1),
	SAQUE 		(2);

	private final Integer idTipoTransacao;

	/**
	 * Construtor padrão.
	 *
	 * @param idTipoTransacao
	 */
	private TipoTransacao(Integer idTipoTransacao) {
		this.idTipoTransacao = idTipoTransacao;
	}

	/**
	 * @return the idTipoTransacao
	 */
	public Integer getIdTipoTransacao() {
		return idTipoTransacao;
	}

	/**
	 * Retorna uma instancia de {@link TipoTransacao}.
	 *
	 * @param idTipoTransacao
	 * @return {@link TipoTransacao}
	 */
	public static TipoTransacao of(Integer idTipoTransacao) {
		return Arrays.stream(values())
			.filter(t -> t.getIdTipoTransacao().equals(idTipoTransacao))
			.findFirst()
			.orElse(null);
	}

}
