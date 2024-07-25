package data;

import functions.Constantes.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.usuario.Admin;

public class DAOAdminMySQL implements IDAOFactory<Admin> {

    private static final String DBNAME = BaseDeDatos.DBNAME.value;
    private static final String TABLENAME = BaseDeDatos.TABLEADMIN.value;
    private static final String DRIVER = BaseDeDatos.DRIVER.value;
    private static final String USER = BaseDeDatos.USER.value;
    private static final String PASSWORD = BaseDeDatos.PASSWORD.value;
    private static final String URL = BaseDeDatos.URL.value;

    private static Connection connector;
    private final ArrayList<Admin> administradores = new ArrayList<>();

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
    public ArrayList<Admin> readAllData() {
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
                    Admin admin = new Admin();
                    admin.setId(conjuntoRegistros.getInt("ID"));
                    admin.setNombre(conjuntoRegistros.getString("Nombre"));
                    admin.setCorreo(conjuntoRegistros.getString("Correo"));
                    admin.setPassword(conjuntoRegistros.getString("Password"));

                    apuntador = conjuntoRegistros.next();
                    administradores.add(admin);
                }
            }
            this.disconnect();
            return administradores;
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
    public void createRegister(Admin nuevoRegistro) {
        connector = connectDB();
        try {
            String query = "INSERT INTO " + TABLENAME
                    + "(ID, Nombre, Correo, Password) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, nuevoRegistro.getId());
            declaration.setString(2, nuevoRegistro.getNombre());
            declaration.setString(3, nuevoRegistro.getCorreo());
            declaration.setString(4, nuevoRegistro.getPassword());
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
    public Admin readRegister(int id) {
        connector = connectDB();
        Admin admin = new Admin();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE ID=" + id + ";";
            Statement stm = connector.createStatement();
            ResultSet data = stm.executeQuery(query);
            boolean apuntador = data.next();
            if (!apuntador) {
                return null;
            } else {
                admin.setId(id);
                admin.setNombre(data.getString("Nombre"));
                admin.setCorreo(data.getString("Correo"));
                admin.setPassword(data.getString("Password"));
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
        return admin;
    }

    @Override
    public void updateData(int id, Admin registro) {
        connector = connectDB();
        try {
            String query = "UPDATE " + TABLENAME + " SET "
                    + "ID = ?, "
                    + "Nombre = ?, "
                    + "Correo = ?, "
                    + "Password = ?, "
                    + "WHERE ID = ?;";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, registro.getId());
            declaration.setString(2, registro.getNombre());
            declaration.setString(3, registro.getCorreo());
            declaration.setString(4, registro.getPassword());
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