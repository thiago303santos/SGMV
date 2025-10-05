package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sgmv.demo.model.Peca;
import sgmv.demo.repository.PecaRepository;

import java.util.List;

@Controller
@RequestMapping("/estoque")
public class PecaController {

    @Autowired
    private PecaRepository pecaRepository;

    // Tela principal
    @GetMapping("/lista")
    public String lista() {
        return "estoque/lista";
    }

    // API para DataTables
    @ResponseBody
    @GetMapping("/listar")
    public List<Peca> listarPecas() {
        return pecaRepository.findAll();
    }

    @GetMapping("/novo")
    public String nova(Model model) {
        model.addAttribute("peca", new Peca());
        return "estoque/cadastraPeca";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Peca peca) {
        pecaRepository.save(peca);
        return "redirect:/estoque/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Peça inválida: " + id));
        model.addAttribute("peca", peca);
        return "estoque/cadastraPeca";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        pecaRepository.deleteById(id);
        return "redirect:/estoque/lista";
    }
}
