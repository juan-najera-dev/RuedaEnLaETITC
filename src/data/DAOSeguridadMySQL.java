package data;

import functions.Constantes.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.usuario.Seguridad;

public class DAOSeguridadMySQL implements IDAOFactory<Seguridad> {

    private static final String DBNAME = BaseDeDatos.DBNAME.value;
    private static final String TABLENAME = BaseDeDatos.TABLESEGURIDAD.value;
    private static final String DRIVER = BaseDeDatos.DRIVER.value;
    private static final String USER = BaseDeDatos.USER.value;
    private static final String PASSWORD = BaseDeDatos.PASSWORD.value;
    private static final String URL = BaseDeDatos.URL.value;

    private static Connection connector;
    private final ArrayList<Seguridad> seguridad = new ArrayList<>();

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
    public ArrayList<Seguridad> readAllData() {
        connector = connectDB();
        try {
            String query = "SELECT * FROM " + TABLENAME + ";";
            Statement stm = connector.createStatement();
            ResultSet conjuntoRegistros = stm.executeQuery(query);
            boolean apuntador = conjuntoRegistros.next();
            if (!apuntador) {
                System.out.println("No hay datos");
            } else {
                while (apuntador) {
                    Seguridad seg = new Seguridad();
                    seg.setId(conjuntoRegistros.getInt("ID"));
                    seg.setNombre(conjuntoRegistros.getString("Nombre"));
                    seg.setPassword(conjuntoRegistros.getString("Password"));
                    seg.setSede(conjuntoRegistros.getString("Sede"));
                    apuntador = conjuntoRegistros.next();
                    this.seguridad.add(seg);
                }
            }
            this.disconnect();
            return seguridad;
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
    public void createRegister(Seguridad nuevoRegistro) {
        connector = connectDB();
        try {
            String query = "INSERT INTO " + TABLENAME
                    + "(ID, Nombre, Password, Sede) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, nuevoRegistro.getId());
            declaration.setString(2, nuevoRegistro.getNombre());
            declaration.setString(3, nuevoRegistro.getPassword());
            declaration.setString(4, nuevoRegistro.getSede());
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
    public Seguridad readRegister(int id) {
        connector = connectDB();
        Seguridad seg = new Seguridad();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE ID=" + id + ";";
            Statement stm = connector.createStatement();
            ResultSet data = stm.executeQuery(query);
            boolean apuntador = data.next();
            if (!apuntador) {
                return null;
            } else {
                seg.setId(id);
                seg.setNombre(data.getString("Nombre"));
                seg.setPassword(data.getString("Password"));
                seg.setSede(data.getString("Sede"));
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
        return seg;
    }

    @Override
    public void updateData(int id, Seguridad registro) {
        connector = connectDB();
        try {
            String query = "UPDATE " + TABLENAME + " SET "
                    + "ID = ?, "
                    + "Nombre = ?, "
                    + "Password = ?, "
                    + "Sede = ? "
                    + "WHERE ID = ?;";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, registro.getId());
            declaration.setString(2, registro.getNombre());
            declaration.setString(3, registro.getPassword());
            declaration.setString(4, registro.getSede());
            declaration.setInt(5, id);
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
            String query = "DELETE FROM " + TABLENAME + " WHERE ID=" + id + ";";
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

    private void disconnect() {
        try {
            connector.close();
            System.out.println("Desconectado");
        } catch (SQLException e) {
            System.out.println("No se pudo desconectar " + e);
        }
    }
}