package sgmv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgmv.demo.repository.ManutencaoRepository;
import sgmv.demo.repository.FuncionarioRepository;
import sgmv.demo.repository.ManutencaoPecaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    // Injeção de Repositórios
    @Autowired 
    private ManutencaoRepository manutencaoRepository;
    @Autowired 
    private FuncionarioRepository funcionarioRepository;
    @Autowired 
    private ManutencaoPecaRepository manutencaoPecaRepository;

    // Utilitário para formatar o resultado em JSON {label: ..., value: ...}
    private Map<String, Object> mapToLabelValue(Object label, Object value) {
        return Map.of("label", label, "value", value);
    }

    // =================================================================
    // 1. FINANÇAS: Valor Total de OSs por Mês (Linha)
    // =================================================================
    @GetMapping("/financeiro/ultimos6meses")
    public List<Map<String, Object>> getDadosFinanceiros() {
        // Chama a consulta Nativa (retorna {month, year, total})
        List<Object[]> results = manutencaoRepository.findTotalValueLastSixMonths(); 
        
        return results.stream()
            .map(row -> {
                // row[0] = Mês (String), row[1] = Ano (Integer/Long), row[2] = Total (BigDecimal)
                String label = row[0].toString() + "/" + row[1].toString();
                BigDecimal totalValue = (BigDecimal) row[2];
                return mapToLabelValue(label, totalValue);
            })
            .toList();
    }

    // =================================================================
    // 2. ESTOQUE: Itens Mais Usados (Rosca/Doughnut)
    // =================================================================
    @GetMapping("/estoque/maisusados")
    public List<Map<String, Object>> getItensMaisUsados() {
        // Chama a consulta JPQL que retorna {nomeProduto, totalQuantidade}
        List<Object[]> results = manutencaoPecaRepository.findTop5MostUsedProducts();
        
        // Retorna apenas os 5 primeiros
        return results.stream()
            .limit(5)
            .map(row -> mapToLabelValue(row[0], row[1]))
            .toList();
    }
    
    // =================================================================
    // 3. PRODUTIVIDADE: Tempo Médio por Funcionário (Barra)
    // =================================================================
    @GetMapping("/produtividade/tempomedio")
    public List<Map<String, Object>> getProdutividade() {
        // Chama a consulta Nativa que retorna {nomeFuncionario, avgTempoEmHoras}
        List<Object[]> results = funcionarioRepository.findAverageServiceTimeByEmployee();
        
        return results.stream()
            .map(row -> mapToLabelValue(row[0], row[1]))
            .toList();
    }
}