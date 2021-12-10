package br.com.dock.desafio2.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dock.desafio2.controller.domain.ContaDTO;
import br.com.dock.desafio2.controller.domain.ErrorDTO;
import br.com.dock.desafio2.controller.domain.NovaContaDTO;
import br.com.dock.desafio2.controller.domain.SaldoDTO;

/**
 * Classe responsavel pelos testes dos recursos de {@link ContaController}.
 *
 * @author matramos
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts = {"/create-tables.sql", "/fill-tables.sql"}),
	@Sql(scripts = {"/drop-tables.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@ActiveProfiles("test")
final class ContaControllerTest {

	private final String basePath = "/gestao/v1/contas";

    @Autowired
    private TestRestTemplate restTemplate;

    private NovaContaDTO novaConta;

    /**
     * Setup inicial.
     */
    @BeforeEach
    public void init() {
    	NovaContaDTO novaConta = new NovaContaDTO();
    	novaConta.setCpf("65961038033");
    	novaConta.setLimiteSaqueDiario(BigDecimal.ONE);
    	novaConta.setTipoConta(3);
    	this.novaConta = novaConta;
    }

    /**
     * Testa o método {@link ContaController#criarConta(br.com.dock.desafio2.controller.domain.NovaContaDTO)}.
     */
    @Test
    void criarConta() {
        ResponseEntity<ContaDTO> response = this.restTemplate.postForEntity(this.basePath, this.novaConta, ContaDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ContaDTO body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, body.getIdConta());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, body.getIdPessoa());
        Assertions.assertEquals(3, body.getTipoConta());
        Assertions.assertEquals(LocalDate.now(), body.getDataCriacao());
        Assertions.assertEquals(BigDecimal.ONE, body.getLimiteSaqueDiario());
        Assertions.assertEquals(BigDecimal.ZERO, body.getSaldo());
    }

    /**
     * Testa o método {@link ContaController#criarConta(br.com.dock.desafio2.controller.domain.NovaContaDTO)}.
     */
    @Test
    void criarConta_PessoaNaoEncontrada() {
    	this.novaConta.setCpf("72764439067");
        ResponseEntity<String> response = this.restTemplate.postForEntity(this.basePath, this.novaConta, String.class);

        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

        String body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals("Cliente 72764439067 nao encontrado", body);
    }

    /**
     * Testa o método {@link ContaController#criarConta(br.com.dock.desafio2.controller.domain.NovaContaDTO)}.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void criarConta_DadosInvalidos() throws JsonMappingException, JsonProcessingException {
    	NovaContaDTO novaConta = new NovaContaDTO();
    	novaConta.setCpf("123");
    	novaConta.setLimiteSaqueDiario(BigDecimal.valueOf(-1));
    	novaConta.setTipoConta(1);

        ResponseEntity<String> response = this.restTemplate.postForEntity(this.basePath, novaConta, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String body = response.getBody();
        Assertions.assertNotNull(body);

        ObjectMapper objectMapper = new ObjectMapper();
        List<ErrorDTO> errors = objectMapper.readValue(body, new TypeReference<List<ErrorDTO>>(){});

        Assertions.assertTrue(errors.contains(ErrorDTO.of("400.001", "CPF: formato invalido")));
        Assertions.assertTrue(errors.contains(ErrorDTO.of("400.001", "Campo limiteSaqueDiario deve ser maior ou igual a 0")));
    }

    /**
     * Testa o método {@link ContaController#getContaPorId(Integer)}
     */
    @Test
    void getContaPorId() {
        this.restTemplate.postForEntity(this.basePath, this.novaConta, ContaDTO.class);

    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	ResponseEntity<ContaDTO> response = this.restTemplate.getForEntity(uri, ContaDTO.class);
        ContaDTO conta = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, conta.getIdConta());
        Assertions.assertEquals(NumberUtils.INTEGER_ONE, conta.getIdPessoa());
        Assertions.assertEquals(Integer.valueOf(3), conta.getTipoConta());
    }

    /**
     * Testa o método {@link ContaController#getContaPorId(Integer)}
     */
    @Test
    void getContaPorId_NaoEncontrada() {
    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

        ResponseEntity<String> response = this.restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Conta 1 nao encontrada", response.getBody());
    }

    /**
     * Testa o método {@link ContaController#getSaldoPorConta(Integer)}
     */
    @Test
    void getSaldoPorConta() {
    	this.restTemplate.postForEntity(this.basePath, this.novaConta, ContaDTO.class);

    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/saldo")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	ResponseEntity<SaldoDTO> response = this.restTemplate.getForEntity(uri, SaldoDTO.class);
    	Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    	SaldoDTO saldo = response.getBody();
        Assertions.assertEquals(new BigDecimal("0.00000"), saldo.getSaldo());
    }

    /**
     * Testa o método {@link ContaController#getSaldoPorConta(Integer)}
     */
    @Test
    void getSaldoPorConta_ContaNaoEncontrada() {
    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/saldo")
            	.buildAndExpand(NumberUtils.INTEGER_ONE)
            	.toUri();

    	ResponseEntity<String> response = this.restTemplate.getForEntity(uri, String.class);

    	Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Conta 1 nao encontrada", response.getBody());
    }

    /**
     * Testa o método {@link ContaController#getSaldoPorConta(Integer)}
     */
    @Test
    void getSaldoPorConta_TipoInvalido() {
    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}/saldo")
            	.buildAndExpand("X")
            	.toUri();

        ResponseEntity<ErrorDTO[]> response = this.restTemplate.getForEntity(uri, ErrorDTO[].class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDTO[] errors = response.getBody();
        Assertions.assertTrue(ArrayUtils.isNotEmpty(errors));

        ErrorDTO error = errors[0];
        Assertions.assertEquals("400.001", error.getCode());
        Assertions.assertEquals("Valor X invalido para o campo idConta", error.getDescription());
    }

    /**
     * Testa o método {@link ContaController#desativarConta(Integer)}
     */
    @Test
    void inativarConta() {
        this.restTemplate.postForEntity(this.basePath, this.novaConta, ContaDTO.class);

    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}")
            	.buildAndExpand(Integer.valueOf(1))
            	.toUri();

    	RequestEntity<Void> request = new RequestEntity<Void>(HttpMethod.DELETE, uri);
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Conta 1 inabilitada com sucesso", response.getBody());
    }

    /**
     * Testa o método {@link ContaController#desativarConta(Integer)}
     */
    @Test
    void inativarConta_NaoEncontrada() {
    	URI uri = UriComponentsBuilder.newInstance()
            	.host(this.basePath)
            	.path("/{idConta}")
            	.buildAndExpand(Integer.valueOf(1))
            	.toUri();

    	RequestEntity<Void> request = new RequestEntity<Void>(HttpMethod.DELETE, uri);
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Conta 1 nao encontrada", response.getBody());
    }

}
