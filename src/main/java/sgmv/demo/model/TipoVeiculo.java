package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tipo_veiculo")
public class TipoVeiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoVeiculo;

    private String tipoVeiculo;

    @OneToMany(mappedBy = "tipo")
    private List<Veiculo> veiculos = new ArrayList<>();

    // --- GETTERS E SETTERS ---
    public Long getIdTipoVeiculo() { return idTipoVeiculo; }
    public void setIdTipoVeiculo(Long idTipoVeiculo) { this.idTipoVeiculo = idTipoVeiculo; }

    public String getTipoVeiculo() { return tipoVeiculo; }
    public void setTipoVeiculo(String tipoVeiculo) { this.tipoVeiculo = tipoVeiculo; }

    public List<Veiculo> getVeiculos() { return veiculos; }
    public void setVeiculos(List<Veiculo> veiculos) { this.veiculos = veiculos; }
}
