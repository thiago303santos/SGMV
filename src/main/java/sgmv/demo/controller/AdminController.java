package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgmv.demo.dto.UsuarioDTO;

import sgmv.demo.model.Funcionario;
import sgmv.demo.model.Usuario;
import sgmv.demo.repository.UsuarioRepository;
import sgmv.demo.repository.FuncionarioRepository;
import sgmv.demo.dto.FuncionarioDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin") 
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    // --- ROTA PRINCIPAL: Painel de Gerenciamento de Acesso (/admin/acesso) ---
    @GetMapping("/acesso")
    public String viewPainelAcesso() {
        return "admin/acesso"; 
    }

    // =================================================================
    // MÉTODOS PARA USUÁRIOS DO SISTEMA (ABA 1)
    // =================================================================

    /** [AJAX] Lista todos os usuários do sistema em formato JSON (usando DTO). */
    @GetMapping("/usuario/listar")
    @ResponseBody
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
               .map(UsuarioDTO::new)
               .toList();
    }
    
    /** [HTML] Abre o formulário para NOVO usuário. */
    @GetMapping("/usuario/novo")
    public String viewNovoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario_form";
    }
    
    /** [HTML] Abre o formulário para EDITAR usuário. */
    @GetMapping("/usuario/editar/{id}")
    public String viewEditarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de Usuário inválido: " + id));
        model.addAttribute("usuario", usuario);
        return "admin/usuario_form";
    }

    /** [AÇÃO] Salva (cria ou edita) um usuário do sistema. */
    @PostMapping("/usuario/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario, 
                                @RequestParam(required = false) String senhaInicial,
                                RedirectAttributes ra) {
        
        if (usuario.getIdUsuario() == null) { // NOVO USUÁRIO
            if (senhaInicial == null || senhaInicial.isEmpty()) {
                 ra.addFlashAttribute("erro", "A senha inicial é obrigatória.");
                 return "redirect:/admin/usuario/novo";
            }
            usuario.setSenhaUsuario(passwordEncoder.encode(senhaInicial));
            
        } else { // EDIÇÃO DE USUÁRIO
            
            // Busca o objeto existente para garantir a hash da senha
            Optional<Usuario> optionalExistente = usuarioRepository.findById(usuario.getIdUsuario());
            
            if (optionalExistente.isEmpty()) {
                ra.addFlashAttribute("erro", "Erro: Usuário de edição não encontrado.");
                return "redirect:/admin/acesso";
            }
            
            Usuario existente = optionalExistente.get();
            
            if (usuario.getSenhaUsuario() == null || usuario.getSenhaUsuario().isEmpty()) {
                // Mantém a senha existente
                usuario.setSenhaUsuario(existente.getSenhaUsuario());
            } else {
                // Criptografa a nova senha (se fornecida)
                usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario())); 
            }
        }
        
        usuarioRepository.save(usuario);
        ra.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
        return "redirect:/admin/acesso";
    }

    /** [AÇÃO] Exclui um usuário do sistema. */
    @GetMapping("/usuario/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("sucesso", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir o usuário.");
        }
        return "redirect:/admin/acesso";
    }


    // =================================================================
    // MÉTODOS PARA FUNCIONÁRIOS (ABA 2)
    // =================================================================
    
    // MÉTODO AUXILIAR PARA PREPARAR USUÁRIOS DISPONÍVEIS
    private void adicionarUsuariosDisponiveisAoModel(Model model, Funcionario funcionario) {
        // 1. Busca usuários que AINDA NÃO estão vinculados a nenhum funcionário
        List<Usuario> usuariosDisponiveis = usuarioRepository.findByDadosFuncionarioIsNull();
        
        // 2. Se estivermos editando um funcionário que JÁ tem uma conta, adicionamos essa conta
        // à lista para que o usuário possa ser mantido ou desvinculado
        if (funcionario.getContaUsuario() != null) {
            usuariosDisponiveis.add(funcionario.getContaUsuario());
        }
        
        model.addAttribute("usuariosDisponiveis", usuariosDisponiveis);
    }
    
    /** [AJAX] Lista todos os funcionários em formato JSON. */
   @GetMapping("/funcionario/listar")
    @ResponseBody
    public List<FuncionarioDTO> listarFuncionarios() {
        // CORREÇÃO: Mapeia a Entidade para a DTO
        return funcionarioRepository.findAll().stream()
               .map(FuncionarioDTO::new) // Note o uso do construtor DTO
               .toList();
    }
    
    /** [HTML] Abre o formulário para NOVO funcionário. */
    @GetMapping("/funcionario/novo")
    public String viewNovoFuncionario(Model model) {
        Funcionario novoFuncionario = new Funcionario();
        model.addAttribute("funcionario", novoFuncionario);
        
        adicionarUsuariosDisponiveisAoModel(model, novoFuncionario); 
        
        return "admin/funcionario_form";
    }
    
    /** [HTML] Abre o formulário para EDITAR funcionário. */
    @GetMapping("/funcionario/editar/{id}")
    public String viewEditarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de Funcionário inválido: " + id));
        model.addAttribute("funcionario", funcionario);
        
        adicionarUsuariosDisponiveisAoModel(model, funcionario);
        
        return "admin/funcionario_form";
    }

    /** [AÇÃO] Salva (cria ou edita) um funcionário (profissional) e vincula o Usuário. */
    @PostMapping("/funcionario/salvar")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario, RedirectAttributes ra) {
        
        // 1. Tratar a Vinculação do Usuário (se um ID foi selecionado no dropdown)
        if (funcionario.getContaUsuario() != null && funcionario.getContaUsuario().getIdUsuario() != null) {
            Long idUsuarioVinculado = funcionario.getContaUsuario().getIdUsuario();
            
            // Busca o objeto Usuario completo para anexar ao Funcionario
            Usuario usuarioVinculado = usuarioRepository.findById(idUsuarioVinculado)
                                                        .orElseThrow(() -> new IllegalArgumentException("Conta de Usuário inválida para vinculação."));
            
            funcionario.setContaUsuario(usuarioVinculado);
        } else {
            // Se o usuário selecionou "-- Nenhum Usuário Vinculado --" (th:value=null), desvincula
            funcionario.setContaUsuario(null);
        }

        // 2. Salva o Funcionário
        funcionarioRepository.save(funcionario);
        ra.addFlashAttribute("sucesso", "Funcionário salvo com sucesso!");
        return "redirect:/admin/acesso";
    }
    
    /** [AÇÃO] Exclui um funcionário. */
    @GetMapping("/funcionario/excluir/{id}")
    public String excluirFuncionario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            funcionarioRepository.deleteById(id);
            ra.addFlashAttribute("sucesso", "Funcionário excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir. O funcionário pode estar vinculado a Ordens de Serviço.");
        }
        return "redirect:/admin/acesso";
    }


    // =================================================================
    // MÉTODOS PARA NÍVEIS DE ACESSO (ABA 3)
    // =================================================================

    /** [AJAX] Retorna a lista de níveis de acesso fixos e suas descrições. */
    @GetMapping("/nivel/listar")
    @ResponseBody
    public List<Map<String, String>> listarNiveis() {
        return List.of(
            Map.of("id", "0", "nome", "Administrador", "descricao", "Controle total, acesso a configurações."),
            Map.of("id", "1", "nome", "Funcionário", "descricao", "Acesso limitado, apenas operações de OS e estoque."),
            Map.of("id", "2", "nome", "Gerente", "descricao", "Acesso a relatórios e operações financeiras.")
        );
    }
}