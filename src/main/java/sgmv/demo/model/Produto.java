package sgmv.demo.model;

import jakarta.persistence.*;
import sgmv.demo.model.enums.UnidadeMedida;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    private String nomeProduto;
    private String fabricante;
    private String codigoBarras;
    private String numeroSerie;

    private int quantidade;

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorCusto;

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorVenda;

    // --- Campos de controle ---
    private boolean perecivel;
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    // Ligação com categoria
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaProduto categoria;

    // --- GETTERS E SETTERS ---
    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorCusto() { return valorCusto; }
    public void setValorCusto(BigDecimal valorCusto) { this.valorCusto = valorCusto; }

    public BigDecimal getValorVenda() { return valorVenda; }
    public void setValorVenda(BigDecimal valorVenda) { this.valorVenda = valorVenda; }

    public boolean isPerecivel() { return perecivel; }
    public void setPerecivel(boolean perecivel) { this.perecivel = perecivel; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public CategoriaProduto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }
}

