package controllers.seguridad;

import java.io.IOException;
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
import model.usuario.Seguridad;

public class PanelSeguridad {

    @FXML
    private AnchorPane root;
    private Seguridad seguridad;

    public void recibirBicicleta() throws IOException{
        this.getData();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setUserData(seguridad);
        new RecibirBicicletaNormal().start(stage);
    
    }
    
    public void entregarBicicleta() throws IOException{
        Stage stage = (Stage) root.getScene().getWindow();
        new EntregarBicicleta().start(stage);
    }
    
    public void getData(){
        Stage stage = (Stage) root.getScene().getWindow();
        seguridad = (Seguridad) stage.getUserData();
    }
    
    public void salir() {
        //Volcado hacia BD
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
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/panelSeguridad.fxml"));
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
