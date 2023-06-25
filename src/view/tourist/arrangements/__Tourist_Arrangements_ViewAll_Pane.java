package view.tourist.arrangements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.DateDAO;
import dao.RoomsDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class __Tourist_Arrangements_ViewAll_Pane extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel headingLabel;
	private JScrollPane scrollPane;
	private Font tableFont;
	private JButton refreshBtn;
	private JButton checkImageBtn;
	private JPanel btnPanel;
	private Font font;

    /**
     * Create the panel.
     */
    public __Tourist_Arrangements_ViewAll_Pane() {
        setLayout(new BorderLayout());
        font = new Font("SansSerif", Font.PLAIN, 20);
        
        headingLabel = new JLabel("Tourist - All Arrangements");
        headingLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        refreshBtn = new JButton("REFRESH");
        checkImageBtn = new JButton("SEE IMAGE");
        btnPanel = new JPanel();
        btnPanel.add(refreshBtn);
        btnPanel.add(checkImageBtn);
        refreshBtn.setFont(font);
        checkImageBtn.setFont(font);
        Insets padding = new Insets(10, 15, 10, 15);
        refreshBtn.setMargin(padding);
        checkImageBtn.setMargin(padding);
        this.add(btnPanel, BorderLayout.SOUTH);
        int topMargin = 30;
        int leftMargin = 10;
        int bottomMargin = 110;
        int rightMargin = 10;
        btnPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
        
        add(headingLabel, BorderLayout.NORTH);
        // Create a table with ten columns
        String[] columnNames = {"ID", "Agent", "Arrangement Type", "Image", "Available Date", "Capacity", "Type of Accomodation", "Price per Person", "Fair discount", "Reservation List", "Available Rooms"};
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
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
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
        
        refreshBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                updateTable(model, columnNames);
            }
        });
        
        checkImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object selectedValue = table.getValueAt(selectedRow, 3);
                    String imgPath = (String) selectedValue;
                    IMAGE image = new IMAGE();
                    image.show(imgPath);
                } else {
                	JOptionPane.showMessageDialog(null, "PLEASE SELECT ARRANGEMENT TO SEE IMAGE :)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    /* 
     * Icon icon = new ImageIcon("path/to/your/image.png");
        JLabel label = new JLabel(icon);
     */
    private void updateTable(DefaultTableModel model, String[] columnNames) {
    	model.setRowCount(0);
        try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			Object[] arrangement = {};    
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");
                arrangement = new Object[lineSplit.length+1];
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
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
