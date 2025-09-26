package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_manutencao_peca")
public class ManutencaoPeca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manutencao_id")
    private Manutencao manutencao;

    @ManyToOne
    @JoinColumn(name = "peca_id")
    private Peca peca;

    private int quantidade;
    private BigDecimal vlUnitario;

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Manutencao getManutencao() { return manutencao; }
    public void setManutencao(Manutencao manutencao) { this.manutencao = manutencao; }

    public Peca getPeca() { return peca; }
    public void setPeca(Peca peca) { this.peca = peca; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getVlUnitario() { return vlUnitario; }
    public void setVlUnitario(BigDecimal vlUnitario) { this.vlUnitario = vlUnitario; }
}
