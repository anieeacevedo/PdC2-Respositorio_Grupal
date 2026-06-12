package Vista;

import Controlador.SistemaVentasControlador;
import Modelo.Zona;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaPanelAdmin extends JDialog {

    private final SistemaVentasControlador controlador;
    private JTable tablaAuditoria;
    private DefaultTableModel modeloTabla;
    private JButton btnRefrescar;
    private JButton btnRegistrarZona;
    private JButton btnCerrar;

    public VistaPanelAdmin(Frame parent, SistemaVentasControlador controlador) {
        super(parent, "Panel de Administración", true);
        this.controlador = controlador;

        initComponents();
        cargarDatos();

        this.setSize(600, 400);
        this.setLocationRelativeTo(parent); // Centrar la ventana
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: TÍTULO ---
        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel lblTitulo = new JLabel("AUDITORÍA DE VENTAS Y ZONAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        this.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA ---
        String[] columnas = {"Zona", "Capacidad Restante", "Entradas Vendidas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que el usuario edite la tabla directamente
            }
        };

        tablaAuditoria = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaAuditoria);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        this.add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTONES ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSur.setBorder(new EmptyBorder(0, 10, 10, 10));

        btnRefrescar = new JButton("Refrescar Tabla");
        btnRegistrarZona = new JButton("Registrar Zona");
        btnCerrar = new JButton("Cerrar");
        
        Font fuenteBotones = new Font("Arial", Font.BOLD, 14);

        java.awt.Dimension tamanoBotones = new java.awt.Dimension(160, 40);

        btnRefrescar.setFont(fuenteBotones);
        btnRefrescar.setPreferredSize(tamanoBotones);

        btnRegistrarZona.setFont(fuenteBotones);
        btnRegistrarZona.setPreferredSize(tamanoBotones);

        btnCerrar.setFont(fuenteBotones);
        btnCerrar.setPreferredSize(tamanoBotones);

        panelSur.add(btnRefrescar);
        panelSur.add(btnRegistrarZona);
        panelSur.add(btnCerrar);

        this.add(panelSur, BorderLayout.SOUTH);

        // --- EVENTOS DE LOS BOTONES (Sin usar expresiones Lambda) ---
        
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
                JOptionPane.showMessageDialog(VistaPanelAdmin.this, "Tabla actualizada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRegistrarZona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoRegistro();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        // Llamamos al controlador para que el administrador supervise
        List<Zona> zonas = controlador.obtenerReporteAuditoriaAdmin();

        if (zonas != null) {
            for (Zona zona : zonas) {
                // Nota: El modelo actual no guarda la capacidad original, por lo que 
                // las "entradas vendidas" se indican como "Ver Historial" por ahora.
                Object[] fila = {
                    zona.getNombre(),
                    zona.getCapacidad(),
                    "Ver Historial" 
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void mostrarDialogoRegistro() {
        // Crear campos de texto para el JOptionPane
        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtPrecio = new JTextField();

        Object[] mensaje = {
            "Nombre de la Zona:", txtNombre,
            "Capacidad:", txtCapacidad,
            "Precio (S/.):", txtPrecio
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Registrar Nueva Zona", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                int precio = Integer.parseInt(txtPrecio.getText().trim());

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Llamar al puente en el controlador
                controlador.registrarNuevaZonaAdmin(nombre, capacidad, precio);
                
                JOptionPane.showMessageDialog(this, "Zona registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Refrescar la tabla automáticamente

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La capacidad y el precio deben ser números enteros válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}