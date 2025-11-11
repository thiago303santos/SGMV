package sgmv.demo.dto;
import sgmv.demo.model.Produto;
import java.math.BigDecimal;

public class ProdutoDTO {
    private Long idProduto;
    private String nomeProduto;
    private String fabricante; 
    private String categoria;
    private String unidadeMedida; 
    private int quantidade;
    private BigDecimal valorVenda;
    private BigDecimal valorCusto; 

    // Construtor Vazio (mantido)
    public ProdutoDTO() {}

    // NOVO CONSTRUTOR: Adicionado para resolver o erro de compilação no Controller.
    // Aceita todos os 7 argumentos de dados que o método listarProdutos envia.
    public ProdutoDTO(Long idProduto, String nomeProduto, String fabricante, String categoria, String unidadeMedida, int quantidade, BigDecimal valorVenda) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        // Nota: valorCusto é deixado como nulo ou zero (se for o comportamento esperado)
    }

    // Construtor de Mapeamento (Entidade -> DTO) - para uso de streams
    public ProdutoDTO(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.nomeProduto = produto.getNomeProduto();
        this.fabricante = produto.getFabricante(); 
        this.quantidade = produto.getQuantidade();
        this.valorVenda = produto.getValorVenda();
        this.valorCusto = produto.getValorCusto();
        
        if (produto.getUnidadeMedida() != null) {
            this.unidadeMedida = produto.getUnidadeMedida().name(); 
        }
        
        if (produto.getCategoria() != null) {
            this.categoria = produto.getCategoria().getNomeCategoria();
        }
    }

    // --- GETTERS E SETTERS ---
    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorVenda() { return valorVenda; }
    public void setValorVenda(BigDecimal valorVenda) { this.valorVenda = valorVenda; }
    public BigDecimal getValorCusto() { return valorCusto; }
    public void setValorCusto(BigDecimal valorCusto) { this.valorCusto = valorCusto; }
}