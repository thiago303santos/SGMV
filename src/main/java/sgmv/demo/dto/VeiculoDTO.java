package sgmv.demo.dto;

import sgmv.demo.model.Veiculo;

public class VeiculoDTO {
    private Long idVeiculo;
    private String placa;
    private String modelo;
    private String ano;

    // Construtor a partir da entidade Veiculo
    public VeiculoDTO(Veiculo veiculo) {
        this.idVeiculo = veiculo.getIdVeiculo();
        this.placa = veiculo.getPlaca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
    }

    // --- GETTERS E SETTERS ---
    public Long getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(Long idVeiculo) { this.idVeiculo = idVeiculo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }
}