// src/main/java/sgmv/demo/model/ManutencaoAgendada.java

package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_manutencao_agendada")
public class ManutencaoAgendada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;

    // Relação com a Regra que originou este agendamento
    @ManyToOne
    @JoinColumn(name = "regra_id", nullable = false)
    private RegraPreventiva regra;

    // Relação com o Veículo
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo; // Assumindo que você tem a Model Veiculo

    @Column(name = "data_agendada", nullable = false)
    private LocalDate dataAgendada; // Data prevista para a próxima manutenção (base para o alerta de tempo)

    @Column(name = "km_agendada")
    private Integer kmAgendada; // KM prevista para a próxima manutenção (base para o alerta de KM)

    @Column(nullable = false)
    private String status = "PENDENTE"; // PENDENTE, ALERTA, CONCLUIDO, CANCELADO

    // --- GETTERS E SETTERS ---
    public Long getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Long idAgendamento) { this.idAgendamento = idAgendamento; }

    public RegraPreventiva getRegra() { return regra; }
    public void setRegra(RegraPreventiva regra) { this.regra = regra; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public LocalDate getDataAgendada() { return dataAgendada; }
    public void setDataAgendada(LocalDate dataAgendada) { this.dataAgendada = dataAgendada; }

    public Integer getKmAgendada() { return kmAgendada; }
    public void setKmAgendada(Integer kmAgendada) { this.kmAgendada = kmAgendada; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}