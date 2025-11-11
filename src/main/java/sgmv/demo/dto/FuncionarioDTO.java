package sgmv.demo.dto;

import sgmv.demo.model.Funcionario;
import java.util.Optional;

public class FuncionarioDTO {
    private Long idFuncionario;
    private String nomeFuncionario;
    private String especialidade;
    private String documento; // Assumindo que este campo existe no Model Funcionario
    private Long idContaUsuario; // Para verificar se está vinculado

    // Construtor de Mapeamento (Entidade -> DTO)
    public FuncionarioDTO(Funcionario f) {
        this.idFuncionario = f.getIdFuncionario();
        this.nomeFuncionario = f.getNomeFuncionario();
        this.especialidade = f.getEspecialidade();
        // Assume que o campo 'documento' foi adicionado no Model Funcionario
        // this.documento = f.getDocumento(); 
        
        // Mapeia o ID do usuário, se houver, sem acessar a Entidade Usuario completa
        this.idContaUsuario = Optional.ofNullable(f.getContaUsuario())
                                      .map(u -> u.getIdUsuario())
                                      .orElse(null);
    }
    
    // --- GETTERS E SETTERS ---
    public Long getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Long idFuncionario) { this.idFuncionario = idFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public Long getIdContaUsuario() { return idContaUsuario; }
    public void setIdContaUsuario(Long idContaUsuario) { this.idContaUsuario = idContaUsuario; }
}