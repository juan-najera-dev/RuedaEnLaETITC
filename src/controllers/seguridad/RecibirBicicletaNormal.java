package controllers.seguridad;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import data.DAOBicicletaNormalMySQL;
import data.DAOParqueaderosMySQL;
import functions.Constantes.ListaEstadoBicicletaYPartes;
import functions.QRFunctions;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.bicicleta.BicicletaNormal;
import model.bicicleta.ParqueaderoSede;

public class RecibirBicicletaNormal {

    @FXML
    private AnchorPane root;
    @FXML
    private CheckBox chkFrenoDelantero;
    @FXML
    private CheckBox chkFrenoTrasero;
    @FXML
    private CheckBox chkMarco;
    @FXML
    private CheckBox chkPedalDerecho;
    @FXML
    private CheckBox chkPedalIzquierdo;
    @FXML
    private CheckBox chkManubrio;
    @FXML
    private CheckBox chkRuedaDelantera;
    @FXML
    private CheckBox chkRuedaTrasera;
    @FXML
    private CheckBox chkSillin;
    @FXML
    private CheckBox chkSistemaMotor;

    public void guardar() throws WriterException, IOException, NotFoundException, Exception {

        //Obtener los datos de la bicicleta y el estudiante en la BD usando el QR
        String str = QRFunctions.devolverInfoQR();
        String[] data = str.split(":");

        //data[1] Numero Inventario Bicicleta
        //data[3] ID Estudiante
        //data[5] Nombre Estudiante
        //data[7] Origen
        //data[9] Destino
        String eFrenoDelantero = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkFrenoDelantero.isSelected()) {
            eFrenoDelantero = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eFrenoTrasero = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkFrenoTrasero.isSelected()) {
            eFrenoTrasero = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eMarco = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkMarco.isSelected()) {
            eMarco = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String ePedalDerecho = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkPedalDerecho.isSelected()) {
            ePedalDerecho = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String ePedalIzquierdo = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkPedalIzquierdo.isSelected()) {
            ePedalIzquierdo = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eManubrio = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkManubrio.isSelected()) {
            eManubrio = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eRuedaDelantera = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkRuedaDelantera.isSelected()) {
            eRuedaDelantera = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eRuedaTrasera = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkRuedaTrasera.isSelected()) {
            eRuedaTrasera = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eSillin = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkSillin.isSelected()) {
            eSillin = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String eMotor = ListaEstadoBicicletaYPartes.FUNCIONAL.name();
        if (!chkSistemaMotor.isSelected()) {
            eMotor = ListaEstadoBicicletaYPartes.NOFUNCIONAL.name();
        }
        String[] eFreno = {eFrenoDelantero, eFrenoTrasero};
        String[] ePedales = {ePedalDerecho, ePedalIzquierdo};
        String[] eManubrios = {eManubrio};
        String[] eRuedas = {eRuedaDelantera, eRuedaTrasera};
        String[] eSillines = {eSillin};

        // Actualizar Estado Bicicleta
        DAOBicicletaNormalMySQL dbBicicleta = new DAOBicicletaNormalMySQL();
        BicicletaNormal bicicleta = (BicicletaNormal) dbBicicleta.readRegister(Integer.parseInt(data[1]));
        bicicleta.actualizarEstadoFrenos(eFreno);
        bicicleta.actualizarEstadoMarco(eMarco);
        bicicleta.actualizarEstadoPedales(ePedales);
        bicicleta.actualizarEstadoManubrio(eManubrios);
        bicicleta.actualizarEstadoRuedas(eRuedas);
        bicicleta.actualizarEstadoSillin(eSillines);
        bicicleta.actualizarEstadoMotor(eMotor);
        bicicleta.actualizarEstado();

        // Actualiza Estado Parqueadero Sede
        DAOParqueaderosMySQL dbSede = new DAOParqueaderosMySQL();
        ParqueaderoSede sede = dbSede.readAllData().get(Integer.parseInt(data[9]));
        sede.takeBicicleta(Integer.parseInt(data[3]), bicicleta);
        dbSede.updateData(sede.getId(), sede);
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelSeguridad().start(stage);
    }

    public void volver() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelSeguridad().start(stage);
    }

    public void start(Stage stage) throws IOException {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/recibirBicicletaNormal.fxml"));
        stage.setTitle("Recibir Bicicleta");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
