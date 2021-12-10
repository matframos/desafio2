package br.com.dock.desafio2.controller.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe disponibilizada para encapsular as informaçoes de um erro.
 *
 * @author matramos
 * @since 1.0
 */
public final class ErrorDTO implements Serializable {

	private static final long serialVersionUID = 6661098288012673555L;

	private String code;
    private String description;

    /**s
     * Construtor padrão.
     */
    public ErrorDTO() {
	}

    /**
     * Construtor padrao.
     *
     * @param codesss
     * @param description
     */
    public ErrorDTO(String code, String description) {
    	this.code = code;
    	this.description = description;
	}

    /**
     * Retorna uma instancia de {@link ErrorDTO}.
     *
     * @param code
     * @param description
     * @return {@link ErrorDTO}
     */
    public static ErrorDTO of(String code, String description) {
    	return new ErrorDTO(code, description);
    }

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code, description);
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
		ErrorDTO other = (ErrorDTO) obj;
		return Objects.equals(code, other.code) && Objects.equals(description, other.description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("ErrorDTO [code=");
		builder.append(code);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
