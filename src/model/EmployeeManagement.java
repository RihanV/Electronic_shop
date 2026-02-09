/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package model;

import backend.entity.Employee;
import backend.pages.EmployeeBackend;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Employee management screen: list, search, add, update, delete, and attendance.
 */
public class EmployeeManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmployeeManagement.class.getName());
    private final EmployeeBackend backend = new EmployeeBackend();

    /** Creates the form, applies layout, and loads employee data. */
    public EmployeeManagement() {
        initComponents();
        applyFullScreenLayout();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        loadEmployees();
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
        jButtonSearch.setPreferredSize(new java.awt.Dimension(90, 27));
        jButtonRefresh.setPreferredSize(new java.awt.Dimension(90, 27));
        jButtonAdd.setPreferredSize(new java.awt.Dimension(120, 27));
        jButtonUpdate.setPreferredSize(new java.awt.Dimension(120, 27));
        jButtonDelete.setPreferredSize(new java.awt.Dimension(90, 27));
        jTextFieldSearch.setPreferredSize(new java.awt.Dimension(220, 28));
        searchPanel.add(searchLabel);
        searchPanel.add(jTextFieldSearch);
        searchPanel.add(jButtonSearch);
        searchPanel.add(jButtonRefresh);
        searchPanel.add(jButtonAdd);
        searchPanel.add(jButtonUpdate);
        searchPanel.add(jButtonDelete);

        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 28, 12, 28));
        topPanel.add(titlePanel, java.awt.BorderLayout.WEST);
        topPanel.add(searchPanel, java.awt.BorderLayout.EAST);

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 28, 0, 28));
        centerPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel rightActions = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
        rightActions.setOpaque(false);
        jButtonAttendance.setPreferredSize(new java.awt.Dimension(140, 27));
        jButtonBack.setPreferredSize(new java.awt.Dimension(70, 27));
        rightActions.add(jButtonAttendance);
        rightActions.add(jButtonBack);

        javax.swing.JPanel bottomPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 28, 24, 28));
        bottomPanel.add(rightActions, java.awt.BorderLayout.EAST);

        jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
        jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
        jPanel1.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /** Loads active employees and refreshes the table. */
    private void loadEmployees() {
        try {
            List<Employee> employees = backend.loadActive();
            fillTable(employees);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load employees: " + ex.getMessage());
        }
    }

    /** Searches employees by keyword and refreshes the table. */
    private void searchEmployees() {
        String keyword = jTextFieldSearch.getText().trim();
        try {
            List<Employee> employees = backend.search(keyword);
            fillTable(employees);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage());
        }
    }

    /** Opens add dialog, validates input, and saves an employee. */
    private void addEmployee() {
        Employee e = promptEmployeeDetails("Add Employee", null);
        if (e == null) return;

        try {
            backend.addEmployee(e);
            loadEmployees();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add employee: " + ex.getMessage());
        }
    }

    /** Shared dialog for add/update employee details. */
    private Employee promptEmployeeDetails(String title, Employee seed) {
        javax.swing.JTextField nameField = new javax.swing.JTextField(20);
        javax.swing.JTextField roleField = new javax.swing.JTextField(20);
        javax.swing.JTextField phoneField = new javax.swing.JTextField(20);
        javax.swing.JTextField emailField = new javax.swing.JTextField(20);

        if (seed != null) {
            if (seed.getName() != null) nameField.setText(seed.getName());
            if (seed.getRole() != null) roleField.setText(seed.getRole());
            if (seed.getPhone() != null) phoneField.setText(seed.getPhone());
            if (seed.getEmail() != null) emailField.setText(seed.getEmail());
        }

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 8, 8));
        panel.add(new javax.swing.JLabel("Name"));
        panel.add(nameField);
        panel.add(new javax.swing.JLabel("Role"));
        panel.add(roleField);
        panel.add(new javax.swing.JLabel("Phone"));
        panel.add(phoneField);
        panel.add(new javax.swing.JLabel("Email"));
        panel.add(emailField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION) return null;

            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee name is required.");
                continue;
            }

            Employee e = new Employee();
            e.setName(name);
            String role = roleField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            if (!FieldValidators.isValidPhone(phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number.");
                continue;
            }
            if (!FieldValidators.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email address.");
                continue;
            }
            e.setRole(role.isEmpty() ? null : role);
            e.setPhone(phone.isEmpty() ? null : phone);
            e.setEmail(email.isEmpty() ? null : email);
            return e;
        }
    }

    /** Opens update dialog for selected employee and saves changes. */
    private void updateEmployee() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee row to update.");
            return;
        }
        int id = (int) jTable1.getValueAt(row, 0);
        Employee seed = new Employee();
        seed.setId(id);
        Object nameObj = jTable1.getValueAt(row, 1);
        Object roleObj = jTable1.getValueAt(row, 2);
        Object phoneObj = jTable1.getValueAt(row, 3);
        Object emailObj = jTable1.getValueAt(row, 4);
        seed.setName(nameObj == null ? null : nameObj.toString());
        seed.setRole(roleObj == null ? null : roleObj.toString());
        seed.setPhone(phoneObj == null ? null : phoneObj.toString());
        seed.setEmail(emailObj == null ? null : emailObj.toString());
        Employee e = promptEmployeeDetails("Update Employee", seed);
        if (e == null) return;

        try {
            e.setId(id);
            backend.updateEmployee(e);
            loadEmployees();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update employee: " + ex.getMessage());
        }
    }

    /** Deactivates the selected employee. */
    private void deleteEmployee() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee row to delete.");
            return;
        }
        int id = (int) jTable1.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            backend.deactivateEmployee(id);
            loadEmployees();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete employee: " + ex.getMessage());
        }
    }

    /** Navigates to attendance management. */
    private void markAttendance() {
        new AttendanceManagement().setVisible(true);
        dispose();
    }

    /** Clears and fills the employee table. */
    private void fillTable(List<Employee> employees) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Employee e : employees) {
            model.addRow(new Object[] { e.getId(), e.getName(), e.getRole(), e.getPhone(), e.getEmail() });
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
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAttendance = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Employees");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Manage employees and roles.");

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

        jButtonAdd.setBackground(new java.awt.Color(102, 153, 255));
        jButtonAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setText("Add Employee");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonUpdate.setBackground(new java.awt.Color(102, 153, 255));
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setText("Update Employee");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setBackground(new java.awt.Color(102, 153, 255));
        jButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonAttendance.setBackground(new java.awt.Color(102, 153, 255));
        jButtonAttendance.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendance.setText("Mark Attendance");
        jButtonAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAttendanceActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Role", "Phone", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

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
                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAttendance)
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
        searchEmployees();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        loadEmployees();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        addEmployee();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateEmployee();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteEmployee();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAttendanceActionPerformed
        markAttendance();
    }//GEN-LAST:event_jButtonAttendanceActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new EmployeeManagement().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAttendance;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
