package model.bicicleta.partes;

public class Rueda extends ComponenteBicicleta {

    private int anchoLlanta;
    private int diametroRin;
    private int numRadios;

    public Rueda(int anchoLlanta, int diametroRin, int numRadios, String estado) {
        this.anchoLlanta = anchoLlanta;
        this.diametroRin = diametroRin;
        this.numRadios = numRadios;
        this.estado = estado;
    }

    public int getAnchoLlanta() {
        return anchoLlanta;
    }

    public void setAnchoLlanta(int anchoLlanta) {
        this.anchoLlanta = anchoLlanta;
    }

    public int getDiametroRin() {
        return diametroRin;
    }

    public void setDiametroRin(int diametroRin) {
        this.diametroRin = diametroRin;
    }

    public int getNumRadios() {
        return numRadios;
    }

    public void setNumRadios(int numRadios) {
        this.numRadios = numRadios;
    }
}
