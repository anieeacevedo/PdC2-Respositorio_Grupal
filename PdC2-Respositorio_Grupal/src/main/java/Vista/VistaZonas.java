/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.SistemaVentasControlador;
import Modelo.Zona;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaZonas extends JDialog {

    private final SistemaVentasControlador controlador;
    private JTable tablaZonas;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;

    public VistaZonas(Frame parent, SistemaVentasControlador controlador) {
        super(parent, "Zonas Disponibles del Concierto", true); // true = modal
        this.controlador = controlador;

        initComponents();
        cargarDatosZonas();

        this.setSize(550, 350);
        this.setLocationRelativeTo(parent); // Centrar respecto a la ventana principal
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: TÍTULO ---
        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel lblTitulo = new JLabel("ZONAS DISPONIBLES PARA EL CONCIERTO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        this.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA DE ZONAS ---
        // 3 columnas: Zona, Precio (S/.) y Capacidad Disponible
        String[] columnas = {"Zona", "Precio (S/.)", "Capacidad Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };

        tablaZonas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaZonas);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        this.add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTÓN CERRAR ---
        JPanel panelSur = new JPanel();
        panelSur.setBorder(new EmptyBorder(0, 10, 10, 10));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnCerrar.addActionListener(e -> this.dispose()); // Regresa al menú principal

        panelSur.add(btnCerrar);
        this.add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Carga los datos de las zonas desde el controlador y los vuelca
     * en el DefaultTableModel, usando controlador.obtenerZonasDisponibles().
     */
    private void cargarDatosZonas() {
        // 1. Recuperar la lista de zonas disponibles a través del controlador
        List<Zona> zonas = controlador.obtenerZonasDisponibles();

        // 2. Limpiar filas previas de la tabla
        modeloTabla.setRowCount(0);

        if (zonas == null || zonas.isEmpty()) {
            return;
        }

        // 3. Volcar los datos en el modelo de la tabla
        for (Zona zona : zonas) {
            Object[] fila = {
                zona.getNombre(),
                "S/. " + zona.getPrecio(),
                zona.getCapacidad()
            };
            modeloTabla.addRow(fila);
        }
    }
}