package Vista;

import Controlador.SistemaVentasControlador;
import Modelo.Venta;
import Modelo.Entrada;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaHistorialCliente extends JDialog {

    private final SistemaVentasControlador controlador;
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private JLabel lblPuntos;

  
    public VistaHistorialCliente(Frame parent, SistemaVentasControlador controlador) {
        super(parent, "Historial de Compras y Puntos", true); // true para hacerlo modal
        this.controlador = controlador;
        
        initComponents();
        cargarDatosCliente();
        
        this.setSize(600, 400);
        this.setLocationRelativeTo(parent); // Centrar respecto a la ventana principal
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        
        // --- PANEL SUPERIOR: TÍTULO Y PUNTOS ---
        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBorder(new EmptyBorder(10, 10, 5, 10));
        
        JLabel lblTitulo = new JLabel("MIS COMPRAS EN EL SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        
        // Muestra la bonificación acumulada de puntos obtenida del Cliente
        lblPuntos = new JLabel("Puntos Acumulados: Cargando...", SwingConstants.LEFT);
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 12));
        panelNorte.add(lblPuntos, BorderLayout.SOUTH);
        
        this.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA DE HISTORIAL ---
        // Definición de las columnas de la tabla de visualización
        String[] columnas = {"N°", "Fecha de Compra", "Monto Total (S/.)", "Entradas Adquiridas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Garantizar que la tabla sea puramente de lectura desde la interfaz
            }
        };
        
        tablaCompras = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Consume la información del modelo a través del controlador respetando 
     * el encapsulamiento inmutable de la lista de ventas.
     */
    private void cargarDatosCliente() {
        // 1. Recuperar la lista de ventas (es de solo lectura por diseño de encapsulamiento)
        List<Venta> historial = controlador.obtenerHistorialCliente();
        
        // 2. Limpiar filas previas de la interfaz gráfica por seguridad
        modeloTabla.setRowCount(0);
        
        if (historial.isEmpty()) {
            lblPuntos.setText("Puntos Acumulados: 0 (Aún no registra compras)");
            return;
        }

        // Variable auxiliar para el total dinámico de puntos
        int totalPuntos = 0;
        int contador = 1;

        // 3. Iterar de forma segura sobre la colección inmutable
        for (Venta venta : historial) {
            int cantidadEntradas = venta.getEntradas().size();
            
            // Reconstrucción del registro para el JTable
            Object[] fila = {
                contador++,
                venta.getFecha().toString(),
                "S/. " + venta.getMonto(),
                cantidadEntradas + " entrada(s)"
            };
            
            modeloTabla.addRow(fila);
            
            // Cálculo derivado: 10 puntos por cada entrada en la transacción
            totalPuntos += (cantidadEntradas * 10);
        }

        // 4. Actualizar el componente gráfico con la información procesada
        lblPuntos.setText("Puntos Acumulados Totales: " + totalPuntos + " pts");
    }
}
