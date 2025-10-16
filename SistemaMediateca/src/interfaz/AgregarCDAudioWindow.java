package interfaz;

import base_datos.GestorBaseDatos;
import modelo.CDAudio;
import javax.swing.*;
import java.awt.*;

public class AgregarCDAudioWindow extends JFrame {
    private GestorBaseDatos gestorBD;
    
    private JTextField txtCodigo, txtTitulo, txtArtista, txtGenero, txtDuracion, txtCanciones, txtUnidades;
    private JButton btnGuardar, btnCancelar;
    
    public AgregarCDAudioWindow(GestorBaseDatos gestorBD) {
        this.gestorBD = gestorBD;
        initComponents();
        this.setLocationRelativeTo(null);
        generarCodigoAutomatico();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar CD de Audio");
        setSize(400, 400);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("AGREGAR CD DE AUDIO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // Panel de campos
        JPanel camposPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Código
        camposPanel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        camposPanel.add(txtCodigo);

        // Título
        camposPanel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        camposPanel.add(txtTitulo);

        // Artista
        camposPanel.add(new JLabel("Artista:"));
        txtArtista = new JTextField();
        camposPanel.add(txtArtista);

        // Género
        camposPanel.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        camposPanel.add(txtGenero);

        // Duración (minutos)
        camposPanel.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        camposPanel.add(txtDuracion);

        // Canciones
        camposPanel.add(new JLabel("Nº Canciones:"));
        txtCanciones = new JTextField();
        camposPanel.add(txtCanciones);

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
            String codigo = gestorBD.generarCodigo("CDAUDIO");
            if (codigo != null) {
                txtCodigo.setText(codigo);
            } else {
                txtCodigo.setText("CDA00001");
            }
        } catch (Exception e) {
            txtCodigo.setText("CDA00001");
        }
    }

    private void btnGuardarActionPerformed() {
        try {
            if (txtTitulo.getText().trim().isEmpty() || txtArtista.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y Artista son obligatorios");
                return;
            }

            CDAudio cd = new CDAudio(
                txtCodigo.getText(),
                txtTitulo.getText(),
                Integer.parseInt(txtUnidades.getText()),
                Integer.parseInt(txtDuracion.getText()),
                txtGenero.getText(),
                txtArtista.getText(),
                Integer.parseInt(txtCanciones.getText())
            );

            if (gestorBD.insertarCDAudio(cd)) {
                JOptionPane.showMessageDialog(this, "✅ CD de Audio agregado exitosamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar el CD");
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