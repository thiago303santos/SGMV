package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_categoria_produto")
public class CategoriaProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    private String nomeCategoria;

    // Ex: OFICINA, LIMPEZA, ORGANIZACAO, ALIMENTOS
    private String tipoUso; 

    // --- GETTERS/SETTERS ---
    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }

    public String getTipoUso() { return tipoUso; }
    public void setTipoUso(String tipoUso) { this.tipoUso = tipoUso; }
}

