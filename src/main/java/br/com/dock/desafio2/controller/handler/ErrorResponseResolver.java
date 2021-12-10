package br.com.dock.desafio2.controller.handler;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import br.com.dock.desafio2.controller.domain.ErrorDTO;

/**
 * Classe para realizar as conversões adequadas para retorno do objeto {@link ErrorDTO}
 * a partir das exceções propagadas pela aplicaçao.
 *
 * @author matramos
 * @since 1.0
 */
@Component
public final class ErrorResponseResolver {

	private CustomMessageSource messageSource;
	private String errorSeparator;

	/**
	 * Setup auxiliar pós contruçao do objeto.
	 */
	@PostConstruct
	void postConstruct() {
		this.errorSeparator = this.messageSource.getMessage("error.separator");
	}

    /**
     * Retorna uma instancia de {@link ErrorDTO} a partir de {@link FieldError}.
     *
     * @param fieldError
     * @return {@link ErrorDTO}
     */
    public ErrorDTO getErrorResponse(FieldError fieldError) {
    	return this.getErrorResponse(
    			fieldError.getDefaultMessage(),
    			fieldError.getField(),
    			fieldError.getRejectedValue());
    }

    /**
     * Retorna uma instancia de {@link ErrorDTO} a partir de {@link ConstraintViolation}.
     *
     * @param cv
     * @return {@link ErrorDTO}
     */
    public ErrorDTO getErrorResponse(ConstraintViolation<?> cv) {
    	NodeImpl node = ((PathImpl) cv.getPropertyPath()).getLeafNode();
    	return this.getErrorResponse(cv.getMessage(), node.getName(), cv.getInvalidValue());
    }

    /**
     * Retorna uma instancia de {@link ErrorDTO}.
     *
     * @param message
     * @return {@link ErrorDTO}
     */
    public ErrorDTO getErrorResponse(String message) {
    	String[] messages = message.split(this.errorSeparator);
    	return ErrorDTO.of(messages[0], messages[1]);
    }

    /**
     * Retorna uma instancia de {@link ErrorDTO}.
     *
     * @param message
     * @param field
     * @param rejectedValue
     * @return {@link ErrorDTO}
     */
    public ErrorDTO getErrorResponse(String message, String field, Object rejectedValue) {
    	String[] messages = message.split(this.errorSeparator);
    	var description = String.format(messages[1], field, rejectedValue);
    	return ErrorDTO.of(messages[0], description);
    }

	/**
	 * @param messageSource the messageSource to set
	 */
    @Autowired
	public void setMessageSource(CustomMessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
