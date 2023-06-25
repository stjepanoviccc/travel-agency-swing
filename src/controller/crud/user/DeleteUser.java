package controller.crud.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.UsersDAO;

public class DeleteUser {
	public static void deleteUser(Integer userId) {
		String path = null;
		String strId = userId.toString();
		if(strId.substring(0,1).equals("1")) { 
			path = "src/assets/data/admins.txt";
		} else if(strId.substring(0,1).equals("2")) {
			path = "src/assets/data/agents.txt";
		} else if(strId.substring(0,1).equals("3")) {
			path = "src/assets/data/tourists.txt";
		}
		String content = "";
		try {
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				String[] lineSplit = line.split("\\|");
				String linee = "";
	            for (int i = 0; i < lineSplit.length; i++) {
	                if (Integer.parseInt(lineSplit[0])==userId) {
	                    lineSplit[10] = "INACTIVE";
	                }
	                if(i<10) {
	                	linee += lineSplit[i] + "|";	                	
	                }
	            }
	            content += linee + lineSplit[10] + System.lineSeparator();
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("error "+path);
		}
		
		// now rewrite
			try {
		        File currentPathFile = new File(path);
		        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
		        writer.write(content);
		        writer.close();
		        JOptionPane.showMessageDialog(null, "User Deleted Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
		        UsersDAO.usersHashLoadAll();
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(null, "Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
