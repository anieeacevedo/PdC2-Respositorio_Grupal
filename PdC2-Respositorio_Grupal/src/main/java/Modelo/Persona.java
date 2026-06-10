package Modelo;

public abstract class Persona {
    protected String nombres;
    protected String apellidos;
    protected String dni;
    protected String contrasena;

    public abstract boolean registrarTarjeta();
    public abstract boolean eliminarTarjeta();
    public abstract boolean anularVenta();
    public abstract boolean comprar();
}