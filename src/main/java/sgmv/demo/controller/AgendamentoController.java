package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import sgmv.demo.model.Agendamento;
import sgmv.demo.repository.AgendamentoRepository;

import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Lista todos
    @GetMapping
    public String listarAgendamentos(Model model, HttpSession session) {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        model.addAttribute("usuarioEmail", session.getAttribute("usuarioEmail"));
        return "agendamento/lista"; // criaremos depois
    }

    // Formulário de novo agendamento
    @GetMapping("/novo")
    public String novoAgendamento(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        return "agendamento/cadastrarAgendamento"; // agora aponta para cadastrarAgendamento.html
    }

    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
        return "redirect:/home/index";
    }

    // Editar agendamento
    @GetMapping("/editar/{id}")
    public String editarAgendamento(@PathVariable Long id, Model model) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("agendamento", agendamento);
        return "agendamento/cadastrarAgendamento"; // também aponta para o mesmo formulário
    }

    // Excluir agendamento
    @GetMapping("/excluir/{id}")
    public String excluirAgendamento(@PathVariable Long id) {
        agendamentoRepository.deleteById(id);
        return "redirect:/agendamentos";
    }
}
