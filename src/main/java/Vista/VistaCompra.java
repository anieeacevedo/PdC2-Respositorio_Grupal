package Vista;

import Controlador.SistemaVentasControlador;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


public class VistaCompra extends javax.swing.JDialog {
    private JComboBox<String> cbZona;
    private JSpinner spinnerCantidad;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private SistemaVentasControlador controlador;

    
    public VistaCompra(JFrame padre, SistemaVentasControlador controlador) {
        // Invoca al constructor de JDialog pasándole el padre y el título
        super(padre, "Solicitar Compra de Entradas", true); 
        
        this.controlador = controlador;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(380, 200);
        this.setLocationRelativeTo(padre); // Se centra con respecto a la ventana padre
        
        initComponentes();
        initEventos();
    }
    // </editor-fold>

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    private void initComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 15));

        panelFormulario.add(new JLabel("Seleccione la Zona:"));
        cbZona = new JComboBox<>();
        cbZona.addItem("VIP");
        cbZona.addItem("General");
        cbZona.addItem("Platea");
        panelFormulario.add(cbZona);

        panelFormulario.add(new JLabel("Cantidad (Máx. 4):"));
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(1, 1, 4, 1);
        spinnerCantidad = new JSpinner(modeloSpinner);
        panelFormulario.add(spinnerCantidad);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    private void initEventos() {
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarAccionCompra();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el JDialog de manera segura
            }
        });
    }

    private void procesarAccionCompra() {
        String zonaSeleccionada = (String) cbZona.getSelectedItem();
        int cantidadSeleccionada = (Integer) spinnerCantidad.getValue();

        try {
            controlador.procesarCompra(zonaSeleccionada, cantidadSeleccionada);
            
            JOptionPane.showMessageDialog(this, 
                    "¡Compra procesada con éxito!", 
                    "Operación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose(); 
            
        } catch (Exception ex) {
            String nombreExcepcion = ex.getClass().getSimpleName();
            switch (nombreExcepcion) {
                case "LimiteEntradasException":
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Límite Superado", JOptionPane.WARNING_MESSAGE);
                    break;
                case "CapacidadMaximaException":
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Zona Agotada", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}