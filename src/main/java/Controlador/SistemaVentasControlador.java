package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaVentasControlador {
    private Concierto concierto;
    private Cliente clienteActual;
    private Usuario administrador;

    public SistemaVentasControlador() {
        this.concierto = new Concierto();
        this.clienteActual = new Cliente();
        this.administrador = new Usuario();
        
        this.concierto.getZonas().add(new Zona("VIP", 10, 150));
        this.concierto.getZonas().add(new Zona("General", 50, 50));
    }

    // Retorna la lista de zonas disponibles en el concierto
    public List<Zona> obtenerZonasDisponibles() {
        return concierto.getZonas();
    }

    // Compra de entradas
    public String procesarCompra(String nombreZona, int cantidadEntradas) {
        // Busca la zona seleccionada
        Zona zonaSeleccionada = null;
        for (Zona z : concierto.getZonas()) {
            if (z.getNombre().equalsIgnoreCase(nombreZona)) {
                zonaSeleccionada = z;
                break;
            }
        }

        if (zonaSeleccionada == null) {
            return "Error: La zona especificada no existe.";
        }

        // Lista de entradas virtuales para simular la compra
        List<Entrada> entradasAComprar = new ArrayList<>();
        for (int i = 0; i < cantidadEntradas; i++) {
            entradasAComprar.add(new Entrada(100 + i, zonaSeleccionada));
        }

        // Capturalas excepciones
        try {
            boolean exito = clienteActual.comprarEntradas(zonaSeleccionada, entradasAComprar);
            if (exito) {
                return "¡Compra exitosa! Se han adquirido " + cantidadEntradas + " entradas para la zona " + zonaSeleccionada.getNombre() + ".\n" +
                       "Puntos acumulados actuales: " + clienteActual.getPuntos();
            }
        } catch (LimiteEntradasException e) {
            return e.getMessage(); // "Error: Una transacción no puede superar las 4 entradas."
        } catch (CapacidadMaximaException e) {
            return e.getMessage(); // "Error: Capacidad insuficiente en la zona."
        } catch (ZonaDiferenteException e) {
            return e.getMessage();
        }

        return "No se pudo procesar la transacción.";
    }

    // Permite ver el historial de compras del cliente
    public List<Venta> obtenerHistorialCliente() {
        return clienteActual.getVentas();
    }

    // Permite al administrador supervisar el estado actual de las ventas
    public void ejecutarSupervisionAdmin() {
        administrador.supervisarVentas(concierto);
    }
}