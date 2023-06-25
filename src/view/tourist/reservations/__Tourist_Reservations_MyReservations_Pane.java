package view.tourist.reservations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.DateDAO;
import dao.ReservationsDAO;
import dao.RoomsDAO;
import model.Reservation;
import model.Tourist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class __Tourist_Reservations_MyReservations_Pane extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel headingLabel;
	private JScrollPane scrollPane;
	private Font tableFont;
	private JButton createBtn;
	private JButton cancelBtn;
	private JButton editBtn;
	private JPanel btnPanel;
	private Font font;
	private ArrayList<Integer> reservationIdList;
	private ArrayList<Reservation> resList;

    /**
     * Create the panel.
     */
    public __Tourist_Reservations_MyReservations_Pane(String loginId) {
    	reservationIdList = new ArrayList<Integer>();
    	resList = new ArrayList<Reservation>();
        setLayout(new BorderLayout());
        font = new Font("SansSerif", Font.PLAIN, 20);
        headingLabel = new JLabel("Tourist - My Reservations");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(headingLabel, BorderLayout.NORTH);
        createBtn = new JButton("CREATE");
        editBtn = new JButton("UPDATE");
        cancelBtn = new JButton("CANCEL");
        btnPanel = new JPanel();
        btnPanel.add(createBtn);
        btnPanel.add(editBtn);
        btnPanel.add(cancelBtn);
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        Insets padding = new Insets(10, 15, 10, 15);
        createBtn.setMargin(padding);
        editBtn.setMargin(padding);
        cancelBtn.setMargin(padding);
        createBtn.setFont(font);
        editBtn.setFont(font);
        cancelBtn.setFont(font);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
               
        // Create a table with ten columns
        String[] columnNames = {"ID", "Tourist", "Agent", "Number of Passengers", "Number of Days", "Price per Day per Person", "Status", "Date of Creation", "Arrangement", "Full price", "Rooms"};
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
        
        updateTableData(model, loginId, reservationIdList);
        
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
          
        // cancel res
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = table.getSelectedRow();
            	Object selectedState = table.getValueAt(selectedRow, 6);
            	String _state = (String) selectedState;
            	if(_state.equals("Created")) {
            		// delete
                    if (selectedRow != -1) {
                        Object selectedValue = table.getValueAt(selectedRow, 0);
                        String _id = (String) selectedValue;
                        int reservationId = Integer.parseInt(_id);
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the reservation?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Delete the user
                        	Tourist tourist = new Tourist();
                        	tourist.cancelReservation(ReservationsDAO.reservationsHash.get(reservationId), "Cancelled");
                        	updateTableData(model, loginId, reservationIdList);
                        	
                        }
                    }
            	} else if(_state.equals("Completed") || _state.equals("Cancelled") || _state.equals("Unsuccessful")) {
            		JOptionPane.showMessageDialog(null, "RESERVATION IS EITHER CANCELLED / UNSUCCESSFUL / COMPLETED ALREADY!", "Error", JOptionPane.ERROR_MESSAGE);
            	}
                 else {
                	JOptionPane.showMessageDialog(null, "NO RESERVATION SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // edit res
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Edit button clicked
                int selectedRow = table.getSelectedRow();
                Object selectedState = table.getValueAt(selectedRow, 6);
                String _state = (String) selectedState;
                if(_state.equals("Created")) {
                	if (selectedRow != -1) {
                        Object selectedValue = table.getValueAt(selectedRow, 0);
                        String _id = (String) selectedValue;
                        int reservationId = Integer.parseInt(_id);
                        __Tourist_Reservations_EditReservation_Dialog editDialog = new __Tourist_Reservations_EditReservation_Dialog();
                        editDialog.editReservation(reservationId);
                        updateTableData(model, loginId, reservationIdList);
                        
                    }
                } else if(_state.equals("Completed") || _state.equals("Cancelled") || _state.equals("Unsuccessful")) {
                	JOptionPane.showMessageDialog(null, "RESERVATION IS EITHER CANCELLED / UNSUCCESSFUL / COMPLETED ALREADY!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {	
                	JOptionPane.showMessageDialog(null, "NO RESERVATION SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // create res
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create button clicked
                __Tourist_Reservations_CreateNew_Dialog registrationForm = new __Tourist_Reservations_CreateNew_Dialog();
                registrationForm.addReservation(Integer.parseInt(loginId));
                // Update the table data
                updateTableData(model, loginId, reservationIdList);
            }
        });
        
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // update table
    private ArrayList<Integer> updateTableData(DefaultTableModel model, String loginId, ArrayList<Integer> reservationIdList) {
    	model.setRowCount(0);
    	try {
			File file = new File("src/assets/data/reservations.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			if(reservationIdList != null) {
        		reservationIdList.clear();                		
        	}
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");
                Object[] reservation = {};
                if(lineSplit[1].equals(loginId)) {
                	reservationIdList.add(Integer.parseInt(lineSplit[0]));
                	reservation = new Object[lineSplit.length+1];
                    for (int i = 0; i < lineSplit.length; i++) {
                        reservation[i] = lineSplit[i];
                        if(i==1 || i==2) {
                        	Integer id = Integer.parseInt(lineSplit[i]);
                        	reservation[i] = dao.UsersDAO.getUserHash(id);
                        } 
                        if(i==7) {
                        	reservation[i] = DateDAO.DateConvert(lineSplit[i]);
                        }
                        if(i == lineSplit.length-1 || i == 5) {
                        	reservation[i] = lineSplit[i] + " $";
                        }
                        reservation[10] = RoomsDAO.roomsHash.get(Integer.parseInt(lineSplit[0]));
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