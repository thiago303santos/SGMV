package sgmv.demo.dto;

import sgmv.demo.model.ManutencaoPeca;
import java.math.BigDecimal;

public class ManutencaoPecaDTO {

    private Long id; 
    private Long idProduto;
    private String nomeProduto;// Descrição da peça (JOGO DE PASTILHA) [cite: 22]
    private String categoria;
    private String unidade;// Un [cite: 22]
    private int quantidade;// Qt [cite: 22]
    private BigDecimal valorUnitario; // Val.Unit. [cite: 22]
    private BigDecimal valorTotal; // Val.Total [cite: 22]

    public ManutencaoPecaDTO() {}

    public ManutencaoPecaDTO(ManutencaoPeca mp) {
        this.id = mp.getId();
        this.idProduto = mp.getProduto().getIdProduto();
        this.nomeProduto = mp.getProduto().getNomeProduto();
        this.categoria = mp.getProduto().getCategoria() != null ? mp.getProduto().getCategoria().getNomeCategoria() : "";
        this.unidade = mp.getProduto().getUnidadeMedida() != null ? mp.getProduto().getUnidadeMedida().name() : "";
        this.quantidade = mp.getQuantidade();
        this.valorUnitario = mp.getVlUnitario() != null ? mp.getVlUnitario() : BigDecimal.ZERO;
        
        // Calcula o valor total no DTO
        this.valorTotal = this.valorUnitario.multiply(BigDecimal.valueOf(this.quantidade));
    }

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Mantido por compatibilidade com o código anterior (agora desnecessário)
    @Deprecated
    public double getValorVenda() { return valorUnitario.doubleValue(); }
    @Deprecated
    public void setValorVenda(double valorVenda) { this.valorUnitario = BigDecimal.valueOf(valorVenda); }
}