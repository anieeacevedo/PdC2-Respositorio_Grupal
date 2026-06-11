/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente extends Persona {

    private int puntos;
    private Tarjeta tarjeta; // Composición (1 a 1)
    private final List<Venta> ventas; // Asociación (1 a muchos)

    
    public Cliente() {
        super();
        this.tarjeta = new Tarjeta(); 
        this.ventas = new ArrayList<>();
        this.puntos = 0;
    }

    
    public Cliente(String nombres, String apellidos, String dni, String contrasena, int puntos) {
        super();
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.contrasena = contrasena;
        this.puntos = puntos;
        this.ventas = new ArrayList<>();
        this.tarjeta = new Tarjeta(); // Mantiene la composición
    }

    
    private boolean ingresar(String usuario, String clave) throws IllegalArgumentException {
        if (this.dni == null || this.contrasena == null) {
            throw new IllegalArgumentException("Credenciales no configuradas.");
        }
        if (this.dni.equals(usuario) && this.contrasena.equals(clave)) {
            return true;
        } else {
            throw new IllegalArgumentException("DNI o contraseña incorrectos.");
        }
    }

   
    public boolean comprarEntradas(Zona zonaDestino, List<Entrada> entradasAComprar) 
            throws LimiteEntradasException, ZonaDiferenteException, CapacidadMaximaException {
        
        // 1. Máximo 4 entradas por transacción
        if (entradasAComprar == null || entradasAComprar.isEmpty() || entradasAComprar.size() > 4) {
            throw new LimiteEntradasException("Error: Una transacción no puede superar las 4 entradas.");
        }
        
        // 2. Controlar la capacidad disponible de la zona
        if (zonaDestino.getCapacidad() < entradasAComprar.size()) {
            throw new CapacidadMaximaException("Error: Capacidad insuficiente en la zona " + zonaDestino.getNombre());
        }
        
        // 3. Generar la transacción de venta
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
        
        // 4. Actualizar datos de la venta e histórico del cliente
        nuevaVenta.setMonto(montoTotal);
        nuevaVenta.setFecha(new java.util.Date());
        this.ventas.add(nuevaVenta);
        
        // 5. Bonificación de puntos por la compra
        this.puntos += (entradasAComprar.size() * 10);
        
        // 6. Reducir la capacidad de la zona de concierto
        zonaDestino.reducirCapacidad(entradasAComprar.size());
        
        return true;
    }

    /**
     * IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS DE PERSONA
     */
    @Override
    public boolean registrarTarjeta() {
        if (this.tarjeta == null) {
            this.tarjeta = new Tarjeta();
        }
        return true;
    }

    @Override
    public boolean eliminarTarjeta() {
        if (this.tarjeta != null) {
            this.tarjeta = null;
            return true;
        }
        return false;
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

    // GETTERS Y SETTERS
    public int getPuntos() { 
        return puntos; 
    }
    
    public void setPuntos(int puntos) { 
        this.puntos = puntos; 
    }
    
    public Tarjeta getTarjeta() { 
        return tarjeta; 
    }
    
    public void setTarjeta(Tarjeta tarjeta) { 
        this.tarjeta = tarjeta; 
    }
    
    // Encapsulado sin setter destructivo para proteger el historial de ventas
    public List<Venta> getVentas() { 
        return Collections.unmodifiableList(ventas); 
    }

    public void agregarVenta(Venta venta) {
        if (venta != null) {
            this.ventas.add(venta);
        }
    }
}
