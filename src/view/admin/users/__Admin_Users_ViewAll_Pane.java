package view.admin.users;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Administrator;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class __Admin_Users_ViewAll_Pane extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTable table;
    private JLabel headingLabel;
    private JScrollPane scrollPane;
    private Font font;
    private Font tableFont;
    private JButton createBtn;
    private JButton editBtn;
    private JButton delBtn;
    private JPanel btnPanel;

    /**
     */
    public __Admin_Users_ViewAll_Pane() {
        setLayout(new BorderLayout());
        font = new Font("SansSerif", Font.PLAIN, 20);
        headingLabel = new JLabel("Admin - All Users");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(headingLabel, BorderLayout.NORTH);
        createBtn = new JButton("CREATE");
        editBtn = new JButton("UPDATE");
        delBtn = new JButton("DELETE");
        btnPanel = new JPanel();
        btnPanel.add(createBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        editBtn.setFont(font);
        delBtn.setFont(font);
        createBtn.setFont(font);
        delBtn.setBackground(Color.RED);
        delBtn.setForeground(Color.WHITE);
        Insets padding = new Insets(10, 15, 10, 15);
        editBtn.setMargin(padding);
        delBtn.setMargin(padding);
        createBtn.setMargin(padding);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        // Create a table with ten columns
        String[] columnNames = {"ID", "Name", "Surname", "JMBG", "Address", "Phone", "Username", "Password", "Gender", "Role"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateTableData(model);

        tableFont = new Font("SansSerif", Font.PLAIN, 14);
        table = new JTable(model);
        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        table.setRowHeight(30);

        // Set selection mode to single selection
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a listener to handle row selection
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Disable row selection if multiple rows are selected
                if (!e.getValueIsAdjusting() && table.getSelectedRowCount() > 1) {
                    selectionModel.clearSelection();
                }
            }
        });
        
     // button listeners
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = table.getValueAt(selectedRow, 0);
                    String _id = (String) selectedValue;
                    int userId = Integer.parseInt(_id);
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the user?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Delete the user
                    	Administrator admin = new Administrator();
                    	admin.deleteUser(userId);
                        model.removeRow(selectedRow);
                    }
                } else {
                	JOptionPane.showMessageDialog(null, "NO USER SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
       
        // edit
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Edit button clicked
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = table.getValueAt(selectedRow, 0);
                    String _id = (String) selectedValue;
                    int userId = Integer.parseInt(_id);
                    __Admin_Users_EditUser_Dialog editDialog = new __Admin_Users_EditUser_Dialog();
                    editDialog.editUser(userId);
                    updateTableData(model);
                    
                } else {	
                	JOptionPane.showMessageDialog(null, "NO USER SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create button clicked
                __Admin_Users_AddUser_Dialog registrationForm = new __Admin_Users_AddUser_Dialog();
                registrationForm.addUser();

                // Update the table data
                updateTableData(model);
            }
        });
      
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // PRIVATE FUNC
    private void updateTableData(DefaultTableModel model) {
        //  clear
        model.setRowCount(0);

        // read user data from files and add it to the table
        String[] fileNames = {"src/assets/data/admins.txt", "src/assets/data/agents.txt", "src/assets/data/tourists.txt"};
        for (String fileName : fileNames) {
            try {
                File file = new File(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineSplit = line.split("\\|");
                    var active_or_inactive = lineSplit[10];
                    if (active_or_inactive.equals("ACTIVE")) {
                        Object[] user = new Object[lineSplit.length];
                        for (int i = 0; i < lineSplit.length - 1; i++) {
                            user[i] = lineSplit[i];
                        }
                        model.addRow(user);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
