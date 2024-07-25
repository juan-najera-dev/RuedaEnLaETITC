package model.usuario;

public class Usuario {
    protected int id;
    protected String nombre;
    protected String password;
    protected String correo;
    
    public Usuario(){}
    
    public Usuario(int id, String nombre, String password, String correo){
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public boolean validarConstrasena (String contrasena){
        return this.password.equals(contrasena);
    }
}
