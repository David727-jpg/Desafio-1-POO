package interfaz;

import base_datos.GestorBaseDatos;
import modelo.Libro;
import javax.swing.*;
import java.awt.*;

public class AgregarLibroWindow extends JFrame {
    private GestorBaseDatos gestorBD;
    
    // Componentes
    private JTextField txtCodigo, txtTitulo, txtAutor, txtPaginas, txtEditorial, txtISBN, txtAnio, txtUnidades;
    private JButton btnGuardar, btnCancelar;
    
    public AgregarLibroWindow(GestorBaseDatos gestorBD) {
        this.gestorBD = gestorBD;
        initComponents();
        this.setLocationRelativeTo(null);
        generarCodigoAutomatico();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Libro");
        setSize(400, 450);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("AGREGAR LIBRO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // Panel de campos
        JPanel camposPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        // Código
        camposPanel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        camposPanel.add(txtCodigo);

        // Título
        camposPanel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        camposPanel.add(txtTitulo);

        // Autor
        camposPanel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        camposPanel.add(txtAutor);

        // Páginas
        camposPanel.add(new JLabel("Páginas:"));
        txtPaginas = new JTextField();
        camposPanel.add(txtPaginas);

        // Editorial
        camposPanel.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        camposPanel.add(txtEditorial);

        // ISBN
        camposPanel.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        camposPanel.add(txtISBN);

        // Año
        camposPanel.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        camposPanel.add(txtAnio);

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
            String codigo = gestorBD.generarCodigo("LIBRO");
            if (codigo != null) {
                txtCodigo.setText(codigo);
            } else {
                txtCodigo.setText("LIB00001");
            }
        } catch (Exception e) {
            txtCodigo.setText("LIB00001");
        }
    }

    private void btnGuardarActionPerformed() {
        try {
            System.out.println("Intentando guardar libro...");
            
            // Validar campos obligatorios
            if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y Autor son obligatorios");
                return;
            }

            // Obtener valores
            String codigo = txtCodigo.getText();
            String titulo = txtTitulo.getText();
            int unidades = Integer.parseInt(txtUnidades.getText());
            String editorial = txtEditorial.getText();
            String autor = txtAutor.getText();
            int paginas = Integer.parseInt(txtPaginas.getText());
            String isbn = txtISBN.getText();
            int año = Integer.parseInt(txtAnio.getText());

            System.out.println("Creando objeto Libro...");
            // Crear objeto Libro
            Libro libro = new Libro(codigo, titulo, unidades, editorial, autor, paginas, isbn, año);

            System.out.println("Insertando en BD...");
            // Insertar en BD
            if (gestorBD.insertarLibro(libro)) {
                JOptionPane.showMessageDialog(this, "✅ Libro agregado exitosamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar el libro");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Error en formatos numéricos\nVerifique páginas, año y unidades");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnCancelarActionPerformed() {
        this.dispose();
    }
}