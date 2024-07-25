package data;

import functions.Constantes.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.usuario.Estudiante;

public final class DAOEstudianteMySQL implements IDAOFactory<Estudiante> {

    private static final String DBNAME = BaseDeDatos.DBNAME.value;
    private static final String TABLENAME = BaseDeDatos.TABLEESTUDIANTES.value;
    private static final String DRIVER = BaseDeDatos.DRIVER.value;
    private static final String USER = BaseDeDatos.USER.value;
    private static final String PASSWORD = BaseDeDatos.PASSWORD.value;
    private static final String URL = BaseDeDatos.URL.value;

    private static Connection connector;
    private final ArrayList<Estudiante> estudiantes = new ArrayList<>();

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
    public ArrayList<Estudiante> readAllData() {
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
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(conjuntoRegistros.getInt("ID"));
                    estudiante.setNombre(conjuntoRegistros.getString("Nombre"));
                    estudiante.setCorreo(conjuntoRegistros.getString("Correo"));
                    estudiante.setPassword(conjuntoRegistros.getString("Password"));
                    estudiante.setCarrera(conjuntoRegistros.getString("Carrera"));
                    estudiante.setSede(conjuntoRegistros.getString("Sede"));
                    estudiante.setEstado(conjuntoRegistros.getString("Estado"));
                    apuntador = conjuntoRegistros.next();
                    estudiantes.add(estudiante);
                }
            }
            this.disconnect();
            return estudiantes;
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
    public void createRegister(Estudiante nuevoRegistro) {
        connector = connectDB();
        try {
            String query = "INSERT INTO " + TABLENAME
                    + "(ID, Nombre, Correo, Password, Carrera, Sede, Estado) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, nuevoRegistro.getId());
            declaration.setString(2, nuevoRegistro.getNombre());
            declaration.setString(3, nuevoRegistro.getCorreo());
            declaration.setString(4, nuevoRegistro.getPassword());
            declaration.setString(5, nuevoRegistro.getCarrera());
            declaration.setString(6, nuevoRegistro.getSede());
            declaration.setString(7, nuevoRegistro.getEstado());
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
    public Estudiante readRegister(int id) {
        connector = connectDB();
        Estudiante estudiante = new Estudiante();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE ID=" + id + ";";
            Statement stm = connector.createStatement();
            ResultSet data = stm.executeQuery(query);
            boolean apuntador = data.next();
            if (!apuntador) {
                return null;
            } else {
                estudiante.setId(id);
                estudiante.setNombre(data.getString("Nombre"));
                estudiante.setCorreo(data.getString("Correo"));
                estudiante.setPassword(data.getString("Password"));
                estudiante.setCarrera(data.getString("Carrera"));
                estudiante.setSede(data.getString("Sede"));
                estudiante.setEstado(data.getString("Estado"));
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
        return estudiante;
    }

    @Override
    public void updateData(int id, Estudiante registro) {
        connector = connectDB();
        try {
            String query = "UPDATE " + TABLENAME + " SET "
                    + "ID = ?, "
                    + "Nombre = ?, "
                    + "Correo = ?, "
                    + "Password = ?, "
                    + "Carrera = ?, "
                    + "Sede = ?, "
                    + "Estado = ? "
                    + "WHERE ID = ?;";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, registro.getId());
            declaration.setString(2, registro.getNombre());
            declaration.setString(3, registro.getCorreo());
            declaration.setString(4, registro.getPassword());
            declaration.setString(5, registro.getCarrera());
            declaration.setString(6, registro.getSede());
            declaration.setString(7, registro.getEstado());
            declaration.setInt(8, id);
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
