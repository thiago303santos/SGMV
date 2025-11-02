package sgmv.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // <-- Verifique este import

@Entity
@Table(name = "tb_tipo_veiculo")
public class TipoVeiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoVeiculo;

    private String tipoVeiculo;

    @JsonIgnore
    @OneToMany(mappedBy = "tipo")
    private List<Veiculo> veiculos = new ArrayList<>();

    // --- GETTERS E SETTERS ---
    public Long getIdTipoVeiculo() { return idTipoVeiculo; }
    public void setIdTipoVeiculo(Long idTipoVeiculo) { this.idTipoVeiculo = idTipoVeiculo; }

    public String getTipoVeiculo() { return tipoVeiculo; }
    public void setTipoVeiculo(String tipoVeiculo) { this.tipoVeiculo = tipoVeiculo; }

    public List<Veiculo> getVeiculos() { return veiculos; }
    public void setVeiculos(List<Veiculo> veiculos) { this.veiculos = veiculos; }

    //
    // ESTES SÃO OS MÉTODOS CRÍTICOS
    //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoVeiculo that = (TipoVeiculo) o;
        return Objects.equals(idTipoVeiculo, that.idTipoVeiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoVeiculo);
    }
}