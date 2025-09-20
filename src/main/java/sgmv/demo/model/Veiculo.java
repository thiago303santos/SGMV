package sgmv.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_veiculo")
public class Veiculo implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_veiculo;
    private String placa;
    private String modelo;
    private String cor;
    private String ano;
    private String marca;
    private int id_tipo;
    private int usuario_id;
    private int manutencao_id;

    public int getId_veiculo() {
        return id_veiculo;
    }
    public void setId_veiculo(int id_veiculo) {
        this.id_veiculo = id_veiculo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public int getId_tipo() {
        return id_tipo;
    }
    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }
    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    public int getManutencao_id() {
        return manutencao_id;
    }
    public void setManutencao_id(int manutencao_id) {
        this.manutencao_id = manutencao_id;
    }

}