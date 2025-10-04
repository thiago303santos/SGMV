package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import sgmv.demo.model.Veiculo;
import sgmv.demo.repository.VeiculosRepository;

import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {



    @Autowired
    private VeiculosRepository veiculosRepository;

    // Lista todos
    @GetMapping
    public String listarVeiculos(Model model, HttpSession session) {
        List<Veiculo> veiculos = veiculosRepository.findAll();
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        model.addAttribute("usuarioEmail", session.getAttribute("usuarioEmail"));
        return "veiculo/lista"; // criaremos depois
    }

    @GetMapping("/listar")
    @ResponseBody
    public List<Veiculo> listarVeiculosAjax() {
        return veiculosRepository.findAll();
    }

    // Formulário de novo agendamento
    @GetMapping("/novo")
    public String novoVeiculo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculo/cadastrarVeiculo"; // agora aponta para cadastrarAgendamento.html
    }

    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Veiculo veiculo) {
        veiculosRepository.save(veiculo);
        return "redirect:/home";
    }

    // Editar agendamento
    @GetMapping("/editar/{id}")
    public String editarAgendamento(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("veiculo", veiculo);
        return "veiculo/cadastrarVeiculo"; // também aponta para o mesmo formulário
    }

    // Excluir agendamento
    @GetMapping("/excluir/{id}")
    public String excluirAgendamento(@PathVariable Long id) {
        veiculosRepository.deleteById(id);
        return "redirect:/veiculos";
    }
}
