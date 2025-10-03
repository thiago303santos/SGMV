package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_agendamento")
public class Agendamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;

    @Column(length = 100, nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private LocalDateTime dataAgendamento;

    @Column(length = 200, nullable = false)
    private String descricaoServico;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private StatusAgendamento statusAgendamento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    // Getters e Setters
    public Long getIdAgendamento() {
        return idAgendamento;
    }
    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    public String getDescricaoServico() {
        return descricaoServico;
    }
    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }
    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // Enum interno
    public enum StatusAgendamento {
        PENDENTE, CONFIRMADO, CONCLUIDO, CANCELADO
    }
}
