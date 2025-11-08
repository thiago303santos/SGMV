package sgmv.demo.dto;

import sgmv.demo.model.ManutencaoPeca;

public class ManutencaoPecaDTO {

    private Long id; // id do ManutencaoPeca
    private Long idProduto;
    private String nomeProduto;
    private String categoria;
    private String unidade;
    private int quantidade;
    private double valorVenda;

    public ManutencaoPecaDTO(ManutencaoPeca mp) {
        this.id = mp.getId();
        this.idProduto = mp.getProduto().getIdProduto();
        this.nomeProduto = mp.getProduto().getNomeProduto();
        this.categoria = mp.getProduto().getCategoria() != null ? mp.getProduto().getCategoria().getNomeCategoria() : "";
        this.unidade = mp.getProduto().getUnidadeMedida() != null ? mp.getProduto().getUnidadeMedida().name() : "";
        this.quantidade = mp.getQuantidade();
        this.valorVenda = mp.getVlUnitario() != null ? mp.getVlUnitario().doubleValue() : 0;
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
    public double getValorVenda() { return valorVenda; }
    public void setValorVenda(double valorVenda) { this.valorVenda = valorVenda; }
}
