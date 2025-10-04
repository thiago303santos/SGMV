package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_manutencao")
public class Manutencao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente dono da manutenção
    @ManyToOne
    @JoinColumn(name = "cliente_id") // chave estrangeira no banco
    private Cliente cliente;

    // Veículo que receberá a manutenção
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    // Funcionário responsável
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    // Usuário do sistema que registrou/manipulou a manutenção
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioResponsavel;

    private LocalDate dataEntrada;
    private LocalDate dataConclusao;
    private String quilometragem;
    private String descricao;
    private String status; // Ex: PENDENTE, EM_PROGRESSO, FINALIZADO
    private BigDecimal vlTotal;

    @OneToMany(mappedBy = "manutencao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ManutencaoPeca> pecas = new ArrayList<>();

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

    public List<ManutencaoPeca> getPecas() { return pecas; }
    public void setPecas(List<ManutencaoPeca> pecas) { this.pecas = pecas; }
}
