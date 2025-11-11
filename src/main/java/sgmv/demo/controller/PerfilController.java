package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // NECESSÁRIO
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import sgmv.demo.model.Usuario;
import sgmv.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // INJEÇÃO DO ENCODER

    // --- ROTA DE VISUALIZAÇÃO DO PERFIL (/perfil) ---
    
    /**
     * Carrega a página de edição do perfil do usuário logado.
     */
    @GetMapping
    public String viewPerfil(HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            return "redirect:/logout"; 
        }

        Usuario usuarioLogado = usuarioRepository.findById(idUsuario)
                .orElse(null);

        if (usuarioLogado == null) {
            return "redirect:/logout"; 
        }

        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("usuarioNome", usuarioLogado.getNomeUsuario()); // Para a Navbar
        
        // Retorna o template na pasta 'perfil' (templates/perfil/perfil.html)
        return "perfil/perfil"; 
    }

    // --- ROTA DE SALVAR DADOS PESSOAIS (Formulário 1) ---

    /**
     * Salva as alterações nos dados pessoais (Nome, Telefone, CPF).
     */
    @PostMapping("/salvar-dados")
    public String salvarDados(@ModelAttribute Usuario usuarioAtualizado, HttpSession session, RedirectAttributes ra) {
        
        // 1. Busca o usuário existente no banco
        Usuario usuarioExistente = usuarioRepository.findById(usuarioAtualizado.getIdUsuario())
                .orElse(null);
        
        if (usuarioExistente == null) {
             ra.addFlashAttribute("erro", "Usuário não encontrado.");
             return "redirect:/perfil";
        }
        
        try {
            // 2. Atualiza apenas os campos editáveis (copia os dados do objeto recebido)
            usuarioExistente.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
            usuarioExistente.setTelefoneUsuario(usuarioAtualizado.getTelefoneUsuario());
            // CPF e Email não são alterados neste formulário
            
            usuarioRepository.save(usuarioExistente);
            
            // 3. Atualiza o nome na sessão para a Navbar
            session.setAttribute("usuarioNome", usuarioExistente.getNomeUsuario());
            
            ra.addFlashAttribute("sucesso", "Dados pessoais atualizados com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao salvar os dados: " + e.getMessage());
        }

        return "redirect:/perfil";
    }

    // --- ROTA DE SALVAR NOVA SENHA (Formulário 2) ---

    /**
     * Altera a senha do usuário logado, verificando a senha antiga.
     */
    @PostMapping("/salvar-senha")
    public String salvarSenha(@RequestParam String senhaAntiga,
                              @RequestParam String novaSenha,
                              @RequestParam String confirmaSenha,
                              HttpSession session, RedirectAttributes ra) {
        
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        
        if (usuario == null) {
             return "redirect:/logout";
        }
        
        // 1. Verifica se a nova senha e a confirmação são iguais
        if (!novaSenha.equals(confirmaSenha)) {
            ra.addFlashAttribute("erro", "A nova senha e a confirmação não coincidem.");
            return "redirect:/perfil";
        }
        
        // 2. Verifica se a senha antiga está correta usando o PasswordEncoder
        if (!passwordEncoder.matches(senhaAntiga, usuario.getSenhaUsuario())) {
            ra.addFlashAttribute("erro", "A senha atual está incorreta.");
            return "redirect:/perfil";
        }
        
        // 3. Atualiza e salva a nova senha criptografada
        usuario.setSenhaUsuario(passwordEncoder.encode(novaSenha)); 
        usuarioRepository.save(usuario);
        
        ra.addFlashAttribute("sucesso", "Senha alterada com sucesso!");
        return "redirect:/perfil";
    }
}