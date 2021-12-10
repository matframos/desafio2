package br.com.dock.desafio2.controller.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.com.dock.desafio2.controller.domain.ErrorDTO;
import br.com.dock.desafio2.exception.ClienteNaoEncontradoException;
import br.com.dock.desafio2.exception.ContaNaoEncontradaException;
import br.com.dock.desafio2.exception.LimiteSaqueException;
import br.com.dock.desafio2.exception.SaldoIndisponivelException;

/**
 * {@code Advice} para realizar o mapeamento, transformação e controle
 * dos erros encontrados durante o processamento das requisiçoes e os retornos
 * adequados para os usuarios.
 *
 * @author matramos
 * @since 1.0
 */
@ControllerAdvice
public final class CustomResponseEntityExceptionHandler {

	private ErrorResponseResolver errorResponseResolver;
	private CustomMessageSource messageSource;

	/**
	 * Realiza a tratativa da {@code exception} {@link NoHandlerFoundException}.
	 *
	 * @param response
	 * @return {@link ErrorDTO}
	 */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundException(NoHandlerFoundException anee) {
    	return this.messageSource.getMessage("message.handler_nao_encontrado");
    }

	/**
	 * Realiza a tratativa da {@code exception} {@link ClienteNaoEncontradoException}.
	 *
	 * @param response
	 * @return {@link ErrorDTO}
	 */
    @ResponseBody
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public String clienteNaoEncontradoException(ClienteNaoEncontradoException cnee) {
    	return this.messageSource.getMessage("message.cliente_nao_encontrado.cpf", cnee.getCpf());
    }

	/**
	 * Realiza a tratativa da {@code exception} {@link ContaNaoEncontradaException}.
	 *
	 * @param response
	 * @return {@link ErrorDTO}
	 */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public String contaNaoEncontradaException(ContaNaoEncontradaException cnee) {
    	return this.messageSource.getMessage("message.conta_nao_encontrada.idConta", cnee.getIdConta());
    }

    /**
     * Realiza a tratativa da {@code exception} {@link ConstraintViolationException}.
     *
     * @param cve
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErrorDTO> constraintViolationException(ConstraintViolationException cve) {
    	return cve.getConstraintViolations()
    			.stream()
    			.map(this.errorResponseResolver::getErrorResponse)
    			.collect(Collectors.toList());
    }

    /**
     * Realiza a tratativa da {@code exception} {@link MethodArgumentNotValidException}.
     *
     * @param manve
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException manve) {
    	return manve.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this.errorResponseResolver::getErrorResponse)
                .collect(Collectors.toList());
    }

    /**
     * Realiza a tratativa da {@code exception} {@link MethodArgumentTypeMismatchException}.
     *
     * @param matme
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public List<ErrorDTO> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme) {
    	return this.getErrorResponse("error.field.tipo.incorreto", matme.getValue(), matme.getName());
    }

    /**
     * Realiza a tratativa da {@code exception} {@link LimiteSaqueException}.
     *
     * @param matme
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LimiteSaqueException.class)
    public List<ErrorDTO> limiteSaqueException(LimiteSaqueException lse) {
    	return this.getErrorResponse("message.limite_saque_atingido", lse.getValor());
    }

    /**
     * Realiza a tratativa da {@code exception} {@link SaldoIndisponivelException}.
     *
     * @param matme
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SaldoIndisponivelException.class)
    public List<ErrorDTO> saldoIndisponivelException(SaldoIndisponivelException sie) {
    	return this.getErrorResponse("message.saldo_indisponivel");
    }

    /**
     * Realiza a tratativa da {@code exception} {@link ObjectOptimisticLockingFailureException}.
     *
     * @param matme
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public List<ErrorDTO> objectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException sie) {
    	return this.getErrorResponse("message.operacoes_simultaneas.pf");
    }

    /**
     * Realiza a tratativa da {@code exception} {@link MissingServletRequestParameterException}.
     *
     * @param msrpe
     * @return {@link ErrorDTO}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public List<ErrorDTO> missingServletRequestParameterException(MissingServletRequestParameterException msrpe) {
    	return this.getErrorResponse("message.parametro_obrigatorio", msrpe.getParameterName());
    }

    /**
     * Realiza a formatação do retorno para o objeto {@link ErrorDTO}
     * a partir de um template de mensagem de erro.
     *
     * @param templateMensagem
     * @param campos
     * @return {@link ErrorDTO}
     */
    private List<ErrorDTO> getErrorResponse(String templateMensagem, Object... campos) {
    	var mensagem = this.messageSource.getMessage(templateMensagem, campos);
    	var error = this.errorResponseResolver.getErrorResponse(mensagem);
    	return Arrays.asList(error);
    }

	/**
	 * @param errorResponseResolver the errorResponseResolver to set
	 */
    @Autowired
    public void setErrorResponseResolver(ErrorResponseResolver errorResponseResolver) {
		this.errorResponseResolver = errorResponseResolver;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
    @Autowired
	public void setMessageSource(CustomMessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
