package br.com.dock.desafio2.exception;

/**
 * {@code Exception} para identificar quando uma conta não
 * for encontrada.
 *
 * @author matramos
 * @since 1.0
 */
public final class ContaNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 455534028384707120L;
	private final Integer idConta;

	/**
	 * Construtor padrão.
	 *
	 * @param idConta
	 */
	public ContaNaoEncontradaException(Integer idConta) {
		this.idConta = idConta;
	}

	/**
	 * @return the idConta
	 */
	public Integer getIdConta() {
		return idConta;
	}

}
