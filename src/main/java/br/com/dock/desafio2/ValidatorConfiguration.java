package br.com.dock.desafio2;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * {@code Configuration} para disponibilizar as instancias das
 * implementaçoes especificas de {@code ResourceBundle} e arquivos de
 * mensagens insternacionalizadas.
 *
 * @author matramos
 * @since 1.0
 */
@Configuration
public class ValidatorConfiguration {

	/**
	 * Retorna uma instancia de {@link MessageSource}.
	 *
	 * @return {@link MessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
	    var rrbms = new ReloadableResourceBundleMessageSource();
	    rrbms.setBasename("classpath:messages");
	    rrbms.setDefaultEncoding(StandardCharsets.UTF_8.toString());
	    return rrbms;
	}

	/**
	 * Retorna uma instancia de {@link LocalValidatorFactoryBean}.
	 *
	 * @param messageSource
	 * @return {@link LocalValidatorFactoryBean}
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
	    var bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource);
	    bean.afterPropertiesSet();
	    return bean;
	}

}
