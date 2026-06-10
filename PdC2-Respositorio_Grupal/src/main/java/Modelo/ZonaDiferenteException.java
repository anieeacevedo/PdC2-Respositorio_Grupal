package Modelo;

// Excepción si el cliente mezcla zonas diferentes en la misma transacción
public class ZonaDiferenteException extends Exception {
    public ZonaDiferenteException(String mensaje) {
        super(mensaje);
    }
}