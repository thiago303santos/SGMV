package sgmv.demo.dto;

import sgmv.demo.model.Usuario;

public class UsuarioDTO {
    private Long idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private int tipoUsuario;

    public UsuarioDTO(Usuario u) {
        this.idUsuario = u.getIdUsuario();
        this.nomeUsuario = u.getNomeUsuario();
        this.emailUsuario = u.getEmailUsuario();
        this.tipoUsuario = u.getTipoUsuario();
    }

    public String getNivelAcessoTexto() {
        switch (tipoUsuario) {
            case 0: return "Administrador";
            case 1: return "Funcionário";
            case 2: return "Gerente";
            default: return "Indefinido";
        }
    }
    
    // --- GETTERS E SETTERS ---
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    public int getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(int tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}