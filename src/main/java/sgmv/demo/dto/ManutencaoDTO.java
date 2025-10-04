package sgmv.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class ManutencaoDTO {
    private Long id; // ID da manutenção, se necessário
    private Long idCliente; // se já existir
    private String nomeCliente;
    private String cpfCliente;
    private String telefoneCliente;
    private String emailCliente;

    private Long idVeiculo; // se já existir
    private String placa;
    private String modelo;
    private String cor;
    private String ano;
    private String marca;
    private Long tipoId;

    // Dados da manutenção
    private LocalDate dataEntrada;
    private String quilometragem;
    private String descricao;
    private String status;
    private BigDecimal vlTotal;

    // Getters e Setters
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public String getCpfCliente() {
        return cpfCliente;
    }
    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
    public String getTelefoneCliente() {
        return telefoneCliente;
    }
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }
    public String getEmailCliente() {
        return emailCliente;
    }
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
    public Long getIdVeiculo() {
        return idVeiculo;
    }
    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public Long getTipoId() {
        return tipoId;
    }
    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
    public LocalDate getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    public String getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getVlTotal() {
        return vlTotal;
    }
    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }
}
