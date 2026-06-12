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
        super(parent, "Zonas Disponibles del Concierto", true);
        this.controlador = controlador;

        initComponents();
        cargarDatosZonas();

        this.setSize(550, 350);
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel lblTitulo = new JLabel("ZONAS DISPONIBLES PARA EL CONCIERTO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        this.add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {"Zona", "Precio (S/.)", "Capacidad Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaZonas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaZonas);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        this.add(scrollPane, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        panelSur.setBorder(new EmptyBorder(0, 10, 10, 10));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnCerrar.addActionListener(e -> this.dispose());

        panelSur.add(btnCerrar);
        this.add(panelSur, BorderLayout.SOUTH);
    }

    private void cargarDatosZonas() {
        List<Zona> zonas = controlador.obtenerZonasDisponibles();
        modeloTabla.setRowCount(0);

        if (zonas == null || zonas.isEmpty()) {
            return;
        }

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
