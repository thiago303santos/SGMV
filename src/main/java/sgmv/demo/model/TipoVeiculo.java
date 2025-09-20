package sgmv.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_tipo_veiculo")
public class TipoVeiculo implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo_veiculo;
    private String tipo_veiculo;

    public int getId_tipo_veiculo() {
        return id_tipo_veiculo;
    }
    public void setId_tipo_veiculo(int id_tipo_veiculo) {
        this.id_tipo_veiculo = id_tipo_veiculo;
    }
    public String getTipo_veiculo() {
        return tipo_veiculo;
    }
    public void setTipo_veiculo(String tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }
   
    
}