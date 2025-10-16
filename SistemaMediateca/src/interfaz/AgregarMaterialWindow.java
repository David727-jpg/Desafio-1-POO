package interfaz;

import base_datos.GestorBaseDatos;
import modelo.*;
import javax.swing.*;

public class AgregarMaterialWindow extends javax.swing.JFrame {
    private GestorBaseDatos gestorBD;
    
    public AgregarMaterialWindow(GestorBaseDatos gestorBD) {
        initComponents();
        this.gestorBD = gestorBD;
        this.setLocationRelativeTo(null);
        cargarTiposMaterial();
    }

    private void initComponents() {
        // Componentes básicos
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboMaterial = new javax.swing.JComboBox<>();
        btnContinuar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Material - Seleccionar Tipo");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setText("AGREGAR NUEVO MATERIAL");

        jLabel2.setText("Tipo de Material:");

        btnContinuar.setText("Continuar");
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(comboMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnContinuar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinuar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void cargarTiposMaterial() {
        comboMaterial.removeAllItems();
        comboMaterial.addItem("LIBRO");
        comboMaterial.addItem("REVISTA");
        comboMaterial.addItem("CD DE AUDIO");
        comboMaterial.addItem("DVD");
    }

  private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {
    String tipoSeleccionado = (String) comboMaterial.getSelectedItem();
    
    switch (tipoSeleccionado) {
        case "LIBRO":
            new AgregarLibroWindow(gestorBD).setVisible(true);
            break;
        case "REVISTA":
            new AgregarRevistaWindow(gestorBD).setVisible(true);
            break;
        case "CD DE AUDIO":
            new AgregarCDAudioWindow(gestorBD).setVisible(true);
            break;
        case "DVD":
            new AgregarDVDWindow(gestorBD).setVisible(true);
            break;
        default:
            JOptionPane.showMessageDialog(this, "Tipo no disponible");
            break;
    }
    this.dispose();
}

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    // Variables declaration
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnContinuar;
    private javax.swing.JComboBox<String> comboMaterial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
}