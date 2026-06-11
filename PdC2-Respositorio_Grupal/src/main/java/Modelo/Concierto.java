package Modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Concierto {
    private String nombre; //Atributos
    private Date fecha;
    private List<Zona> zonas;

    public Concierto() {
        this.zonas = new ArrayList<>();
    }
    
    public Concierto(String nombre, Date fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.zonas = new ArrayList<>();
    }

    public boolean agregarZona(String nombreZona, int capacidad, int precio) {
        // Validación: Evitar registrar una zona con el mismo nombre
        for (Zona z : zonas) {
            if (z.getNombre().equalsIgnoreCase(nombreZona)) {
                return false; 
            }
        }
        
        Zona nuevaZona = new Zona(nombreZona, capacidad, precio);
        this.zonas.add(nuevaZona);
        return true;
    }

    // Método que coincide exactamente con la firma del diagrama UML
    public boolean agregarZona(String nombre) {
        // Llama al método principal pasando valores por defecto
        return agregarZona(nombre, 0, 0);
    }

    public boolean eliminarZona(String nombre) {
    	for (Zona z : zonas) {
            // Comparamos el nombre de la zona actual con el que recibimos por parámetro
            if (z.getNombre().equalsIgnoreCase(nombre)) {
                // Si coincide, eliminamos la zona de la lista
                zonas.remove(z); 
                return true; // Retornamos true porque encontramos y eliminamos un elemento
            }
        }
        return false;
    }

    public List<Zona> getZonas() {
        return zonas;
    }
    public void setZonas(List<Zona> zonas) { 
    	this.zonas = zonas; 
    }

    public String getNombre() {
        return nombre;
    }
    public void setFecha(Date fecha) { 
    	this.fecha = fecha; 
    }
}