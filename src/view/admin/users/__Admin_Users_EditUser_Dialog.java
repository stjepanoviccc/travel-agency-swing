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
import dao.UsersDAO;
import model.Administrator;
import model.ENUM_Gender;
import model.ENUM_Role;
import model.Tourist;
import model.TouristAgent;

public class __Admin_Users_EditUser_Dialog {
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
    private JButton saveChange;
    private Font font;
    private Font font2;

	public void editUser(int userId) {
		 font = new Font("SansSerif", Font.PLAIN, 20);
	     font2 = new Font("SansSerif", Font.PLAIN, 18);
	        
	     dialog = new JDialog();
	     dialog.setTitle("Admin - Edit User");
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
        saveChange = new JButton("Apply Changes");
        Insets margin = new Insets(10,30,10,30);
        saveChange.setMargin(margin);
        saveChange.setFont(font);   
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        buttonPanel.add(saveChange);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // taking values out of hash
        String _id = Integer.toString(userId);
        String substrId = _id.substring(0,1);
        if(substrId.equals("1")) {
        	Administrator admin = UsersDAO.adminHash.get(userId);
        	firstNameField.setText(admin.getName());
        	lastNameField.setText(admin.getSurname());
        	JMBGField.setText(admin.getJMBG());
        	addressField.setText(admin.getAddress());
        	Integer phone = admin.getPhone();
        	phoneNumberField.setText("0" + phone.toString());
        	usernameField.setText(admin.getUsername());
        	passwordField.setText(admin.getPassword());
        	genderComboBox.setSelectedItem(admin.getGender());
        	roleComboBox.setSelectedItem("Administrator");
        } else if(substrId.equals("2")) {
        	TouristAgent agent = UsersDAO.agentHash.get(userId);
        	System.out.println("HEYHEY");
        	System.out.println(agent);
        	firstNameField.setText(agent.getName());
        	lastNameField.setText(agent.getSurname());
        	JMBGField.setText(agent.getJMBG());
        	addressField.setText(agent.getAddress());
        	Integer phone = agent.getPhone();
        	phoneNumberField.setText("0" + phone.toString());
        	usernameField.setText(agent.getUsername());
        	passwordField.setText(agent.getPassword());
        	genderComboBox.setSelectedItem(agent.getGender());
        	roleComboBox.setSelectedItem("Tourist agent");
        } else if(substrId.equals("3")) {
        	Tourist tourist = UsersDAO.touristHash.get(userId);
        	firstNameField.setText(tourist.getName());
        	lastNameField.setText(tourist.getSurname());
        	JMBGField.setText(tourist.getJMBG());
        	addressField.setText(tourist.getAddress());
        	Integer phone = tourist.getPhone();
        	phoneNumberField.setText("0" + phone.toString());
        	usernameField.setText(tourist.getUsername());
        	passwordField.setText(tourist.getPassword());
        	genderComboBox.setSelectedItem(tourist.getGender());
        	roleComboBox.setSelectedItem("Tourist");
        }
        // add action listener to login button
        saveChange.addActionListener(new ActionListener() {
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
				
				// same validation is used as in addUser (just first validation because second will throw error of duplicate) *IMPORTANT* 
                if(AddUser.addUser_VALIDATE(userInput) == true) {
                		Administrator.editUser(userInput, userId);
                } else {
                	System.out.println("ERROR");
                }
                
            }
        });
        
        dialog.setVisible(true);    
	}
}