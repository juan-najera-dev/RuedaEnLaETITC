package controllers.admin;

import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PanelAdmin {

    @FXML
    private AnchorPane root;

    public void inscribirEstudiante() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new InscribirEstudiante().start(stage);
    }

    public void verEstudiantes() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new VerEstudiantes().start(stage);
    }

    public void verBicicletas() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new VerBicicletas().start(stage);
    }

    public void verSedes() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new VerSedes().start(stage);
    }

    public void salir() {
        Alert exitApp = new Alert(Alert.AlertType.CONFIRMATION,
                "Está segur@ que quiere salir?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> confirm = exitApp.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.YES) {
            Platform.exit();
        }
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/panelAdmin.fxml"));
        stage.setTitle("Rueda en la ETITC");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
        stage.setOnCloseRequest(E -> {
            salir();
        });
    }
}
