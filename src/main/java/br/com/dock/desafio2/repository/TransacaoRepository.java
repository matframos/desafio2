package br.com.dock.desafio2.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dock.desafio2.domain.TipoTransacao;
import br.com.dock.desafio2.domain.Transacao;

/**
 * Implementaçao de {@link JpaRepository} para gerenciar as informaçoes
 * da entidade {@link Transacao} - Tabela desafio2.transacao.
 *
 * @author matramos
 * @since 1.0.0
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

	/**
	 * Retorna o valor total em transaçoes realizadas por um cliente
	 * em um dia especifico.
	 *
	 * @param idConta
	 * @param data
	 * @return valor total
	 */
	@Query("SELECT SUM(t.valor) FROM Transacao t "
			+ "WHERE t.idConta = :idConta "
			+ "AND t.dataTransacao = :data "
			+ "AND t.tipoTransacao = :tipoTransacao ")
	BigDecimal getValorTransacoesPorDia(Integer idConta, LocalDate data, TipoTransacao tipoTransacao);

	/**
	 * Recupera a lista de transacoes de um cliente em um determinado periodo.
	 *
	 * @param idConta
	 * @param dataInicial
	 * @param dataFinal
	 * @return {@link Transacao}
	 */
	@Query("SELECT t FROM Transacao t "
			+ "WHERE t.idConta = :idConta "
			+ "AND t.dataTransacao BETWEEN :dataInicial AND :dataFinal ")
	List<Transacao> getTransacoesPorPeriodo(Integer idConta, LocalDate dataInicial, LocalDate dataFinal);

}
