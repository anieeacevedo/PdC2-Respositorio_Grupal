package Modelo;

public class Entrada {
    private int numero;
    private String estado;
    private Zona zonaPertenece; // Atributo de referencia cruzada para validar navegación

    public Entrada(int numero, Zona zona) {
        this.numero = numero;
        this.zonaPertenece = zona;
        this.estado = "DISPONIBLE";
    }

    public boolean vender() {
        this.estado = "VENDIDA";
        return true;
    }

    public boolean liberar() {
        this.estado = "DISPONIBLE";
        return true;
    }

    public Zona getZonaPertenece() { return zonaPertenece; }
    public String getEstado() { return estado; }
}