package sgmv.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "tb_estoque")
public class Estoque implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_peca;
    private int nome_peca;
    private String fabricante;
    private String quantidade;
    @NumberFormat(pattern = "#.###.###.###.###,##")
    private BigDecimal vl_custo;
    @NumberFormat(pattern = "#.###.###.###.###,##")
    private BigDecimal vl_venda;
    
    private String nr_serie;

    public int getId_peca() {
        return id_peca;
    }
    public void setId_peca(int id_peca) {
        this.id_peca = id_peca;
    }
    public int getNome_peca() {
        return nome_peca;
    }
    public void setNome_peca(int nome_peca) {
        this.nome_peca = nome_peca;
    }
    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public String getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getVl_custo() {
        return vl_custo;
    }
    public void setVl_custo(BigDecimal vl_custo) {
        this.vl_custo = vl_custo;
    }
    public BigDecimal getVl_venda() {
        return vl_venda;
    }
    public void setVl_venda(BigDecimal vl_venda) {
        this.vl_venda = vl_venda;
    }
    public String getNr_serie() {
        return nr_serie;
    }
    public void setNr_serie(String nr_serie) {
        this.nr_serie = nr_serie;
    }

    
    
}