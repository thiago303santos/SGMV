package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sgmv.demo.dto.ProdutoDTO;
import sgmv.demo.model.Produto;
import sgmv.demo.model.enums.UnidadeMedida;
import sgmv.demo.repository.ProdutoRepository;
import sgmv.demo.repository.CategoriaProdutoRepository;

import java.util.List;

@Controller
@RequestMapping("/estoque")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaRepository;

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "estoque/lista";
    }

    @ResponseBody
    @GetMapping("/listar")
    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
            .map(p -> new ProdutoDTO(
                p.getIdProduto(),
                p.getNomeProduto(),
                p.getCategoria() != null ? p.getCategoria().getNomeCategoria() : "",
                p.getUnidadeMedida() != null ? p.getUnidadeMedida().name() : "",
                p.getQuantidade(),
                p.getValorVenda()
            ))
            .toList();
    }


    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("unidadesMedida", UnidadeMedida.values());
        return "estoque/cadastraProduto";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/estoque/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "estoque/cadastraProduto";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/estoque/lista";
    }
}

