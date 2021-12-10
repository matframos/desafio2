package br.com.dock.desafio2.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dock.desafio2.controller.domain.OperacaoDTO;
import br.com.dock.desafio2.controller.domain.TransacaoDTO;
import br.com.dock.desafio2.controller.handler.CustomMessageSource;
import br.com.dock.desafio2.controller.mapper.TransacaoMapper;
import br.com.dock.desafio2.service.TransacaoService;

/**
 * {@code Controller} que disponibiliza os recursos do {@code path} /transacoes
 * da API de Gestão de Transações.
 *
 * @author matramos
 * @since 1.0
 */
@Validated
@RestController
@RequestMapping(path = "/gestao/v1/transacoes")
public class TransacaoController {

	private TransacaoService transacaoService;
	private TransacaoMapper transacaoMapper;
	private CustomMessageSource messageSource;

    /**
     * Realiza uma operação de deposito em uma conta.
     *
     * @param idConta
     * @param operacao
     * @return no_content
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{idConta}/deposito", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deposito(@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta,
    		@Valid @RequestBody OperacaoDTO operacao) {
    	this.transacaoService.deposito(idConta, operacao);
    	return this.messageSource.getMessage("message.deposito_sucesso");
    }

    /**
     * Realiza uma operação de saque em uma conta.
     *
     * @param idConta
     * @param operacao
     * @return no_content
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{idConta}/saque", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saque(@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta,
    		@Valid @RequestBody OperacaoDTO operacao) {
    	this.transacaoService.saque(idConta, operacao);
    	return this.messageSource.getMessage("message.saque_sucesso");
    }

    /**
     * Realiza a busca do extrato do cliente em um periodo especifico.
     *
     * @param idConta
     * @param dataInicial
     * @return ok
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{idConta}/extrato", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransacaoDTO> getExtratoPorPeriodo(
    		@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta,
    		@RequestParam("dataInicial")
    		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
    		@RequestParam("dataFinal")
    		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

    	var transacoes = this.transacaoService.getTransacoesPorPeriodo(idConta, dataInicial, dataFinal);
    	return this.transacaoMapper.transacaoToTransacaoDTO(transacoes);
    }

	/**
	 * @param transacaoService the transacaoService to set
	 */
	@Autowired
	public void setTransacaoService(TransacaoService transacaoService) {
		this.transacaoService = transacaoService;
	}

	/**
	 * @param transacaoMapper the transacaoMapper to set
	 */
	@Autowired
	public void setTransacaoMapper(TransacaoMapper transacaoMapper) {
		this.transacaoMapper = transacaoMapper;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(CustomMessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
