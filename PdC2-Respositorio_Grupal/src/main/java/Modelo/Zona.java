package Modelo;

import java.util.List;
import java.util.ArrayList;

public class Zona {
    private String nombre;
    private int capacidad;
    private int precio;
    private List<Entrada> entradas;

    public Zona(String nombre, int capacidad, int precio) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
        this.entradas = new ArrayList<>();
    }

    public void reducirCapacidad(int cantidad) {
        this.capacidad -= cantidad;
    }

    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public int getPrecio() { return precio; }
    public List<Entrada> getEntradas() { return entradas; }
}