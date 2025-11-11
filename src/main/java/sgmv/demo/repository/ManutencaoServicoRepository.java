package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sgmv.demo.model.ManutencaoServico;

public interface ManutencaoServicoRepository extends JpaRepository<ManutencaoServico, Long> {
     
}