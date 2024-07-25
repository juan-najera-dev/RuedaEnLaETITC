package model.usuario;

import functions.Constantes.ListaEstadosEstudiante;
import java.util.ArrayList;
import model.bicicleta.Bicicleta;
import functions.Constantes.tipoSancion;

public class Estudiante extends Usuario {

    private String carrera;
    private String sede;
    private String estado;
    private Bicicleta bicileta;
    private ArrayList<Sancion> sanciones = new ArrayList<>();

    public Estudiante() {
    }

    public Estudiante(int id, String nombre, String password, String email) {
        super(id, nombre, email, password);
    }

    public Bicicleta getBicileta() {
        return bicileta;
    }

    public void setBicileta(Bicicleta bicileta) {
        this.bicileta = bicileta;
    }

    public void addSancion(Sancion sancion) {
        sanciones.add(sancion);
    }

    public void deleteSancion(Sancion sancion) {
        sanciones.remove(sancion);
    }

    public boolean hasDeuda() {
        for (Sancion i : sanciones) {
            if (i.getTipoSancion().equals(tipoSancion.DEUDA)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRestriccion() {
        for (Sancion i : sanciones) {
            if (i.getTipoSancion().equals(tipoSancion.RESTRICCION)) {
                return true;
            }
        }
        return false;
    }

    public double getDeuda() {
        double deuda = 0;
        for (Sancion i : sanciones) {
            deuda += i.getDeuda();
        }
        return deuda;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setSancion(Sancion sanciones) {
        this.sanciones.add(sanciones);
    }

    public boolean isActivo() {
        return this.estado.equals(ListaEstadosEstudiante.ACTIVO.name());
    }
}
