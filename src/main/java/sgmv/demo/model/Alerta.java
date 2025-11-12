// src/main/java/sgmv/demo/model/Alerta.java

package sgmv.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo; // Ex: "Estoque Baixo: Filtro de Óleo"
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao; // Ex: "A quantidade atual (8) está abaixo do estoque mínimo (10)."

    @Column(name = "tipo_alerta", nullable = false)
    @Enumerated(EnumType.STRING)
    
    private TipoAlerta tipoAlerta; // Usa o ENUM que criaremos (ESTOQUE, TEMPO, DOCUMENTO)

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "esta_lido", nullable = false)
    private boolean estaLido = false; // Flag para o usuário marcar como lido

    // Opcional: Para relacionar o alerta a um item específico (Veículo ou Produto)
    private Long idEntidadeRelacionada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isEstaLido() {
        return estaLido;
    }

    public void setEstaLido(boolean estaLido) {
        this.estaLido = estaLido;
    }

    public Long getIdEntidadeRelacionada() {
        return idEntidadeRelacionada;
    }

    public void setIdEntidadeRelacionada(Long idEntidadeRelacionada) {
        this.idEntidadeRelacionada = idEntidadeRelacionada;
    }


}