package model.bicicleta;

import functions.Constantes.ListaEstadoBicicletaYPartes;
import functions.Constantes.ListaTiposBicicletas;
import model.bicicleta.partes.*;

public final class BicicletaNormal extends Bicicleta implements IBicicleta {

    public BicicletaNormal() {
    }

    public BicicletaNormal(int numeroInventario, int serial, String estadoBicicleta,
            String tipoFreno, String[] estadoFrenos, String materialManubrio,
            String[] estadoManubrio, String materialMarco, int tamanoMarco,
            String estadoMarco, String materialPedal, String[] estadoPedal,
            int anchoLlanta, int diametroRin, int numRadios, String[] estadoRueda,
            String[] estadoSillin, String tipoMotor, String estadoMotor) {
        this.serial = serial;
        this.numeroInventario = numeroInventario;
        this.tipoBicicleta = ListaTiposBicicletas.NORMAL.name();
        this.estado = estadoBicicleta;
        this.construirFrenos(tipoFreno, estadoFrenos);
        this.construirManubios(materialManubrio, estadoManubrio);
        this.construirMarco(materialMarco, tamanoMarco, materialMarco);
        this.construitPedales(materialPedal, estadoPedal);
        this.construirRuedas(anchoLlanta, diametroRin, numRadios, estadoRueda);
        this.construirSillines(estadoSillin);
        this.construirMotor(tipoMotor, estadoMotor);
    }

    @Override
    public void construirFrenos(String tipo, String[] estado) {
        Freno[] frenosBicicleta = new Freno[2];
        frenosBicicleta[0] = new Freno(tipo, estado[0]);
        frenosBicicleta[1] = new Freno(tipo, estado[1]);
        this.frenos = frenosBicicleta;
    }

    @Override
    public void construirManubios(String material, String[] estado) {
        Manubrio[] manubioBicicleta = new Manubrio[1];
        manubioBicicleta[0] = new Manubrio(material, estado[0]);
        this.manubrio = manubioBicicleta;
    }

    @Override
    public void construirMarco(String material, int tamano, String estado) {
        Marco marcoBicicleta = new Marco(material, tamano, estado);
        this.marco = marcoBicicleta;
    }

    @Override
    public void construitPedales(String material, String[] estado) {
        Pedal[] pedalesbicicleta = new Pedal[2];
        pedalesbicicleta[0] = new Pedal(material, estado[0]);
        pedalesbicicleta[1] = new Pedal(material, estado[1]);
        this.pedales = pedalesbicicleta;
    }

    @Override
    public void construirRuedas(int anchoLlanta, int diametroRin, int numRadios, String[] estado) {
        Rueda[] ruedasBicicleta = new Rueda[2];
        ruedasBicicleta[0] = new Rueda(anchoLlanta, diametroRin, numRadios, estado[0]);
        ruedasBicicleta[1] = new Rueda(anchoLlanta, diametroRin, numRadios, estado[1]);
        this.ruedas = ruedasBicicleta;
    }

    @Override
    public void construirSillines(String[] estado) {
        Sillin[] sillinBicicleta = new Sillin[1];
        sillinBicicleta[0] = new Sillin(estado[0]);
        this.sillin = sillinBicicleta;
    }

    @Override
    public void construirMotor(String tipo, String estado) {
        SistemaMotor motor = new SistemaMotor(tipo, estado);
        this.sistemamotor = motor;
    }
    
    public void actualizarEstadoFrenos(String[] estado){
        this.frenos[0].setEstado(estado[0]);
        this.frenos[1].setEstado(estado[1]);
    }
    
    public void actualizarEstadoManubrio(String[] estado){
        this.manubrio[0].setEstado(estado[0]);
    }
    
    public void actualizarEstadoMarco(String estado){
        this.marco.setEstado(estado);
    }
    
    public void actualizarEstadoPedales(String[] estado){
        this.pedales[0].setEstado(estado[0]);
        this.pedales[1].setEstado(estado[1]);
    }
    public void actualizarEstadoRuedas(String[] estado){
        this.ruedas[0].setEstado(estado[0]);
        this.ruedas[1].setEstado(estado[1]);
    }
    
    public void actualizarEstadoSillin(String[] estado){
        this.sillin[0].setEstado(estado[0]);
    }
    
    public void actualizarEstadoMotor(String estado){
        this.sistemamotor.setEstado(estado);
    }
    
    @Override
    public void actualizarEstado() {
        boolean estadoFrenos = false;
        boolean estadoManubrio = false;
        boolean estadoMarco = false;
        boolean estadoPedales = false;
        boolean estadoRuedas = false;
        boolean estadoSillin = false;
        boolean estadoMotor = false;
        if ((this.frenos[0].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) && 
                (this.frenos[1].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name()))) {
            estadoFrenos = true;
        }
        if (this.manubrio[0].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) {
            estadoManubrio = true;
        }
        if (this.marco.getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) {
            estadoMarco = true;
        }
        if ((this.pedales[0].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) && 
                (this.pedales[1].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name()))) {
            estadoPedales = true;
        }
        if ((this.ruedas[0].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) && 
                (this.ruedas[1].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name()))) {
            estadoRuedas = true;
        }
        if (this.sillin[0].getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) {
            estadoSillin = true;
        }
        if (this.sistemamotor.getEstado().equals(ListaEstadoBicicletaYPartes.FUNCIONAL.name())) {
            estadoMotor = true;
        }
        if ((estadoFrenos && estadoManubrio && estadoMarco && estadoPedales && estadoRuedas && 
                estadoSillin && estadoMotor) == false) {
            this.estado = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        } else {
            this.estado = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        }
    }
}
