package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
// Repositórios necessários para agregação de dados:
import sgmv.demo.repository.ManutencaoRepository;
import sgmv.demo.repository.FuncionarioRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random; 

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    // Injeção de Repositórios (para buscar dados do banco)
    @Autowired 
    private ManutencaoRepository manutencaoRepository;
    @Autowired 
    private FuncionarioRepository funcionarioRepository;

    // --- UTILITY: MOCK DATA (Substituir pela Lógica Real de Agregação JPA/SQL) ---
    private List<Map<String, Object>> generateMockData(String type) {
        Random rand = new Random();
        if ("financial".equals(type)) {
            // Valores Fictícios para Finanças (Gráfico de Linha)
            return List.of(
                Map.of("label", "Mai", "value", BigDecimal.valueOf(1200 + rand.nextInt(500))),
                Map.of("label", "Jun", "value", BigDecimal.valueOf(1900 + rand.nextInt(500))),
                Map.of("label", "Jul", "value", BigDecimal.valueOf(3000 + rand.nextInt(500))),
                Map.of("label", "Ago", "value", BigDecimal.valueOf(500 + rand.nextInt(500))),
                Map.of("label", "Set", "value", BigDecimal.valueOf(2200 + rand.nextInt(500))),
                Map.of("label", "Out", "value", BigDecimal.valueOf(1000 + rand.nextInt(500)))
            );
        } else if ("inventory".equals(type)) {
            // Valores Fictícios para Estoque (Gráfico de Rosca)
            return List.of(
                Map.of("label", "Óleo", "value", BigDecimal.valueOf(30)),
                Map.of("label", "Pneu", "value", BigDecimal.valueOf(20)),
                Map.of("label", "Filtro", "value", BigDecimal.valueOf(25)),
                Map.of("label", "Pastilha", "value", BigDecimal.valueOf(25))
            );
        } else if ("productivity".equals(type)) {
            // Valores Fictícios para Produtividade (Gráfico de Barra)
            return List.of(
                Map.of("label", "João", "value", BigDecimal.valueOf(4.5)), 
                Map.of("label", "Maria", "value", BigDecimal.valueOf(6.2)),
                Map.of("label", "Carlos", "value", BigDecimal.valueOf(3.8))
            );
        }
        return new ArrayList<>();
    }


    // =================================================================
    // 1. FINANÇAS: Valor Total de OSs por Mês (Linha)
    // Rota: /dashboard/financeiro/ultimos6meses
    // =================================================================
    @GetMapping("/financeiro/ultimos6meses")
    public List<Map<String, Object>> getDadosFinanceiros() {

        return generateMockData("financial");
    }

    // =================================================================
    // 2. ESTOQUE: Itens Mais Usados (Rosca/Doughnut)
    // Rota: /dashboard/estoque/maisusados
    // =================================================================
    @GetMapping("/estoque/maisusados")
    public List<Map<String, Object>> getItensMaisUsados() {

        return generateMockData("inventory");
    }
    
    // =================================================================
    // 3. PRODUTIVIDADE: Tempo Médio por Funcionário (Barra)
    // Rota: /dashboard/produtividade/tempomedio
    // =================================================================
    @GetMapping("/produtividade/tempomedio")
    public List<Map<String, Object>> getProdutividade() {

        // agrupada por FuncionarioExecutor.
        return generateMockData("productivity");
    }
}