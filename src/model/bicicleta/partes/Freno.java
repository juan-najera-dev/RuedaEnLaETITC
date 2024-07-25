package model.bicicleta.partes;

public class Freno extends ComponenteBicicleta {

    private String tipo;

    public Freno(String tipo, String estado) {
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
