package controllers.admin;

import data.DAOBicicletaNormalMySQL;
import data.DAOParqueaderosMySQL;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.bicicleta.Bicicleta;
import model.bicicleta.ParqueaderoSede;

public class VerSedes {

    @FXML
    private AnchorPane root;
    @FXML
    private WebView webResultado;
    private ArrayList<ParqueaderoSede> sedes = new ArrayList<>();

    public void volver() throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelAdmin().start(stage);
    }

    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/verSedes.fxml"));
        stage.setTitle("Ver Bicicletas");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() {
        DAOParqueaderosMySQL db = new DAOParqueaderosMySQL();
        sedes = db.readAllData();
        String str = this.construirHTML();
        webResultado.getEngine().loadContent(str);
    }

    private String construirHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Lista Bicicletas</title>");
        sb.append("<meta charset = \"UTF-8\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<table align = \"center\">");
        sb.append("<tr>");
        sb.append("<td style = \"border-style: solid\">ID</td>");
        sb.append("<td style = \"border-style: solid\">Nombre</td>");
        sb.append("<td style = \"border-style: solid\">CuposTotales</td>");
        sb.append("<td style = \"border-style: solid\">CuposDisponibles</td>");
        sb.append("</tr>");
        for (ParqueaderoSede e : sedes) {
            sb.append("<tr>");
            sb.append("<td>").append(e.getId()).append("</td>");
            sb.append("<td>").append(e.getNombreSede()).append("</td>");
            sb.append("<td>").append(e.getCuposTotales()).append("</td>");
            sb.append("<td>").append(e.getCuposDisponibles()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

}
