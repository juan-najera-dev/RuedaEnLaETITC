package controllers.admin;

import data.DAOEstudianteMySQL;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.usuario.Estudiante;

public class VerEstudiantes {

    @FXML
    private AnchorPane root;
    @FXML
    private WebView webResultado;
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();

    public void volver() throws Exception{
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelAdmin().start(stage);
    }
    
    public void start(Stage stage) throws IOException {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/verEstudiantes.fxml"));
        stage.setTitle("Ver Estudiantes");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() {
        DAOEstudianteMySQL db = new DAOEstudianteMySQL();
        estudiantes = db.readAllData();
        String str = this.construirHTML();
        webResultado.getEngine().loadContent(str);
    }
    
    private String construirHTML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Lista Estudiantes</title>");
        sb.append("<meta charset = \"UTF-8\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<table align = \"center\">");
        sb.append("<tr>");
        sb.append("<td style = \"border-style: solid\">ID</td>");
        sb.append("<td style = \"border-style: solid\">NOMBRE</td>");
        sb.append("<td style = \"border-style: solid\">CORREO</td>");
        sb.append("<td style = \"border-style: solid\">PASSWORD</td>");
        sb.append("<td style = \"border-style: solid\">CARRERA</td>");
        sb.append("<td style = \"border-style: solid\">SEDE</td>");
        sb.append("<td style = \"border-style: solid\">ESTADO</td>");
        sb.append("</tr>");
        for(Estudiante e: estudiantes){
            sb.append("<tr>");
            sb.append("<td>").append(Integer.toString(e.getId())).append("</td>");
            sb.append("<td>").append(e.getNombre()).append("</td>");
            sb.append("<td>").append(e.getCorreo()).append("</td>");
            sb.append("<td>").append(e.getPassword()).append("</td>");
            sb.append("<td>").append(e.getCarrera()).append("</td>");
            sb.append("<td>").append(e.getSede()).append("</td>");
            sb.append("<td>").append(e.getEstado()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");                
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}