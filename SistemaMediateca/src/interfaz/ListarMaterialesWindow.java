package interfaz;

import base_datos.GestorBaseDatos;
import modelo.*;
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
        cargarTodosLosMateriales();
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMateriales = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Materiales - Todos los Tipos");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setText("TODOS LOS MATERIALES DISPONIBLES");

        tablaMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Código", "Tipo", "Título", "Detalle Específico", "Unidades"
            }
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
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
        modeloTabla.setRowCount(0);
    }

    private void cargarTodosLosMateriales() {
        modeloTabla.setRowCount(0);
        
        // Cargar libros
        List<Libro> libros = gestorBD.obtenerLibros();
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                libro.getCodigo(),
                "LIBRO",
                libro.getTitulo(),
                "Autor: " + libro.getAutor() + " | Editorial: " + libro.getEditorial(),
                libro.getUnidades()
            });
        }
        
        // Cargar revistas
        List<Revista> revistas = gestorBD.obtenerRevistas();
        for (Revista revista : revistas) {
            modeloTabla.addRow(new Object[]{
                revista.getCodigo(),
                "REVISTA", 
                revista.getTitulo(),
                "Periodicidad: " + revista.getPeriodicidad() + " | Editorial: " + revista.getEditorial(),
                revista.getUnidades()
            });
        }
        
        // Cargar CDs de audio
        List<CDAudio> cds = gestorBD.obtenerCDsAudio();
        for (CDAudio cd : cds) {
            modeloTabla.addRow(new Object[]{
                cd.getCodigo(),
                "CD AUDIO",
                cd.getTitulo(),
                "Artista: " + cd.getArtista() + " | Género: " + cd.getGenero() + " | Canciones: " + cd.getNumCanciones(),
                cd.getUnidades()
            });
        }
        
        // Cargar DVDs
        List<DVD> dvds = gestorBD.obtenerDVDs();
        for (DVD dvd : dvds) {
            modeloTabla.addRow(new Object[]{
                dvd.getCodigo(),
                "DVD",
                dvd.getTitulo(),
                "Director: " + dvd.getDirector() + " | Género: " + dvd.getGenero() + " | Duración: " + dvd.getDuracion() + " min",
                dvd.getUnidades()
            });
        }
        
        // Mostrar estadísticas
        int totalMateriales = libros.size() + revistas.size() + cds.size() + dvds.size();
        jLabel1.setText("TODOS LOS MATERIALES (" + totalMateriales + " registros)");
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarTodosLosMateriales();
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