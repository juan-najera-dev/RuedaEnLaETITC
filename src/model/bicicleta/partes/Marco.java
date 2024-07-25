package model.bicicleta.partes;

public class Marco extends ComponenteBicicleta {

    private String material;
    private int tamano;

    public Marco(String material, int tamano, String estado) {
        this.material = material;
        this.tamano = tamano;
        this.estado = estado;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

}
