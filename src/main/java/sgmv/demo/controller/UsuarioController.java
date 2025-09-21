package sgmv.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sgmv.demo.model.Usuario;
import sgmv.demo.repository.UsuarioRepository;


@Controller
@RequestMapping("/")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    // Método de Cadastro
    @PostMapping("/cadastrarUsuario")
    @ResponseBody
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Usuário com este e-mail já existe!");
        }

        usuario.setSenha_usuario(passwordEncoder.encode(usuario.getSenha_usuario()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("success");
    }




    // Método para o login
    @PostMapping("/logarUsuario")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String emailUsuario = loginData.get("emailUsuario");
        String senha = loginData.get("senha_usuario");

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailUsuario(emailUsuario);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senha, usuario.getSenha_usuario())) {
                return new ResponseEntity<>("Login bem-sucedido!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Credenciais inválidas.", HttpStatus.UNAUTHORIZED);
    }
    
    // Método para a troca de senha
    @PostMapping("/troca_senha")
    @ResponseBody
    public ResponseEntity<?> trocarSenha(@RequestBody Map<String, String> senhaData) {
        String emailUsuario = senhaData.get("emailUsuario");
        String senhaAntiga = senhaData.get("senha_antiga");
        String novaSenha = senhaData.get("nova_senha");

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailUsuario(emailUsuario);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senhaAntiga, usuario.getSenha_usuario())) {
                usuario.setSenha_usuario(passwordEncoder.encode(novaSenha));
                usuarioRepository.save(usuario);
                return new ResponseEntity<>("Senha alterada com sucesso!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Credenciais ou senha antiga inválidas.", HttpStatus.UNAUTHORIZED);
    }

    // Métodos para servir as páginas HTML
    // Mantidos para que o controller possa continuar retornando views
    @RequestMapping("/")
    public String viewIndex() {
        return "login/index";
    }

    @RequestMapping("/cadastro")
    public String viewCadastro() {
        return "login/cadastro";
    }
}