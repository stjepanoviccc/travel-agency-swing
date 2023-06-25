package controller.crud.reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.ArrangementsDAO;
import dao.ReservationsDAO;
import model.Arrangement;
import model.Reservation;

// only tourist can edit number of PASSENGERS!
public class EditReservation {
	public static void editReservation(Integer id, int passengersNum) {
		Reservation res = ReservationsDAO.reservationsHash.get(id);
		Arrangement ar = ArrangementsDAO.arrangementsHash.get(res.getArrangementId());
		Integer currId;
		String content="";
	    String path = "src/assets/data/reservations.txt";
	    float fullPrice;
	    int daysNum;
	    float pricePerPerson;
	    try {
	        File file = new File(path);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] lineSplit = line.split("\\|");
	            for (int i = 0; i < lineSplit.length; i++) {
	            	currId = Integer.parseInt(lineSplit[0]);
	            	if(id.equals(currId)){
	            		fullPrice = Float.parseFloat(lineSplit[9]);
	            		daysNum = Integer.parseInt(lineSplit[4]);
	            		pricePerPerson = Float.parseFloat(lineSplit[5]);
	            		fullPrice = res.calculateThePrice(pricePerPerson, daysNum, passengersNum, ar.getFairDiscount());
	            		lineSplit[3] = ""+passengersNum+"";
	            		lineSplit[9] = ""+fullPrice+"";
	            	}
	                if(i < lineSplit.length-1) {
	                	content += lineSplit[i] + "|";		                	
	                } else {
	                	content += lineSplit[i] + System.lineSeparator();;
	                }  
	            }
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // rewrite
	    try {
	        File currentPathFile = new File(path);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
	        writer.write(content);
	        writer.close();
	        JOptionPane.showMessageDialog(null, "Reservation Updated Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
	        ReservationsDAO.reservationsHashLoad();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
