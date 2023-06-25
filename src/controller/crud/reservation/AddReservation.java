package controller.crud.reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import controller.ReadFile;
import dao.ArrangementsDAO;
import dao.ReservationsDAO;
import dao.RoomsDAO;
import dao.UsersDAO;
import model.Arrangement;
import model.Reservation;

public class AddReservation {
	public static void addReservation(Reservation res, String state) {
		Arrangement ar = ArrangementsDAO.arrangementsHash.get(res.getArrangementId());
		float fullPrice = res.calculateThePrice(res.getPricePerDayPerPerson(), res.getNumberOfPassengers(), res.getNumberOfDays(), ar.getFairDiscount());
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to proceed? Price is "+fullPrice+"$", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			
		    // User clicked Yes
			Integer lastID = null;
		    String path = "src/assets/data/reservations.txt";
		    try {
		        File file = new File(path);
		        BufferedReader reader = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = reader.readLine()) != null) {
		            String[] lineSplit = line.split("\\|");
		            for (int i = 0; i < lineSplit.length; i++) {
		                if (i == 0) {
		                    lastID = Integer.parseInt(lineSplit[i]);
		                }
		            }
		        }
		        // last ID is added by +1;
		        lastID += 1;
		        res.setId(lastID);
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		    String prevContent = ReadFile.readFile(path);
		    // now send to file
		    Integer agentID = UsersDAO.getReverseUserHash(res.getReservationSeller().getUsername());
		    Integer touristID = UsersDAO.getReverseUserHash(res.getReservationCustomer().getUsername());
		    String newContent = lastID + "|" + touristID + "|" + agentID + "|" + res.getNumberOfPassengers() + "|" + res.getNumberOfDays() + "|" + res.getPricePerDayPerPerson() + "|" 
		    		+ state + "|" + res.getDate() + "|" + res.getArrangementId() + "|"  + res.calculateThePrice(res.getPricePerDayPerPerson(), res.getNumberOfPassengers(), res.getNumberOfDays(), ar.getFairDiscount());
		    try {
		    	//
		    	int totalRooms = RoomsDAO.roomsHash.get(res.getArrangementId());
		    	if(res.getNumberOfRooms() > totalRooms) {
		    		JOptionPane.showMessageDialog(null, "There is not that much of available rooms! Available number is "+totalRooms+".", "Validation Error", JOptionPane.ERROR_MESSAGE);
		    		return;
		    	}else {
		    		int newRoomsValue = totalRooms - res.getNumberOfRooms();
		    		RoomsDAO.roomsHashUpdate(newRoomsValue, res.getArrangementId());
		    	}
		        File currentPathFile = new File(path);
		        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
		        writer.write(prevContent + newContent);
		        writer.close();
		        JOptionPane.showMessageDialog(null, "Reservation Created Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
		        RoomsDAO.roomsHashWrite(res.getNumberOfRooms(), res.getId());
		        RoomsDAO.roomsHashLoad();
		        
		        String prev_list = ar.getReservations();
		        if(prev_list.equals("/")) {
		        	prev_list = ""+res.getId()+"";
		        }else {
		        	prev_list += ","+res.getId()+"";
		        }
		        ar.setReservations(prev_list);
		        controller.crud.arrangement.EditArrangement.editArrangement(ar);
		        ArrangementsDAO.arrangementsHashLoad();
		        ReservationsDAO.reservationsHashLoad();
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    // User clicked No or closed the dialog
			JOptionPane.showMessageDialog(null, "Creation of Reservation cancelled!", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
