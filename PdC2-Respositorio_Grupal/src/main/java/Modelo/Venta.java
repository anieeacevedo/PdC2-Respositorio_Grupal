package Modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Venta {
    private Date fecha;
    private int monto;
    private List<Entrada> entradas;

    public Venta() {
        this.entradas = new ArrayList<>();
    }
    public boolean anular() {
        return false;
    }
    public List<Entrada> getEntradas() {
        return entradas;
    }
    public void setMonto(int monto) {
        this.monto = monto;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getFecha() {
        return fecha;
    }
    public int getMonto() {
        return monto;
    }
}