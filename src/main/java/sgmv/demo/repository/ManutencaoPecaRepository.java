package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgmv.demo.model.ManutencaoPeca;

import java.util.List;

public interface ManutencaoPecaRepository extends JpaRepository<ManutencaoPeca, Long> {
    List<ManutencaoPeca> findByManutencaoId(Long manutencaoId);

    // ManutencaoPecaRepository.java

    // ✅ 3. ESTOQUE: Top 5 Itens Mais Usados (JPQL)
    @Query("SELECT mp.produto.nomeProduto, SUM(mp.quantidade) AS totalQuantidade " +
        "FROM ManutencaoPeca mp " +
        "GROUP BY mp.produto.nomeProduto " +
        "ORDER BY totalQuantidade DESC ")
    List<Object[]> findTop5MostUsedProducts();
}
