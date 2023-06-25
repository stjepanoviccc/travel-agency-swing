package view.tourist.reservations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Tourist;

public class __Tourist_Reservations_EditReservation_Dialog {
    private JLabel numberOfPassengersLabel;
    private JTextField numberOfPassengersField;
    private JLabel reservationIdLabel;
    private JDialog dialog;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JButton editButton;
    private Font font;
    
	public void editReservation(Integer reservationId) {
		font = new Font("SansSerif", Font.PLAIN, 20);
        
        dialog = new JDialog();
        dialog.setTitle("Tourist - Edit Reservation");
        dialog.setSize(400, 350);
        dialog.setMinimumSize(new Dimension(dialog.getWidth(), 350));
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
        
        reservationIdLabel = new JLabel("Reservation ID: ");
        numberOfPassengersLabel = new JLabel("Number of passengers: ");
        numberOfPassengersField = new JTextField(6);
        reservationIdLabel.setFont(font);
        numberOfPassengersLabel.setFont(font);
        numberOfPassengersField.setFont(font);
        JComboBox<String> resIdBox = new JComboBox();
        resIdBox.addItem(reservationId.toString());
        resIdBox.setEnabled(false);
        resIdBox.setFont(font);
        
        // style
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(reservationIdLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(resIdBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(numberOfPassengersLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(numberOfPassengersField, gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        editButton = new JButton("Update");
        Insets margin = new Insets(10,30,10,30);
        editButton.setMargin(margin);
        editButton.setFont(font);   
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        buttonPanel.add(editButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);		
        
        // add action listener to login button
        editButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				int passengers = Integer.parseInt(numberOfPassengersField.getText());
				if(passengers < 0) {
					return;
				}
				Tourist tourist = new Tourist();
				tourist.editReservation(reservationId, passengers);
            }
        });
        dialog.setVisible(true);
    
	}
}
