// src/main/java/sgmv/demo/service/PreventivaService.java

package sgmv.demo.service;

import sgmv.demo.model.*;
import sgmv.demo.repository.ManutencaoAgendadaRepository;
import sgmv.demo.repository.RegraPreventivaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PreventivaService {

    private static final Logger logger = LoggerFactory.getLogger(PreventivaService.class);

    private final RegraPreventivaRepository regraPreventivaRepository;
    private final ManutencaoAgendadaRepository manutencaoAgendadaRepository;

    public PreventivaService(
            RegraPreventivaRepository regraPreventivaRepository,
            ManutencaoAgendadaRepository manutencaoAgendadaRepository) {
        this.regraPreventivaRepository = regraPreventivaRepository;
        this.manutencaoAgendadaRepository = manutencaoAgendadaRepository;
    }

    /**
     * Processa uma manutenção concluída e gera o próximo agendamento preventivo
     * para as peças trocadas que possuem uma regra definida.
     * @param manutencao A OS que acabou de ser concluída.
     */
    @Transactional
    public void gerarProximosAgendamentos(Manutencao manutencao) {
        
        // 1. O veículo é o ponto central
        Veiculo veiculo = manutencao.getVeiculo();
        if (veiculo == null) {
            logger.warn("OS #{} concluída, mas sem veículo associado. Pulando geração de preventiva.", manutencao.getId());
            return;
        }

        // 2. Busque todas as regras ativas
        // Idealmente, você buscaria regras mapeadas para o Produto ou Serviço usado.
        // Vamos buscar todas e tentar fazer a correspondência pelo NOME do produto.
        var regrasAtivas = regraPreventivaRepository.findAll();
        
        Set<ManutencaoPeca> pecasTrocadas = manutencao.getPecas();

        for (ManutencaoPeca mp : pecasTrocadas) {
            Produto produto = mp.getProduto();
            
            // Tenta encontrar uma regra pelo nome do produto (requer padronização de nomenclatura)
            Optional<RegraPreventiva> regraOpt = regrasAtivas.stream()
                .filter(regra -> produto.getNomeProduto().toUpperCase().contains(regra.getNomeRegra().split(" ")[0]))
                .findFirst();

            if (regraOpt.isPresent()) {
                RegraPreventiva regra = regraOpt.get();
                
                // 3. Calcula a Próxima Data e KM Agendada
                LocalDate dataUltima = manutencao.getDataConclusao() != null ? manutencao.getDataConclusao() : manutencao.getDataEntrada();
                
                // Cálculo por TEMPO (Sempre é calculado, pois o óleo degrada)
                LocalDate proximaData = dataUltima.plusDays(regra.getPeriodicidadeDias());
                
                // Cálculo por KM (Apenas se a regra tiver periodicidade KM e se a KM de registro for válida)
                Integer proximaKm = null;
                try {
                    Integer kmAtual = Integer.valueOf(manutencao.getQuilometragem().replaceAll("[^0-9]", ""));
                    if (regra.getPeriodicidadeKm() != null) {
                        proximaKm = kmAtual + regra.getPeriodicidadeKm();
                    }
                } catch (NumberFormatException e) {
                    logger.error("Falha ao converter KM para número na OS #{}", manutencao.getId());
                }

                // 4. Cria o novo Agendamento
                ManutencaoAgendada novoAgendamento = new ManutencaoAgendada();
                novoAgendamento.setVeiculo(veiculo);
                novoAgendamento.setRegra(regra);
                novoAgendamento.setDataAgendada(proximaData);
                novoAgendamento.setKmAgendada(proximaKm);
                novoAgendamento.setStatus("PENDENTE");
                
                // 5. Salva o agendamento
                manutencaoAgendadaRepository.save(novoAgendamento);
                logger.info("Novo agendamento gerado para {}: Data: {} | KM: {}", 
                            regra.getNomeRegra(), proximaData, proximaKm);
                
                // Opcional: Marcar agendamentos antigos (abertos) para a mesma regra/veículo como "SUPERSEDIDO"
            }
        }
    }
}