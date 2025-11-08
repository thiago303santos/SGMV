package sgmv.demo.dto;
import java.math.BigDecimal;

public class ProdutoDTO {
        private Long idProduto;
    private String nomeProduto;
    private String categoria;
    private String unidade;
    private int quantidade;
    private BigDecimal valorVenda;

    public Long getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorVenda() {
        return valorVenda;
    }
    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public ProdutoDTO(Long idProduto, String nomeProduto, String categoria, String unidade, int quantidade, BigDecimal valorVenda) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
    }

}
