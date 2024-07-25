package controllers.admin;

import data.DAOEstudianteMySQL;
import functions.Constantes.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.layout.AnchorPane;
import model.usuario.Estudiante;

public class InscribirEstudiante {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private ChoiceBox cbxCarrera;
    @FXML
    private ChoiceBox cbxSede;

    public void enviar() throws Exception {
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setId(Integer.parseInt(txtId.getText()));
        nuevoEstudiante.setNombre(txtNombre.getText());
        nuevoEstudiante.setCorreo(txtEmail.getText());
        nuevoEstudiante.setPassword("123456789");   //Contraseña por Defecto
        nuevoEstudiante.setCarrera(cbxCarrera.getValue().toString());
        nuevoEstudiante.setSede(cbxSede.getValue().toString());
        nuevoEstudiante.setEstado(ListaEstadosEstudiante.ACTIVO.name());
        DAOEstudianteMySQL db = new DAOEstudianteMySQL();
        db.createRegister(nuevoEstudiante);
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelAdmin().start(stage);
    }

    public void volver() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelAdmin().start(stage);
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/inscribirEstudiante.fxml"));
        stage.setTitle("Inscribir Estudiante");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() throws Exception {
        cbxCarrera.setItems(FXCollections.observableArrayList(ListaCarreras.values()));
        cbxSede.setItems(FXCollections.observableArrayList(listaSedes.values()));
    }
}
