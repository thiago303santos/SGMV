// src/main/java/sgmv/demo/model/RegraPreventiva.java

package sgmv.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_regra_preventiva")
public class RegraPreventiva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegra;

    @Column(nullable = false, unique = true)
    private String nomeRegra; // Ex: "ÓLEO E FILTRO DO MOTOR"
    
    // Periodicidade em dias (Ex: 180 para 6 meses)
    @Column(name = "periodicidade_dias", nullable = false)
    private int periodicidadeDias;

    // Periodicidade em KM (Ex: 10000)
    @Column(name = "periodicidade_km")
    private Integer periodicidadeKm; // Pode ser null se a regra for apenas por tempo

    // Quantos dias antes do vencimento o sistema deve gerar o alerta
    @Column(name = "dias_alerta", nullable = false)
    private int diasAlerta = 30; // Padrão: avisar 30 dias antes

    @Column(nullable = false)
    private boolean ativo = true;

    // --- GETTERS E SETTERS ---
    public Long getIdRegra() { return idRegra; }
    public void setIdRegra(Long idRegra) { this.idRegra = idRegra; }

    public String getNomeRegra() { return nomeRegra; }
    public void setNomeRegra(String nomeRegra) { this.nomeRegra = nomeRegra; }

    public int getPeriodicidadeDias() { return periodicidadeDias; }
    public void setPeriodicidadeDias(int periodicidadeDias) { this.periodicidadeDias = periodicidadeDias; }

    public Integer getPeriodicidadeKm() { return periodicidadeKm; }
    public void setPeriodicidadeKm(Integer periodicidadeKm) { this.periodicidadeKm = periodicidadeKm; }

    public int getDiasAlerta() { return diasAlerta; }
    public void setDiasAlerta(int diasAlerta) { this.diasAlerta = diasAlerta; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}