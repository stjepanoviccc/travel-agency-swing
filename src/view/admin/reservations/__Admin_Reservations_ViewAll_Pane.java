package view.admin.reservations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.DateDAO;
import dao.ReservationsDAO;
import dao.RoomsDAO;
import model.Administrator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class __Admin_Reservations_ViewAll_Pane extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel headingLabel;
	private JScrollPane scrollPane;
	private Font tableFont;
	private JButton cancelBtn;
	private JPanel btnPanel;

    /**
     * Create the panel.
     */
    public __Admin_Reservations_ViewAll_Pane() {
        setLayout(new BorderLayout());
        
        cancelBtn = new JButton("CANCEL RESERVATION");
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        Insets padding = new Insets(10, 15, 10, 15);
        cancelBtn.setMargin(padding);
        btnPanel = new JPanel();
        btnPanel.add(cancelBtn);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
        
        headingLabel = new JLabel("Admin - All Reservations");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(headingLabel, BorderLayout.NORTH);
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
        
        updateTable(model, columnNames);
        
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
        
     // button listeners
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	Object selectedState = table.getValueAt(selectedRow, 6);
                	String _state = (String) selectedState;
                	if(_state.equals("Created") || _state.equals("Completed")) {
                        Object selectedValue = table.getValueAt(selectedRow, 0);
                        String _id = (String) selectedValue;
                        int reservationId = Integer.parseInt(_id);
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the reservation?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Cancel the arr
                        	Administrator admin = new Administrator();
                        	admin.cancelReservation(ReservationsDAO.reservationsHash.get(reservationId), "Unsuccessful");
                        	updateTable(model, columnNames);
                        }
                    } 
                	else if(_state.equals("Cancelled") || _state.equals("Unsuccessful") ){
                    	JOptionPane.showMessageDialog(null, "RESERVATION IS EITHER CANCELLED OR UNSUCCESSFUL!", "Error", JOptionPane.ERROR_MESSAGE);
               } 	
               } else {
                	JOptionPane.showMessageDialog(null, "NO RESERVATION SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
               }
            }
        });
        
       
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void updateTable(DefaultTableModel model, String[] columnNames) {
    	Object[] reservation = {};
        model.setRowCount(0);
        try {
			File file = new File("src/assets/data/reservations.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");
                reservation = new Object[columnNames.length+1];
                for (int i = 0; i < lineSplit.length; i++) {
                    reservation[i] = lineSplit[i];
                    if(i==1 || i==2) {
                    	Integer id = Integer.parseInt(lineSplit[i]);
                    	reservation[i] = dao.UsersDAO.getUserHash(id);
                    } 
                    if(i == lineSplit.length-1 || i == 5) {
                    	reservation[i] = lineSplit[i] + " $";
                    }
                    if(i==7) {
                    	reservation[i] = DateDAO.DateConvert(lineSplit[i]);
                    }
                    reservation[10] = RoomsDAO.roomsHash.get(Integer.parseInt(lineSplit[0]));
                }
                model.addRow(reservation);
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}