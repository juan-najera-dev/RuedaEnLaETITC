package model.bicicleta.partes;

public class Pedal extends ComponenteBicicleta {

    private String material;

    public Pedal(String material, String estado) {
        this.material = material;
        this.estado = estado;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
