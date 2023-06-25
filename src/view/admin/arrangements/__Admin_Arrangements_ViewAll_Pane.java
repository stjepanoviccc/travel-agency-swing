package view.admin.arrangements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.DateDAO;
import dao.RoomsDAO;
import model.Administrator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class __Admin_Arrangements_ViewAll_Pane extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JLabel headingLabel;
	private JScrollPane scrollPane;
	private Font tableFont;
	private Font font;
	private JButton createBtn;
	private JButton editBtn;
	private JButton delBtn;
	private JButton refreshBtn;
	private JPanel btnPanel;
    /**
     *
     */
    public __Admin_Arrangements_ViewAll_Pane() {
        setLayout(new BorderLayout());
        font = new Font("SansSerif", Font.PLAIN, 20);
        headingLabel = new JLabel("Admin - All Arrangements");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(headingLabel, BorderLayout.NORTH);
        createBtn = new JButton("CREATE");
        editBtn = new JButton("UPDATE");
        delBtn = new JButton("DELETE");
        refreshBtn = new JButton("REFRESH");
        btnPanel = new JPanel();
        btnPanel.add(createBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(refreshBtn);
        editBtn.setFont(font);
        delBtn.setFont(font);
        createBtn.setFont(font);
        refreshBtn.setFont(font);
        delBtn.setBackground(Color.RED);
        delBtn.setForeground(Color.WHITE);
        Insets padding = new Insets(10, 15, 10, 15);
        editBtn.setMargin(padding);
        delBtn.setMargin(padding);
        createBtn.setMargin(padding);
        refreshBtn.setMargin(padding);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
        
        // Create a table with ten columns
        String[] columnNames = {"ID", "Agent", "Arrangement Type", "Image", "Available Date", "Capacity", "Type of Accomodation", "Price per Person", "Fair discount", "Reservation List", "Available Rooms"};
        Object[][] data = {};
        /*
         * 
         */
        
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
                    int arrangementId = Integer.parseInt(_id);
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the arrangement?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Delete the user
                    	Administrator admin = new Administrator();
                    	admin.deleteArrangement(arrangementId);
                        model.removeRow(selectedRow);
                    }
                } else {
                	JOptionPane.showMessageDialog(null, "NO ARRANGEMENT SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
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
                    int arrangementId = Integer.parseInt(_id);
                    __Admin_Arrangements_EditArrangement_Dialog editDialog = new __Admin_Arrangements_EditArrangement_Dialog();
                    editDialog.editArrangement(arrangementId);
                    updateTableData(model);
                    
                } else {	
                	JOptionPane.showMessageDialog(null, "NO ARRANGEMENT SELECTED!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create button clicked
                __Admin_Arrangements_CreateNew_Dialog registrationForm = new __Admin_Arrangements_CreateNew_Dialog();
                registrationForm.addArrangement();
                // Update the table data
                updateTableData(model);
            }
        });
        
        refreshBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                updateTableData(model);
            }
        });
        
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
	private void updateTableData(DefaultTableModel model) {
		model.setRowCount(0);
		try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");                
                Object[] arrangement = new Object[lineSplit.length+1];
                var active_or_inactive = lineSplit[11];
                if(active_or_inactive.equals("ACTIVE")) {
                	for (int i = 0; i < lineSplit.length; i++) {               	
                        arrangement[i] = lineSplit[i];
                        if(i==1) {
                        	Integer id = Integer.parseInt(lineSplit[i]);
                        	arrangement[i] = dao.UsersDAO.getUserHash(id);
                        }
                        if(i==3) {
                        	File imageFile = new File(lineSplit[3]);
                        	arrangement[i] = imageFile.getName();
                        }
                        if(i==4) {
                        	arrangement[i] = DateDAO.DateConvert(lineSplit[4]);
                        }
                        if(i==7) {
                        	arrangement[i] = lineSplit[i] + " $";
                        }
                        if(i==8) {
                        	arrangement[i] = lineSplit[i] + " %";
                        }
                        arrangement[10] = RoomsDAO.roomsHash.get(Integer.parseInt(lineSplit[0]));
                    }            
                    model.addRow(arrangement);	
                }              
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}