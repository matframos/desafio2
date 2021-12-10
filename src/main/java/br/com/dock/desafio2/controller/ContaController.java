package br.com.dock.desafio2.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dock.desafio2.controller.domain.ContaDTO;
import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.SaldoDTO;
import br.com.dock.desafio2.controller.handler.CustomMessageSource;
import br.com.dock.desafio2.controller.mapper.ContaMapper;
import br.com.dock.desafio2.service.ContaService;

/**
 * {@code Controller} que disponibiliza os recursos do {@code path} /contas
 * da API de Gestão de Contas.
 *
 * @author matramos
 * @since 1.0
 */
@Validated
@RestController
@RequestMapping(path = "/gestao/v1/contas")
public class ContaController {

    private ContaService contaService;
    private ContaMapper contaMapper;
    private CustomMessageSource messageSource;

    /**
     * Recupera uma conta pelo ID.
     *
     * @param idConta
     * @return {@link ContaDTO}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{idConta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContaDTO getContaPorId(
    		@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta) {
    	var conta = this.contaService.getContaPorId(idConta);
        return this.contaMapper.contaToContaDTO(conta);
    }

    /**
     * Recupera uma conta pelo ID.
     *
     * @param idConta
     * @return {@link SaldoDTO}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{idConta}/saldo", produces = MediaType.APPLICATION_JSON_VALUE)
    public SaldoDTO getSaldoPorConta(
    		@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta) {
    	var saldo = this.contaService.getSaldoPorIdConta(idConta);
    	return new SaldoDTO(saldo);
    }

    /**
     * Realiza a criação de uma conta na base de dados.
     *
     * @param conta
     * @return created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContaDTO criarConta(@Valid @RequestBody NovaContaDTO novaConta) {
    	var conta = this.contaService.criarConta(novaConta);
    	return this.contaMapper.contaToContaDTO(conta);
    }

    /**
     * Realiza a inativção de uma conta.
     *
     * @param idConta
     * @return ok
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{idConta}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String desativarConta(
    		@PathVariable
    		@NotNull(message = "{error.field.conta.idConta.NotNull}") Integer idConta) {
    	this.contaService.inativarConta(idConta);
    	return this.messageSource.getMessage("message.conta_inabilitada_sucesso", idConta);
    }

    /**
     * @param contaService the contaService to set
     */
    @Autowired
    public void setContaService(ContaService contaService) {
		this.contaService = contaService;
	}

    /**
     * @param contaMapper the contaService to set
     */
    @Autowired
    public void setContaMapper(ContaMapper contaMapper) {
		this.contaMapper = contaMapper;
	}

    /**
     * @param messageSource the messageSource to set
     */
    @Autowired
    public void setMessageSource(CustomMessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
