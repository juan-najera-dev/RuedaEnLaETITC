package controllers;

import controllers.seguridad.PanelSeguridad;
import controllers.admin.PanelAdmin;
import controllers.estudiante.PanelEstudiante;
import data.DAOAdminMySQL;
import data.DAOEstudianteMySQL;
import data.DAOSeguridadMySQL;
import functions.Constantes.listaRoles;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.usuario.Usuario;

public class IniciarSesion extends Application {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtId;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private ChoiceBox cbxRol;
    private Usuario user;

    public void iniciarSesion() throws Exception {
        String comparador = this.validarContrasena();
        String txtError;
        if (comparador != null) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setUserData(user);
            switch (comparador) {
                case "class model.usuario.Estudiante" -> {
                    new PanelEstudiante().start(stage);
                }
                case "class model.usuario.Admin" -> {
                    new PanelAdmin().start(stage);
                }
                case "class model.usuario.Seguridad" -> {
                    new PanelSeguridad().start(stage);
                }
                case "No esta inscrito" -> {
                    txtError = "El usuario no está inscrit@, contacte al administrador";
                    this.mostrarError(txtError);
                }
            }
        } else {
            txtError = "El usuario o la contraseña son incorrectas, intente nuevamente";
            this.mostrarError(txtError);
        }
    }

    public void mostrarError(String txtError) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Entrada no valida");
        errorAlert.setContentText(txtError);
        errorAlert.showAndWait();
        txtId.setText("");
        txtContrasena.setText("");
    }

    private void getUsuario(String tipoUsuario, int id) {
        if (tipoUsuario.equals(listaRoles.Estudiante.name())) {
            DAOEstudianteMySQL dbEstudiante = new DAOEstudianteMySQL();
            user = dbEstudiante.readRegister(id);
        } else if (tipoUsuario.equals(listaRoles.Administrador.name())) {
            DAOAdminMySQL dbAdmin = new DAOAdminMySQL();
            user = dbAdmin.readRegister(id);
        } else {
            DAOSeguridadMySQL dbSeguridad = new DAOSeguridadMySQL();
            user = dbSeguridad.readRegister(id);
        }
    }

    private String validarContrasena() {
        this.getUsuario(cbxRol.getValue().toString(), Integer.parseInt(txtId.getText()));
        if (user == null) {
            return "No esta inscrito";
        } else if (user.validarConstrasena(txtContrasena.getText())) {
            return user.getClass().toString();
        }
        return null;
    }

    public void initialize() {
        cbxRol.setItems(FXCollections.observableArrayList(listaRoles.values()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../views/iniciarSesion.fxml"));
        stage.setTitle("Rueda en la ETITC");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
