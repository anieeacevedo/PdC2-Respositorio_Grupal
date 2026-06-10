package Modelo;

import java.util.List;
import java.util.ArrayList;

public class Usuario extends Persona {
    private boolean estado;
    private List<Concierto> conciertos; // Agregación (1 a muchos)

    public Usuario() {
        this.conciertos = new ArrayList<>();
        this.estado = true;
    }

    public void registrarZonas(Concierto concierto, String nombreZona, int capacidad, int precio) 
            throws IllegalArgumentException {
        
        // Validación preventiva 
        if (capacidad <= 0 || precio <= 0) {
            throw new IllegalArgumentException("La capacidad y el precio deben ser mayores a cero.");
        }
        
        concierto.agregarZona(nombreZona);
        System.out.println("Zona '" + nombreZona + "' registrada con éxito por el administrador.");
    }
    
    public void supervisarVentas(Concierto concierto) {
        System.out.println("=== Reporte de supervisión para: " + concierto.getNombre() + " ===");
        // Itera sobre las zonas para auditar las entradas vendidas
        for (Zona zona : concierto.getZonas()) {
            System.out.println("Zona: " + zona.getNombre() + " | Capacidad Restante: " + zona.getCapacidad());
        }
    }

    @Override
    public boolean registrarTarjeta() {
        return false;
    }

    @Override
    public boolean eliminarTarjeta() {
        return false;
    }

    @Override
    public boolean anularVenta() {
        return true;
    }

    @Override
    public boolean comprar() {
        return false;
    }
    
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public List<Concierto> getConciertos() { return conciertos; }
}