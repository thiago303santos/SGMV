package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; // IMPORTANTE: Para precisão de tempo (HH:MM:SS)
import java.util.HashSet; 
import java.util.Set; 

@Entity
@Table(name = "tb_manutencao")
public class Manutencao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente dono da manutenção
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Veículo que receberá a manutenção
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    // Funcionário responsável (Registro da OS)
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    
    // NOVO RELACIONAMENTO: Profissional que EXECUTOU o serviço (para cálculo de tempo/produtividade)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_executor_id")
    private Funcionario funcionarioExecutor;

    // Usuário do sistema que registrou/manipulou a manutenção
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioResponsavel;

    private LocalDate dataEntrada;
    private LocalDate dataConclusao;
    
    // NOVOS CAMPOS PARA RASTREAMENTO DE TEMPO
    private LocalDateTime dataHoraInicio; // Hora em que o serviço foi dado como "Em Andamento"
    private LocalDateTime dataHoraFim;    // Hora em que o serviço foi dado como "Concluído"
    
    private String quilometragem;
    private String descricao;
    private String status;
    private BigDecimal vlTotal;

    @OneToMany(mappedBy = "manutencao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManutencaoPeca> pecas = new HashSet<>();

    @OneToMany(mappedBy = "manutencao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManutencaoServico> servicos = new HashSet<>();

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public Usuario getUsuarioResponsavel() { return usuarioResponsavel; }
    public void setUsuarioResponsavel(Usuario usuarioResponsavel) { this.usuarioResponsavel = usuarioResponsavel; }

    // NOVOS GETTERS/SETTERS:
    public Funcionario getFuncionarioExecutor() { return funcionarioExecutor; }
    public void setFuncionarioExecutor(Funcionario funcionarioExecutor) { this.funcionarioExecutor = funcionarioExecutor; }
    
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }
    
    // ... (restante dos getters e setters existentes) ...
    
    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }

    public String getQuilometragem() { return quilometragem; }
    public void setQuilometragem(String quilometragem) { this.quilometragem = quilometragem; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getVlTotal() { return vlTotal; }
    public void setVlTotal(BigDecimal vlTotal) { this.vlTotal = vlTotal; }

    public Set<ManutencaoPeca> getPecas() { return pecas; }
    public void setPecas(Set<ManutencaoPeca> pecas) { this.pecas = pecas; }
    
    public Set<ManutencaoServico> getServicos() { return servicos; }
    public void setServicos(Set<ManutencaoServico> servicos) { this.servicos = servicos; }
}