package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sgmv.demo.model.ManutencaoPeca;

import java.util.List;

public interface ManutencaoPecaRepository extends JpaRepository<ManutencaoPeca, Long> {
    List<ManutencaoPeca> findByManutencaoId(Long manutencaoId);
}
