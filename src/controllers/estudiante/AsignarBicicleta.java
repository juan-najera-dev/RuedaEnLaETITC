package controllers.estudiante;

import com.google.zxing.WriterException;
import data.DAOBicicletaNormalMySQL;
import data.DAOParqueaderosMySQL;
import functions.Constantes.listaSedes;
import functions.QRFunctions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.layout.AnchorPane;
import model.bicicleta.Bicicleta;
import model.bicicleta.ParqueaderoSede;
import model.usuario.Estudiante;

public class AsignarBicicleta {

    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox cbxSedeOrigen;
    @FXML
    private ChoiceBox cbxSedeDestino;
    private ArrayList<ParqueaderoSede> sedes;
    private Estudiante estudiante;

    public void initialize() {
        cbxSedeOrigen.setItems(FXCollections.observableArrayList(listaSedes.values()));
        cbxSedeDestino.setItems(FXCollections.observableArrayList(listaSedes.values()));
    }

    public void getData() {
        Stage stage = (Stage) root.getScene().getWindow();
        estudiante = (Estudiante) stage.getUserData();
    }

    public void Asignar() throws WriterException, Exception {
        this.getData();
        DAOParqueaderosMySQL dbP = new DAOParqueaderosMySQL();
        sedes = dbP.readAllData();
        int indexSedeOrigen = this.encontrarSede(cbxSedeOrigen.getValue().toString());
        int indexSedeDestino = this.encontrarSede(cbxSedeDestino.getValue().toString());
        if (sedes.get(indexSedeOrigen).hasBicicletaDisponible()
                && sedes.get(indexSedeDestino).hasCupoDisponible()
                && !estudiante.hasRestriccion()) {
            Bicicleta bicicleta = sedes.get(indexSedeOrigen).reservarBicicleta(estudiante.getId());
            estudiante.setBicileta(bicicleta);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            //GenerarQR
            QRFunctions.guardarQR("ID Bicicleta:" + Integer.toString(estudiante.getBicileta().getNumeroInventario())
                    + ":ID Estudiante:" + Integer.toString(estudiante.getId())
                    + ":Nombre Estudiante:" + estudiante.getNombre()
                    + ":Origen:" + indexSedeOrigen
                    + ":Destino:" + indexSedeDestino
                    + ":Generado:" + dtf.format(now));

            //Volcar a BD
            DAOBicicletaNormalMySQL db = new DAOBicicletaNormalMySQL();
            db.updateData(bicicleta.getNumeroInventario(), bicicleta);
            Stage stage = (Stage) root.getScene().getWindow();
            new MostrarQR().start(stage);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("No hay disponibilidad por el momento, intente despúes");
            errorAlert.showAndWait();
        }
    }

    public int encontrarSede(String sede) {
        for (int indexSede = 0; indexSede < sedes.size(); indexSede++) {
            if (sedes.get(indexSede).getNombreSede().equals(sede)) {
                return indexSede;
            }
        }
        return 9; //Valor no alcanzable
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/asignarBicicleta.fxml"));
        stage.setTitle("Solicitar Bicicleta");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
