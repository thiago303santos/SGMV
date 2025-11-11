package sgmv.demo.dto;

import sgmv.demo.model.ManutencaoServico; // Importe o Model ManutencaoServico
import java.math.BigDecimal;

public class ManutencaoServicoDTO {
    private Long id; // Adicionando o ID para remoção e atualização
    private String descricao;// MÃO DE OBRA - Servico Executado [cite: 21]
    private int quantidade;// 1 [cite: 21]
    private BigDecimal valorUnitario;// 360,00 [cite: 21]
    private BigDecimal valorTotal;// 360,00 [cite: 21]

    // Construtor de Mapeamento (Model para DTO)
    public ManutencaoServicoDTO(ManutencaoServico ms) {
        this.id = ms.getId();
        this.descricao = ms.getDescricao();
        this.quantidade = ms.getQuantidade();
        this.valorUnitario = ms.getVlUnitario();
        // Calcula o valor total a partir do Model
        this.valorTotal = ms.getVlUnitario().multiply(BigDecimal.valueOf(ms.getQuantidade()));
    }

    // Construtor padrão (necessário para o Spring/Jackson)
    public ManutencaoServicoDTO() {}

    // Construtor para facilitar a criação (Opcional)
    public ManutencaoServicoDTO(String descricao, int quantidade, BigDecimal valorUnitario) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }
    
    // O valor total é importante para a tabela
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}