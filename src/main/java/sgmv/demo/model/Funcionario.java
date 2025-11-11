package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    private String nomeFuncionario;
    private String especialidade;
    
    // NOVO RELACIONAMENTO 1:1: Mapeia o Funcionário para uma conta de Usuário (login)
    // Opcional, pois pode haver funcionários sem login no sistema.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true) // Chave estrangeira fica na tabela tb_funcionario
    private Usuario contaUsuario; 

    @OneToMany(mappedBy = "funcionario")
    private List<Manutencao> manutencoes = new ArrayList<>();

    // --- GETTERS E SETTERS ---
    public Long getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Long idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public List<Manutencao> getManutencoes() { return manutencoes; }
    public void setManutencoes(List<Manutencao> manutencoes) { this.manutencoes = manutencoes; }
    
    // NOVO GETTER/SETTER:
    public Usuario getContaUsuario() { return contaUsuario; }
    public void setContaUsuario(Usuario contaUsuario) { this.contaUsuario = contaUsuario; }
}