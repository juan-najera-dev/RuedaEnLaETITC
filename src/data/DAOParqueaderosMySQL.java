package data;

import functions.Constantes.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.bicicleta.ParqueaderoSede;

public class DAOParqueaderosMySQL implements IDAOFactory<ParqueaderoSede> {

    private static final String DBNAME = BaseDeDatos.DBNAME.value;
    private static final String TABLENAME = BaseDeDatos.TABLEPARQUEADEROS.value;
    private static final String DRIVER = BaseDeDatos.DRIVER.value;
    private static final String USER = BaseDeDatos.USER.value;
    private static final String PASSWORD = BaseDeDatos.PASSWORD.value;
    private static final String URL = BaseDeDatos.URL.value;

    private static Connection connector;
    private final ArrayList<ParqueaderoSede> sedes = new ArrayList<>();

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
    public ArrayList<ParqueaderoSede> readAllData() {
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
                    ParqueaderoSede sede = new ParqueaderoSede();
                    sede.setId(conjuntoRegistros.getInt("ID"));
                    sede.setNombreSede(conjuntoRegistros.getString("Nombre"));
                    sede.setCuposDisponibles(conjuntoRegistros.getInt("CuposTotales"));
                    sede.setCuposTotales(conjuntoRegistros.getInt("CuposDisponibles"));

                    DAOBicicletaNormalMySQL db = new DAOBicicletaNormalMySQL();
                    sede.setBicicletas(db.getBicicletasSede(sede.getNombreSede()));

                    apuntador = conjuntoRegistros.next();
                    sedes.add(sede);
                }
            }
            this.disconnect();
            return sedes;
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
    public void createRegister(ParqueaderoSede nuevoRegistro) {
        connector = connectDB();
        try {
            String query = "INSERT INTO " + TABLENAME
                    + "(ID, Nombre, CuposTotales, CuposDisponibles) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement declaration = connector.prepareStatement(query);
            declaration.setInt(1, nuevoRegistro.getId());
            declaration.setString(2, nuevoRegistro.getNombreSede());
            declaration.setInt(3, nuevoRegistro.getCuposTotales());
            declaration.setInt(4, nuevoRegistro.getCuposDisponibles());
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
    public ParqueaderoSede readRegister(int id) {
        connector = connectDB();
        ParqueaderoSede parqueaderoSede = new ParqueaderoSede();
        try {
            String query = "SELECT * FROM " + TABLENAME + " WHERE ID=" + id + ";";
            Statement stm = connector.createStatement();
            ResultSet data = stm.executeQuery(query);
            boolean apuntador = data.next();
            if (!apuntador) {
                return null;
            } else {
                parqueaderoSede.setId(id);
                parqueaderoSede.setNombreSede(data.getString("Nombre"));
                parqueaderoSede.setCuposTotales(data.getInt("CuposTotales"));
                parqueaderoSede.setCuposDisponibles(data.getInt("CuposDisponibles"));
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
        return parqueaderoSede;
    }

    @Override
    public void updateData(int id, ParqueaderoSede registro) {
        connector = connectDB();
        try {
            String query = "UPDATE " + TABLENAME + " SET "                    
                    + "Nombre = ?, "
                    + "ID = ?, "
                    + "CuposTotales = ?, "
                    + "CuposDisponibles = ? "
                    + "WHERE ID = ?;";
            PreparedStatement declaration = connector.prepareStatement(query);           
            declaration.setString(1, registro.getNombreSede());
            declaration.setInt(2, registro.getId());
            declaration.setInt(3, registro.getCuposTotales());
            declaration.setInt(4, registro.getCuposDisponibles());
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
