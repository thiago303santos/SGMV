package sgmv.demo.controller;

import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sgmv.demo.model.Usuario;
import sgmv.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Cadastro ---
    @PostMapping("/cadastrarUsuario")
    @ResponseBody
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getSenhaUsuario() == null || usuario.getSenhaUsuario().isEmpty()) {
            return ResponseEntity.badRequest().body("A senha não pode ser vazia!");
        }

        if (usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Usuário com este e-mail já existe!");
        }

        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("success");
    }

    // --- Login ---
    @PostMapping("/logarUsuario")
    public String login(@RequestParam String emailUsuario,
                        @RequestParam String senha_usuario,
                        HttpSession session,
                        Model model) {

        if (emailUsuario == null || senha_usuario == null || emailUsuario.isEmpty() || senha_usuario.isEmpty()) {
            model.addAttribute("erro", "E-mail ou senha não podem ser vazios.");
            return "login/index";
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailUsuario(emailUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senha_usuario, usuario.getSenhaUsuario())) {
                session.setAttribute("usuarioNome", usuario.getNomeUsuario());
                session.setAttribute("usuarioEmail", usuario.getEmailUsuario());
                session.setAttribute("idUsuario", usuario.getIdUsuario());
                // Redireciona para a home
                return "redirect:/home";
            }
        }

        model.addAttribute("erro", "Credenciais inválidas.");
        return "login/index";
    }

    // --- Logout ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // encerra a sessão do usuário
        return "redirect:/"; // volta para a tela de login
    }

    // --- Troca de senha ---
    @PostMapping("/troca_senha")
    @ResponseBody
    public ResponseEntity<?> trocarSenha(@RequestBody Map<String, String> senhaData) {
        String emailUsuario = senhaData.get("emailUsuario");
        String senhaAntiga = senhaData.get("senhaAntiga");
        String novaSenha = senhaData.get("novaSenha");

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailUsuario(emailUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senhaAntiga, usuario.getSenhaUsuario())) {
                usuario.setSenhaUsuario(passwordEncoder.encode(novaSenha));
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Senha alterada com sucesso!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Credenciais ou senha antiga inválidas.");
    }

    // --- Páginas ---
    @GetMapping("/")
    public String viewIndex(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/index";
    }

    @GetMapping("/cadastro")
    public String viewCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/cadastro";
    }
}
