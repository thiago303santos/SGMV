package sgmv.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Manutencao;

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

    List<Manutencao> findByStatus(String status);
}