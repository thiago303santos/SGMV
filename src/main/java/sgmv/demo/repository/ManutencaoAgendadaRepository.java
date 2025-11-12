package sgmv.demo.repository;

import sgmv.demo.model.ManutencaoAgendada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ManutencaoAgendadaRepository extends JpaRepository<ManutencaoAgendada, Long> {

    /**
     * Busca agendamentos PENDENTES cuja data prevista é HOJE ou no FUTURO.
     * O Join com Regra Preventiva é para obter o campo 'diasAlerta'.
     * @param dataLimiteAlerta A data de corte (Ex: hoje + 30 dias)
     */
    @Query("SELECT ma FROM ManutencaoAgendada ma JOIN FETCH ma.regra r " +
           "WHERE ma.status = 'PENDENTE' AND ma.dataAgendada <= :dataLimiteAlerta")
    List<ManutencaoAgendada> findAgendamentosProximos(@Param("dataLimiteAlerta") LocalDate dataLimiteAlerta);

        @Query("SELECT a FROM ManutencaoAgendada a " +
       "LEFT JOIN FETCH a.veiculo v " +
       "LEFT JOIN FETCH v.cliente c " + // <<--- ESTE FETCH É VITAL!
       "LEFT JOIN FETCH a.regra " +
       "WHERE a.dataAgendada <= :dataLimite AND a.status = 'PENDENTE' " +
       "ORDER BY a.dataAgendada ASC")
    List<ManutencaoAgendada> findAgendamentosProximosComDetalhes(@Param("dataLimite") LocalDate dataLimite);
}