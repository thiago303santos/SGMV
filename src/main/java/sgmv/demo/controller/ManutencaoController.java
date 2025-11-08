package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgmv.demo.dto.ManutencaoDTO;
import sgmv.demo.dto.ManutencaoPecaDTO;
import sgmv.demo.model.*;
import sgmv.demo.repository.*;

@Controller
@RequestMapping("/manutencao")
public class ManutencaoController {

    @Autowired
    private ManutencaoRepository manutencaoRepository;
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private VeiculosRepository veiculosRepository;
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ManutencaoPecaRepository manutencaoPecaRepository;

    // --- NOVA ORDEM DE SERVIÇO ---
    @GetMapping("/nova")
    public String novaOS(Model model, HttpSession session) {
        model.addAttribute("manutencaoDTO", new ManutencaoDTO());
        model.addAttribute("clientes", clientesRepository.findAll());
        model.addAttribute("tiposVeiculos", tipoVeiculoRepository.findAll());
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "manutencao/cadastrarOS";
    }

    @PostMapping("/salvar")
    public String salvarOS(@ModelAttribute ManutencaoDTO dto, HttpSession session) {

        // Cliente
        Cliente cliente = (dto.getIdCliente() != null)
                ? clientesRepository.findById(dto.getIdCliente()).orElseThrow()
                : salvarNovoCliente(dto);

        // Veículo
        Veiculo veiculo = (dto.getIdVeiculo() != null)
                ? veiculosRepository.findById(dto.getIdVeiculo()).orElseThrow()
                : salvarNovoVeiculo(dto, cliente);

        // Manutenção
        Manutencao manutencao = new Manutencao();
        manutencao.setCliente(cliente);
        manutencao.setVeiculo(veiculo);
        manutencao.setDataEntrada(dto.getDataEntrada());
        manutencao.setDescricao(dto.getDescricao());
        manutencao.setQuilometragem(dto.getQuilometragem());
        manutencao.setStatus(dto.getStatus());
        manutencao.setVlTotal(dto.getVlTotal());
        manutencao.setUsuarioResponsavel((Usuario) session.getAttribute("usuario"));

        manutencaoRepository.save(manutencao);

        return "redirect:/home";
    }

    private Cliente salvarNovoCliente(ManutencaoDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente(dto.getNomeCliente());
        cliente.setCpfCliente(dto.getCpfCliente());
        cliente.setTelefoneCliente(dto.getTelefoneCliente());
        cliente.setEmailCliente(dto.getEmailCliente());
        return clientesRepository.save(cliente);
    }

