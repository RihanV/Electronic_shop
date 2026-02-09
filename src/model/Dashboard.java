/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package model;

import javax.swing.JOptionPane;

/**
 * Main navigation dashboard for the app.
 * Provides buttons to open each management module.
 */
public class Dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dashboard.class.getName());

    /** Builds the dashboard layout and wires navigation buttons. */
   public Dashboard() {
    initComponents();

    applyDashboardLayout();
    
    // Maximize the window to fill the screen
    this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

    // Center the dashboard content within the window
    javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.GridBagLayout());
    wrapper.setBackground(jPanel1.getBackground());

    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());
    wrapper.add(jPanel1);
    getContentPane().add(wrapper, java.awt.BorderLayout.CENTER);

    // Keep your navigation logic
    jButton1.addActionListener(e -> openProductManagement());
    jButton3.addActionListener(e -> { new Billing().setVisible(true); dispose(); });
    jButton5.addActionListener(e -> { new stock().setVisible(true); dispose(); });
    jButton6.addActionListener(e -> openSupplierManagement());
    jButton8.addActionListener(e -> { new CategoryManagement().setVisible(true); dispose(); });
    jButton9.addActionListener(e -> { new DeliveryManagement().setVisible(true); dispose(); });
    jButton10.addActionListener(e -> {
        try {
            new EmployeeManagement().setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open Employees: " + ex.getMessage());
        }
    });
    jButton11.addActionListener(e -> { new WarrantyManagement().setVisible(true); dispose(); });
    jButton12.addActionListener(e -> { new ReportsManagement().setVisible(true); dispose(); });
    jButton7.addActionListener(e -> {
        new Login().setVisible(true);
        dispose();
    });
}

    /** Arranges the header, button grid, and footer. */
    private void applyDashboardLayout() {
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        applyDashboardColors();

        javax.swing.JPanel header = new javax.swing.JPanel();
        header.setOpaque(false);
        header.setLayout(new javax.swing.BoxLayout(header, javax.swing.BoxLayout.Y_AXIS));
        header.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 10, 20));
        header.add(jLabel2);
        header.add(javax.swing.Box.createVerticalStrut(4));
        header.add(jLabel1);

        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        buttonsPanel.setOpaque(false);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(12, 12, 12, 12);
        buttonsPanel.add(jButton1, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(jButton5, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buttonsPanel.add(jButton3, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(jButton6, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buttonsPanel.add(jButton8, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(jButton11, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buttonsPanel.add(jButton9, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(jButton12, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buttonsPanel.add(jButton10, gbc);

        javax.swing.JPanel center = new javax.swing.JPanel(new java.awt.BorderLayout());
        center.setOpaque(false);
        center.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        center.add(buttonsPanel, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel footer = new javax.swing.JPanel(new java.awt.BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        javax.swing.JPanel logoutPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false);
        logoutPanel.add(jButton7);
        footer.add(logoutPanel, java.awt.BorderLayout.EAST);

        jPanel1.add(header, java.awt.BorderLayout.NORTH);
        jPanel1.add(center, java.awt.BorderLayout.CENTER);
        jPanel1.add(footer, java.awt.BorderLayout.SOUTH);

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /** Applies consistent colors to dashboard buttons. */
    private void applyDashboardColors() {
        setButtonStyle(jButton1, new java.awt.Color(90, 141, 238));  // Product Mgmt
        setButtonStyle(jButton5, new java.awt.Color(69, 163, 229));  // View Stock
        setButtonStyle(jButton3, new java.awt.Color(46, 204, 113));  // Sales / Billing
        setButtonStyle(jButton6, new java.awt.Color(0, 184, 169));   // Supplier
        setButtonStyle(jButton8, new java.awt.Color(246, 170, 28));  // Category Mgmt
        setButtonStyle(jButton11, new java.awt.Color(255, 127, 80)); // Warranty
        setButtonStyle(jButton9, new java.awt.Color(46, 134, 171));  // Delivery Mgmt
        setButtonStyle(jButton12, new java.awt.Color(52, 73, 94));   // Reports
        setButtonStyle(jButton10, new java.awt.Color(212, 160, 23)); // Employee Mgmt

        setButtonStyle(jButton7, new java.awt.Color(231, 76, 60));   // Logout
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
    }

    /** Helper to set background/foreground color on a button. */
    private void setButtonStyle(javax.swing.JButton button, java.awt.Color background) {
        button.setBackground(background);
        button.setForeground(new java.awt.Color(255, 255, 255));
    }

    /** Opens product management screen. */
    private void openProductManagement() {
        new ProductManagement().setVisible(true);
        dispose();
    }

    /** Opens supplier management screen with error handling. */
    private void openSupplierManagement() {
        try {
            new SupplierManagement().setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open Supplier: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Product Mgmt");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton2.setBackground(new java.awt.Color(204, 0, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Delete Product");
        jButton2.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton3.setBackground(new java.awt.Color(0, 153, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Sales / Billing");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton4.setBackground(new java.awt.Color(102, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Update Product");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton5.setBackground(new java.awt.Color(102, 153, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("View Stock");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton6.setBackground(new java.awt.Color(102, 153, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Supplier");
        jButton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton8.setBackground(new java.awt.Color(102, 153, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Category Mgmt");
        jButton8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton9.setBackground(new java.awt.Color(102, 153, 255));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Delivery Mgmt");
        jButton9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton9.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton10.setBackground(new java.awt.Color(102, 153, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Employee Mgmt");
        jButton10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton10.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton11.setBackground(new java.awt.Color(102, 153, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Warranty");
        jButton11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton11.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton12.setBackground(new java.awt.Color(102, 153, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Reports");
        jButton12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton12.setPreferredSize(new java.awt.Dimension(520, 90));

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Logout");
        jButton7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.setPreferredSize(new java.awt.Dimension(110, 32));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Logged in. Use the buttons above to navigate.");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(17, 17, 17));
        jLabel2.setText("Electronics Shop Dashboard");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(345, 345, 345))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel2)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(226, 226, 226))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        UiScaleFix.apply();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
