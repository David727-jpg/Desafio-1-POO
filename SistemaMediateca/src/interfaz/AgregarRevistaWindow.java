package interfaz;

import base_datos.GestorBaseDatos;
import modelo.Revista;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarRevistaWindow extends JFrame {
    private GestorBaseDatos gestorBD;
    
    private JTextField txtCodigo, txtTitulo, txtEditorial, txtPeriodicidad, txtUnidades;
    private JTextField txtFecha;
    private JButton btnGuardar, btnCancelar;
    
    public AgregarRevistaWindow(GestorBaseDatos gestorBD) {
        this.gestorBD = gestorBD;
        initComponents();
        this.setLocationRelativeTo(null);
        generarCodigoAutomatico();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Revista");
        setSize(400, 400);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("AGREGAR REVISTA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // Panel de campos
        JPanel camposPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Código
        camposPanel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        camposPanel.add(txtCodigo);

        // Título
        camposPanel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        camposPanel.add(txtTitulo);

        // Editorial
        camposPanel.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        camposPanel.add(txtEditorial);

        // Periodicidad
        camposPanel.add(new JLabel("Periodicidad:"));
        txtPeriodicidad = new JTextField();
        camposPanel.add(txtPeriodicidad);

        // Fecha (formato: YYYY-MM-DD)
        camposPanel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        camposPanel.add(txtFecha);

        // Unidades
        camposPanel.add(new JLabel("Unidades:"));
        txtUnidades = new JTextField("1");
        camposPanel.add(txtUnidades);

        panel.add(camposPanel);
        panel.add(Box.createVerticalStrut(20));

        // Panel de botones
        JPanel botonesPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);
        
        panel.add(botonesPanel);

        // Agregar panel a la ventana
        add(panel);

        // Agregar eventos
        btnGuardar.addActionListener(e -> btnGuardarActionPerformed());
        btnCancelar.addActionListener(e -> btnCancelarActionPerformed());
    }

    private void generarCodigoAutomatico() {
        try {
            String codigo = gestorBD.generarCodigo("REVISTA");
            if (codigo != null) {
                txtCodigo.setText(codigo);
            } else {
                txtCodigo.setText("REV00001");
            }
        } catch (Exception e) {
            txtCodigo.setText("REV00001");
        }
    }

    private void btnGuardarActionPerformed() {
        try {
            // Validar campos obligatorios
            if (txtTitulo.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y Editorial son obligatorios");
                return;
            }

            // Convertir fecha
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(txtFecha.getText());

            // Crear objeto Revista
            Revista revista = new Revista(
                txtCodigo.getText(),
                txtTitulo.getText(),
                Integer.parseInt(txtUnidades.getText()),
                txtEditorial.getText(),
                txtPeriodicidad.getText(),
                fecha
            );

            // Insertar en BD
            if (gestorBD.insertarRevista(revista)) {
                JOptionPane.showMessageDialog(this, "✅ Revista agregada exitosamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar la revista");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Error en formato numérico\nVerifique las unidades");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnCancelarActionPerformed() {
        this.dispose();
    }
}