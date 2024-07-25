package model.bicicleta;

public interface IBicicleta {
    public void construirFrenos(String tipo, String[] estado);
    public void construirManubios(String material, String[] estado);
    public void construirMarco(String material, int tamano, String estado);
    public void construitPedales(String material, String[] estado);
    public void construirRuedas(int anchoLlanta, int diametroRin, int numRadios, String[] estado);
    public void construirSillines(String[] estado);
    public void construirMotor(String tipo, String estado);
    public void actualizarEstado();
}
