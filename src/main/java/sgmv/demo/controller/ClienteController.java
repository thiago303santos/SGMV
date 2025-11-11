package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import sgmv.demo.model.Cliente;
import sgmv.demo.model.Veiculo; // 1. Importe o Veiculo
import sgmv.demo.repository.ClientesRepository;
import sgmv.demo.repository.VeiculosRepository; // 2. Importe o VeiculosRepository
import sgmv.demo.dto.VeiculoDTO;

import java.util.List;

@Controller
@RequestMapping("/clientes") // Define /clientes como o prefixo para este controller
public class ClienteController {

    @Autowired
    private ClientesRepository clientesRepository;

    // 3. INJETE O REPOSITÓRIO DE VEÍCULOS
    @Autowired
    private VeiculosRepository veiculosRepository;

    /**
     * [HTML] Método GET para MOSTRAR a lista de todos os clientes.
     */
    @GetMapping
    public String listarClientes(Model model, HttpSession session) {
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "cliente/lista"; // Caminho: /templates/cliente/lista.html
    }

    /**
     * [JSON] Método GET para LISTAR TODOS os clientes em JSON (para o DataTables).
     */
    @GetMapping("/listar")
    @ResponseBody // <-- Retorna JSON
    public List<Cliente> listarClientesJson() {
        return clientesRepository.findAll();
    }

    /**
     * [HTML] Método GET para MOSTRAR o formulário de um novo cliente.
     */
    @GetMapping("/novo")
    public String novoClienteForm(Model model, HttpSession session) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "cliente/formulario"; // Caminho: /templates/cliente/formulario.html
    }

    /**
     * [HTML] Método GET para MOSTRAR o formulário de edição de um cliente existente.
     */
    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model, HttpSession session) {
        Cliente cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Cliente inválido: " + id));
        
        model.addAttribute("cliente", cliente);
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "cliente/formulario"; // Reutiliza o mesmo formulário
    }

    /**
     * [AÇÃO] Método POST para SALVAR um cliente (novo ou existente).
     */
    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clientesRepository.save(cliente);
            redirectAttributes.addFlashAttribute("sucesso", "Cliente salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar cliente: " + e.getMessage());
            if (cliente.getIdCliente() != null) {
                 return "redirect:/clientes/editar/" + cliente.getIdCliente();
            }
            return "redirect:/clientes/novo";
        }
        return "redirect:/clientes"; // Redireciona para o GET /clientes (lista)
    }

    /**
     * [AÇÃO] Método GET para EXCLUIR um cliente.
     */
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clientesRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir. O cliente pode estar vinculado a veículos ou Ordens de Serviço.");
        }
        return "redirect:/clientes";
    }

    // --- Endpoints de API (JSON) para AJAX ---

    /**
     * [AJAX] Método GET para BUSCAR clientes por parte do nome (ignorando maiúsculas/minúsculas).
     */
    @GetMapping("/buscar")
    @ResponseBody // <-- Retorna JSON
    public List<Cliente> buscarClientesPorNome(@RequestParam("nome") String nome) {
        // Usa o método que você criou no seu ClientesRepository
        return clientesRepository.findByNomeClienteContainingIgnoreCase(nome);
    }

    /**
     * [AJAX] Método GET para buscar um cliente único pelo ID.
     */
    @GetMapping("/{id}")
    @ResponseBody // <-- Retorna JSON
    public ResponseEntity<Cliente> getClientePorId(@PathVariable Long id) {
        return clientesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * =====================================================================
     * [NOVO MÉTODO AJAX]
     * Método GET para buscar os veículos de um cliente específico.
     * Usado no formulário de "Cadastrar O.S."
     * =====================================================================
     */
    @GetMapping("/{id}/veiculos")
    @ResponseBody // <-- Retorna JSON
    public ResponseEntity<List<VeiculoDTO>> getVeiculosPorCliente(@PathVariable Long id) {
        
        // 1. Busca os veículos
        List<Veiculo> veiculos = veiculosRepository.findByClienteIdCliente(id);
        
        // 2. Mapeia para DTO
        List<VeiculoDTO> veiculosDTO = veiculos.stream()
            .map(VeiculoDTO::new)
            .toList();
        
        // Retorna 200 OK com a lista de DTOs
        return ResponseEntity.ok(veiculosDTO);
    }
}