package Modelo;

// Excepción si la zona ya no tiene capacidad libre
public class CapacidadMaximaException extends Exception {
    public CapacidadMaximaException(String mensaje) {
        super(mensaje);
    }
}