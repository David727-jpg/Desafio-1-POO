package interfaz;

import base_datos.GestorBaseDatos;
import modelo.Libro;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListarMaterialesWindow extends javax.swing.JFrame {
    private GestorBaseDatos gestorBD;
    private DefaultTableModel modeloTabla;
    
    public ListarMaterialesWindow(GestorBaseDatos gestorBD) {
        initComponents();
        this.gestorBD = gestorBD;
        this.setLocationRelativeTo(null);
        configurarTabla();
        cargarMateriales();
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMateriales = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Materiales");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setText("MATERIALES DISPONIBLES");

        tablaMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Código", "Tipo", "Título", "Autor/Director", "Unidades"}
        ));
        jScrollPane1.setViewportView(tablaMateriales);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnActualizar))
                .addContainerGap())
        );

        pack();
    }

    private void configurarTabla() {
        modeloTabla = (DefaultTableModel) tablaMateriales.getModel();
        modeloTabla.setRowCount(0); // Limpiar tabla
    }

    private void cargarMateriales() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        // Cargar libros
        List<Libro> libros = gestorBD.obtenerLibros();
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                libro.getCodigo(),
                "LIBRO",
                libro.getTitulo(),
                libro.getAutor(),
                libro.getUnidades()
            });
        }
        
        // Aquí agregarías otros tipos de materiales cuando los implementes
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarMateriales();
        javax.swing.JOptionPane.showMessageDialog(this, "✅ Lista actualizada");
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    // Variables declaration
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMateriales;
}