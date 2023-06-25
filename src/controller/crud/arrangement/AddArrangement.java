package controller.crud.arrangement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.ReadFile;
import dao.ArrangementsDAO;
import dao.UsersDAO;
import model.Arrangement;

public class AddArrangement {
	public static boolean addArrangement_VALIDATE(Arrangement arr, int rooms) {
		String browseButtonResult = arr.getImage();
		Integer capacity = arr.getCapacity();
		Float price = arr.getPricePerPerson();
		Float discount = arr.getFairDiscount();
		
		if(browseButtonResult.equals("Browse image")) {
			JOptionPane.showMessageDialog(null, "Please browse an image for arrangement", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;	
		} else if(capacity<0) {
			JOptionPane.showMessageDialog(null, "Please be more precious with capacity", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
		}else if(price<0) {
			JOptionPane.showMessageDialog(null, "Please be more precious with price", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
		} else if(discount<0 || discount>100) {
			JOptionPane.showMessageDialog(null, "Please be more precious with fair discount", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if(rooms<0) {
			JOptionPane.showMessageDialog(null, "Please be more precious with number of rooms", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;	
	}
	
	public static ActionListener BrowseImage(JDialog dialog, JButton browseButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File initDir = new File("C:\\\\Users\\\\WIN\\\\Desktop\\\\IMAGES\\\\");
                fileChooser.setCurrentDirectory(initDir);
                int result = fileChooser.showOpenDialog(dialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    String[] lineSplit = filePath.split("\\\\");
                    String line = null;
                    for(var i=0; i<lineSplit.length; i++) {
                    	if(i== lineSplit.length-1) {
                    		line = lineSplit[i];
                    	}
                    }
                    browseButton.setText(line);
                }
            }          
        };
    }
	
	public static void createArrangement(Arrangement arr) {
		Integer lastID = null;
	    String path = "src/assets/data/arrangements.txt";
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
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
		String modifiedArrangementTypeName = arr.getTypeOfArrangement().getModifiedName();
	    String prevContent = ReadFile.readFile(path);
	    // now send to file
	    arr.setImage("C:\\\\Users\\\\WIN\\\\Desktop\\\\IMAGES\\\\" + arr.getImage());
	    Integer agentID = UsersDAO.getReverseUserHash(arr.getSeller().getUsername());
	    String newContent = lastID + "|" + agentID + "|" + modifiedArrangementTypeName + "|" + arr.getImage() + "|" + arr.getAvailableDate() + "|" + arr.getCapacity() + "|" 
	    		+ arr.getTypeOfAccomodation() + "|" + arr.getPricePerPerson() + "|" + arr.getFairDiscount() + "|" + "/" + "|" + arr.getTypeOfRoom() + "|" + "ACTIVE";
	    try {
	        File currentPathFile = new File(path);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
	        writer.write(prevContent + newContent);
	        writer.close();
	        JOptionPane.showMessageDialog(null, "Arrangement Created Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
	        ArrangementsDAO.arrangementsHashLoad();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
}
