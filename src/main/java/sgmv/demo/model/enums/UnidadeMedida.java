package sgmv.demo.model.enums;

public enum UnidadeMedida {
    UNIDADE("UN"),
    LITRO("L"),
    KILOGRAMA("KG"),
    PECA("PÇ"),
    CAIXA("CX");

    private final String sigla;

    UnidadeMedida(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }
}
