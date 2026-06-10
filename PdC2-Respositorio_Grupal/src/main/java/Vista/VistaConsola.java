package Vista;

import Controlador.SistemaVentasControlador;
import Modelo.Zona;
import Modelo.Venta;
import java.util.Scanner;
import java.util.List;

public class VistaConsola {
    private SistemaVentasControlador controlador;
    private Scanner teclado;

    public VistaConsola() {
        this.controlador = new SistemaVentasControlador();
        this.teclado = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        System.out.println("   SISTEMA DE VENTA DE ENTRADAS  ");

        do {
            System.out.println("\n MENÚ DE OPCIONES");
            System.out.println("1. Ver zonas de concierto y precios");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Ver mis puntos y compras (Cliente)");
            System.out.println("4. Panel de supervisión (Administrador)");
            System.out.println("5. Salir del sistema");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                
                switch (opcion) {
                    case 1:
                        mostrarZonas();
                        break;
                    case 2:
                        solicitarCompra();
                        break;
                    case 3:
                        mostrarHistorialCliente();
                        break;
                    case 4:
                        mostrarReporteAdmin();
                        break;
                    case 5:
                        System.out.println("Vuelva pronto.");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (opcion != 5);
    }

    private void mostrarZonas() {
        System.out.println("\n--- ZONAS DISPONIBLES ---");
        List<Zona> zonas = controlador.obtenerZonasDisponibles();
        for (Zona z : zonas) {
            System.out.println("» " + z.getNombre() + " | Precio: S/. " + z.getPrecio() + " | Capacidad Disponible: " + z.getCapacidad());
        }
    }

    private void solicitarCompra() {
        System.out.println("\n--- PROCESO DE COMPRA ---");
        System.out.print("Ingrese el nombre de la zona (ej. VIP / General): ");
        String zona = teclado.nextLine();
        
        System.out.print("¿Cuántas entradas desea comprar? (Máximo 4): ");
        try {
            int cantidad = Integer.parseInt(teclado.nextLine());
            String resultado = controlador.procesarCompra(zona, cantidad);
            System.out.println("\n[RESULTADO]: " + resultado);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: La cantidad debe ser un número entero.");
        }
    }

    private void mostrarHistorialCliente() {
        System.out.println("\n--- HISTORIAL DE COMPRAS ---");
        List<Venta> compras = controlador.obtenerHistorialCliente();
        if (compras.isEmpty()) {
            System.out.println("Usted aún no ha realizado ninguna compra.");
        } else {
            for (int i = 0; i < compras.size(); i++) {
                Venta v = compras.get(i);
                System.out.println((i + 1) + ". Compra Fecha: " + v.getFecha() + " || Monto Total: S/. " + v.getMonto());
            }
        }
    }

    private void mostrarReporteAdmin() {
        System.out.println("\n--- ACCESO ADMINISTRATIVO ---");
        controlador.ejecutarSupervisionAdmin();
    }
}