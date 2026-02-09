/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package model;

import backend.pages.ReportBackend;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Reports screen: low stock, top selling, and profit reports.
 */
public class ReportsManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReportsManagement.class.getName());
    private final ReportBackend backend = new ReportBackend();

    /** Creates the form, applies layout, and loads report data. */
    public ReportsManagement() {
        initComponents();
        applyFullScreenLayout();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        loadLowStock();
        loadTopSelling();
        loadProfit();
    }

    /** Builds the full-screen layout and tabs. */
    private void applyFullScreenLayout() {
        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        applyTabLayouts();

        javax.swing.JPanel titlePanel = new javax.swing.JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new javax.swing.BoxLayout(titlePanel, javax.swing.BoxLayout.Y_AXIS));
        titlePanel.add(jLabel1);
        titlePanel.add(javax.swing.Box.createVerticalStrut(4));
        titlePanel.add(jLabel2);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 28, 12, 28));
        topPanel.add(titlePanel, java.awt.BorderLayout.WEST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 28, 0, 28));
        centerPanel.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel backPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        backPanel.setOpaque(false);
        jButtonBack.setPreferredSize(new java.awt.Dimension(70, 27));
        backPanel.add(jButtonBack);

        javax.swing.JPanel bottomPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 28, 24, 28));
        bottomPanel.add(backPanel, java.awt.BorderLayout.EAST);

        jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
        jPanel1.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /** Configures layout for each report tab. */
    private void applyTabLayouts() {
        configureLowStockTab();
        configureTopSellingTab();
        configureProfitTab();
    }

    /** Layout for the low-stock report tab. */
    private void configureLowStockTab() {
        jPanelLow.removeAll();
        jPanelLow.setLayout(new java.awt.BorderLayout());
        jPanelLow.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldLowStock.setPreferredSize(new java.awt.Dimension(80, 27));
        jButtonLowRefresh.setPreferredSize(new java.awt.Dimension(90, 27));

        javax.swing.JPanel controls = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        controls.setOpaque(false);
        controls.add(jLabel3);
        controls.add(jTextFieldLowStock);
        controls.add(jButtonLowRefresh);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 20, 12, 20));
        topPanel.add(controls, java.awt.BorderLayout.WEST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        centerPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLow.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanelLow.add(centerPanel, java.awt.BorderLayout.CENTER);
    }

    /** Layout for the top-selling report tab. */
    private void configureTopSellingTab() {
        jPanelTop.removeAll();
        jPanelTop.setLayout(new java.awt.BorderLayout());
        jPanelTop.setBackground(new java.awt.Color(0, 0, 0));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldTopLimit.setPreferredSize(new java.awt.Dimension(80, 27));
        jButtonTopRefresh.setPreferredSize(new java.awt.Dimension(90, 27));

        javax.swing.JPanel controls = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        controls.setOpaque(false);
        controls.add(jLabel4);
        controls.add(jTextFieldTopLimit);
        controls.add(jButtonTopRefresh);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 20, 12, 20));
        topPanel.add(controls, java.awt.BorderLayout.WEST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        centerPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanelTop.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanelTop.add(centerPanel, java.awt.BorderLayout.CENTER);
    }

    /** Layout for the profit report tab. */
    private void configureProfitTab() {
        jPanelProfit.removeAll();
        jPanelProfit.setLayout(new java.awt.BorderLayout());
        jPanelProfit.setBackground(new java.awt.Color(0, 0, 0));

        jButtonProfitRefresh.setPreferredSize(new java.awt.Dimension(90, 27));

        javax.swing.JPanel controls = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        controls.setOpaque(false);
        controls.add(jButtonProfitRefresh);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 20, 12, 20));
        topPanel.add(controls, java.awt.BorderLayout.WEST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        centerPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanelProfit.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanelProfit.add(centerPanel, java.awt.BorderLayout.CENTER);
    }

    /** Loads low-stock rows using a threshold. */
    private void loadLowStock() {
        int threshold = parseIntSafe(jTextFieldLowStock.getText(), 5);
        try {
            List<Object[]> rows = backend.lowStock(threshold);
            DefaultTableModel model = (DefaultTableModel) jTableLowStock.getModel();
            model.setRowCount(0);
            for (Object[] r : rows) {
                model.addRow(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load low stock: " + ex.getMessage());
        }
    }

    /** Loads top-selling rows using a limit. */
    private void loadTopSelling() {
        int limit = parseIntSafe(jTextFieldTopLimit.getText(), 10);
        try {
            List<Object[]> rows = backend.topSelling(limit);
            DefaultTableModel model = (DefaultTableModel) jTableTopSelling.getModel();
            model.setRowCount(0);
            for (Object[] r : rows) {
                model.addRow(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load top selling: " + ex.getMessage());
        }
    }

    /** Loads profit report rows. */
    private void loadProfit() {
        try {
            List<Object[]> rows = backend.profitReport();
            DefaultTableModel model = (DefaultTableModel) jTableProfit.getModel();
            model.setRowCount(0);
            for (Object[] r : rows) {
                model.addRow(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load profit report: " + ex.getMessage());
        }
    }

    /** Safe int parsing with a fallback value. */
    private int parseIntSafe(String value, int fallback) {
        if (value == null) return fallback;
        String v = value.trim();
        if (v.isEmpty()) return fallback;
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException ex) {
            return fallback;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelLow = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLowStock = new javax.swing.JTextField();
        jButtonLowRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLowStock = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTopLimit = new javax.swing.JTextField();
        jButtonTopRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTopSelling = new javax.swing.JTable();
        jPanelProfit = new javax.swing.JPanel();
        jButtonProfitRefresh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProfit = new javax.swing.JTable();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reports");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Low stock, top selling, and profit reports.");

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));

        jPanelLow.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Low stock threshold:");

        jTextFieldLowStock.setColumns(6);
        jTextFieldLowStock.setText("5");

        jButtonLowRefresh.setBackground(new java.awt.Color(102, 153, 255));
        jButtonLowRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonLowRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLowRefresh.setText("Refresh");
        jButtonLowRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLowRefreshActionPerformed(evt);
            }
        });

        jTableLowStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableLowStock);

        javax.swing.GroupLayout jPanelLowLayout = new javax.swing.GroupLayout(jPanelLow);
        jPanelLow.setLayout(jPanelLowLayout);
        jPanelLowLayout.setHorizontalGroup(
            jPanelLowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLowLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(jTextFieldLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonLowRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(864, Short.MAX_VALUE))
            .addGroup(jPanelLowLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelLowLayout.setVerticalGroup(
            jPanelLowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLowLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelLowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLowRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Low Stock", jPanelLow);

        jPanelTop.setBackground(new java.awt.Color(0, 0, 0));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Top selling limit:");

        jTextFieldTopLimit.setColumns(6);
        jTextFieldTopLimit.setText("10");

        jButtonTopRefresh.setBackground(new java.awt.Color(102, 153, 255));
        jButtonTopRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonTopRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTopRefresh.setText("Refresh");
        jButtonTopRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTopRefreshActionPerformed(evt);
            }
        });

        jTableTopSelling.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product ID", "Product Name", "Qty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableTopSelling);

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(jTextFieldTopLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonTopRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(870, Short.MAX_VALUE))
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldTopLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonTopRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Top Selling", jPanelTop);

        jPanelProfit.setBackground(new java.awt.Color(0, 0, 0));

        jButtonProfitRefresh.setBackground(new java.awt.Color(102, 153, 255));
        jButtonProfitRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonProfitRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProfitRefresh.setText("Refresh");
        jButtonProfitRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProfitRefreshActionPerformed(evt);
            }
        });

        jTableProfit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Profit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableProfit);

        javax.swing.GroupLayout jPanelProfitLayout = new javax.swing.GroupLayout(jPanelProfit);
        jPanelProfit.setLayout(jPanelProfitLayout);
        jPanelProfitLayout.setHorizontalGroup(
            jPanelProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfitLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButtonProfitRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1110, Short.MAX_VALUE))
            .addGroup(jPanelProfitLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelProfitLayout.setVerticalGroup(
            jPanelProfitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfitLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButtonProfitRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profit", jPanelProfit);

        jButtonBack.setBackground(new java.awt.Color(255, 51, 0));
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonBack)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLowRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLowRefreshActionPerformed
        loadLowStock();
    }//GEN-LAST:event_jButtonLowRefreshActionPerformed

    private void jButtonTopRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTopRefreshActionPerformed
        loadTopSelling();
    }//GEN-LAST:event_jButtonTopRefreshActionPerformed

    private void jButtonProfitRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProfitRefreshActionPerformed
        loadProfit();
    }//GEN-LAST:event_jButtonProfitRefreshActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new Dashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReportsManagement().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonLowRefresh;
    private javax.swing.JButton jButtonProfitRefresh;
    private javax.swing.JButton jButtonTopRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelLow;
    private javax.swing.JPanel jPanelProfit;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableLowStock;
    private javax.swing.JTable jTableProfit;
    private javax.swing.JTable jTableTopSelling;
    private javax.swing.JTextField jTextFieldLowStock;
    private javax.swing.JTextField jTextFieldTopLimit;
    // End of variables declaration//GEN-END:variables
}
