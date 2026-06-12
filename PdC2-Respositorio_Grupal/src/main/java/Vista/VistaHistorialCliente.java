package Vista;

import Controlador.SistemaVentasControlador;
import Modelo.Venta;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private JButton btnAnularUltima;
    private JButton btnGestionarTarjeta;
    private final Frame parentFrame; // Guardamos la referencia para abrir el otro JDialog

    public VistaHistorialCliente(Frame parent, SistemaVentasControlador controlador) {
        super(parent, "Historial de Compras y Puntos", true);
        this.controlador = controlador;
        this.parentFrame = parent;
        
        initComponents();
        cargarDatosCliente();
        
        this.setSize(600, 450); // Ajustado el alto para dar espacio a los botones
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        
        // --- PANEL SUPERIOR: TÍTULO Y PUNTOS (INDICADOR) ---
        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBorder(new EmptyBorder(10, 10, 5, 10));
        
        JLabel lblTitulo = new JLabel("MIS COMPRAS EN EL SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        
        lblPuntos = new JLabel("Puntos Acumulados: Cargando...", SwingConstants.LEFT);
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 12));
        panelNorte.add(lblPuntos, BorderLayout.SOUTH);
        
        this.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA DE HISTORIAL (COLUMNAS SOLICITADAS) ---
        String[] columnas = {"Nº", "Fecha", "Monto Total (S/.)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaCompras = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        this.add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTONES ---
        JPanel panelSur = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));
        panelSur.setBorder(new EmptyBorder(5, 10, 10, 10));

        btnAnularUltima = new JButton("Anular Última Compra");
        btnGestionarTarjeta = new JButton("Gestionar Tarjeta");

        panelSur.add(btnAnularUltima);
        panelSur.add(btnGestionarTarjeta);
        this.add(panelSur, BorderLayout.SOUTH);

        // --- LÓGICA Y EVENTOS DE LOS BOTONES ---
        
        // Acción para invocar la anulación a través del controlador
        btnAnularUltima.addActionListener(e -> {
            boolean exito = controlador.anularUltimaVenta();
            if (exito) {
                JOptionPane.showMessageDialog(this, "Última venta anulada con éxito.", "Venta Anulada", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosCliente(); // Refresca inmediatamente la JTable y los puntos en pantalla
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo anular la transacción o no posee compras en el historial.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Acción para abrir la ventana VistaRegistroTarjeta
        btnGestionarTarjeta.addActionListener(e -> {
            try {
                // Descomenta las siguientes líneas cuando hayas creado el archivo VistaRegistroTarjeta.java:
                /*
                VistaRegistroTarjeta vistaTarjeta = new VistaRegistroTarjeta(parentFrame, true);
                vistaTarjeta.setVisible(true);
                */
                
                // Mensaje provisional de control hasta que desarrolles el archivo correspondiente
                JOptionPane.showMessageDialog(this, "Abriendo la ventana VistaRegistroTarjeta...");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir la interfaz de tarjetas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void cargarDatosCliente() {
        List<Venta> historial = controlador.obtenerHistorialCliente();
        
        modeloTabla.setRowCount(0);
        
        int puntosReales = controlador.obtenerPuntosCliente();
        lblPuntos.setText("Puntos Acumulados Totales: " + puntosReales + " pts");

        if (historial.isEmpty()) {
            return;
        }

        int contador = 1;
        for (Venta venta : historial) {
            Object[] fila = {
                contador++,
                venta.getFecha().toString(),
                "S/. " + venta.getMonto()
            };
            modeloTabla.addRow(fila);
        }
    }
}
