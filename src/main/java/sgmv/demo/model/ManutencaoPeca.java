package sgmv.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_manutencao_peca")
public class ManutencaoPeca implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_manutencao_peca;
    private int manutencao_id;
    private int peca_id;

public int getId_manutencao_peca() {
        return id_manutencao_peca;
    }
    public void setId_manutencao_peca(int id_manutencao_peca) {
        this.id_manutencao_peca = id_manutencao_peca;
    }
    public int getManutencao_id() {
        return manutencao_id;
    }
    public void setManutencao_id(int manutencao_id) {
        this.manutencao_id = manutencao_id;
    }
    public int getPeca_id() {
        return peca_id;
    }
    public void setPeca_id(int peca_id) {
        this.peca_id = peca_id;
    }
    
}