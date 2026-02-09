/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package model;

import backend.entity.Category;
import backend.pages.CategoryBackend;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Category management screen: list, search, add, rename, deactivate.
 */
public class CategoryManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoryManagement.class.getName());
    private final CategoryBackend backend = new CategoryBackend();

    /** Creates the form, applies layout, and loads categories. */
    public CategoryManagement() {
        initComponents();
        applyFullScreenLayout();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        loadCategories();
    }

    /** Builds a full-screen layout using the existing Swing components. */
    private void applyFullScreenLayout() {
        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.JPanel titlePanel = new javax.swing.JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new javax.swing.BoxLayout(titlePanel, javax.swing.BoxLayout.Y_AXIS));
        titlePanel.add(jLabel1);
        titlePanel.add(javax.swing.Box.createVerticalStrut(4));
        titlePanel.add(jLabel2);

        javax.swing.JLabel searchLabel = new javax.swing.JLabel("Search :");
        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        searchLabel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.JPanel searchPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
        searchPanel.setOpaque(false);
        jTextFieldSearch.setPreferredSize(new java.awt.Dimension(220, 28));
        searchPanel.add(searchLabel);
        searchPanel.add(jTextFieldSearch);
        searchPanel.add(jButtonSearch);
        searchPanel.add(jButtonRefresh);
        searchPanel.add(jButtonDeactivate);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 28, 12, 28));
        topPanel.add(titlePanel, java.awt.BorderLayout.WEST);
        topPanel.add(searchPanel, java.awt.BorderLayout.EAST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 28, 0, 28));
        centerPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel leftActions = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        leftActions.setOpaque(false);
        leftActions.add(jButtonAdd);
        leftActions.add(jButtonRename);

        javax.swing.JPanel rightActions = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        rightActions.setOpaque(false);
        rightActions.add(jButtonBack);

        javax.swing.JPanel bottomPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 28, 24, 28));
        bottomPanel.add(leftActions, java.awt.BorderLayout.WEST);
        bottomPanel.add(rightActions, java.awt.BorderLayout.EAST);

        jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
        jPanel1.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /** Loads active categories into the table. */
    private void loadCategories() {
        try {
            List<Category> categories = backend.loadActive();
            fillTable(categories);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load categories: " + ex.getMessage());
        }
    }

    /** Searches categories by keyword. */
    private void searchCategories() {
        String keyword = jTextFieldSearch.getText().trim();
        try {
            List<Category> categories = backend.search(keyword);
            fillTable(categories);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage());
        }
    }

    /** Prompts for a name and adds a category. */
    private void addCategory() {
        String name = JOptionPane.showInputDialog(this, "Category name:");
        if (name == null) return;
        name = name.trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name is required.");
            return;
        }
        try {
            backend.addCategory(name);
            loadCategories();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add category: " + ex.getMessage());
        }
    }

    /** Renames the selected category. */
    private void renameCategory() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a category row to rename.");
            return;
        }
        int id = (int) jTable1.getValueAt(row, 0);
        String current = jTable1.getValueAt(row, 1).toString();
        String name = JOptionPane.showInputDialog(this, "New category name:", current);
        if (name == null) return;
        name = name.trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name is required.");
            return;
        }
        try {
            backend.renameCategory(id, name);
            loadCategories();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to rename category: " + ex.getMessage());
        }
    }

    /** Deactivates the selected category. */
    private void deactivateCategory() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a category row to deactivate.");
            return;
        }
        int id = (int) jTable1.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Deactivate this category?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            backend.deactivateCategory(id);
            loadCategories();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to deactivate category: " + ex.getMessage());
        }
    }

    /** Clears and fills the category table. */
    private void fillTable(List<Category> categories) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Category c : categories) {
            model.addRow(new Object[] { c.getId(), c.getName(), c.isActive() ? "Active" : "Inactive" });
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
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jButtonDeactivate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonRename = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categories");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Manage product categories. Search, add, rename, or deactivate.");

        jTextFieldSearch.setColumns(20);

        jButtonSearch.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSearch.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonRefresh.setBackground(new java.awt.Color(102, 153, 255));
        jButtonRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Refresh");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jButtonDeactivate.setBackground(new java.awt.Color(102, 153, 255));
        jButtonDeactivate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonDeactivate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeactivate.setText("Deactivate");
        jButtonDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeactivateActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Category ID", "Category Name", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonAdd.setBackground(new java.awt.Color(102, 153, 255));
        jButtonAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonRename.setBackground(new java.awt.Color(102, 153, 255));
        jButtonRename.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRename.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRename.setText("Rename");
        jButtonRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenameActionPerformed(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonRename, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonRename)
                    .addComponent(jButtonBack))
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

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        searchCategories();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        loadCategories();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeactivateActionPerformed
        deactivateCategory();
    }//GEN-LAST:event_jButtonDeactivateActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        addCategory();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenameActionPerformed
        renameCategory();
    }//GEN-LAST:event_jButtonRenameActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CategoryManagement().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDeactivate;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRename;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
