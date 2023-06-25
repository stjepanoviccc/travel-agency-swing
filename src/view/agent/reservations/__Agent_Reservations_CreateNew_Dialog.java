package view.agent.reservations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import dao.ArrangementsDAO;
import dao.DateDAO;
import dao.EnumDAO;
import dao.ReservationsDAO;
import dao.UsersDAO;
import model.Arrangement;
import model.ENUM_TypeOfRoom;
import model.Reservation;
import model.Tourist;
import model.TouristAgent;

public class __Agent_Reservations_CreateNew_Dialog {
	private JDateChooser dateField; 
    private JTextField roomsNumField;
    private JTextField daysNumField;
    private JTextField passengerNumField;
    private JLabel arrangementsLabel;
    private JLabel touristNameLabel;
    private JLabel passengerNumLabel;
    private JLabel dateLabel;
    private JLabel daysNumLabel;
    private JLabel roomsNumLabel;
    private JDialog dialog;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JButton createButton;
    private Font font;
    private Font font2;
    private ArrayList<Integer> arrangementIdList;

    public void addReservation(Integer loginId) {
        font = new Font("SansSerif", Font.PLAIN, 20);
        font2 = new Font("SansSerif", Font.PLAIN, 18);
    	arrangementIdList = new ArrayList<Integer>();
    	ArrangementsDAO.getSpecificArrangements(loginId.toString(), arrangementIdList);
        dialog = new JDialog();
        dialog.setTitle("Agent - Create New Reservation");
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

        touristNameLabel = new JLabel("Tourist(Customer): ");
        arrangementsLabel = new JLabel("Arrangements: ");
        roomsNumLabel = new JLabel("Number of rooms: ");
        dateLabel = new JLabel("Date: ");
        daysNumLabel = new JLabel("Number of days: ");
        passengerNumLabel = new JLabel("Number of passengers: ");

        JComboBox<String> touristComboBox = new JComboBox<>();
        List<String> tourists = UsersDAO.getOnlyTourists();
        for(String tourist : tourists) {
        	touristComboBox.addItem(tourist);
        }
        JComboBox<String> arrangementsComboBox = new JComboBox<>(); 
        arrangementsComboBox.addItem("PICK ARRANGEMENT");
        arrangementsComboBox.setSelectedItem("PICK ARRANGEMENT");
        for (Integer number : arrangementIdList) {
        	arrangementsComboBox.addItem(String.valueOf(number));
        }

        JComboBox<String> roomTypeComboBox = new JComboBox<>(EnumDAO.returnModifiedName(ENUM_TypeOfRoom.values()));
        touristComboBox.setFont(font2);
        arrangementsComboBox.setFont(font2);
        roomTypeComboBox.setFont(font2);
        
        roomsNumField = new JTextField(20);
        daysNumField = new JTextField(20);
        passengerNumField = new JTextField(20);
        
        // date
        dateField = new JDateChooser();
        dateField.setPreferredSize(new Dimension(240, 30));
        dateField.setFont(font2);
        Calendar today = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2023, Calendar.DECEMBER, 31);
        dateField.setSelectableDateRange(today.getTime(), endDate.getTime());

        arrangementsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(arrangementsComboBox.getSelectedItem().toString().equals("/") || arrangementsComboBox.getSelectedItem().toString().equals("PICK ARRANGEMENT")) {
            		return;
            	}
            	arrangementsComboBox.setEnabled(false);
            	Integer arrId = Integer.parseInt(arrangementsComboBox.getSelectedItem().toString());
            	Arrangement arr = ArrangementsDAO.arrangementsHash.get(arrId);
            	String resListString = arr.getReservations();
            	
            	String[] splittedResListString = resListString.split(",");
            	ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            	for(var i=0; i<splittedResListString.length; i++) {
            		if(splittedResListString[i].equals("/")) {
            			return;
            		}
            		Integer id = Integer.parseInt(splittedResListString[i]);
            		reservations.add(ReservationsDAO.reservationsHash.get(id));
            	}          	              
            	for(Reservation res : reservations) {
            		JCalendar calendar = dateField.getJCalendar();
            		LocalDateTime startDay = res.getDate();
                    int howMuchIStay = res.getNumberOfRooms();
                    Calendar disabledStartDate = Calendar.getInstance();
                    disabledStartDate.setTime(Date.from(startDay.atZone(ZoneId.systemDefault()).toInstant()));
                    Calendar disabledEndDate = Calendar.getInstance();
                    disabledEndDate.setTime(Date.from(startDay.plusDays(howMuchIStay).atZone(ZoneId.systemDefault()).toInstant()));                  
                    calendar.getDayChooser().addDateEvaluator(new DateDAO.DisabledDateEvaluator(disabledStartDate, disabledEndDate));    
                    ////
            	}    
            }
        });    
     
        List<JLabel> labelList = Arrays.asList(arrangementsLabel, touristNameLabel, dateLabel, daysNumLabel, passengerNumLabel, roomsNumLabel);
        for (JLabel label : labelList) {
            label.setFont(font);
        }
        
        List<JTextField> fieldList = Arrays.asList(daysNumField, passengerNumField, roomsNumField);
        for (JTextField field : fieldList) {
        	field.setFont(font2);
        }

        // style
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(arrangementsLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(arrangementsComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(touristNameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(touristComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(dateField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(daysNumLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(daysNumField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(passengerNumLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(passengerNumField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(roomsNumLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(roomsNumField, gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        createButton = new JButton("Create");
        Insets margin = new Insets(10,30,10,30);
        createButton.setMargin(margin);
        createButton.setFont(font);   
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        buttonPanel.add(createButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        // add action listener to login button
        
        createButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(arrangementsComboBox.getSelectedItem().toString().equals("PICK ARRANGEMENT")) {
					return;
				}
				// validations that needed to be in this file, rest are in controller
				if(daysNumField.getText().equals("") || passengerNumField.getText().equals("") || roomsNumField.getText().equals("")) {
					return;
				}
				Calendar selectedDate = dateField.getCalendar();            
                if (selectedDate.before(today) || selectedDate.after(endDate)) {
                    return;
                }
				
				// switching back to real name, again will revert in next validation
				
				// instancing res
				Reservation res = new Reservation();		
				TouristAgent agent = new TouristAgent();
				String arr = arrangementsComboBox.getSelectedItem().toString();
				int arrInt = Integer.parseInt(arr);
				Arrangement arrangement = ArrangementsDAO.arrangementsHash.get(arrInt);
				agent.setUsername(arrangement.getSeller().getUsername());
				Tourist tourist = new Tourist();
				tourist.setUsername(touristComboBox.getSelectedItem().toString());
				int daysNum = Integer.parseInt(daysNumField.getText());
				int roomsNum = Integer.parseInt(roomsNumField.getText());
				int passengerNum = Integer.parseInt(passengerNumField.getText());
				float pricePerPerson = arrangement.getPricePerPerson();
				LocalDateTime availableDate = LocalDateTime.ofInstant(dateField.getDate().toInstant(), ZoneId.systemDefault());
				res.setReservationSeller(agent);
				res.setReservationCustomer(tourist);
				res.setDate(availableDate);
				res.setNumberOfPassengers(passengerNum);
				res.setNumberOfDays(daysNum);
				res.setNumberOfRooms(roomsNum);
				res.setPricePerDayPerPerson(pricePerPerson);
				res.setArrangementId(arrInt);
				TouristAgent.createReservation(res, "Completed");
            }
        });
        dialog.setVisible(true);
    }
}
