package view.agent.arrangements;

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
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controller.crud.arrangement.AddArrangement;
import dao.ArrangementsDAO;
import dao.EnumDAO;
import dao.UsersDAO;
import model.Arrangement;
import model.ENUM_ArrangementType;
import model.ENUM_TypeOfAccomodation;
import model.ENUM_TypeOfRoom;
import model.TouristAgent;

public class __Agent_Arrangements_EditArrangement_Dialog {
	private JButton browseButton;
    private JDateChooser dateField; 
    private JTextField capacityField;
    private JTextField pricePerPersonField;
    private JTextField fairDiscountField;
    private JLabel agentNameLabel;
    private JLabel arrangementTypeLabel;
    private JLabel imagesLabel;
    private JLabel dateLabel;
    private JLabel capacityLabel;
    private JLabel typeOfRoomLabel;
    private JLabel typeOfAccomodationLabel;
    private JLabel pricePerPersonLabel;
    private JLabel fairDiscountLabel;
    private JDialog dialog;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JButton editButton;
    private Font font;
    private Font font2;

    public void editArrangement(Integer arrangementId) {
        font = new Font("SansSerif", Font.PLAIN, 20);
        font2 = new Font("SansSerif", Font.PLAIN, 18);
        
        dialog = new JDialog();
        dialog.setTitle("Agent - Edit Arrangement");
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

        agentNameLabel = new JLabel("Agent: ");
        arrangementTypeLabel = new JLabel("Arrangement Type: ");
        imagesLabel = new JLabel("Image: ");
        dateLabel = new JLabel("Date: ");
        capacityLabel = new JLabel("Capacity: ");
        typeOfRoomLabel = new JLabel("Available Rooms: ");
        typeOfAccomodationLabel = new JLabel("Accomodation Type: ");
        pricePerPersonLabel = new JLabel("Price per person ($): ");
        fairDiscountLabel = new JLabel("Fair discount ($): ");
        
        List<String> agentsList = UsersDAO.getOnlyAgents();
        String[] agentsArray = agentsList.toArray(new String[agentsList.size()]);
        JComboBox<String> agentsComboBox = new JComboBox<>(agentsArray);
        JComboBox<String> arrangementTypeComboBox = new JComboBox<>(EnumDAO.returnModifiedName(ENUM_ArrangementType.values()));
        JComboBox<String> roomTypeComboBox = new JComboBox<>(EnumDAO.returnModifiedName(ENUM_TypeOfRoom.values()));
        JComboBox<ENUM_TypeOfAccomodation> accomodationTypeComboBox = new JComboBox<>(ENUM_TypeOfAccomodation.values());
        agentsComboBox.setFont(font2);
        arrangementTypeComboBox.setFont(font2);
        accomodationTypeComboBox.setFont(font2);
        roomTypeComboBox.setFont(font2);
        
        browseButton = new JButton("Browse image");
        capacityField = new JTextField(20);
        pricePerPersonField = new JTextField(20);
        fairDiscountField = new JTextField(20);
        
        // date
        dateField = new JDateChooser();
        dateField.setPreferredSize(new Dimension(240, 30));
        dateField.setFont(font2);
        Calendar today = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2023, Calendar.DECEMBER, 31);
        dateField.setSelectableDateRange(today.getTime(), endDate.getTime());
        
        List<JLabel> labelList = Arrays.asList(agentNameLabel, arrangementTypeLabel, imagesLabel, dateLabel, capacityLabel, typeOfRoomLabel, typeOfAccomodationLabel, pricePerPersonLabel, fairDiscountLabel);
        for (JLabel label : labelList) {
            label.setFont(font);
        }
        
        List<JTextField> fieldList = Arrays.asList(capacityField, pricePerPersonField, fairDiscountField);
        for (JTextField field : fieldList) {
        	field.setFont(font2);
        }
        
        browseButton.setFont(font2);
        browseButton.addActionListener(AddArrangement.BrowseImage(dialog, browseButton));

