package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String cpfUsuario;
    private String nomeUsuario;
    private String telefoneUsuario;

    @Column(unique = true)
    private String emailUsuario;

    private String senhaUsuario;
    // CORREÇÃO ESSENCIAL: De 'int' para 'Integer' para permitir valor nulo no formulário
    private Integer tipoUsuario; // 0 = ADMIN, 1 = FUNCIONARIO, 2 = GERENTE, etc.

    // NOVO RELACIONAMENTO: Mapeamento reverso para Funcionario (dados de profissional)
    @OneToOne(mappedBy = "contaUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Funcionario dadosFuncionario;

    // Usuário pode criar/manter várias manutenções
    @OneToMany(mappedBy = "usuarioResponsavel")
    private List<Manutencao> manutencoes = new ArrayList<>();

    // --- GETTERS E SETTERS ---
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getCpfUsuario() { return cpfUsuario; }
    public void setCpfUsuario(String cpfUsuario) { this.cpfUsuario = cpfUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getTelefoneUsuario() { return telefoneUsuario; }
    public void setTelefoneUsuario(String telefoneUsuario) { this.telefoneUsuario = telefoneUsuario; }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    public String getSenhaUsuario() { return senhaUsuario; }
    public void setSenhaUsuario(String senhaUsuario) { this.senhaUsuario = senhaUsuario; }

    // GETTER/SETTER ATUALIZADO PARA Integer
    public Integer getTipoUsuario() { return tipoUsuario; } 
    public void setTipoUsuario(Integer tipoUsuario) { this.tipoUsuario = tipoUsuario; } 

    public List<Manutencao> getManutencoes() { return manutencoes; }
    public void setManutencoes(List<Manutencao> manutencoes) { this.manutencoes = manutencoes; }
    
    // NOVO GETTER/SETTER:
    public Funcionario getDadosFuncionario() { return dadosFuncionario; }
    public void setDadosFuncionario(Funcionario dadosFuncionario) { this.dadosFuncionario = dadosFuncionario; }
}