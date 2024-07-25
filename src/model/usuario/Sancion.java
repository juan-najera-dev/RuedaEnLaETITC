package model.usuario;

import functions.Constantes.tipoSancion;

public class Sancion {

    private final tipoSancion sancion;
    private final String descriptorSancion;
    private final int diasRestriccion;
    private final double deuda;

    public Sancion(String sancion, String descriptorSancion, double deuda) {
        this.sancion = tipoSancion.valueOf(sancion);
        this.descriptorSancion = descriptorSancion;
        this.deuda = deuda;
        this.diasRestriccion = 0;
    }

    public Sancion(String sancion, String descriptorSancion, int dias) {
        this.sancion = tipoSancion.valueOf(sancion);
        this.descriptorSancion = descriptorSancion;
        this.diasRestriccion = dias;
        this.deuda = 0;
    }

    public tipoSancion getTipoSancion() {
        return sancion;
    }

    public double getDeuda() {
        return deuda;
    }

    public String getDescriptorSancion() {
        return descriptorSancion;
    }

    public int getDiasRestriccion() {
        return diasRestriccion;
    }
}