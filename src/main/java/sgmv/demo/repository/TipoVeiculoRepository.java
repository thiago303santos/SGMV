package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.TipoVeiculo;

@Repository
public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {
}
