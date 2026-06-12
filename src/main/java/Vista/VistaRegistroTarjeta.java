/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import Controlador.SistemaVentasControlador;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author Usuario
 */
public class VistaRegistroTarjeta extends JDialog {
    // Instancia del controlador para conectar con los modelos
    private final SistemaVentasControlador controlador;

    // Componentes gráficos solicitados
    private JTextField txtNumero;
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JPasswordField txtCVV;

    private JButton btnGuardar;
    private JButton btnEliminar;

    public VistaRegistroTarjeta(Frame parent, boolean modal, SistemaVentasControlador controlador) {
        super(parent, "Registro de Tarjeta", modal);
        this.controlador = controlador;

        initComponents();

        this.setSize(350, 250);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));

        // --- PANEL CENTRAL: FORMULARIO ---
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(new EmptyBorder(15, 15, 15, 15));

        panelFormulario.add(new JLabel("Número de la tarjeta:"));
        txtNumero = new JTextField();
        panelFormulario.add(txtNumero);

        panelFormulario.add(new JLabel("Nombre del titular:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Fecha (MM/YY):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("CVV:"));
        txtCVV = new JPasswordField();
        panelFormulario.add(txtCVV);

        this.add(panelFormulario, BorderLayout.CENTER);

        // --- PANEL INFERIOR: BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        this.add(panelBotones, BorderLayout.SOUTH);

        // --- LÓGICA DE EVENTOS ---
        btnGuardar.addActionListener(e -> guardarDatos());
        btnEliminar.addActionListener(e -> eliminarDatos());
    }

    private void guardarDatos() {
        String numero = txtNumero.getText().trim();
        String nombre = txtNombre.getText().trim();
        String fecha = txtFecha.getText().trim();
        String cvv = new String(txtCVV.getPassword()).trim();

        if (numero.isEmpty() || nombre.isEmpty() || fecha.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos de la tarjeta.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = controlador.registrarTarjetaCliente(numero, nombre, fecha, cvv);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Los datos de su tarjeta han sido guardados exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDatos() {
        boolean exito = controlador.eliminarTarjetaCliente();

        if (exito) {
            JOptionPane.showMessageDialog(this, "Tarjeta eliminada del sistema.", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            txtNumero.setText("");
            txtNombre.setText("");
            txtFecha.setText("");
            txtCVV.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No posee ninguna tarjeta registrada para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
