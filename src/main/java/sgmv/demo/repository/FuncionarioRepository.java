package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Funcionario;
import java.util.Optional; // Importe Optional

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
        
    // MÉTODO NECESSÁRIO PARA PRODUTIVIDADE
    // Busca um Funcionario pelo ID do Usuario associado (acessa o campo contaUsuario dentro de Funcionario)
    Optional<Funcionario> findByContaUsuarioIdUsuario(Long idUsuario);
}