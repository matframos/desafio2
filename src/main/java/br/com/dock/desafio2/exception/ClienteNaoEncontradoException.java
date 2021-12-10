package br.com.dock.desafio2.exception;

/**
 * {@code Exception} para identificar quando um cliente não
 * for encontrado.
 *
 * @author matramos
 * @since 1.0
 */
public final class ClienteNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 608582990276956068L;
	private final String cpf;

	/**
	 * Construtor padrao.
	 *
	 * @param idPessoa
	 */
    public ClienteNaoEncontradoException(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
		return cpf;
	}

}