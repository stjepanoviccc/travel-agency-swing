package view.agent.reservations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.DateDAO;
import dao.ReservationsDAO;
import dao.RoomsDAO;
import model.Reservation;
import model.TouristAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class __Agent_Reservations_MyClientsReservation_Pane extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel headingLabel;
	private JScrollPane scrollPane;
	private Font tableFont;
	private JButton createBtn;
	private JButton acceptBtn;
	private JButton rejectBtn;
	private JPanel btnPanel;
	private Font font;
	private ArrayList<Integer> reservationIdList;
	private ArrayList<Reservation> resList;
	
    /**
     * Create the panel.
     */
    public __Agent_Reservations_MyClientsReservation_Pane(String loginId) {
    	reservationIdList = new ArrayList<Integer>();
    	resList = new ArrayList<Reservation>();
        setLayout(new BorderLayout());
        font = new Font("SansSerif", Font.PLAIN, 20);       
        createBtn = new JButton("CREATE");
        acceptBtn = new JButton("ACCEPT");
        rejectBtn = new JButton("REJECT");
        btnPanel = new JPanel();
        btnPanel.add(createBtn);
        btnPanel.add(acceptBtn);
        btnPanel.add(rejectBtn);
        createBtn.setFont(font);
        acceptBtn.setFont(font);
        rejectBtn.setFont(font);
        rejectBtn.setBackground(Color.RED);
        rejectBtn.setForeground(Color.WHITE);
        Insets padding = new Insets(10, 15, 10, 15);
        createBtn.setMargin(padding);
        acceptBtn.setMargin(padding);
        rejectBtn.setMargin(padding);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
             
        headingLabel = new JLabel("Agent - My Client's Reservations");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(headingLabel, BorderLayout.NORTH);
        // Create a table with ten columns
        String[] columnNames = {"ID", "Tourist", "Number of Passengers", "Number of Days", "Price per Day per Person", "Status", "Date of Creation", "Arrangement", "Full price", "Rooms"};
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
        
        updateTableData(model, columnNames, loginId, reservationIdList);
        
        tableFont = new Font("SansSerif", Font.PLAIN, 14);
        table = new JTable(model);
        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        table.setRowHeight(30);
        
     // Add a listener to handle row selection
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Disable row selection if multiple rows are selected
                if (!e.getValueIsAdjusting() && table.getSelectedRowCount() > 1) {
                    selectionModel.clearSelection();
                }
            }
        });
        
     // create res
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create button clicked
                __Agent_Reservations_CreateNew_Dialog registrationForm = new __Agent_Reservations_CreateNew_Dialog();
                registrationForm.addReservation(Integer.parseInt(loginId));
                // Update the table data
                updateTableData(model, columnNames, loginId, reservationIdList);
            }
        });
        
        rejectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	Object selectedState = table.getValueAt(selectedRow, 5);
                	String _state = (String) selectedState;
                	if(_state.equals("Created")) {
                        Object selectedValue = table.getValueAt(selectedRow, 0);
                        String _id = (String) selectedValue;
                        int reservationId = Integer.parseInt(_id);
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to REJECT the reservation?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Cancel the res
                        	TouristAgent agent = new TouristAgent();
                        	agent.cancelReservation(ReservationsDAO.reservationsHash.get(reservationId), "Unsuccessful");
                        	updateTableData(model, columnNames, loginId, reservationIdList);
                        }
                    } 
                	else if(_state.equals("Cancelled") || _state.equals("Unsuccessful") || _state.equals("Completed")){
                    	JOptionPane.showMessageDialog(null, "RESERVATION IS EITHER CANCELLED / UNSUCCESSFUL / COMPLETED ALREADY!", "Error", JOptionPane.ERROR_MESSAGE);
               } 	
               } else {
                	JOptionPane.showMessageDialog(null, "NO RESERVATION SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
               }
            }
        });
        
        acceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	Object selectedState = table.getValueAt(selectedRow, 5);
                	String _state = (String) selectedState;
                	if(_state.equals("Created")) {
                        Object selectedValue = table.getValueAt(selectedRow, 0);
                        String _id = (String) selectedValue;
                        int reservationId = Integer.parseInt(_id);
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to ACCEPT the reservation?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Cancel the res
                        	TouristAgent agent = new TouristAgent();
                        	agent.cancelReservation(ReservationsDAO.reservationsHash.get(reservationId), "Completed");
                        	updateTableData(model, columnNames, loginId, reservationIdList);
                        }
                    } 
                	else if(_state.equals("Cancelled") || _state.equals("Unsuccessful") || _state.equals("Completed")){
                    	JOptionPane.showMessageDialog(null, "RESERVATION IS EITHER CANCELLED / UNSUCCESSFUL / COMPLETED ALREADY!", "Error", JOptionPane.ERROR_MESSAGE);
               } 	
               } else {
                	JOptionPane.showMessageDialog(null, "NO RESERVATION SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
               }
            }
        });
        
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public ArrayList<Integer> updateTableData(DefaultTableModel model, String[] columnNames, String loginId, ArrayList<Integer> reservationIdList) {
    	model.setRowCount(0);
    	try {
            Object[] reservation = {};
			File file = new File("src/assets/data/reservations.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			if(reservationIdList != null) {
        		reservationIdList.clear();                		
        	}
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");
                if(lineSplit[2].equals(loginId)) {
                	reservationIdList.add(Integer.parseInt(lineSplit[0]));
                	reservation = new Object[lineSplit.length+1];
                	int j=0;
                    for (int i = 0; i < lineSplit.length; i++) {
                    	// make sure to jump over ID because we don't want to see ID
                    	if(i>0 && i<lineSplit.length-1) {
                    		reservation[i] = lineSplit[j+1];                    		
                    	} else {
                    		reservation[i] = lineSplit[j];                    		
                    	}
                    	if(i==7) {                 		
                    		reservation[6] = DateDAO.DateConvert(lineSplit[7]);
                    	}
                    	// rewrite on specific 'i'
                        if(i == 8 || i==4){
                        	reservation[i] = lineSplit[j+1] + " $";
                        }
                        if(i == 1) {
                            Integer id = Integer.parseInt(lineSplit[i]);
                            reservation[i] = dao.UsersDAO.getUserHash(id);
                        }
                        reservation[9] = RoomsDAO.roomsHash.get(Integer.parseInt(lineSplit[0]));
                        j++;
                    }
                    model.addRow(reservation);
                }                
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	updateResList(reservationIdList);
    	return reservationIdList;
    }
    
    private ArrayList<Reservation> updateResList(ArrayList<Integer> reservationIdList) {
    	for(Integer i : reservationIdList) {
    		resList.add(ReservationsDAO.reservationsHash.get(i));
    	}	
		return resList;
    }
}