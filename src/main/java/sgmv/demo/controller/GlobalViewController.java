package sgmv.demo.controller;

import sgmv.demo.service.AlertaService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalViewController {

    private final AlertaService alertaService;

    // Injeta o AlertaService para acesso à contagem
    public GlobalViewController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    /**
     * Adiciona o valor de 'notificacoes' (contagem de alertas não lidos) ao Model
     * de TODAS as views. O nome "notificacoes" deve corresponder ao parâmetro
     * que você usa na sua navbar (th:text="${notificacoes}").
     */
    @ModelAttribute("notificacoes")
    public long getContagemAlertasNaoLidos() {
        // Esta variável será injetada em TODAS as views que usam a navbar.
        return alertaService.contarAlertasNaoLidos();
    }
}