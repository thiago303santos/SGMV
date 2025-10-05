package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Peca;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
}
