package view.admin.users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import controller.crud.user.AddUser;
import dao.EnumDAO;
import model.Administrator;
import model.ENUM_Gender;
import model.ENUM_Role;

public class __Admin_Users_AddUser_Dialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField JMBGField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel JMBGLabel;
    private JLabel addressLabel;
    private JLabel phoneNumberLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel genderLabel;
    private JLabel roleLabel;
    private JDialog dialog;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JButton registerButton;
    private Font font;
    private Font font2;

    public void addUser() {
        font = new Font("SansSerif", Font.PLAIN, 20);
        font2 = new Font("SansSerif", Font.PLAIN, 18);
        
        dialog = new JDialog();
        dialog.setTitle("Admin - Add User");
        dialog.setSize(600, 750);
        dialog.setMinimumSize(new Dimension(dialog.getWidth(), 750));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(true);
        contentPanel = new JPanel(new BorderLayout());
        dialog.setContentPane(contentPanel);

        formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets.top = 10;
        gbc.insets.left = 10;
        gbc.insets.right = 10;

        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        JMBGLabel = new JLabel("JMBG: ");
        addressLabel = new JLabel("Address: ");
        phoneNumberLabel = new JLabel("Tel: ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        genderLabel = new JLabel("Gender: ");
        roleLabel = new JLabel("Role: ");
        
        JComboBox<ENUM_Gender> genderComboBox = new JComboBox<>(ENUM_Gender.values());
        JComboBox<String> roleComboBox = new JComboBox<>(EnumDAO.returnModifiedName(ENUM_Role.values()));
        genderComboBox.setFont(font2);
        roleComboBox.setFont(font2);
        
        firstNameLabel.setFont(font);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        JMBGField = new JTextField(20);
        addressField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        List<JLabel> labelList = Arrays.asList(firstNameLabel, lastNameLabel, JMBGLabel, addressLabel, phoneNumberLabel, usernameLabel, passwordLabel, genderLabel, roleLabel);
        for (JLabel label : labelList) {
            label.setFont(font);
        }
        
        List<JTextField> fieldList = Arrays.asList(firstNameField, lastNameField, JMBGField, addressField, phoneNumberField, usernameField, passwordField);
        for (JTextField field : fieldList) {
        	field.setFont(font2);
        }
         
        // style
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(firstNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(lastNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(JMBGLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(JMBGField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(addressField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(phoneNumberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(genderComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(roleLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(roleComboBox,gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        registerButton = new JButton("Add User");
        Insets margin = new Insets(10,30,10,30);
        registerButton.setMargin(margin);
        registerButton.setFont(font);   
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        buttonPanel.add(registerButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        // add action listener to login button
        registerButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				Object[] userInput = new Object[9];
				userInput[0] = firstNameField.getText();
				userInput[1] = lastNameField.getText();
				userInput[2] = JMBGField.getText();
				userInput[3] = addressField.getText();
				userInput[4] = phoneNumberField.getText();
				userInput[5] = usernameField.getText();
				userInput[6] = new String(passwordField.getPassword());
				userInput[7] = genderComboBox.getSelectedItem();
				userInput[8] = roleComboBox.getSelectedItem();
				userInput[7] = userInput[7].toString();
				userInput[8] = userInput[8].toString();
				
                if(AddUser.addUser_VALIDATE(userInput) == true) {
                	if(AddUser.addUser_VALIDATE_2(userInput)== true) {
                		Administrator.addUser(userInput);
                	}
                } else {
                	System.out.println("ERROR");
                }
                
            }
        });
        dialog.setVisible(true);

    }
}

       