        // style
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(agentNameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(agentsComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(arrangementTypeLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(arrangementTypeComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(imagesLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(browseButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(dateField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(capacityLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(capacityField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(typeOfRoomLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(roomTypeComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(typeOfAccomodationLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(accomodationTypeComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(pricePerPersonLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(pricePerPersonField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(fairDiscountLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(fairDiscountField,gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        editButton = new JButton("Update");
        Insets margin = new Insets(10,30,10,30);
        editButton.setMargin(margin);
        editButton.setFont(font);   
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        buttonPanel.add(editButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
      
     //   taking values out of hash
        Arrangement arr = ArrangementsDAO.arrangementsHash.get(arrangementId);
        Integer ARRANGEMENT_ID = arr.getId();
        Integer sellerId = arr.getSeller().getId();
        TouristAgent agent = UsersDAO.agentHash.get(sellerId);
        agentsComboBox.setSelectedItem(agent.getUsername());
        agentsComboBox.setEnabled(false);
        String ar = arr.getTypeOfArrangement().getModifiedName();
        arrangementTypeComboBox.setSelectedItem(ar);
        browseButton.setText(arr.getImage());
        dateField.setEnabled(false);
        Integer cap = arr.getCapacity();
        capacityField.setText(Integer.toString(cap));
        String room = "Single bed";
        roomTypeComboBox.setSelectedItem(room);
        accomodationTypeComboBox.setSelectedItem(arr.getTypeOfAccomodation());
        Float pricePerPerson = arr.getPricePerPerson();
        Float fairDiscount = arr.getFairDiscount();
        pricePerPersonField.setText(Float.toString(pricePerPerson));
        fairDiscountField.setText(Float.toString(fairDiscount));		
        
        // add action listener to login button
        editButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				// validations that needed to be in this file, rest are in controller
				if(capacityField.getText().equals("") || fairDiscountField.getText().equals("") || pricePerPersonField.getText().equals("")) {
					return;
				}	
				// switching back to real name, again will revert in next validation								
                String modifiedArrangementTypeName = EnumDAO.modifyENUMTypeName(arrangementTypeComboBox.getSelectedItem().toString());
				String modifiedRoomTypeName = EnumDAO.modifyENUMTypeName(roomTypeComboBox.getSelectedItem().toString());

				// instancing arrangement now
				Arrangement arr = new Arrangement();				
				TouristAgent agent = new TouristAgent();
				agent.setUsername(agentsComboBox.getSelectedItem().toString());
				ENUM_ArrangementType arrangementType = ENUM_ArrangementType.valueOf(modifiedArrangementTypeName);
				ENUM_TypeOfRoom roomType = ENUM_TypeOfRoom.valueOf(modifiedRoomTypeName);
				ENUM_TypeOfAccomodation accommodationType = ENUM_TypeOfAccomodation.valueOf(accomodationTypeComboBox.getSelectedItem().toString());
				String image = browseButton.getText();
				int capacity = Integer.parseInt(capacityField.getText());
				float pricePerPerson = Float.parseFloat(pricePerPersonField.getText());
				float fairDiscount = Float.parseFloat(fairDiscountField.getText());
				
				arr.setId(ARRANGEMENT_ID);
				arr.setSeller(agent);
				arr.setTypeOfArrangement(arrangementType);
				arr.setImage(image);
				arr.setCapacity(capacity);
				arr.setPricePerPerson(pricePerPerson);
				arr.setFairDiscount(fairDiscount);
				arr.setTypeOfRoom(roomType);
				arr.setTypeOfAccomodation(accommodationType);
                System.out.println(arr.getAvailableDate());
                if(AddArrangement.addArrangement_VALIDATE(arr, 1) == true) {
                	TouristAgent.editArrangement(arr);
                } else {
                	System.out.println("ERROR");
                }
                
            }
        });
        dialog.setVisible(true);
    }
}

