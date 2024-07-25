package model.bicicleta.partes;

public class SistemaMotor extends ComponenteBicicleta {

    private String tipo;

    public SistemaMotor(String tipo, String estado) {
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
