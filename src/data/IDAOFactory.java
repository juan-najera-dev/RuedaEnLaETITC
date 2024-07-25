package data;

import java.util.ArrayList;

public interface IDAOFactory<T> {
    ArrayList<T> readAllData();
    void createRegister(T nuevoRegistro);
    T readRegister(int id);
    void updateData(int id, T registro);
    void deleteRegister(int id);
}
