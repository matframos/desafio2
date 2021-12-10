package br.com.dock.desafio2.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.dock.desafio2.domain.Conta;

/**
 * Implementaçao de {@link JpaRepository} para gerenciar as informaçoes
 * da entidade {@link Conta} - Tabela desafio2.conta.
 *
 * @author matramos
 * @since 1.0
 */
public interface ContaRepository extends JpaRepository<Conta, Integer> {

	/**
	 * Método utilizado para realizar a operação de acrescimo de valores
	 * no saldo do cliente.
	 *
	 * @param idConta
	 * @param operacao
	 */
	@Modifying
	@Query("UPDATE Conta c set c.saldo = :valor WHERE c.idConta = :idConta")
	void atualizarSaldo(Integer idConta, BigDecimal valor);

}
