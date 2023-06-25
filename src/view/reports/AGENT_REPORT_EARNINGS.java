package view.reports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.UsersDAO;
import model.Tourist;

public class AGENT_REPORT_EARNINGS {
	private Font font;
	private Font tableFont;
	private JDialog dialog;
	private JPanel contentPanel; 
	private JTable table;
	private float totalPrice;
	private JLabel totalPriceLabel;
	private JDateChooser startDate;
	private JDateChooser endDate;
	private JPanel datePanel;
	private ArrayList<Integer> myClientResIds;
	
	public void earnings(String loginId) {
		myClientResIds = new ArrayList<Integer>();
		loadClientReservations(loginId, myClientResIds);
		
		font = new Font("SansSerif", Font.PLAIN, 30);
		dialog = new JDialog();
        dialog.setTitle("Agent - All Time Earnings Report");
        dialog.setSize(750, 750);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        contentPanel = new JPanel(new BorderLayout());
        dialog.setContentPane(contentPanel);   
        
        // date section
        datePanel = new JPanel();
     	Calendar calendar = Calendar.getInstance();
     	calendar.set(2023, Calendar.JANUARY, 1);
     	Date startDateLimit = calendar.getTime();
     	calendar.set(2023, Calendar.DECEMBER, 30);
     	Date endDateLimit = calendar.getTime();

     	startDate = new JDateChooser();
     	startDate.setSelectableDateRange(startDateLimit, endDateLimit);
     	startDate.setPreferredSize(new Dimension(200, startDate.getPreferredSize().height));
     	endDate = new JDateChooser();
     	endDate.setSelectableDateRange(startDateLimit, endDateLimit);
     	endDate.setPreferredSize(new Dimension(200, endDate.getPreferredSize().height));
     	JLabel fromLabel = new JLabel("FROM: ");
     	JLabel toLabel = new JLabel("TO: ");
     	fromLabel.setFont(font);
     	toLabel.setFont(font);
     	datePanel.add(fromLabel, BorderLayout.WEST);
     	datePanel.add(startDate, BorderLayout.CENTER);
     	datePanel.add(toLabel, BorderLayout.LINE_END);
     	datePanel.add(endDate, BorderLayout.EAST);
     	JButton generateButton = new JButton("GENERATE");
     	generateButton.setFont(font);
     	JPanel buttonPanel = new JPanel();
     	buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 0));
     	buttonPanel.add(generateButton);
     	datePanel.add(buttonPanel, BorderLayout.SOUTH);
     	
     	contentPanel.add(datePanel, BorderLayout.NORTH);
           
        String[] columnNames = {"ID", "Customer", "Full Price"};
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
         	
        tableFont = new Font("SansSerif", Font.PLAIN, 14);
        table = new JTable(model);
        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        table.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        totalPriceLabel = new JLabel();
        contentPanel.add(table, BorderLayout.CENTER);       
        contentPanel.add(totalPriceLabel, BorderLayout.WEST);
        
        // GENERATE LISTENER
     	generateButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		model.setRowCount(0);
        		totalPrice = 0;
        		for(int i=0; i<myClientResIds.size(); i++) {
        			if(startDate.getDate() != null || endDate.getDate() != null) {
        				LocalDateTime start = LocalDateTime.ofInstant(startDate.getDate().toInstant(), ZoneId.systemDefault());
        				LocalDateTime end = LocalDateTime.ofInstant(endDate.getDate().toInstant(), ZoneId.systemDefault());			
        				loadEarnings(model, myClientResIds.get(i), totalPrice, start, end);
        			} else {
        				loadEarnings(model, myClientResIds.get(i), totalPrice);
        			}
    			}
                
            }
        });
     	
	    dialog.setVisible(true);
	 }
	
	
	// ALL DATES 
	private void loadEarnings(DefaultTableModel model, Integer resId, float totalPrice) {
		try {
			File file = new File("src/assets/data/reservations.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineSplit = line.split("\\|");
				Object[] reservation = {};
				String resIdStr = resId.toString();
				if(lineSplit[0].equals(resIdStr) && lineSplit[6].equals("Completed")) {
					reservation = new Object[lineSplit.length+1]; 	
					reservation[0] = "RESERVATION: " + lineSplit[0];
					Tourist customer = UsersDAO.touristHash.get(Integer.parseInt(lineSplit[1]));					
					reservation[1] = "CUSTOMER: " + customer.getUsername();
					reservation[2] = "EARNED: " + lineSplit[9] + " $";
					this.totalPrice += Float.parseFloat(lineSplit[9]);
					model.addRow(reservation);	
				}          
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        addTotalPrice();
	}
	
	// SPECIFIC DATES
	private void loadEarnings(DefaultTableModel model, Integer resId, float totalPrice, LocalDateTime start, LocalDateTime end) {
	    try {
	        File file = new File("src/assets/data/reservations.txt");
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");    
	        while ((line = reader.readLine()) != null) {
	            String[] lineSplit = line.split("\\|");
	            Object[] reservation = {};
	            String resIdStr = resId.toString();       
	            if (lineSplit[0].equals(resIdStr) && lineSplit[6].equals("Completed")) {
	                LocalDateTime reservationDate = LocalDateTime.parse(lineSplit[7], formatter);
	                if (reservationDate.isAfter(start) && reservationDate.isBefore(end)) {
	                    reservation = new Object[lineSplit.length + 1]; 
	                    reservation[0] = "RESERVATION: " + lineSplit[0];
	                    Tourist customer = UsersDAO.touristHash.get(Integer.parseInt(lineSplit[1]));                   
	                    reservation[1] = "CUSTOMER: " + customer.getUsername();
	                    reservation[2] = "EARNED: " + lineSplit[9] + " $";
	                    this.totalPrice += Float.parseFloat(lineSplit[9]);
	                    model.addRow(reservation);
	                }
	            }
	        }
	        
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    addTotalPrice();
	}
	
	public void addTotalPrice() {
		contentPanel.remove(totalPriceLabel);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedTotalPrice = decimalFormat.format(totalPrice);
        totalPriceLabel = new JLabel("Total: "+formattedTotalPrice+" $");
        totalPriceLabel.setFont(font);
        totalPriceLabel.setHorizontalAlignment(JLabel.CENTER);
        totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        contentPanel.add(totalPriceLabel, BorderLayout.SOUTH);
	}
	
	// LOAD RES IDS
	private void loadClientReservations(String loginId, ArrayList<Integer> myClientResIds) {
		if (myClientResIds != null) {
		    this.myClientResIds.clear();
		}
		try {
		    File file = new File("src/assets/data/arrangements.txt");
		    BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        String[] lineSplit = line.split("\\|");
		        if (loginId.equals(lineSplit[1])) {
		            String reservations = lineSplit[9];
		            if (!reservations.equals("/")) {
		                String[] splittedReservationIds = reservations.split("\\,");
		                for (var j = 0; j < splittedReservationIds.length; j++) {
		                    int reservationId = Integer.parseInt(splittedReservationIds[j]);
		                    if (!myClientResIds.contains(reservationId)) {
		                        this.myClientResIds.add(reservationId);
		                    }
		                }
		            }
		        }
		    }
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}

