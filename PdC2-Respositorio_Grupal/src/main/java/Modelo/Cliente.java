package Modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Persona {
    private int puntos;
    private Tarjeta tarjeta; // Composición (1 a 1)
    private List<Venta> ventas; // Asociación (1 a muchos)

    public Cliente() {
        this.tarjeta = new Tarjeta(); 
        this.ventas = new ArrayList<>();
        this.puntos = 0;
    }

    private void ingresar(String usuario, String clave) {
    }

    public boolean comprarEntradas(Zona zonaDestino, List<Entrada> entradasAComprar) 
            throws LimiteEntradasException, ZonaDiferenteException, CapacidadMaximaException {
        
        // Máximo 4 entradas por transacción
        if (entradasAComprar == null || entradasAComprar.isEmpty() || entradasAComprar.size() > 4) {
            throw new LimiteEntradasException("Error: Una transacción no puede superar las 4 entradas.");
        }
        
        //Controlar la capacidad disponible de la zona
        if (zonaDestino.getCapacidad() < entradasAComprar.size()) {
            throw new CapacidadMaximaException("Error: Capacidad insuficiente en la zona " + zonaDestino.getNombre());
        }
        
        // Generar la transacción de venta
        Venta nuevaVenta = new Venta();
        int montoTotal = 0;
        
        for (Entrada entrada : entradasAComprar) {
            // Todas las entradas de la misma transacción deben pertenecer a la misma zona
            if (!entrada.getZonaPertenece().equals(zonaDestino)) {
                throw new ZonaDiferenteException("Error: Todas las entradas en una transacción deben ser de la misma zona.");
            }
            
            // Cambiar estado de la entrada de forma segura
            entrada.vender();
            nuevaVenta.getEntradas().add(entrada);
            montoTotal += zonaDestino.getPrecio();
        }
        
        // Actualizar datos de la venta e histórico del cliente
        nuevaVenta.setMonto(montoTotal);
        nuevaVenta.setFecha(new java.util.Date());
        this.ventas.add(nuevaVenta);
        
        // Bonificación de puntos por la compra
        this.puntos += (entradasAComprar.size() * 10);
        
        // Reducir la capacidad de la zona de concierto
        zonaDestino.reducirCapacidad(entradasAComprar.size());
        
        return true;
    }

    @Override
    public boolean registrarTarjeta() {
        if (this.tarjeta == null) {
            this.tarjeta = new Tarjeta();
        }
        return true;
    }

    @Override
    public boolean eliminarTarjeta() {
        this.tarjeta = null;
        return true;
    }

    @Override
    public boolean anularVenta() {
        if (this.ventas.isEmpty()) {
            return false;
        }
        Venta ultimaVenta = this.ventas.remove(this.ventas.size() - 1);
        return ultimaVenta.anular();
    }
    
    @Override
    public boolean comprar() {
        return !this.ventas.isEmpty();
    }

    public int getPuntos() { return puntos; }
    public Tarjeta getTarjeta() { return tarjeta; }
    public List<Venta> getVentas() { return ventas; }
}