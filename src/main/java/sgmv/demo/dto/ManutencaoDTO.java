package sgmv.demo.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
// Importe este se você planeja formatar a data/hora dentro da DTO (embora o Controller seja o ideal)
import java.time.format.DateTimeFormatter; 

public class ManutencaoDTO {
    private Long id; 
    
    // Dados do Cliente
    private Long idCliente; 
    private String nomeCliente;
    private String cpfCliente;
    private String telefoneCliente;
    private String celularCliente;
    private String emailCliente;
    private String enderecoCliente;
    private String bairroCliente;
    private String cidadeCliente;

    // Dados do Veículo
    private Long idVeiculo; 
    private String placa;
    private String modelo;
    private String cor;
    private String ano;
    private String marca;
    private String chassi;
    private Long tipoId;

    // Dados da manutenção
    private LocalDate dataEntrada;
    private LocalDate dataEntrega;
    private String profissional;
    private String quilometragem;
    private String descricao;
    private String status;
    
    // CAMPOS DE PRODUTIVIDADE (String para exibir data e hora formatadas)
    private String dataHoraInicio;
    private String dataHoraFim;
    private String funcionarioExecutor; 
    
    // Valores e Listas
    private BigDecimal totalServicos;
    private BigDecimal totalPecas;
    private BigDecimal vlTotal;

    private List<ManutencaoServicoDTO> servicos;
    private List<ManutencaoPecaDTO> pecas;


    // --- GETTERS E SETTERS ---
    
    // NOVOS GETTERS E SETTERS PARA PRODUTIVIDADE
    public String getDataHoraInicio() {
        return dataHoraInicio;
    }
    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraFim() {
        return dataHoraFim;
    }
    public void setDataHoraFim(String dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
    
    public String getFuncionarioExecutor() {
        return funcionarioExecutor;
    }
    public void setFuncionarioExecutor(String funcionarioExecutor) {
        this.funcionarioExecutor = funcionarioExecutor;
    }


    // --- GETTERS E SETTERS EXISTENTES (MANTIDOS) ---
    
    public LocalDate getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    
    public String getProfissional() {
        return profissional;
    }
    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getCelularCliente() {
        return celularCliente;
    }
    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }
    
    public String getEnderecoCliente() {
        return enderecoCliente;
    }
    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }
    
    public String getBairroCliente() {
        return bairroCliente;
    }
    public void setBairroCliente(String bairroCliente) {
        this.bairroCliente = bairroCliente;
    }
    
    public String getCidadeCliente() {
        return cidadeCliente;
    }
    public void setCidadeCliente(String cidadeCliente) {
        this.cidadeCliente = cidadeCliente;
    }

    public String getChassi() {
        return chassi;
    }
    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public List<ManutencaoServicoDTO> getServicos() {
        return servicos;
    }
    public void setServicos(List<ManutencaoServicoDTO> servicos) {
        this.servicos = servicos;
    }

    public List<ManutencaoPecaDTO> getPecas() {
        return pecas;
    }
    public void setPecas(List<ManutencaoPecaDTO> pecas) {
        this.pecas = pecas;
    }

    public BigDecimal getTotalServicos() {
        return totalServicos;
    }
    public void setTotalServicos(BigDecimal totalServicos) {
        this.totalServicos = totalServicos;
    }

    public BigDecimal getTotalPecas() {
        return totalPecas;
    }
    public void setTotalPecas(BigDecimal totalPecas) {
        this.totalPecas = totalPecas;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
    public String getTelefoneCliente() { return telefoneCliente; }
    public void setTelefoneCliente(String telefoneCliente) { this.telefoneCliente = telefoneCliente; }
    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }
    public Long getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(Long idVeiculo) { this.idVeiculo = idVeiculo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public Long getTipoId() { return tipoId; }
    public void setTipoId(Long tipoId) { this.tipoId = tipoId; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }
    public String getQuilometragem() { return quilometragem; }
    public void setQuilometragem(String quilometragem) { this.quilometragem = quilometragem; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getVlTotal() { return vlTotal; }
    public void setVlTotal(BigDecimal vlTotal) { this.vlTotal = vlTotal; }

}