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

    public boolean agregarZona(String nombre) {
        return false;
    }

    public boolean eliminarZona(String nombre) {
        return false;
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public String getNombre() {
        return nombre;
    }
}