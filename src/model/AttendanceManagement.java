/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package model;

import backend.entity.Attendance;
import backend.entity.Employee;
import backend.entity.Shift;
import backend.pages.AttendanceBackend;
import backend.pages.EmployeeBackend;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class AttendanceManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AttendanceManagement.class.getName());
    private final AttendanceBackend backend = new AttendanceBackend();
    private final EmployeeBackend employeeBackend = new EmployeeBackend();
    private final Map<Integer, String> employeeOptions = new LinkedHashMap<>();
    private final Map<Integer, String> shiftOptions = new LinkedHashMap<>();

    /**
     * Creates new form AttendanceManagement
     */
    public AttendanceManagement() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        loadEmployees();
        loadShifts();
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeBackend.loadActive();
            employeeOptions.clear();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Employee e : employees) {
                employeeOptions.put(e.getId(), e.getName());
                model.addElement(e.getName());
            }
            jComboBoxEmployee.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load employees: " + ex.getMessage());
        }
    }

    private void loadShifts() {
        try {
            List<Shift> shifts = backend.loadShifts();
            shiftOptions.clear();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Shift s : shifts) {
                shiftOptions.put(s.getId(), s.getName());
                model.addElement(s.getName());
            }
            jComboBoxShift.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load shifts: " + ex.getMessage());
        }
    }

    private int getSelectedEmployeeId() {
        int idx = jComboBoxEmployee.getSelectedIndex();
        if (idx < 0 || employeeOptions.isEmpty()) return -1;
        return employeeOptions.keySet().stream().skip(idx).findFirst().orElse(-1);
    }

    private int getSelectedShiftId() {
        int idx = jComboBoxShift.getSelectedIndex();
        if (idx < 0 || shiftOptions.isEmpty()) return -1;
        return shiftOptions.keySet().stream().skip(idx).findFirst().orElse(-1);
    }

    private void markAttendance() {
        int employeeId = getSelectedEmployeeId();
        int shiftId = getSelectedShiftId();
        String date = jTextFieldDate.getText().trim(); // yyyy-mm-dd
        String status = String.valueOf(jComboBoxStatus.getSelectedItem());
        String note = jTextFieldNote.getText().trim();

        if (employeeId <= 0) {
            JOptionPane.showMessageDialog(this, "Select an employee.");
            return;
        }
        if (shiftId <= 0) {
            JOptionPane.showMessageDialog(this, "Select a shift.");
            return;
        }
        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Date is required (YYYY-MM-DD).");
            return;
        }
        try {
            Attendance a = new Attendance();
            a.setEmployeeId(employeeId);
            a.setShiftId(shiftId);
            a.setDate(date);
            a.setStatus(status);
            a.setNote(note.isEmpty() ? null : note);
            backend.markAttendance(a);
            loadAttendanceForEmployee();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to mark attendance: " + ex.getMessage());
        }
    }

    private void loadAttendanceForEmployee() {
        int employeeId = getSelectedEmployeeId();
        if (employeeId <= 0) return;
        try {
            List<Attendance> items = backend.loadByEmployee(employeeId);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            for (Attendance a : items) {
                model.addRow(new Object[] { a.getId(), a.getDate(), a.getShiftId(), a.getStatus(), a.getNote() });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load attendance: " + ex.getMessage());
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxEmployee = new javax.swing.JComboBox<>();
        jComboBoxShift = new javax.swing.JComboBox<>();
        jTextFieldDate = new javax.swing.JTextField();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jTextFieldNote = new javax.swing.JTextField();
        jButtonMark = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Attendance");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mark attendance and view records by employee.");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Employee");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Shift");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Date (YYYY-MM-DD)");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Status");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Note");

        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Present", "Absent", "Leave" }));

        jButtonMark.setBackground(new java.awt.Color(102, 153, 255));
        jButtonMark.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonMark.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMark.setText("Mark Attendance");
        jButtonMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMarkActionPerformed(evt);
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
                "ID", "Date", "Shift ID", "Status", "Note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxEmployee, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxShift, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate)
                            .addComponent(jComboBoxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNote, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonMark, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBoxEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxShift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonMark, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButtonMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMarkActionPerformed
        markAttendance();
    }//GEN-LAST:event_jButtonMarkActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new EmployeeManagement().setVisible(true);
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
        java.awt.EventQueue.invokeLater(() -> new AttendanceManagement().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonMark;
    private javax.swing.JComboBox<String> jComboBoxEmployee;
    private javax.swing.JComboBox<String> jComboBoxShift;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldNote;
    // End of variables declaration//GEN-END:variables
}
