/*
package com.church;

import com.church.dao.MemberDAO;
import com.church.model.Member;
import com.church.service.MemberService;
import com.church.util.DatabaseInitializer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class MainGUI extends JFrame {

    private final MemberDAO dao;
    private final MemberService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainGUI() {
        dao = new MemberDAO();
        service = new MemberService(dao);
        DatabaseInitializer.initialization();

        setTitle("Church od God of Prophecy");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadMembersTable();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{
                "ID","First Name","Last Name","DOB","Phone","Email","Status","Tithing","Baptized", "married"
        }, 0);

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addBtn = new JButton("Add Member");
        JButton updateBtn = new JButton("Update Member");
        JButton deleteBtn = new JButton("Delete Member");
        JButton importBtn = new JButton("Import CSV");
        JButton totalBtn = new JButton("Total Tithing");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(importBtn);
        buttonPanel.add(totalBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> addMemberDialog());
        updateBtn.addActionListener(e -> updateMemberDialog());
        deleteBtn.addActionListener(e -> deleteSelectedMember());
        importBtn.addActionListener(e -> importMembersFromFile());
        totalBtn.addActionListener(e -> showTotalTithing());
    }

    private void loadMembersTable() {
        tableModel.setRowCount(0);
        try {
            List<Member> members = dao.getAllMembers();
            for (Member m : members) {
                tableModel.addRow(new Object[]{
                        m.get_member_id(),
                        truncate(m.get_first_name(),25),
                        truncate(m.get_last_name(),25),
                        truncate(m.get_date_of_birth(),12),
                        truncate(m.get_phone_number(),15),
                        truncate(m.get_email(),25),
                        truncate(m.get_membership_status(),10),
                        m.get_tithing_amount(),
                        m.is_baptized() ? "Yes" : "No",
                        m.is_married() ? "Yes" : "No"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMemberDialog() {
        JTextField firstField = new JTextField();
        JTextField lastField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField tithingField = new JTextField();

        JLabel firstError = new JLabel("Error wrong entry");
        firstError.setForeground(Color.RED);
        firstError.setVisible(false);

        JLabel lastError = new JLabel("Error wrong entry");
        lastError.setForeground(Color.RED);
        lastError.setVisible(false);

        JLabel dobLabel = new JLabel("Error: wrong entry");
        dobLabel.setForeground(Color.RED);
        dobLabel.setVisible(false);

        JLabel emailLabel = new JLabel("Email not ye completed");
        emailLabel.setForeground(Color.RED);
        emailLabel.setVisible(false);

        JLabel statusLabel = new JLabel("Email not ye completed");
        statusLabel.setForeground(Color.RED);
        statusLabel.setVisible(false);

        JLabel phoneLabel = new JLabel("Error: wrong entry");
        phoneLabel.setForeground(Color.RED);
        phoneLabel.setVisible(false);

        JLabel tithingLabel = new JLabel("Error: wrong entry");
        tithingLabel.setForeground(Color.RED);
        tithingLabel.setVisible(false);

        javax.swing.event.DocumentListener validator = new javax.swing.event.DocumentListener() {

            private void validateFields() {

                String first = firstField.getText();
                String last = lastField.getText();
                String dob = dobField.getText();
                String email = emailField.getText();
                String status = statusField.getText();
                String phone = phoneField.getText();
                String tithing = tithingField.getText();

                firstError.setVisible(!first.matches("[a-zA-Z]*"));

                lastError.setVisible(!last.matches("[a-zA-Z]*"));

                if (dob.isEmpty()) {
                    dobLabel.setVisible(false);
                }
                else dobLabel.setVisible(!dob.matches("(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{0,4}"));

                if (email.isEmpty()) {
                    emailLabel.setVisible(false);
                }
                else emailLabel.setVisible(!email.contains("@"));

                if (status.isEmpty()) {
                    statusLabel.setVisible(false);
                }
                else statusLabel.setVisible(!status.toLowerCase().contains("active") && !status.toLowerCase().contains("inactive"));

                if (phone.isEmpty()) {
                    phoneLabel.setVisible(false);
                }
                else phoneLabel.setVisible(phone.matches("\\d*"));

                if (tithing.isEmpty()) {
                    tithingLabel.setVisible(false);
                }
                else tithingLabel.setVisible(!tithing.matches("\\d*"));
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateFields(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateFields(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateFields(); }
        };

        firstField.getDocument().addDocumentListener(validator);
        lastField.getDocument().addDocumentListener(validator);
        dobField.getDocument().addDocumentListener(validator);
        emailField.getDocument().addDocumentListener(validator);
        statusField.getDocument().addDocumentListener(validator);
        phoneField.getDocument().addDocumentListener(validator);
        tithingField.getDocument().addDocumentListener(validator);

        JCheckBox baptizedBox = new JCheckBox();
        JCheckBox marriedBox = new JCheckBox();

        Object[] message = {
                "First Name:", firstField,
                firstError,
                "Last Name:", lastField,
                lastError,
                "DOB (yyyy-mm-dd):", dobField,
                dobLabel,
                "Phone:", phoneField,
                phoneLabel,
                "Email:", emailField,
                emailLabel,
                "Status:", statusField,
                statusLabel,
                "Tithing Amount:", tithingField,
                tithingLabel,
                "Baptized:", baptizedBox,
                "Married:", marriedBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String first = firstField.getText().trim();
                String last = lastField.getText().trim();

                if (!isValidName(first)) {
                    JOptionPane.showMessageDialog(this,
                            "First Name must contain letters only.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidName(last)) {
                    JOptionPane.showMessageDialog(this,
                            "Last Name must contain letters only.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dob = dobField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String status = statusField.getText();
                double tithing = Double.parseDouble(tithingField.getText());
                boolean baptized = baptizedBox.isSelected();
                boolean married = marriedBox.isSelected();

                service.addMemberManually(first, last, dob, phone, email, status, tithing, baptized, married);
                service.saveMembersToCSV("C:\\Users\\relis\\Desktop\\members.csv");
                loadMembersTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Invalid input: "+ex.getMessage());
            }
        }
    }

    private void updateMemberDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,"Select a member to update.");
            return;
        }

        int memberId = (int) tableModel.getValueAt(selectedRow,0);
        try {
            Member m = dao.getAllMembers().stream().filter(mem -> mem.get_member_id()==memberId).findFirst().orElse(null);
            if (m == null) return;

            JTextField phoneField = new JTextField(m.get_phone_number());
            JTextField emailField = new JTextField(m.get_email());
            JTextField statusField = new JTextField(m.get_membership_status());
            JTextField tithingField = new JTextField(String.valueOf(m.get_tithing_amount()));
            JCheckBox baptizedBox = new JCheckBox();
            JCheckBox marrieddBox = new JCheckBox();
            baptizedBox.setSelected(m.is_baptized());
            marrieddBox.setSelected(m.is_married());

            Object[] message = {
                    "Phone:", phoneField,
                    "Email:", emailField,
                    "Status:", statusField,
                    "Tithing:", tithingField,
                    "Baptized:", baptizedBox,
                    "Married:", baptizedBox
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Update Member", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                m.set_phone_number(phoneField.getText());
                m.set_email(emailField.getText());
                m.set_membership_status(statusField.getText());
                m.set_tithing_amount(Double.parseDouble(tithingField.getText()));
                m.set_baptized(baptizedBox.isSelected());
                dao.updateMember(m);
                loadMembersTable();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
        }
    }

    private void deleteSelectedMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,"Select a member to delete.");
            return;
        }
        int memberId = (int) tableModel.getValueAt(selectedRow,0);
        int option = JOptionPane.showConfirmDialog(this,"Delete member ID "+memberId+"?","Confirm Delete",JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                dao.deleteMember(memberId);
                service.saveMembersToCSV("C:\\Users\\relis\\Desktop\\members.csv");
                loadMembersTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void importMembersFromFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            service.addMembersFromFile(file.getAbsolutePath());
            loadMembersTable();
        }
    }

    private void showTotalTithing() {
        try {
            double total = dao.getTotalTithing();
            JOptionPane.showMessageDialog(this,"Total Tithing: $" + total);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String truncate(String str,int maxLength) {
        if (str==null) return "";
        return str.length()<=maxLength ? str : str.substring(0,maxLength-3)+"...";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
*/
