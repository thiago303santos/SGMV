package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "tb_peca")
public class Peca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeca;

    private String nomePeca;
    private String fabricante;
    private int quantidade;
    private String nrSerie;

    @NumberFormat(pattern = "#.###.###.###.###,##")
    private BigDecimal vlCusto;

    @NumberFormat(pattern = "#.###.###.###.###,##")
    private BigDecimal vlVenda;

    // --- GETTERS E SETTERS ---
    public Long getIdPeca() { return idPeca; }
    public void setIdPeca(Long idPeca) { this.idPeca = idPeca; }

    public String getNomePeca() { return nomePeca; }
    public void setNomePeca(String nomePeca) { this.nomePeca = nomePeca; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getNrSerie() { return nrSerie; }
    public void setNrSerie(String nrSerie) { this.nrSerie = nrSerie; }

    public BigDecimal getVlCusto() { return vlCusto; }
    public void setVlCusto(BigDecimal vlCusto) { this.vlCusto = vlCusto; }

    public BigDecimal getVlVenda() { return vlVenda; }
    public void setVlVenda(BigDecimal vlVenda) { this.vlVenda = vlVenda; }
}