    private Veiculo salvarNovoVeiculo(ManutencaoDTO dto, Cliente cliente) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setCor(dto.getCor());
        veiculo.setAno(dto.getAno());
        veiculo.setMarca(dto.getMarca());
        veiculo.setCliente(cliente);
        return veiculosRepository.save(veiculo);
    }

    // --- LISTA ORDENS DE SERVIÇO ---
    @GetMapping("/lista")
    public String listarOS(Model model, HttpSession session) {
        model.addAttribute("manutencoes", manutencaoRepository.findAll());
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "manutencao/listaOS";
    }

    @ResponseBody
    @GetMapping("/lista-json")
    public List<ManutencaoDTO> listarOSJson() {
        List<Manutencao> lista = manutencaoRepository.findAll();
        return lista.stream().map(this::converterParaDTO).toList();
    }

    private ManutencaoDTO converterParaDTO(Manutencao os) {
        ManutencaoDTO dto = new ManutencaoDTO();
        dto.setId(os.getId());

        if (os.getCliente() != null) {
            dto.setIdCliente(os.getCliente().getIdCliente());
            dto.setNomeCliente(os.getCliente().getNomeCliente());
            dto.setCpfCliente(os.getCliente().getCpfCliente());
            dto.setTelefoneCliente(os.getCliente().getTelefoneCliente());
            dto.setEmailCliente(os.getCliente().getEmailCliente());
        }

        if (os.getVeiculo() != null) {
            dto.setIdVeiculo(os.getVeiculo().getIdVeiculo());
            dto.setPlaca(os.getVeiculo().getPlaca());
            dto.setModelo(os.getVeiculo().getModelo());
            dto.setCor(os.getVeiculo().getCor());
            dto.setAno(os.getVeiculo().getAno());
            dto.setMarca(os.getVeiculo().getMarca());
            if (os.getVeiculo().getTipo() != null) {
                dto.setTipoId(os.getVeiculo().getTipo().getIdTipoVeiculo());
            }
        }

        dto.setDataEntrada(os.getDataEntrada());
        dto.setQuilometragem(os.getQuilometragem());
        dto.setDescricao(os.getDescricao());
        dto.setStatus(os.getStatus());
        dto.setVlTotal(os.getVlTotal());

        return dto;
    }

    // --- PEÇAS DA MANUTENÇÃO ---
    @ResponseBody
    @GetMapping("/{id}/pecas/list")
    public ResponseEntity<?> listarPecasDaManutencao(@PathVariable Long id) {
        return manutencaoRepository.findById(id)
            .map(manutencao -> {
                // Inicializa LAZY
                manutencao.getPecas().size();
                // Mapeia para DTO
                var listaDTO = manutencao.getPecas().stream()
                                        .map(ManutencaoPecaDTO::new)
                                        .toList();
                return ResponseEntity.ok(listaDTO);
            })
            .orElseGet(() -> ResponseEntity.ok(List.of())); // retorna lista vazia se manutenção não existir
    }

    @ResponseBody
    @PostMapping("/{id}/pecas/add")
    public ResponseEntity<?> adicionarPeca(@PathVariable Long id, @RequestBody Map<String,Object> payload) {
        var manutencaoOpt = manutencaoRepository.findById(id);
        if (manutencaoOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("success", false, "message", "Manutenção não encontrada"));
        }

        Long produtoId = Long.valueOf(payload.get("produtoId").toString());
        int quantidade = Integer.parseInt(payload.get("quantidade").toString());
        double valorUnit = Double.parseDouble(payload.get("valorUnit").toString());

        var produtoOpt = produtoRepository.findById(produtoId);
        if (produtoOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("success", false, "message", "Produto não encontrado"));
        }

        var produto = produtoOpt.get();
        if (produto.getQuantidade() < quantidade) {
            return ResponseEntity.ok(Map.of("success", false, "message",
                    "Estoque insuficiente. Disponível: " + produto.getQuantidade()));
        }

        var mp = new ManutencaoPeca();
        mp.setManutencao(manutencaoOpt.get());
        mp.setProduto(produto);
        mp.setQuantidade(quantidade);
        mp.setVlUnitario(java.math.BigDecimal.valueOf(valorUnit));

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoRepository.save(produto);
        manutencaoPecaRepository.save(mp);

        return ResponseEntity.ok(Map.of("success", true, "manutencaoPecaId", mp.getId()));
    }

    @ResponseBody
    @PostMapping("/{id}/pecas/update")
    public ResponseEntity<?> atualizarPeca(@PathVariable Long id, @RequestBody Map<String,Object> payload) {
        Long manPecaId = Long.valueOf(payload.get("id").toString());
        int novaQtde = Integer.parseInt(payload.get("quantidade").toString());

        var mpOpt = manutencaoPecaRepository.findById(manPecaId);
        if (mpOpt.isEmpty()) return ResponseEntity.status(404)
                .body(Map.of("success", false, "message", "Peça não encontrada"));

        var mp = mpOpt.get();
        var produto = mp.getProduto();
        int delta = novaQtde - mp.getQuantidade();

        if (delta > 0 && produto.getQuantidade() < delta) {
            return ResponseEntity.ok(Map.of("success", false,
                    "message", "Estoque insuficiente. Disponível: " + produto.getQuantidade()));
        }

        produto.setQuantidade(produto.getQuantidade() - delta);
        produtoRepository.save(produto);

        mp.setQuantidade(novaQtde);
        manutencaoPecaRepository.save(mp);

        return ResponseEntity.ok(Map.of("success", true));
    }

    // --- TELAS DE EDIÇÃO / DETALHES ---
    @GetMapping("/editar/{id}")
    public String editarOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        return "manutencao/editarOS";
    }

    @GetMapping("/pecas/{id}")
    public String incluirPecas(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        model.addAttribute("pecas", manutencao.getPecas());
        return "manutencao/incluirPecas";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        return "manutencao/detalhesOS";
    }
}
