/*
 * ProductManagement.java
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProductManagement.class.getName());

    public ProductManagement() {
        initUi();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void initUi() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new java.awt.BorderLayout());
        root.setBackground(new Color(0, 0, 0));
        setContentPane(root);

        JLabel title = new JLabel("Product Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Add, update, or delete products.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new javax.swing.BoxLayout(titlePanel, javax.swing.BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(24, 28, 12, 28));
        titlePanel.add(title);
        titlePanel.add(javax.swing.Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        JButton addButton = createPrimaryButton("Add Product");
        JButton updateButton = createPrimaryButton("Update Product");
        JButton deleteButton = createDangerButton("Delete Product");
        JButton backButton = createDangerButton("Back");
        backButton.setPreferredSize(new Dimension(90, 27));

        addButton.addActionListener(e -> {
            new AddProduct().setVisible(true);
            dispose();
        });
        updateButton.addActionListener(e -> {
            new UpdateProduct().setVisible(true);
            dispose();
        });
        deleteButton.addActionListener(e -> {
            new DeleteItem().setVisible(true);
            dispose();
        });
        backButton.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(12, 12, 12, 12);
        centerPanel.add(addButton, gbc);
        gbc.gridy++;
        centerPanel.add(updateButton, gbc);
        gbc.gridy++;
        centerPanel.add(deleteButton, gbc);

        JPanel bottomPanel = new JPanel(new java.awt.BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 28, 24, 28));
        JPanel backPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        backPanel.setOpaque(false);
        backPanel.add(backButton);
        bottomPanel.add(backPanel, java.awt.BorderLayout.EAST);

        root.add(titlePanel, java.awt.BorderLayout.NORTH);
        root.add(centerPanel, java.awt.BorderLayout.CENTER);
        root.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(102, 153, 255));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createSoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        button.setPreferredSize(new Dimension(520, 90));
        return button;
    }

    private JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 51, 51));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createSoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        button.setPreferredSize(new Dimension(520, 90));
        return button;
    }

    public static void main(String args[]) {
        UiScaleFix.apply();
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

        java.awt.EventQueue.invokeLater(() -> new ProductManagement().setVisible(true));
    }
}
