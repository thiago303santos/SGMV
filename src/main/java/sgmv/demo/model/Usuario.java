package sgmv.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String cpf_usuario;
    private String nome_usuario;
    private String telefone_usuario;
    @Column(name = "email_usuario")
    private String emailUsuario;
    private String senha_usuario;
    private int tipo_usuario; // Ex: ADMIN, USER

    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getCpf_usuario() {
        return cpf_usuario;
    }
    public void setCpf_usuario(String cpf_usuario) {
        this.cpf_usuario = cpf_usuario;
    }
    public String getNome_usuario() {
        return nome_usuario;
    }
    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }
    public String getTelefone_usuario() {
        return telefone_usuario;
    }
    public void setTelefone_usuario(String telefone_usuario) {
        this.telefone_usuario = telefone_usuario;
    }
    public String getEmailUsuario() {
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    public String getSenha_usuario() {
        return senha_usuario;
    }
    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }
    public int getTipo_usuario() {
        return tipo_usuario;
    }
    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }


}