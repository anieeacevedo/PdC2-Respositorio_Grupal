package Modelo;

// Excepción si se intentan comprar más de 4 entradas en una sola transacción
public class LimiteEntradasException extends Exception {
    public LimiteEntradasException(String mensaje) {
        super(mensaje);
    }
}