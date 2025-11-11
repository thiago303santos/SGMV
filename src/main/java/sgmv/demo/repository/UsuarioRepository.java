package sgmv.demo.repository;

import java.util.List; // Importe List
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgmv.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailUsuario(String email_usuario);
    
    // NOVO MÉTODO: Encontra todos os usuários que NÃO têm um Funcionario vinculado (dadosFuncionario é NULL)
    List<Usuario> findByDadosFuncionarioIsNull(); 
}