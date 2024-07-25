package controllers.estudiante;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MostrarQR {

    @FXML
    private AnchorPane root;

    public void volver() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelEstudiante().start(stage);
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/mostrarQR.fxml"));
        stage.setTitle("Rueda en la ETITC");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
