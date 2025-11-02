package sgmv.demo.repository;

import java.util.Optional;
import java.util.List; // 1. IMPORTE O 'java.util.List'

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Long> {

    // Este é o método que você já tinha:
    Optional<Veiculo> findByPlacaIgnoreCase(String placa);
    
    // 
    // 2. ADICIONE ESTE MÉTODO (O QUE ESTÁ FALTANDO):
    //
    List<Veiculo> findByClienteIdCliente(Long idCliente);

}