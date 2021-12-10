package br.com.dock.desafio2.controller.handler;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Implementaçao customizada de para facilitar e abstrair a utilizaçao de {@link MessageSource}.
 *
 * @author matramos
 * @since 1.0
 */
@Component
public final class CustomMessageSource {

	private MessageSource messageSource;

	/**
	 * Recupera a mensagem formatada dentre as propriedades a partir
	 * do codigo informado.
	 *
	 * @param code
	 * @return message
	 */
	public String getMessage(String code) {
		return this.messageSource.getMessage(code, null, Locale.getDefault());
	}

	/**
	 * Recupera a mensagem formatada dentre as propriedades a partir
	 * do codigo informado.
	 *
	 * @param code
	 * @param args
	 * @return message
	 */
	public String getMessage(String code, Object... args) {
		return this.messageSource.getMessage(code, args, Locale.getDefault());
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
