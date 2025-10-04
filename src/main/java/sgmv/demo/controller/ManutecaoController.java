package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sgmv.demo.dto.ManutencaoDTO;
import sgmv.demo.model.Cliente;
import sgmv.demo.model.Manutencao;
import sgmv.demo.model.Usuario;
import sgmv.demo.model.Veiculo;
import sgmv.demo.repository.ManutencaoRepository;
import sgmv.demo.repository.TipoVeiculoRepository;
import sgmv.demo.repository.ClientesRepository;
import sgmv.demo.repository.VeiculosRepository;

@Controller
@RequestMapping("/manutencao")
public class ManutecaoController {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private VeiculosRepository veiculosRepository;

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

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
        Cliente cliente;

        if (dto.getIdCliente() != null) {
            // cliente já existente
            cliente = clientesRepository.findById(dto.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        } else {
            // novo cliente
            cliente = new Cliente();
            cliente.setNomeCliente(dto.getNomeCliente());
            cliente.setCpfCliente(dto.getCpfCliente());
            cliente.setTelefoneCliente(dto.getTelefoneCliente());
            cliente.setEmailCliente(dto.getEmailCliente());
            cliente = clientesRepository.save(cliente);
        }

        Veiculo veiculo;
        if (dto.getIdVeiculo() != null) {
            veiculo = veiculosRepository.findById(dto.getIdVeiculo())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        } else {
            veiculo = new Veiculo();
            veiculo.setPlaca(dto.getPlaca());
            veiculo.setModelo(dto.getModelo());
            veiculo.setCor(dto.getCor());
            veiculo.setAno(dto.getAno());
            veiculo.setMarca(dto.getMarca());
            veiculo.setCliente(cliente);
            veiculo = veiculosRepository.save(veiculo);
        }

        // Cria a manutenção vinculando cliente/veículo
        Manutencao manutencao = new Manutencao();
        manutencao.setCliente(cliente); // precisa ter o campo na entidade Manutencao se ainda não tiver
        manutencao.setVeiculo(veiculo);
        manutencao.setDataEntrada(dto.getDataEntrada());
        manutencao.setDescricao(dto.getDescricao());
        manutencao.setQuilometragem(dto.getQuilometragem());
        manutencao.setStatus(dto.getStatus());
        manutencao.setVlTotal(dto.getVlTotal());

        // usuário logado
        manutencao.setUsuarioResponsavel((Usuario) session.getAttribute("usuario"));

        manutencaoRepository.save(manutencao);

        return "redirect:/home";
    }

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

        return lista.stream().map(os -> {
            ManutencaoDTO dto = new ManutencaoDTO();

            // ID da Ordem de Serviço
            dto.setId(os.getId());

            // Cliente
            if (os.getCliente() != null) {
                dto.setIdCliente(os.getCliente().getIdCliente());
                dto.setNomeCliente(os.getCliente().getNomeCliente());
                dto.setCpfCliente(os.getCliente().getCpfCliente());
                dto.setTelefoneCliente(os.getCliente().getTelefoneCliente());
                dto.setEmailCliente(os.getCliente().getEmailCliente());
            }

            // Veículo
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

            // Manutenção
            dto.setDataEntrada(os.getDataEntrada());
            dto.setQuilometragem(os.getQuilometragem());
            dto.setDescricao(os.getDescricao());
            dto.setStatus(os.getStatus());
            dto.setVlTotal(os.getVlTotal());

            return dto;
        }).toList();
    }


    @GetMapping("/editar/{id}")
    public String editarOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencao", manutencao);
        return "manutencao/editarOS"; // formulário de edição
    }

    @GetMapping("/pecas/{id}")
    public String incluirPecas(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencao", manutencao);
        model.addAttribute("pecas", manutencao.getPecas());
        return "manutencao/incluirPecas"; // tela para adicionar peças
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesOS(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        model.addAttribute("manutencao", manutencao);
        return "manutencao/detalhesOS";
    }
}
