package Modelo;

public class Tarjeta {
    private String numero;
    private String nombre;
    private String fecha;
    private String CVV;

    public Tarjeta() {
        // Constructor vacío necesario para Cliente.java
    }

    public Tarjeta(String numero, String nombre, String fecha, String cvv) {
        this.numero = numero;
        this.nombre = nombre;
        this.fecha = fecha;
        this.CVV = cvv;
    }

    // Getters y Setters (opcionales por ahora, pero buena práctica)
    public String getNumero() { return numero; }
    public String getNombre() { return nombre; }
    public String getFecha() { return fecha; }
    public String getCVV() { return CVV; }
}