package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "tb_manutencao_peca")
public class ManutencaoPeca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação com a manutenção principal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manutencao_id", nullable = false)
    private Manutencao manutencao;

    // Relação com o produto (antiga peça)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal vlUnitario;

    // Valor total (opcional, mas útil)
    @Transient
    public BigDecimal getValorTotal() {
        if (vlUnitario == null) return BigDecimal.ZERO;
        return vlUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Manutencao getManutencao() { return manutencao; }
    public void setManutencao(Manutencao manutencao) { this.manutencao = manutencao; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getVlUnitario() { return vlUnitario; }
    public void setVlUnitario(BigDecimal vlUnitario) { this.vlUnitario = vlUnitario; }
}
