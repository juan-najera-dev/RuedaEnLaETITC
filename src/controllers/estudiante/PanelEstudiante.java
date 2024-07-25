package controllers.estudiante;

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
import model.usuario.Estudiante;

public class PanelEstudiante {

    @FXML
    private AnchorPane root;
    private Estudiante estudiante;

    public void solicitarBicicleta() throws Exception {
        this.getData();
        if (estudiante.hasRestriccion()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("El estudiante tiene restricción");
            errorAlert.showAndWait();
        } else if (!estudiante.isActivo()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("El semestre terminó");
            errorAlert.showAndWait();
        } else {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setUserData(estudiante);
            new AsignarBicicleta().start(stage);
        }
    }
    
    public void getData(){
        Stage stage = (Stage) root.getScene().getWindow();
        estudiante = (Estudiante) stage.getUserData();
    }

    public void cambiarContrasena() throws Exception {
        this.getData();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setUserData(estudiante);
        new CambiarContrasena().start(stage);
    }

    public void mostrarQR() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new MostrarQR().start(stage);
    }

    public void salir() {
        //Volcado hacia BD??
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
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/panelEstudiante.fxml"));
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
