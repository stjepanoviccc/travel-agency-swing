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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class AGENT_REPORT_SORT_ARR {
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
	private ArrayList<Integer> myArrangementsIds;
	private ArrayList<Integer> myArrangementsCounters;
	
	public void sorting(String loginId) {
		myArrangementsIds = new ArrayList<Integer>();
		myArrangementsCounters = new ArrayList<Integer>();
		loadMyArrangements(loginId);
		
		font = new Font("SansSerif", Font.PLAIN, 30);
		dialog = new JDialog();
        dialog.setTitle("Agent - Sort Arrangements by Popularity");
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
     	JButton generateButton = new JButton("SORT ARR");
     	generateButton.setFont(font);
     	JPanel buttonPanel = new JPanel();
     	buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 0));
     	buttonPanel.add(generateButton);
     	datePanel.add(buttonPanel, BorderLayout.SOUTH);
     	
     	contentPanel.add(datePanel, BorderLayout.NORTH);
           
        String[] columnNames = {"ID", "Times Completed"};
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
        		if(startDate.getDate() != null || endDate.getDate() != null) {
        			loadMyArrangements(loginId);
        			LocalDateTime start = LocalDateTime.ofInstant(startDate.getDate().toInstant(), ZoneId.systemDefault());
        			LocalDateTime end = LocalDateTime.ofInstant(endDate.getDate().toInstant(), ZoneId.systemDefault());			
        			sortArrangements(model, myArrangementsIds, totalPrice, start, end);
        		} else {
        			return;
        		}               
            }
        });  	
	    dialog.setVisible(true);
	 }
	
	// SPECIFIC DATES
	private void sortArrangements(DefaultTableModel model, ArrayList<Integer> myArrangementsIds, float totalPrice, LocalDateTime start, LocalDateTime end) {
	    try {
	        List<Object[]> arrangements = new ArrayList<>();
	        for (int i=0; i<myArrangementsIds.size(); i++) {
	        	int counter = 0;
	        	File file = new File("src/assets/data/reservations.txt");
	        	BufferedReader reader = new BufferedReader(new FileReader(file));
	        	String line;
	        	while((line = reader.readLine()) != null) {
	        		String[] lineSplit = line.split("\\|");
	        		String arrIdString = myArrangementsIds.get(i).toString();
	        		if(lineSplit[8].equals(arrIdString) && lineSplit[6].equals("Completed")) {
	        			LocalDateTime fileDate = LocalDateTime.parse(lineSplit[7]);
	        			if (fileDate.isAfter(start) && fileDate.isBefore(end)) {
	                        counter += 1;
	                    }
	        		}
	        	}
	        	reader.close();
	        	myArrangementsCounters.set(i, counter);
	        }
	        Object[] arrangement = {};
	        for (int j=0; j<myArrangementsIds.size(); j++) {
	        	arrangement = new Object[myArrangementsIds.size()+1];
	        	arrangement[0] = myArrangementsIds.get(j);
	        	arrangement[1] = myArrangementsCounters.get(j);
	        	arrangements.add(arrangement);
	        }
	        // aNgoritam is function that sorts two ArrayList<Integer> and combine into List<Object>;
	        List<Object[]> sortedArrangements = new ArrayList<>();
	        aNgoritam(sortedArrangements);
	            
	        for(Object[] arr:sortedArrangements) {
	        	arr[0] = "ARR: "+arr[0];
	        	arr[1] = "COMPLETED: "+arr[1];
	        	model.addRow(arr);
	        } 
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	// LOAD RES IDS
	private void loadMyArrangements(String loginId) {
		if (myArrangementsIds != null) {
		    myArrangementsIds.clear();
		    myArrangementsCounters.clear();
		}
		try {
		    File file = new File("src/assets/data/arrangements.txt");
		    BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        String[] lineSplit = line.split("\\|");
		        if (loginId.equals(lineSplit[1])) {
		        	myArrangementsIds.add(Integer.parseInt(lineSplit[0]));		            
		        	myArrangementsCounters.add(0);
		        }
		    }
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private void aNgoritam(List<Object[]> sortedArrangements) {
		while(myArrangementsIds.size() != 0) {
			Object[] newArrangement = {};
			newArrangement = new Object[myArrangementsIds.size()+1];
	        int maxArrangement = myArrangementsIds.get(0);
	        int maxCounter = myArrangementsCounters.get(0);
	        int index = 0;
	        for (int i = 1; i < myArrangementsIds.size(); i++) {
	            if(maxCounter < myArrangementsCounters.get(i)) {
	            	maxCounter = myArrangementsCounters.get(i);
	            	maxArrangement = myArrangementsIds.get(i);
	            	index = i;
	            }
	        }
	        newArrangement[0] = maxArrangement;
	        newArrangement[1] = maxCounter;
	        sortedArrangements.add(newArrangement);
	        myArrangementsIds.remove(index);
	        myArrangementsCounters.remove(index);
		}     
	}
}