package data;

import functions.Constantes.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.bicicleta.Bicicleta;
import model.bicicleta.BicicletaNormal;
import model.bicicleta.partes.Freno;
import model.bicicleta.partes.Manubrio;
import model.bicicleta.partes.Marco;
import model.bicicleta.partes.Pedal;
import model.bicicleta.partes.Rueda;
import model.bicicleta.partes.Sillin;
import model.bicicleta.partes.SistemaMotor;

public class DAOBicicletaNormalMySQL implements IDAOFactory<Bicicleta> {

    private static final String DBNAME = BaseDeDatos.DBNAME.value;
    private static final String TABLENAME = BaseDeDatos.TABLEBICICLETAS.value;
    private static final String DRIVER = BaseDeDatos.DRIVER.value;
    private static final String USER = BaseDeDatos.USER.value;
    private static final String PASSWORD = BaseDeDatos.PASSWORD.value;
    private static final String URL = BaseDeDatos.URL.value;

    private static Connection connector;
    private final ArrayList<Bicicleta> bicicletas = new ArrayList<>();

    private Connection connectDB() {
        try {
            Class.forName(DRIVER);
            connector = (Connection) DriverManager.getConnection(URL + DBNAME, USER, PASSWORD);
            if (connector != null) {
                System.out.println("Conexión a la tabla " + TABLENAME + " OK");
            }
        } catch (SQLException e) {
            System.err.println("""
                               Error al conectar a mysql:
                               \tdescripcion:""" + e.getMessage()
                    + "\n\tlocalizacion:" + e.getLocalizedMessage()
                    + "\n\tSQL Result:" + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.err.println("""
                               Error al encontrar el controlador:
                               \tdescripcion:""" + e.getMessage()
                    + "\n\tlocalizacion:" + e.getLocalizedMessage());
        }
        return connector;
    }

    @Override
    public ArrayList<Bicicleta> readAllData() {
        connector = connectDB();
        try {
            String query = "SELECT * FROM " + TABLENAME + ";";
            Statement stm = connector.createStatement();
            ResultSet conjuntoRegistros = stm.executeQuery(query);
            boolean apuntador = conjuntoRegistros.next();
            if (!apuntador) {
                return null;
            } else {
                while (apuntador) {
                    BicicletaNormal bicicleta = new BicicletaNormal();
                    bicicleta.setNumeroInventario(conjuntoRegistros.getInt("NumeroInventario"));
                    bicicleta.setSerial(conjuntoRegistros.getInt("Serial"));
                    bicicleta.setTipoBicicleta(conjuntoRegistros.getString("Tipo"));
                    bicicleta.setEstado(conjuntoRegistros.getString("Estado"));
                    bicicleta.setEstudianteID(conjuntoRegistros.getInt("EstudianteID"));
                    bicicleta.setSede(conjuntoRegistros.getString("Sede"));
                    String tipo = conjuntoRegistros.getString("tipoFreno");
                    String[] estados = new String[2];
                    estados[0] = conjuntoRegistros.getString("estadoFreno1");
                    estados[1] = conjuntoRegistros.getString("estadoFreno2");
                    bicicleta.construirFrenos(tipo, estados);
                    estados[0] = conjuntoRegistros.getString("estadoManubrio");
                    bicicleta.construirManubios(conjuntoRegistros.getString("materialManubrio"), estados);
                    bicicleta.construirMarco(conjuntoRegistros.getString("materialMarco"), conjuntoRegistros.getInt("tamanoMarco"), conjuntoRegistros.getString("estadoMarco"));
                    estados[0] = conjuntoRegistros.getString("estadoPedal1");
                    estados[1] = conjuntoRegistros.getString("estadoPedal2");
                    bicicleta.construitPedales(conjuntoRegistros.getString("materialPedal"), estados);
                    int anchoLlanta = conjuntoRegistros.getInt("anchoLlanta");
                    int diametroRin = conjuntoRegistros.getInt("diametroRin");
                    int numeroRadios = conjuntoRegistros.getInt("numRadios");
                    estados[0] = conjuntoRegistros.getString("estadoRueda1");
                    estados[1] = conjuntoRegistros.getString("estadoRueda2");
                    bicicleta.construirRuedas(anchoLlanta, diametroRin, numeroRadios, estados);
                    estados[0] = conjuntoRegistros.getString("estadoSillin");
                    bicicleta.construirSillines(estados);
                    estados[0] = conjuntoRegistros.getString("estadoMotor");
                    bicicleta.construirMotor(conjuntoRegistros.getString("tipoMotor"), estados[0]);
                    apuntador = conjuntoRegistros.next();
                    bicicletas.add(bicicleta);
                }
            }
            this.disconnect();
            return bicicletas;
        } catch (SQLException ex) {
            System.err.println("""
                               Error al obtener los registros de la tabla en la base de datos: 
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
            return null;
        }
    }

    @Override
    public void createRegister(Bicicleta nuevoRegistro) {
        connector = connectDB();
        try {
            String query = "INSERT INTO " + TABLENAME
                    + "(NumeroInventario, Serial, Tipo, EstudianteID, Sede) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, nuevoRegistro.getNumeroInventario());
            declaration.setInt(2, nuevoRegistro.getSerial());
            declaration.setString(3, nuevoRegistro.getTipoBicicleta());
            declaration.setInt(4, nuevoRegistro.getEstudianteID());
            declaration.setString(5, nuevoRegistro.getSede());
            declaration.executeUpdate();
            System.out.println("Datos ingresados exitosamente");
            this.disconnect();
        } catch (SQLException ex) {
            System.err.println("""
                               Error al insertar datos en la base de datos
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
        }
    }

    @Override
    public Bicicleta readRegister(int id) {
        connector = connectDB();
        BicicletaNormal bicicleta = new BicicletaNormal();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE NumeroInventario=" + id + ";";
            Statement stm = connector.createStatement();
            ResultSet data = stm.executeQuery(query);
            boolean apuntador = data.next();
            if (!apuntador) {
                this.disconnect();
                return null;
            } else {
                bicicleta.setNumeroInventario(data.getInt("NumeroInventario"));
                bicicleta.setSerial(data.getInt("Serial"));
                bicicleta.setTipoBicicleta(data.getString("Tipo"));
                bicicleta.setEstado(data.getString("Estado"));
                bicicleta.setEstudianteID(data.getInt("EstudianteID"));
                bicicleta.setSede(data.getString("Sede"));
                String tipo = data.getString("tipoFreno");
                String[] estados = new String[2];
                estados[0] = data.getString("estadoFreno1");
                estados[1] = data.getString("estadoFreno2");
                bicicleta.construirFrenos(tipo, estados);
                estados[0] = data.getString("estadoManubrio");
                bicicleta.construirManubios(data.getString("materialManubrio"), estados);
                bicicleta.construirMarco(data.getString("materialMarco"), data.getInt("tamanoMarco"), data.getString("estadoMarco"));
                estados[0] = data.getString("estadoPedal1");
                estados[1] = data.getString("estadoPedal2");
                bicicleta.construitPedales(data.getString("materialPedal"), estados);
                int anchoLlanta = data.getInt("anchoLlanta");
                int diametroRin = data.getInt("diametroRin");
                int numeroRadios = data.getInt("numRadios");
                estados[0] = data.getString("estadoRueda1");
                estados[1] = data.getString("estadoRueda2");
                bicicleta.construirRuedas(anchoLlanta, diametroRin, numeroRadios, estados);
                estados[0] = data.getString("estadoSillin");
                bicicleta.construirSillines(estados);
                estados[0] = data.getString("estadoMotor");
                bicicleta.construirMotor(data.getString("tipoMotor"), estados[0]);
                System.out.println("Lectura Exitosa");
            }
            this.disconnect();
        } catch (SQLException ex) {
            System.out.println("""
                               Error al obtener el registro en la base de datos
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
        }
        return bicicleta;
    }

    @Override
    public void updateData(int id, Bicicleta registro) {
        connector = connectDB();
        try {
            String query = "UPDATE " + TABLENAME + " SET "
                    + "NumeroInventario = ?, "
                    + "Serial = ?, "
                    + "Tipo = ?, "
                    + "Estado = ?, "
                    + "EstudianteID = ?, "
                    + "Sede = ?, "
                    + "tipoFreno = ?, "
                    + "estadoFreno1 = ?, "
                    + "estadoFreno2 = ?, "
                    + "materialManubrio = ?, "
                    + "estadoManubrio = ?, "
                    + "materialMarco = ?, "
                    + "tamanoMarco = ?, "
                    + "estadoMarco = ?, "
                    + "materialPedal = ?, "
                    + "estadoPedal1 = ?, "
                    + "estadoPedal2 = ?, "
                    + "anchoLlanta = ?, "
                    + "diametroRin = ?, "
                    + "numRadios = ?, "
                    + "estadoRueda1 = ?, "
                    + "estadoRueda2 = ?, "
                    + "estadoSillin = ?, "
                    + "tipoMotor = ?, "
                    + "estadoMotor = ? "
                    + "WHERE NumeroInventario = ?;";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, registro.getNumeroInventario());
            declaration.setInt(2, registro.getSerial());
            declaration.setString(3, registro.getTipoBicicleta());
            declaration.setString(4, registro.getEstado());
            declaration.setInt(5, registro.getEstudianteID());
            declaration.setString(6, registro.getSede());
            Freno[] frenos = registro.getFrenos();
            declaration.setString(7, frenos[0].getTipo());
            declaration.setString(8, frenos[0].getEstado());
            declaration.setString(9, frenos[1].getEstado());
            Manubrio[] manubrio = registro.getManubrio();
            declaration.setString(10, manubrio[0].getMaterial());
            declaration.setString(11, manubrio[0].getEstado());
            Marco marco = registro.getMarco();
            declaration.setString(12, marco.getMaterial());
            declaration.setInt(13, marco.getTamano());
            declaration.setString(14, marco.getEstado());
            Pedal[] pedales = registro.getPedales();
            declaration.setString(15, pedales[0].getMaterial());
            declaration.setString(16, pedales[0].getEstado());
            declaration.setString(17, pedales[1].getEstado());
            Rueda[] ruedas = registro.getRuedas();           
            declaration.setInt(18, ruedas[0].getAnchoLlanta());
            declaration.setInt(19, ruedas[0].getDiametroRin());
            declaration.setInt(20, ruedas[0].getNumRadios());
            declaration.setString(21, ruedas[0].getEstado());
            declaration.setString(22, ruedas[1].getEstado());           
            Sillin[] sillin = registro.getSillin();
            declaration.setString(23, sillin[0].getEstado());
            SistemaMotor motor = registro.getSistemamotor();         
            declaration.setString(24, motor.getTipo());
            declaration.setString(25, motor.getEstado());
            declaration.setInt(26, id);
            declaration.executeUpdate();
            this.disconnect();
        } catch (SQLException ex) {
            System.err.println("""
                               Error al editar datos en la base de datos
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
        }
    }

    @Override
    public void deleteRegister(int id) {
        connector = connectDB();
        try {
            String query = "DELETE FROM " + TABLENAME + " WHERE NumeroInventario=" + id + ";";
            Statement stm = connector.createStatement();
            stm.executeUpdate(query);
            this.disconnect();
        } catch (SQLException ex) {
            System.err.println("""
                               Error al eliminar datos en la base de datos
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
        }
    }

    public ArrayList<Bicicleta> getBicicletasSede(String sede) {
        connector = connectDB();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE Sede = '" + sede + "';";
            Statement stm = connector.createStatement();
            ResultSet conjuntoRegistros = stm.executeQuery(query);
            boolean apuntador = conjuntoRegistros.next();
            if (!apuntador) {
                return null;
            } else {
                while (apuntador) {
                    BicicletaNormal bicicleta = new BicicletaNormal();
                    bicicleta.setNumeroInventario(conjuntoRegistros.getInt("NumeroInventario"));
                    bicicleta.setSerial(conjuntoRegistros.getInt("Serial"));
                    bicicleta.setTipoBicicleta(conjuntoRegistros.getString("Tipo"));
                    bicicleta.setEstado(conjuntoRegistros.getString("Estado"));
                    bicicleta.setEstudianteID(conjuntoRegistros.getInt("EstudianteID"));
                    bicicleta.setSede(conjuntoRegistros.getString("Sede"));
                    String tipo = conjuntoRegistros.getString("tipoFreno");
                    String[] estados = new String[2];
                    estados[0] = conjuntoRegistros.getString("estadoFreno1");
                    estados[1] = conjuntoRegistros.getString("estadoFreno2");
                    bicicleta.construirFrenos(tipo, estados);
                    estados[0] = conjuntoRegistros.getString("estadoManubrio");
                    bicicleta.construirManubios(conjuntoRegistros.getString("materialManubrio"), estados);
                    bicicleta.construirMarco(conjuntoRegistros.getString("materialMarco"), conjuntoRegistros.getInt("tamanoMarco"), conjuntoRegistros.getString("estadoMarco"));
                    estados[0] = conjuntoRegistros.getString("estadoPedal1");
                    estados[1] = conjuntoRegistros.getString("estadoPedal2");
                    bicicleta.construitPedales(conjuntoRegistros.getString("materialPedal"), estados);
                    int anchoLlanta = conjuntoRegistros.getInt("anchoLlanta");
                    int diametroRin = conjuntoRegistros.getInt("diametroRin");
                    int numeroRadios = conjuntoRegistros.getInt("numRadios");
                    estados[0] = conjuntoRegistros.getString("estadoRueda1");
                    estados[1] = conjuntoRegistros.getString("estadoRueda2");
                    bicicleta.construirRuedas(anchoLlanta, diametroRin, numeroRadios, estados);
                    estados[0] = conjuntoRegistros.getString("estadoSillin");
                    bicicleta.construirSillines(estados);
                    estados[0] = conjuntoRegistros.getString("estadoMotor");
                    bicicleta.construirMotor(conjuntoRegistros.getString("tipoMotor"), estados[0]);
                    apuntador = conjuntoRegistros.next();
                    bicicletas.add((Bicicleta) bicicleta);
                }
            }
            this.disconnect();
            return bicicletas;
        } catch (SQLException ex) {
            System.err.println("""
                               Error al obtener los registros de la tabla en la base de datos: 
                                \t Descripcion:""" + ex.getMessage()
                    + "\n \t Localizacion: " + ex.getLocalizedMessage()
                    + "\n \t SQL result: " + ex.getSQLState());
            this.disconnect();
            return null;
        }
    }

    private void disconnect() {
        try {
            connector.close();
            System.out.println("Desconectado");
        } catch (SQLException e) {
            System.out.println("No se pudo desconectar " + e);
        }
    }
}
