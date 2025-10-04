package sgmv.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Long> {


    Optional<Veiculo> findByPlacaIgnoreCase(String placa);

}