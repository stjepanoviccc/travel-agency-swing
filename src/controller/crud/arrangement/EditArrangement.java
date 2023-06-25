package controller.crud.arrangement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.ArrangementsDAO;
import dao.UsersDAO;
import model.Arrangement;

public class EditArrangement {
	public static void editArrangement(Arrangement arr) {
		Integer id = arr.getId();
	    String path = "src/assets/data/arrangements.txt";
	    String newContent = "";
	    System.out.println(arr.getAvailableDate());
	    try {
	    	String line;
	        File file = new File(path);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        while ((line = reader.readLine()) != null) {
	            String[] lineSplit = line.split("\\|");
	            int i = Integer.parseInt(lineSplit[0]);
	                if (i == id) {
	                	String modifiedArrangementTypeName = arr.getTypeOfArrangement().getModifiedName();
	            	    arr.setImage("C:\\\\Users\\\\WIN\\\\Desktop\\\\IMAGES\\\\" + arr.getImage());
	            	    Integer agentID = UsersDAO.getReverseUserHash(arr.getSeller().getUsername());
	            	    String reservations = arr.getReservations();
	            	    if(reservations.length() > 5) {
	            	    	reservations = reservations.substring(1, reservations.length());
	            	    }
	            	    //lineSplit[4] is date.
	            	    String newCurrentContent = id + "|" + agentID + "|" + modifiedArrangementTypeName + "|" + arr.getImage() + "|" + lineSplit[4] + "|" + arr.getCapacity() + "|" 
	            	    		+ arr.getTypeOfAccomodation() + "|" + arr.getPricePerPerson() + "|" + arr.getFairDiscount() + "|" + arr.getReservations() + "|" + arr.getTypeOfRoom() + "|" + "ACTIVE";
	            	    newContent += newCurrentContent + System.lineSeparator();;
	                } else {
	                	newContent += line + System.lineSeparator();;
	                }
	            i++;
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // rewrite
	    try {
	        File currentPathFile = new File(path);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
	        writer.write(newContent);
	        writer.close();
	        JOptionPane.showMessageDialog(null, "Arrangement Updated Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
	        ArrangementsDAO.arrangementsHashLoad();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
