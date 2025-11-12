package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sgmv.demo.model.Alerta;
import sgmv.demo.model.TipoAlerta;

import java.util.List;

// A Interface AlertaRepository herda todos os métodos CRUD básicos
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    /**
     * Conta quantos alertas não lidos existem. Usado na Navbar.
     */
    long countByEstaLidoFalse();

    /**
     * Novo método para verificar se já existe um alerta ATIVO (não lido)
     * para uma determinada entidade (Produto, OS, Agendamento).
     * * Usado para evitar spam de alertas repetidos pelos jobs agendados.
     */
    boolean existsByTipoAlertaAndIdEntidadeRelacionadaAndEstaLidoFalse(
        TipoAlerta tipoAlerta, 
        Long idEntidadeRelacionada
    );

    /**
     * Busca todos os alertas não lidos, ordenados do mais novo para o mais antigo.
     * Usado na Central de Alertas.
     */
    List<Alerta> findByEstaLidoFalseOrderByDataCriacaoDesc();
    
    /**
     * Retorna todos os alertas, ordenados do mais recente para o mais antigo.
     * (Mantido para compatibilidade com o método findAllByOrderByDataCriacaoDesc)
     */
    List<Alerta> findAllByOrderByDataCriacaoDesc();
}