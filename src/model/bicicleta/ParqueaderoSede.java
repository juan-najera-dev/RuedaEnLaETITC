package model.bicicleta;

import data.DAOBicicletaNormalMySQL;
import data.DAOEstudianteMySQL;
import functions.Constantes;
import functions.Constantes.ListaEstadoBicicletaYPartes;
import functions.Constantes.listaSedes;
import java.util.ArrayList;
import model.usuario.Estudiante;

public class ParqueaderoSede {

    private int id;
    private String nombreSede;
    private int cuposTotales;
    private int cuposDisponibles;
    private ArrayList<Bicicleta> bicicletas = new ArrayList<>();

    public ParqueaderoSede() {
    }

    public ParqueaderoSede(String nombreSede, int cuposTotales, int cuposDisponibles) {
        this.nombreSede = listaSedes.valueOf(nombreSede).name();
        this.cuposTotales = cuposTotales;
        this.cuposDisponibles = cuposDisponibles;
    }

    public Bicicleta reservarBicicleta(int estudianteID) {
        for (Bicicleta b : bicicletas) {
            if (b.getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name()) && b.getEstudianteID() == 0) {
                BicicletaNormal bicicleta = (BicicletaNormal) b;
                b.setEstudianteID(estudianteID);
                return bicicleta;
            }
        }
        return null;
    }

    public void giveBicicleta(int indexEstudiante, int indexBicicleta) {
        //Obtencion datos Bicicleta
        DAOBicicletaNormalMySQL dbBicicleta = new DAOBicicletaNormalMySQL();
        Bicicleta bicicleta = dbBicicleta.readRegister(indexBicicleta);
        bicicleta.setSede(Constantes.listaSedesDesarrollo.ESTUDIANTE.name());
        dbBicicleta.updateData(bicicleta.getNumeroInventario(), bicicleta);

        //Obtencion datos Estudiante
        DAOEstudianteMySQL dbEstudiante = new DAOEstudianteMySQL();
        Estudiante estudiante = dbEstudiante.readRegister(indexEstudiante);
        estudiante.setBicileta(bicicleta);
        dbEstudiante.updateData(estudiante.getId(), estudiante);

        this.deleteBicicleta(bicicleta);
    }

    public void takeBicicleta(int indexEstudiante, Bicicleta bicicletaEntrega) {
        //Actualizacion datos Bicicleta
        DAOBicicletaNormalMySQL dbBicicleta = new DAOBicicletaNormalMySQL();
        bicicletaEntrega.setSede(this.nombreSede);
        bicicletaEntrega.setEstudianteID(0);
        dbBicicleta.updateData(bicicletaEntrega.getNumeroInventario(), bicicletaEntrega);

        //Actualizacion datos Estudiante
        
        DAOEstudianteMySQL dbEstudiante = new DAOEstudianteMySQL();
        Estudiante estudiante = dbEstudiante.readRegister(indexEstudiante);
        estudiante.setBicileta(null);
        dbEstudiante.updateData(estudiante.getId(), estudiante);

        this.addBicicleta(bicicletaEntrega);
    }
    
    public boolean hasCupoDisponible() {
        return this.cuposDisponibles > 0;
    }

    public boolean hasBicicletaDisponible() {
        return this.bicicletasDisponibles() > 0;
    }

    public void addBicicleta(Bicicleta bicicleta) {
        this.cuposDisponibles--;
        bicicletas.add(bicicleta);
    }

    public void deleteBicicleta(Bicicleta bicicleta) {
        bicicletas.remove(bicicleta);
        this.cuposDisponibles++;
    }

    public int bicicletasDisponibles() {
        int cont = 0;
        for (Bicicleta b : bicicletas) {
            if (b.getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) {
                cont++;
            }
        }
        return cont;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public int getCuposTotales() {
        return cuposTotales;
    }

    public void setCuposTotales(int cuposTotales) {
        this.cuposTotales = cuposTotales;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public ArrayList<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(ArrayList<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }
}
