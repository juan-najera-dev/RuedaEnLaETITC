package controllers.estudiante;

import data.DAOEstudianteMySQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.usuario.Estudiante;

public class CambiarContrasena {

    @FXML
    private AnchorPane root;
    @FXML
    private PasswordField txtContrasenaAnterior;
    @FXML
    private PasswordField txtNuevaContrasena;
    @FXML
    private PasswordField txtConfirmeContrasena;
    private Estudiante estudiante;
    
    public void actualizarContrasena() throws Exception {
        this.getData();
        String txtError;
        if (estudiante.validarConstrasena(txtContrasenaAnterior.getText())) {
            if (txtContrasenaAnterior.getText().equals(txtNuevaContrasena.getText())) {
                txtError = "la contraseña es igual a la anterior, intente nuevamente";
                this.mostrarError(txtError);
            } else {
                if (!txtNuevaContrasena.getText().equals(txtConfirmeContrasena.getText())) {
                    txtError = "la contraseña nueva no coincide";
                    this.mostrarError(txtError);
                } else {
                    estudiante.setPassword(txtNuevaContrasena.getText());
                    this.volcadoBD();
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setUserData(estudiante);
                    new PanelEstudiante().start(stage);
                }
            }
        } else {
            txtError = "la contraseña es incorrecta, intente nuevamente";
            this.mostrarError(txtError);
        }
    }

    public void mostrarError(String txtError) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Entrada no valida");
        errorAlert.setContentText(txtError);
        errorAlert.showAndWait();
        txtContrasenaAnterior.setText("");
        txtNuevaContrasena.setText("");
        txtConfirmeContrasena.setText("");
    }
    
    public void getData(){
        Stage stage = (Stage) root.getScene().getWindow();
        estudiante = (Estudiante) stage.getUserData();
    }

    public void volcadoBD() {
        DAOEstudianteMySQL dbE = new DAOEstudianteMySQL();
        dbE.updateData(estudiante.getId(), estudiante);
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/cambiarContrasena.fxml"));
        stage.setTitle("Cambiar Contraseña");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
