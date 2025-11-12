package sgmv.demo.controller;

import sgmv.demo.model.Alerta;
import sgmv.demo.model.ManutencaoAgendada;
import sgmv.demo.repository.AlertaRepository;
import sgmv.demo.repository.ManutencaoAgendadaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.Serializable; // Importação necessária para resolver o tipo

@Controller
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaRepository alertaRepository;
    private final ManutencaoAgendadaRepository manutencaoAgendadaRepository;

    public AlertaController(
        AlertaRepository alertaRepository,
        ManutencaoAgendadaRepository manutencaoAgendadaRepository) {
        
        this.alertaRepository = alertaRepository;
        this.manutencaoAgendadaRepository = manutencaoAgendadaRepository;
    }

    /**
     * Exibe a Central de Prevenção e Alertas, calculando os dias restantes no lado Java.
     */
    @GetMapping
    public String mostrarCentral(Model model) {
        
        LocalDate hoje = LocalDate.now();
        final int DIAS_VISIVEIS = 90; 
        LocalDate dataLimiteBusca = hoje.plusDays(DIAS_VISIVEIS);
        
        // 1. Busque os Agendamentos com Fetch Join
        List<ManutencaoAgendada> agendamentos = 
                           manutencaoAgendadaRepository.findAgendamentosProximosComDetalhes(dataLimiteBusca);
        
        // 2. Calcule os Dias Restantes no Java e Mapeie para resolver o 'Type mismatch'
        // Mapeamos para Map<String, Object> para que o Thymeleaf consiga acessá-los.
        List<Map<String, Object>> agendamentosMapeados = agendamentos.stream()
            .map(a -> {
                long diasRestantes = ChronoUnit.DAYS.between(hoje, a.getDataAgendada());
                
                // Retorna um Map com o objeto agendamento original e a variável calculada
                // Usamos a tipagem segura, garantindo que o Map seja compatível com <Object>
                return Map.<String, Object>of(
                    "agendamento", a,
                    "diasRestantes", diasRestantes
                );
            })
            .collect(Collectors.toList());

        // 3. Adiciona as listas ao Model
        model.addAttribute("alertasCriticos", alertaRepository.findByEstaLidoFalseOrderByDataCriacaoDesc());
        model.addAttribute("agendamentosFuturos", agendamentosMapeados); 

        return "alerta/centralPreventiva"; 
    }

    /**
     * Marca um alerta específico como lido e redireciona para a Central.
     */
    @PostMapping("/marcar-lido/{id}")
    public String marcarComoLido(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Alerta> alertaOpt = alertaRepository.findById(id);

        if (alertaOpt.isPresent()) {
            Alerta alerta = alertaOpt.get();
            alerta.setEstaLido(true);
            alertaRepository.save(alerta);
            ra.addFlashAttribute("sucesso", "Alerta marcado como lido com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "Alerta não encontrado.");
        }

        return "redirect:/alertas";
    }
}