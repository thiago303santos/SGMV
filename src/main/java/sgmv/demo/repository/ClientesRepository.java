package sgmv.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Cliente;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Long> {


    List<Cliente> findByNomeClienteContainingIgnoreCase(String nomeCliente);

}