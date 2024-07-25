package controllers.seguridad;

import data.DAOParqueaderosMySQL;
import functions.QRFunctions;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.bicicleta.ParqueaderoSede;

public class EntregarBicicleta {

    @FXML
    private AnchorPane root;
    private ParqueaderoSede sede;

    public void volver() throws Exception {        
        String str = QRFunctions.devolverInfoQR();
        String[] data = str.split(":");
        
        //data[1] Numero Inventario Bicicleta
        //data[3] ID Estudiante
        //data[5] Nombre Estudiante
        //data[7] Origen
        //data[9] Destino
        
        // Actualizacion datos Sede
        DAOParqueaderosMySQL dbP = new DAOParqueaderosMySQL();
        sede = dbP.readAllData().get(Integer.parseInt(data[7]));
        sede.giveBicicleta(Integer.parseInt(data[3]), Integer.parseInt(data[1]));
        dbP.updateData(sede.getId(), sede);
        
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelSeguridad().start(stage);
    }

    public void start(Stage stage) throws IOException {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/entregarBicicleta.fxml"));
        stage.setTitle("Entregar Bicicleta");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
