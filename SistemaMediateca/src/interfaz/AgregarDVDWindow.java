package interfaz;

import base_datos.GestorBaseDatos;
import modelo.DVD;
import javax.swing.*;
import java.awt.*;

public class AgregarDVDWindow extends JFrame {
    private GestorBaseDatos gestorBD;
    
    private JTextField txtCodigo, txtTitulo, txtDirector, txtGenero, txtDuracion, txtUnidades;
    private JButton btnGuardar, btnCancelar;
    
    public AgregarDVDWindow(GestorBaseDatos gestorBD) {
        this.gestorBD = gestorBD;
        initComponents();
        this.setLocationRelativeTo(null);
        generarCodigoAutomatico();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar DVD");
        setSize(400, 400);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("AGREGAR DVD");
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

        // Director
        camposPanel.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        camposPanel.add(txtDirector);

        // Género
        camposPanel.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        camposPanel.add(txtGenero);

        // Duración (minutos)
        camposPanel.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        camposPanel.add(txtDuracion);

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

        add(panel);

        // Eventos
        btnGuardar.addActionListener(e -> btnGuardarActionPerformed());
        btnCancelar.addActionListener(e -> btnCancelarActionPerformed());
    }

    private void generarCodigoAutomatico() {
        try {
            String codigo = gestorBD.generarCodigo("DVD");
            if (codigo != null) {
                txtCodigo.setText(codigo);
            } else {
                txtCodigo.setText("DVD00001");
            }
        } catch (Exception e) {
            txtCodigo.setText("DVD00001");
        }
    }

    private void btnGuardarActionPerformed() {
        try {
            if (txtTitulo.getText().trim().isEmpty() || txtDirector.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y Director son obligatorios");
                return;
            }

            DVD dvd = new DVD(
                txtCodigo.getText(),
                txtTitulo.getText(),
                Integer.parseInt(txtUnidades.getText()),
                Integer.parseInt(txtDuracion.getText()),
                txtGenero.getText(),
                txtDirector.getText()
            );

            if (gestorBD.insertarDVD(dvd)) {
                JOptionPane.showMessageDialog(this, "✅ DVD agregado exitosamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar el DVD");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Error en formatos numéricos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    private void btnCancelarActionPerformed() {
        this.dispose();
    }
}