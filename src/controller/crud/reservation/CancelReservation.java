package controller.crud.reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.ReservationsDAO;
import dao.RoomsDAO;
import model.Reservation;

public class CancelReservation {
	public static void cancelReservation(Reservation res, String state) {
		 String content = "";
		 String path = "src/assets/data/reservations.txt";
		 
		    try {
		        File file = new File(path);
		        BufferedReader reader = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = reader.readLine()) != null) {
		            String[] lineSplit = line.split("\\|");
		            for (int i = 0; i < lineSplit.length; i++) {
		            	Integer id = Integer.parseInt(lineSplit[0]);
		            	if(id.equals(res.getId())){
		            		lineSplit[6] = ""+state+"";
		            	}
		                if(i < lineSplit.length-1) {
		                	content += lineSplit[i] + "|";		                	
		                } else {
		                	content += lineSplit[i] + System.lineSeparator();;
		                }  
		            }
		        }
		        System.out.println(content);
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		    // rewrite
		    try {
		    	// update rooms after cancel
		    	int getRooms = res.getNumberOfRooms();
		    	int totalRooms = RoomsDAO.roomsHash.get(res.getArrangementId());
		    	totalRooms += getRooms;
		    	RoomsDAO.roomsHashUpdate(totalRooms, res.getArrangementId());
		    	
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