package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Funcionario;

import java.util.List;
import java.util.Optional; // Importe Optional

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
        
    // MÉTODO NECESSÁRIO PARA PRODUTIVIDADE
    // Busca um Funcionario pelo ID do Usuario associado (acessa o campo contaUsuario dentro de Funcionario)
    Optional<Funcionario> findByContaUsuarioIdUsuario(Long idUsuario);

    @Query(value = "SELECT f.nome_funcionario, AVG(TIMESTAMPDIFF(SECOND, m.data_hora_inicio, m.data_hora_fim)) / 3600.0 " +
               "FROM tb_manutencao m JOIN tb_funcionario f ON m.funcionario_executor_id = f.id_funcionario " +
               "WHERE m.data_hora_fim IS NOT NULL AND m.data_hora_inicio IS NOT NULL " +
               "GROUP BY f.nome_funcionario", nativeQuery = true)
    List<Object[]> findAverageServiceTimeByEmployee();
}