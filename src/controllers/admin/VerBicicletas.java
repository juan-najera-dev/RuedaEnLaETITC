package controllers.admin;

import data.DAOBicicletaNormalMySQL;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.bicicleta.Bicicleta;

public class VerBicicletas {

    @FXML
    private AnchorPane root;
    @FXML
    private WebView webResultado;
    private ArrayList<Bicicleta> bicicletas = new ArrayList<>();

    public void volver() throws Exception{
        Stage stage = (Stage) root.getScene().getWindow();
        new PanelAdmin().start(stage);
    }
    
    public void start(Stage stage) throws Exception {
        Parent localRoot = FXMLLoader.load(getClass().getResource("../../views/verBicicletas.fxml"));
        stage.setTitle("Ver Bicicletas");
        stage.setScene(new Scene(localRoot));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() {
        DAOBicicletaNormalMySQL db = new DAOBicicletaNormalMySQL();
        bicicletas = db.readAllData();
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
        sb.append("<td style = \"border-style: solid\">NumeroInventario</td>");
        sb.append("<td style = \"border-style: solid\">Serial</td>");
        sb.append("<td style = \"border-style: solid\">Tipo</td>");
        sb.append("<td style = \"border-style: solid\">Estado</td>");
        sb.append("<td style = \"border-style: solid\">EstudianteID</td>");
        sb.append("<td style = \"border-style: solid\">Sede</td>");
        sb.append("<td style = \"border-style: solid\">tipoFreno</td>");
        sb.append("<td style = \"border-style: solid\">estadoFreno1</td>");
        sb.append("<td style = \"border-style: solid\">estadoFreno2</td>");
        sb.append("<td style = \"border-style: solid\">materialManubrio</td>");
        sb.append("<td style = \"border-style: solid\">estadoManubrio</td>");
        sb.append("<td style = \"border-style: solid\">materialMarco</td>");
        sb.append("<td style = \"border-style: solid\">tamanoMarco</td>");
        sb.append("<td style = \"border-style: solid\">estadoMarco</td>");
        sb.append("<td style = \"border-style: solid\">materialPedal</td>");
        sb.append("<td style = \"border-style: solid\">estadoPedal1</td>");
        sb.append("<td style = \"border-style: solid\">estadoPedal2</td>");
        sb.append("<td style = \"border-style: solid\">anchoLlanta</td>");
        sb.append("<td style = \"border-style: solid\">diametroRin</td>");
        sb.append("<td style = \"border-style: solid\">numRadios</td>");
        sb.append("<td style = \"border-style: solid\">estadoRueda1</td>");
        sb.append("<td style = \"border-style: solid\">estadoRueda2</td>");
        sb.append("<td style = \"border-style: solid\">estadoSillin</td>");
        sb.append("<td style = \"border-style: solid\">tipoMotor</td>");
        sb.append("<td style = \"border-style: solid\">estadoMotor</td>");
        sb.append("</tr>");
        for (Bicicleta e : bicicletas) {
            sb.append("<tr>");
            sb.append("<td>").append(e.getNumeroInventario()).append("</td>");
            sb.append("<td>").append(e.getSerial()).append("</td>");
            sb.append("<td>").append(e.getTipoBicicleta()).append("</td>");
            sb.append("<td>").append(e.getEstado()).append("</td>");
            sb.append("<td>").append(e.getEstudianteID()).append("</td>");
            sb.append("<td>").append(e.getSede()).append("</td>");
            sb.append("<td>").append(e.getFrenos()[0].getTipo()).append("</td>");
            sb.append("<td>").append(e.getFrenos()[0].getEstado()).append("</td>");
            sb.append("<td>").append(e.getFrenos()[1].getEstado()).append("</td>");
            sb.append("<td>").append(e.getManubrio()[0].getMaterial()).append("</td>");
            sb.append("<td>").append(e.getManubrio()[0].getEstado()).append("</td>");
            sb.append("<td>").append(e.getMarco().getMaterial()).append("</td>");
            sb.append("<td>").append(e.getMarco().getTamano()).append("</td>");
            sb.append("<td>").append(e.getMarco().getEstado()).append("</td>");
            sb.append("<td>").append(e.getPedales()[0].getMaterial()).append("</td>");
            sb.append("<td>").append(e.getPedales()[0].getEstado()).append("</td>");
            sb.append("<td>").append(e.getPedales()[1].getEstado()).append("</td>");
            sb.append("<td>").append(e.getRuedas()[0].getAnchoLlanta()).append("</td>");
            sb.append("<td>").append(e.getRuedas()[0].getDiametroRin()).append("</td>");
            sb.append("<td>").append(e.getRuedas()[0].getNumRadios()).append("</td>");
            sb.append("<td>").append(e.getRuedas()[0].getEstado()).append("</td>");
            sb.append("<td>").append(e.getRuedas()[1].getEstado()).append("</td>");
            sb.append("<td>").append(e.getSillin()[0].getEstado()).append("</td>");
            sb.append("<td>").append(e.getSistemamotor().getTipo()).append("</td>");
            sb.append("<td>").append(e.getSistemamotor().getEstado()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
