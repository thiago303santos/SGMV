package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
