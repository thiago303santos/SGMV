package sgmv.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Manutencao;
import sgmv.demo.model.ManutencaoAgendada;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    @Query("SELECT DISTINCT m FROM Manutencao m " +
        "LEFT JOIN FETCH m.cliente c " +
        "LEFT JOIN FETCH m.veiculo v " +
        "LEFT JOIN FETCH v.tipo vt " +
        "LEFT JOIN FETCH m.funcionario f " +
        "LEFT JOIN FETCH m.usuarioResponsavel ur " +
        "LEFT JOIN FETCH m.funcionarioExecutor fe " +
        "LEFT JOIN FETCH m.servicos ms " +
        "LEFT JOIN FETCH m.pecas mp " +
        "LEFT JOIN FETCH mp.produto p " +
        "LEFT JOIN FETCH p.categoria pc " +
        "WHERE m.id = :id")
    Optional<Manutencao> findByIdWithDetails(@Param("id") Long id);

    @Query(value = "SELECT " +
                "  MONTHNAME(m.data_entrada) AS month, " +
                "  YEAR(m.data_entrada) AS year, " +
                "  SUM(m.vl_total) AS total " +
                "FROM tb_manutencao m " +
                "WHERE m.data_entrada >= DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH) " +
                "GROUP BY month, year " +
                "ORDER BY year, month", nativeQuery = true) // Usar nativeQuery=true para SQL puro
    List<Object[]> findTotalValueLastSixMonths();

    List<Manutencao> findByStatus(String status);

    @Query("SELECT m FROM Manutencao m WHERE m.dataConclusao <= :dataAtual AND m.status NOT IN ('CONCLUIDA', 'CANCELADA')")
    List<Manutencao> findManutencoesAtrasadas(@Param("dataAtual") LocalDate dataAtual);
}