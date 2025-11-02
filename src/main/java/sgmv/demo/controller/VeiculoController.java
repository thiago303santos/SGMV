package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 1. Importe este

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import sgmv.demo.model.Veiculo;
import sgmv.demo.repository.VeiculosRepository;
import sgmv.demo.repository.ClientesRepository; // 2. Importe o repo de Clientes
import sgmv.demo.repository.TipoVeiculoRepository; // 3. Importe o repo de Tipos

import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculosRepository veiculosRepository;
    
    // 4. Injete os repositórios necessários para o formulário
    @Autowired
    private ClientesRepository clientesRepository; 

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository; 

    /**
     * [HTML] Retorna a PÁGINA da lista.
     * O DataTables vai carregar os dados via /listar (AJAX).
     */
    @GetMapping
    public String listarVeiculos(Model model, HttpSession session) {
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "veiculo/lista"; // Caminho: /templates/veiculo/lista.html
    }

    /**
     * [JSON] Retorna os DADOS da lista para o DataTables.
     * (O seu método já estava correto!)
     */
    @GetMapping("/listar")
    @ResponseBody
    public List<Veiculo> listarVeiculosAjax() {
        return veiculosRepository.findAll();
    }

    /**
     * [HTML] Mostra o formulário para um NOVO veículo.
     */
    @GetMapping("/novo")
    public String novoVeiculo(Model model, HttpSession session) {
        model.addAttribute("veiculo", new Veiculo());
        // 5. Envia as listas para os dropdowns
        model.addAttribute("listaClientes", clientesRepository.findAll()); 
        model.addAttribute("listaTipos", tipoVeiculoRepository.findAll()); 
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "veiculo/formulario"; // Padronizando o nome do arquivo
    }

    /**
     * [AÇÃO] Salva um veículo (novo ou editado).
     */
    @PostMapping("/salvar")
    public String salvarVeiculo(@ModelAttribute Veiculo veiculo, RedirectAttributes redirectAttributes) {
        try {
            veiculosRepository.save(veiculo);
            redirectAttributes.addFlashAttribute("sucesso", "Veículo salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar veículo: " + e.getMessage());
            // Se der erro, volta para o formulário
            if (veiculo.getIdVeiculo() != null) {
                 return "redirect:/veiculos/editar/" + veiculo.getIdVeiculo();
            }
            return "redirect:/veiculos/novo";
        }
        return "redirect:/veiculos"; // 6. Redireciona para a lista
    }

    /**
     * [HTML] Mostra o formulário para EDITAR um veículo.
     */
    @GetMapping("/editar/{id}")
    public String editarVeiculo(@PathVariable Long id, Model model, HttpSession session) {
        Veiculo veiculo = veiculosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("veiculo", veiculo);
        // 7. Envia as listas para os dropdowns
        model.addAttribute("listaClientes", clientesRepository.findAll()); 
        model.addAttribute("listaTipos", tipoVeiculoRepository.findAll()); 
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "veiculo/formulario"; // Reutiliza o mesmo formulário
    }

    /**
     * [AÇÃO] Exclui um veículo.
     */
    @GetMapping("/excluir/{id}")
    public String excluirVeiculo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // 8. Adiciona try...catch para tratar erros de exclusão
        try {
            veiculosRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            // Provável erro: Veículo está vinculado a uma Ordem de Serviço
            redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir. O veículo pode estar vinculado a Ordens de Serviço.");
        }
        return "redirect:/veiculos"; // Redireciona para a lista
    }
}