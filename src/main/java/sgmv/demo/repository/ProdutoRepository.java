package sgmv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sgmv.demo.model.Produto;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaNomeCategoria(String nomeCategoria);

    List<Produto> findByPerecivelTrueAndDataValidadeLessThanEqualAndQuantidadeGreaterThan(LocalDate dataLimite, int quantidadeMinima);

    // Simplificando o método de busca de estoque para o Job de Vencimento
    List<Produto> findByPerecivelTrueAndDataValidadeLessThanEqual(LocalDate dataLimite);

    /**
     * CORRIGIDO: Encontra produtos onde a quantidade atual é menor ou igual ao estoque mínimo.
     * Usamos @Query (JPQL) para comparar duas propriedades internas da entidade Produto,
     * pois o Spring Data JPA não suporta esta comparação usando apenas a convenção de nomes.
     */
    @Query("SELECT p FROM Produto p WHERE p.quantidade <= p.estoqueMinimo")
    List<Produto> findByQuantidadeLessThanEqualEstoqueMinimo();

}