package sgmv.demo.repository;

import sgmv.demo.model.RegraPreventiva;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegraPreventivaRepository extends JpaRepository<RegraPreventiva, Long> {

    List<RegraPreventiva> findByAtivoTrue();
}