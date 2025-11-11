package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.util.List;
import java.util.Map;
import java.util.Optional;

import sgmv.demo.dto.ManutencaoDTO;
import sgmv.demo.dto.ManutencaoPecaDTO;
import sgmv.demo.dto.ManutencaoServicoDTO;
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
    @Autowired
    private ManutencaoServicoRepository manutencaoServicoRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Utilitário para buscar o Funcionario logado
    private Funcionario getFuncionarioExecutor(HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        if (idUsuario == null) return null;
        return funcionarioRepository.findByContaUsuarioIdUsuario(idUsuario).orElse(null); 
    }

    // --- NOVA ORDEM DE SERVIÇO (Mantido) ---
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
        Cliente cliente = (dto.getIdCliente() != null)
                ? clientesRepository.findById(dto.getIdCliente()).orElseThrow()
                : salvarNovoCliente(dto);
        Veiculo veiculo = (dto.getIdVeiculo() != null)
                ? veiculosRepository.findById(dto.getIdVeiculo()).orElseThrow()
                : salvarNovoVeiculo(dto, cliente);

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
    
    // --- CONVERTER PARA DTO (Final e Completo) ---
    
    // ManutencaoController.java

    private ManutencaoDTO converterParaDTO(Manutencao os) {
    ManutencaoDTO dto = new ManutencaoDTO();
    final BigDecimal[] totalServicos = { BigDecimal.ZERO };
    final BigDecimal[] totalPecas = { BigDecimal.ZERO };
    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // -----------------------------------------------------------
    // 1. Dados Básicos da Manutenção
    // -----------------------------------------------------------
    dto.setId(os.getId());
    dto.setDataEntrada(os.getDataEntrada());
    dto.setDescricao(os.getDescricao());
    dto.setQuilometragem(os.getQuilometragem());
    dto.setStatus(os.getStatus());
    if (os.getDataHoraFim() != null) {
        dto.setDataEntrega(os.getDataHoraFim().toLocalDate());
    } else {
        dto.setDataEntrega(os.getDataConclusao()); // fallback (se algum registro antigo tiver essa coluna)
    }

    // Mapeamento de Produtividade (Hora Início/Fim)
    if (os.getDataHoraInicio() != null) {
        dto.setDataHoraInicio(os.getDataHoraInicio().format(FORMATTER));
    }
    if (os.getDataHoraFim() != null) {
        dto.setDataHoraFim(os.getDataHoraFim().format(FORMATTER));
    }
    if (os.getFuncionarioExecutor() != null) {
        dto.setProfissional(os.getFuncionarioExecutor().getNomeFuncionario());
    }

    // Profissional principal
    if (os.getFuncionario() != null) {
        dto.setProfissional(os.getFuncionario().getNomeFuncionario());
    }

    // -----------------------------------------------------------
    // 2. Dados do Cliente
    // -----------------------------------------------------------
    if (os.getCliente() != null) {
        dto.setIdCliente(os.getCliente().getIdCliente());
        dto.setNomeCliente(os.getCliente().getNomeCliente());
        dto.setCpfCliente(os.getCliente().getCpfCliente());
        dto.setTelefoneCliente(os.getCliente().getTelefoneCliente());
        dto.setEmailCliente(os.getCliente().getEmailCliente());
    }

    // -----------------------------------------------------------
    // 3. Dados do Veículo
    // -----------------------------------------------------------
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

    // -----------------------------------------------------------
    // 4. Processamento de Serviços e Peças
    // -----------------------------------------------------------

    // --- Serviços ---
    List<ManutencaoServicoDTO> servicosDTO = (os.getServicos() != null ? os.getServicos() : List.<ManutencaoServico>of())
        .stream()
        .map(serv -> {
            ManutencaoServicoDTO servDto = new ManutencaoServicoDTO(serv);
           BigDecimal qtd = BigDecimal.valueOf(serv.getQuantidade());
            BigDecimal vlUnit = serv.getVlUnitario() != null ? serv.getVlUnitario() : BigDecimal.ZERO;
            BigDecimal subtotal = vlUnit.multiply(qtd);
            totalServicos[0] = totalServicos[0].add(subtotal);
            servDto.setValorTotal(subtotal);
            return servDto;
        })
        .toList();
    dto.setServicos(servicosDTO);
    dto.setTotalServicos(totalServicos[0]);

    // --- Peças ---
    List<ManutencaoPecaDTO> pecasDTO = (os.getPecas() != null ? os.getPecas() : List.<ManutencaoPeca>of())
        .stream()
        .map(peca -> {
            ManutencaoPecaDTO pecaDto = new ManutencaoPecaDTO(peca);
            BigDecimal qtd = BigDecimal.valueOf(
                peca.getQuantidade() != 0 ? peca.getQuantidade() : 1
            );
            BigDecimal vlUnit = peca.getVlUnitario() != null ? peca.getVlUnitario() : BigDecimal.ZERO;
            BigDecimal subtotal = vlUnit.multiply(qtd);
            totalPecas[0] = totalPecas[0].add(subtotal);
            pecaDto.setValorTotal(subtotal);
            return pecaDto;
        })
        .toList();

    dto.setPecas(pecasDTO);
    dto.setTotalPecas(totalPecas[0]);

    // -----------------------------------------------------------
    // 5. Total Geral (Serviços + Peças)
    // -----------------------------------------------------------
    BigDecimal totalGeral = totalServicos[0].add(totalPecas[0]);
    dto.setVlTotal(totalGeral);

    return dto;
}



    // --- LISTA ORDENS DE SERVIÇO ---
    
    // Mapeamento flexível para URLs com ou sem barra final
    @GetMapping({"/lista", "/lista/"})
    public String listarOS(Model model, HttpSession session) {
        model.addAttribute("manutencoes", manutencaoRepository.findAll());
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        return "manutencao/listaOS";
    }

    /** [AJAX] Lista todas as OSs em JSON, garantindo que Cliente e Veículo sejam carregados. */
    @ResponseBody
    @GetMapping("/lista-json")
    public List<ManutencaoDTO> listarOSJson() {
        List<Manutencao> lista = manutencaoRepository.findAll();
        
        // --- FORÇAR INICIALIZAÇÃO DE RELACIONAMENTOS VITAIS ---
        for (Manutencao os : lista) {
            if (os.getCliente() != null) {
                os.getCliente().getNomeCliente(); 
            }
            if (os.getVeiculo() != null) {
                os.getVeiculo().getModelo(); 
            }
            if (os.getPecas() != null) {
                os.getPecas().size(); 
            }
            if (os.getServicos() != null) {
                os.getServicos().size();
            }
        }
        // ----------------------------------------------------

        return lista.stream().map(this::converterParaDTO).toList();
    }


    // --- ROTAS DE PRODUTIVIDADE E STATUS (NOVAS) ---
    // ... (iniciarManutencao e finalizarManutencao mantidos) ...

    @PostMapping("/{id}/status/iniciar")
    @ResponseBody
    public ResponseEntity<?> iniciarManutencao(@PathVariable Long id, HttpSession session) {
        Funcionario executor = getFuncionarioExecutor(session);
        if (executor == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Funcionário não logado ou não vinculado."));
        }
        
        return manutencaoRepository.findById(id)
            .map(manutencao -> {
                String status = manutencao.getStatus();
                if (status.equals("ABERTA") || status.equals("PENDENTE")) {
                    manutencao.setStatus("EM_ANDAMENTO");
                    manutencao.setDataHoraInicio(LocalDateTime.now());
                    manutencao.setFuncionarioExecutor(executor);
                    manutencaoRepository.save(manutencao);
                    return ResponseEntity.ok(Map.of("success", true, "message", "Serviço iniciado."));
                }
                return ResponseEntity.ok(Map.of("success", false, "message", "O serviço já está em andamento ou foi concluído."));
            })
            .orElseGet(() -> ResponseEntity.status(404).body(Map.of("success", false, "message", "OS não encontrada.")));
    }

    @PostMapping("/{id}/status/finalizar")
    @ResponseBody
    public ResponseEntity<?> finalizarManutencao(@PathVariable Long id) {
        return manutencaoRepository.findById(id)
            .map(manutencao -> {
                String status = manutencao.getStatus();
                if (status.equals("EM_ANDAMENTO")) {
                    manutencao.setStatus("CONCLUIDA");
                    manutencao.setDataHoraFim(LocalDateTime.now());
                    manutencaoRepository.save(manutencao);
                    return ResponseEntity.ok(Map.of("success", true, "message", "Serviço finalizado."));
                }
                return ResponseEntity.ok(Map.of("success", false, "message", "O serviço ainda não foi iniciado ou já foi finalizado."));
            })
            .orElseGet(() -> ResponseEntity.status(404).body(Map.of("success", false, "message", "OS não encontrada.")));
    }
    
    // --- PEÇAS DA MANUTENÇÃO (Existente) ---
    // ... (listarPecas, adicionarPeca, atualizarPeca, listarServicos, removerServico mantidos) ...

    @ResponseBody
    @GetMapping("/{id}/pecas/list")
    public ResponseEntity<?> listarPecasDaManutencao(@PathVariable Long id) {
        return manutencaoRepository.findById(id)
            .map(manutencao -> {
                manutencao.getPecas().size();
                var listaDTO = manutencao.getPecas().stream()
                                         .map(ManutencaoPecaDTO::new)
                                         .toList();
                return ResponseEntity.ok(listaDTO);
            })
            .orElseGet(() -> ResponseEntity.ok(List.of()));
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

    @ResponseBody
    @GetMapping("/{id}/servicos/list")
    public ResponseEntity<?> listarServicosDaManutencao(@PathVariable Long id) {
        return manutencaoRepository.findById(id)
            .map(manutencao -> {
                manutencao.getServicos().size(); 
                var listaDTO = manutencao.getServicos().stream()
                                         .map(ManutencaoServicoDTO::new) 
                                         .toList();
                return ResponseEntity.ok(listaDTO);
            })
            .orElseGet(() -> ResponseEntity.ok(List.of()));
    }

    @ResponseBody
    @PostMapping("/{id}/servicos/add")
    public ResponseEntity<?> adicionarServico(@PathVariable Long id, @RequestBody Map<String,Object> payload) {
        Optional<Manutencao> manutencaoOpt = manutencaoRepository.findById(id);
        if (manutencaoOpt.isEmpty()) {
            return ResponseEntity.status(404)
                 .body(Map.of("success", false, "message", "Manutenção não encontrada"));
        }

        String descricao = payload.get("descricao").toString();
        int quantidade = Integer.parseInt(payload.get("quantidade").toString());
        double valorUnitario = Double.parseDouble(payload.get("valorUnitario").toString()); 

        ManutencaoServico ms = new ManutencaoServico();
        ms.setManutencao(manutencaoOpt.get());
        ms.setDescricao(descricao);
        ms.setQuantidade(quantidade);
        ms.setVlUnitario(java.math.BigDecimal.valueOf(valorUnitario));

        manutencaoServicoRepository.save(ms);

        return ResponseEntity.ok(Map.of("success", true, "manutencaoServicoId", ms.getId()));
    }

    @ResponseBody
    @PostMapping("/{id}/servicos/remove")
    public ResponseEntity<?> removerServico(@PathVariable Long id, @RequestParam Long idServico) {
        try {
            manutencaoServicoRepository.deleteById(idServico);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Erro ao remover serviço."));
        }
    }


    // --- TELAS DE EDIÇÃO / DETALHES ---
    @GetMapping("/editar/{id}")
    public String editarOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findByIdWithDetails(id)
             .orElseThrow(() -> new IllegalArgumentException("OS não encontrada."));
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        return "manutencao/editarOS";
    }

    @GetMapping("/pecas/{id}")
    public String incluirPecas(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findByIdWithDetails(id)
             .orElseThrow(() -> new IllegalArgumentException("OS não encontrada."));
             
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        model.addAttribute("pecas", manutencao.getPecas());
        return "manutencao/incluirPecas";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findByIdWithDetails(id) 
            .orElseThrow(() -> new IllegalArgumentException("OS não encontrada"));
            
        model.addAttribute("manutencaoDTO", converterParaDTO(manutencao));
        return "manutencao/detalhesOS";
    }

    @GetMapping("/nova/agendamento/{idAgendamento}")
    public String novaOSBaseadaEmAgendamento(@PathVariable Long idAgendamento, Model model, HttpSession session) {
        
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
            .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + idAgendamento));
            
        ManutencaoDTO dto = new ManutencaoDTO();
        
        dto.setNomeCliente(agendamento.getNomeCliente()); 
        dto.setDescricao(agendamento.getDescricaoServico());
        dto.setDataEntrada(LocalDate.now());
        dto.setStatus("ABERTA"); 
        
        model.addAttribute("manutencaoDTO", dto);
        
        model.addAttribute("clientes", clientesRepository.findAll());
        model.addAttribute("tiposVeiculos", tipoVeiculoRepository.findAll());
        model.addAttribute("funcionarios", funcionarioRepository.findAll()); 
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        
        return "manutencao/cadastrarOS"; 
    }

    @GetMapping("/imprimir/{id}")
    public String imprimirOS(@PathVariable Long id, Model model) {
        
        Manutencao manutencao = manutencaoRepository.findByIdWithDetails(id) 
            .orElseThrow(() -> new IllegalArgumentException("OS não encontrada para impressão."));
            
        // 2. Converte a entidade carregada para DTO
        ManutencaoDTO dto = converterParaDTO(manutencao);
            
        model.addAttribute("manutencaoDTO", dto);
        
        // Retorna o template de impressão que criamos
        return "manutencao/imprimirOSpdf";
    }
}