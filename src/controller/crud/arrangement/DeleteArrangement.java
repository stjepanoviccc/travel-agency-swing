package controller.crud.arrangement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.ArrangementsDAO;

public class DeleteArrangement {
	public static void deleteArrangement(Integer userId) {
		String content = "";
		try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				String[] lineSplit = line.split("\\|");
				String linee = "";
	            for (int i = 0; i < lineSplit.length; i++) {
	                if (Integer.parseInt(lineSplit[0])==userId) {
	                    lineSplit[11] = "INACTIVE";
	                }
	                if(i<11) {
	                	linee += lineSplit[i] + "|";	                	
	                }
	            }
	            content += linee + lineSplit[11] + System.lineSeparator();
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("error "+"src/assets/data/arrangements.txt");
		}
		
		// now rewrite
			try {
		        File currentPathFile = new File("src/assets/data/arrangements.txt");
		        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
		        writer.write(content);
		        writer.close();
		        JOptionPane.showMessageDialog(null, "Arrangement Deleted Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
		        ArrangementsDAO.arrangementsHashLoad();
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(null, "Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
