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
@Table(name = "tb_manutencao")
public class Manutencao implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_manutencao;
    private int veiculo_id;
    private String data_entrada;
    private String data_conclusao;
    private String funcionario_id;
    private String quilometragem;
    private String descricao;
    private String status;
    @NumberFormat(pattern = "#.###.###.###.###,##")
    private BigDecimal vl_total;
    private int peca_id;

    public int getId_manutencao() {
        return id_manutencao;
    }
    public void setId_manutencao(int id_manutencao) {
        this.id_manutencao = id_manutencao;
    }
    public int getVeiculo_id() {
        return veiculo_id;
    }
    public void setVeiculo_id(int veiculo_id) {
        this.veiculo_id = veiculo_id;
    }
    public String getData_entrada() {
        return data_entrada;
    }
    public void setData_entrada(String data_entrada) {
        this.data_entrada = data_entrada;
    }
    public String getData_conclusao() {
        return data_conclusao;
    }
    public void setData_conclusao(String data_conclusao) {
        this.data_conclusao = data_conclusao;
    }
    public String getFuncionario_id() {
        return funcionario_id;
    }
    public void setFuncionario_id(String funcionario_id) {
        this.funcionario_id = funcionario_id;
    }
    public String getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getPeca_id() {
        return peca_id;
    }
    public void setPeca_id(int peca_id) {
        this.peca_id = peca_id;
    }
    public BigDecimal getVl_total() {
        return vl_total;
    }
    public void setVl_total(BigDecimal vl_total) {
        this.vl_total = vl_total;
    }

}