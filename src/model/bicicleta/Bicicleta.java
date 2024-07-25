package model.bicicleta;

import model.bicicleta.partes.Rueda;
import model.bicicleta.partes.Freno;
import model.bicicleta.partes.Pedal;
import model.bicicleta.partes.Manubrio;
import model.bicicleta.partes.SistemaMotor;
import model.bicicleta.partes.Sillin;
import model.bicicleta.partes.Marco;

public class Bicicleta {

    protected int numeroInventario;
    protected int serial;
    protected String tipoBicicleta;
    protected String estado;
    protected int estudianteID;
    protected String sede;
    protected Freno[] frenos;
    protected Manubrio[] manubrio;
    protected Marco marco;
    protected Pedal[] pedales;
    protected Rueda[] ruedas;
    protected Sillin[] sillin;
    protected SistemaMotor sistemamotor;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
    
    public int getNumeroInventario() {
        return numeroInventario;
    }
    
    public void setNumeroInventario(int numeroInventario) {
        this.numeroInventario = numeroInventario;
    }

    public String getTipoBicicleta() {
        return tipoBicicleta;
    }
    
    public void setTipoBicicleta(String tipoBicicleta) {
        this.tipoBicicleta = tipoBicicleta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEstudianteID() {
        return estudianteID;
    }

    public void setEstudianteID(int estudianteID) {
        this.estudianteID = estudianteID;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Freno[] getFrenos() {
        return frenos;
    }
    
    public void setFrenos(Freno[] frenos) {
        this.frenos = frenos;
    }

    public Manubrio[] getManubrio() {
        return manubrio;
    }
    
    public void setManubrio(Manubrio[] manubrio) {
        this.manubrio = manubrio;
    }

    public Marco getMarco() {
        return marco;
    }
    
    public void setMarco(Marco marco) {
        this.marco = marco;
    }

    public Pedal[] getPedales() {
        return pedales;
    }
    
    public void setPedales(Pedal[] pedales) {
        this.pedales = pedales;
    }

    public Rueda[] getRuedas() {
        return ruedas;
    }
    
    public void setRuedas(Rueda[] ruedas) {
        this.ruedas = ruedas;
    }

    public Sillin[] getSillin() {
        return sillin;
    }
    
    public void setSillin(Sillin[] sillin) {
        this.sillin = sillin;
    }

    public SistemaMotor getSistemamotor() {
        return sistemamotor;
    }
    
    public void setSistemamotor(SistemaMotor sistemamotor) {
        this.sistemamotor = sistemamotor;
    }
}
