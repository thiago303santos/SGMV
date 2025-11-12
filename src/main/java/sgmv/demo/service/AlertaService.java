package sgmv.demo.service;

import sgmv.demo.model.Alerta;
import sgmv.demo.model.Manutencao;
import sgmv.demo.model.ManutencaoAgendada;
import sgmv.demo.model.Produto;
import sgmv.demo.model.TipoAlerta;
import sgmv.demo.repository.AlertaRepository;
import sgmv.demo.repository.ManutencaoAgendadaRepository;
import sgmv.demo.repository.ManutencaoRepository;
import sgmv.demo.repository.ProdutoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlertaService {

    private static final Logger logger = LoggerFactory.getLogger(AlertaService.class);

    private final AlertaRepository alertaRepository;
    private final ProdutoRepository produtoRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final ManutencaoAgendadaRepository manutencaoAgendadaRepository;

    // Construtor atualizado para injetar TODAS as dependências
    public AlertaService(AlertaRepository alertaRepository, ProdutoRepository produtoRepository, ManutencaoRepository manutencaoRepository, ManutencaoAgendadaRepository manutencaoAgendadaRepository) {
        this.alertaRepository = alertaRepository;
        this.produtoRepository = produtoRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.manutencaoAgendadaRepository = manutencaoAgendadaRepository;  
    }

    /**
     * Retorna a contagem de alertas não lidos para exibição na navbar.
     */
    public long contarAlertasNaoLidos() {
        // Exemplo de log para monitorar a execução
        logger.debug("Contando alertas não lidos...");
        return alertaRepository.countByEstaLidoFalse();
    }
    
    // =================================================================
    // NOVO JOB: VERIFICAÇÃO DE ESTOQUE CRÍTICO
    // Executado a cada 1 minuto (para fins de teste rápido)
    // =================================================================
    
    @Scheduled(fixedRate = 60000) // Roda a cada 60 segundos
    public void gerarAlertasEstoqueCritico() {
        logger.info("Executando Job: Verificação de Estoque Crítico...");
        
        // 1. Busca produtos onde quantidade <= estoqueMinimo
        List<Produto> produtosCriticos = produtoRepository.findByQuantidadeLessThanEqualEstoqueMinimo();

        for (Produto produto : produtosCriticos) {
            
            // 2. Verifica se já existe um alerta ATIVO para este produto
            // Isso evita spam de alertas repetidos
            boolean alertaJaExiste = alertaRepository.existsByTipoAlertaAndIdEntidadeRelacionadaAndEstaLidoFalse(
                TipoAlerta.ESTOQUE, 
                produto.getIdProduto()
            );

            if (!alertaJaExiste) {
                // 3. Cria o Alerta
                String titulo = String.format("🚨 ESTOQUE CRÍTICO: %s", produto.getNomeProduto());
                String descricao = String.format(
                    "O item **%s** está com apenas **%d unidades** em estoque. O mínimo necessário é **%d**. Ações urgentes necessárias.",
                    produto.getNomeProduto(), produto.getQuantidade(), produto.getEstoqueMinimo()
                );
                
                Alerta novoAlerta = new Alerta();
                novoAlerta.setTitulo(titulo);
                novoAlerta.setDescricao(descricao);
                novoAlerta.setTipoAlerta(TipoAlerta.ESTOQUE);
                novoAlerta.setIdEntidadeRelacionada(produto.getIdProduto()); // ID do Produto
                novoAlerta.setDataCriacao(java.time.LocalDateTime.now());
                novoAlerta.setEstaLido(false);

                // 4. Salva o alerta
                alertaRepository.save(novoAlerta);
                logger.error("ALERTA CRÍTICO DE ESTOQUE GERADO para: {}", produto.getNomeProduto());
            } else {
                logger.debug("Alerta de estoque já existe e está ativo para: {}", produto.getNomeProduto());
            }
        }
        
        logger.info("Job de Estoque Crítico concluído. {} itens críticos encontrados.", produtosCriticos.size());
    }

    // Seu método de Vencimento de Produtos (com a correção da injeção)
    @Scheduled(cron = "0 0 0 * * *")
    public void gerarAlertasVencimentoProdutos() {
        logger.info("Executando Job: Verificação de Vencimento de Produtos...");
        
        final int DIAS_LIMITE_ALERTA = 60; // Avisar 60 dias antes
        
        // 1. Calcule a data de corte (Hoje + 60 dias)
        LocalDate dataLimite = LocalDate.now().plusDays(DIAS_LIMITE_ALERTA);
        
        // 2. Busque os produtos que vencem na data limite ou antes
        List<Produto> produtosVencendo = produtoRepository.findByPerecivelTrueAndDataValidadeLessThanEqual(dataLimite);

        for (Produto produto : produtosVencendo) {
            
            // Lógica para evitar spam de alertas repetidos (usando AlertaService)
            boolean alertaJaExiste = alertaRepository.existsByTipoAlertaAndIdEntidadeRelacionadaAndEstaLidoFalse(
                TipoAlerta.DOCUMENTO, // Reutilizando DOCUMENTO para perecível
                produto.getIdProduto()
            );

            if (!alertaJaExiste) {
                // Verifica quantos dias faltam
                long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), produto.getDataValidade());

                // 3. Crie o Alerta
                String titulo = String.format("⚠️ Produto Vencendo: %s", produto.getNomeProduto());
                String descricao = String.format(
                    "O item **%s** (%s) está com a data de validade (%s) se aproximando. Faltam apenas **%d dias**.",
                    produto.getNomeProduto(), produto.getFabricante(), produto.getDataValidade(), diasRestantes
                );
                
                Alerta novoAlerta = new Alerta();
                novoAlerta.setTitulo(titulo);
                novoAlerta.setDescricao(descricao);
                novoAlerta.setTipoAlerta(TipoAlerta.DOCUMENTO);
                novoAlerta.setIdEntidadeRelacionada(produto.getIdProduto());
                novoAlerta.setDataCriacao(java.time.LocalDateTime.now());

                // 4. Salve o alerta
                alertaRepository.save(novoAlerta);
                logger.warn("Alerta de Vencimento gerado para: {}", produto.getNomeProduto());
            }
        }
        
        logger.info("Job de Vencimento concluído. {} alertas gerados.", produtosVencendo.size());
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void gerarAlertasAtraso() {
        logger.info("Executando Job: Verificação de Atraso na Execução de Manutenções...");
        
        // 1. Busque todas as manutenções que deveriam ter sido concluídas até hoje (ou antes)
        // NOTA: Assumimos que findManutencoesAtrasadas filtra pelo status 'EM ANDAMENTO' ou similar.
        List<Manutencao> manutencoesAtrasadas = manutencaoRepository.findManutencoesAtrasadas(LocalDate.now());

        for (Manutencao manutencao : manutencoesAtrasadas) {
            
            // Lógica para evitar spam de alertas repetidos
            boolean alertaJaExiste = alertaRepository.existsByTipoAlertaAndIdEntidadeRelacionadaAndEstaLidoFalse(
                TipoAlerta.ATRASO_OS, 
                manutencao.getId()
            );

            if (!alertaJaExiste) {
                // Verifica há quantos dias está atrasada (Ex: dataConclusao prevista era há 3 dias)
                // Usamos dataEntrada como fallback para comparação se dataConclusao não for um campo de prazo
                long diasAtraso = ChronoUnit.DAYS.between(manutencao.getDataEntrada(), LocalDate.now());
                
                String placaVeiculo = manutencao.getVeiculo() != null ? manutencao.getVeiculo().getPlaca() : "N/D";

                String titulo = String.format("❌ OS ATRASADA: Veículo %s", placaVeiculo);
                String descricao = String.format(
                    "A Ordem de Serviço #%d para o veículo **%s** está **atrasada em %d dias** (desde a entrada). Verificar status.",
                    manutencao.getId(),
                    placaVeiculo,
                    diasAtraso
                );
                
                // 3. Crie o Alerta
                Alerta novoAlerta = new Alerta();
                novoAlerta.setTitulo(titulo);
                novoAlerta.setDescricao(descricao);
                novoAlerta.setTipoAlerta(TipoAlerta.ATRASO_OS);
                novoAlerta.setIdEntidadeRelacionada(manutencao.getId());
                novoAlerta.setDataCriacao(java.time.LocalDateTime.now());

                // 4. Salve o alerta
                alertaRepository.save(novoAlerta);
                logger.warn("Alerta de Atraso gerado para OS #{}", manutencao.getId());
            }
        }
        
        logger.info("Job de Atraso concluído. {} alertas gerados.", manutencoesAtrasadas.size());
    }

    @Scheduled(cron = "0 45 0 * * *")
    public void gerarAlertasManutencaoAgendada() {
        logger.info("Executando Job: Verificação de Agendamentos Preventivos...");
        
        // 1. Busque todos os agendamentos PENDENTES
        final int DIAS_MAX_BUSCA = 60;
        LocalDate dataLimiteBusca = LocalDate.now().plusDays(DIAS_MAX_BUSCA);

        List<ManutencaoAgendada> agendamentos = manutencaoAgendadaRepository.findAgendamentosProximos(dataLimiteBusca);

        for (ManutencaoAgendada agendamento : agendamentos) {
            
            // 2. Calcule a data limite REAL de alerta (usando a regra do item)
            int diasAlertaRegra = agendamento.getRegra().getDiasAlerta();
            LocalDate dataLimiteAlerta = agendamento.getDataAgendada().minusDays(diasAlertaRegra);

            // 3. Verifica se o alerta deve ser disparado HOJE
            if (LocalDate.now().isAfter(dataLimiteAlerta)) {
                
                // Lógica para evitar spam de alertas repetidos
                boolean alertaJaExiste = alertaRepository.existsByTipoAlertaAndIdEntidadeRelacionadaAndEstaLidoFalse(
                    TipoAlerta.TEMPO_PREVENTIVA, 
                    agendamento.getIdAgendamento() 
                );

                if (!alertaJaExiste) {
                    long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), agendamento.getDataAgendada());
                    
                    String titulo = String.format("⏰ Agendamento Próximo: %s", agendamento.getRegra().getNomeRegra());
                    String descricao = String.format(
                        "O **%s** do veículo %s está agendado para **%s**. Faltam **%d dias**.",
                        agendamento.getRegra().getNomeRegra(),
                        agendamento.getVeiculo().getPlaca(), 
                        agendamento.getDataAgendada(),
                        diasRestantes
                    );
                    
                    // 4. Crie e Salve o Alerta
                    Alerta novoAlerta = new Alerta();
                    novoAlerta.setTitulo(titulo);
                    novoAlerta.setDescricao(descricao);
                    novoAlerta.setTipoAlerta(TipoAlerta.TEMPO_PREVENTIVA);
                    novoAlerta.setIdEntidadeRelacionada(agendamento.getIdAgendamento()); // Usa o ID do agendamento
                    novoAlerta.setDataCriacao(java.time.LocalDateTime.now());
                    
                    alertaRepository.save(novoAlerta);
                    logger.warn("Alerta de Agendamento Preventivo gerado para regra: {}", agendamento.getRegra().getNomeRegra());
                }
            }
        }
        
        logger.info("Job de Agendamentos Preventivos concluído. {} agendamentos verificados.", agendamentos.size());
    }
}