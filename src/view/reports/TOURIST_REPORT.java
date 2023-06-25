package view.reports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TOURIST_REPORT {
	private Font font;
	private Font tableFont;
	private JDialog dialog;
	private JPanel contentPanel; 
	private JTable table;
	private float totalPrice;
	private JLabel headingLabel;
	private JLabel totalPriceLabel;
	
	public void report(String loginId) {
		font = new Font("SansSerif", Font.PLAIN, 30);
		headingLabel = new JLabel("EXPENSES REPORT");
        headingLabel.setFont(font);
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
		dialog = new JDialog();
        dialog.setTitle("Tourist - All Time Expenses Report");
        dialog.setSize(600, 750);
        dialog.setMinimumSize(new Dimension(dialog.getWidth(), 750));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(true);
        contentPanel = new JPanel(new BorderLayout());
        dialog.setContentPane(contentPanel);        
        contentPanel.add(headingLabel, BorderLayout.NORTH);
           
        String[] columnNames = {"ID", "Full Price"};
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
        
        loadTable(model, loginId, totalPrice);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedTotalPrice = decimalFormat.format(totalPrice);

        totalPriceLabel = new JLabel("Total: "+formattedTotalPrice+" $");
        totalPriceLabel.setFont(font);
        totalPriceLabel.setHorizontalAlignment(JLabel.CENTER);
        totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        contentPanel.add(totalPriceLabel, BorderLayout.SOUTH);
         	
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
        
        contentPanel.add(table, BorderLayout.CENTER);       
	    dialog.setVisible(true);
	 }
	
	// table loading
	private void loadTable(DefaultTableModel model, String loginId, float totalPrice) {
		model.setRowCount(0);
		try {
    		File file = new File("src/assets/data/reservations.txt");
    		BufferedReader reader = new BufferedReader(new FileReader(file));
    		String line;
               while ((line = reader.readLine()) != null) {
                   String[] lineSplit = line.split("\\|");
                   Object[] reservation = {};
                   if(lineSplit[1].equals(loginId) && lineSplit[6].equals("Completed")) {
                   	   reservation = new Object[lineSplit.length+1];    	
                       reservation[0] = "RESERVATION: "+lineSplit[0];
                       reservation[1] = "PAID: "+lineSplit[9] + " $";
                       this.totalPrice += Float.parseFloat(lineSplit[9]);
                       model.addRow(reservation);	
                   }          
                }
              reader.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}
