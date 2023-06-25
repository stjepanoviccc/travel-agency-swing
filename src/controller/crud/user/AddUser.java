package controller.crud.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import controller.ReadFile;
import dao.UsersDAO;

public class AddUser {
	public static boolean addUser_VALIDATE(Object[] userInput) {
		String firstName = (String) userInput[0];
        String lastName = (String) userInput[1];
        String JMBG = (String) userInput[2];
        String address = (String) userInput[3];
        String phoneNumber = (String) userInput[4];
        String username = (String) userInput[5];
        String password = (String) userInput[6];

        if(firstName.length()<2 || firstName.length()>20) {
        	JOptionPane.showMessageDialog(null, "First name must be between 2 and 20 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;	
        } else if (!Character.isUpperCase(firstName.charAt(0))) {
            JOptionPane.showMessageDialog(null, "First name must start with an uppercase letter", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(lastName.length()<2 || lastName.length()>20) {
        	JOptionPane.showMessageDialog(null, "Last name must be between 2 and 20 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;	
        } else if (!Character.isUpperCase(lastName.charAt(0))) {
            JOptionPane.showMessageDialog(null, "Last name must start with an uppercase letter", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(JMBG.length()!=13) {
        	JOptionPane.showMessageDialog(null, "JMBG must be equal to 13 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        } else if(address.length()<5 || address.length()>50) {
        	JOptionPane.showMessageDialog(null, "Address must be between 5 and 20 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        } else if (!Character.isUpperCase(address.charAt(0))) {
            JOptionPane.showMessageDialog(null, "Address must start with an uppercase letter", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(phoneNumber.length()<9 || phoneNumber.length()>10) {
        	JOptionPane.showMessageDialog(null, "Phone number must be between 9 to 10 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        } else if(username.length()<2 || username.length()>20) {	
        	JOptionPane.showMessageDialog(null, "Username must be between 2 and 20 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        } else if(password.length()<5 || password.length()>20) {
        	JOptionPane.showMessageDialog(null, "Password must be between 5 and 20 characters length", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
		return true;		
	}
	
	public static boolean addUser_VALIDATE_2(Object[] userInput) {
	    // userInput[8] = ROLE!
		String JMBG = (String) userInput[2];
		String phone = (String) userInput[4];
		String username = (String) userInput[5];
		String[] fileNames = {"src/assets/data/admins.txt", "src/assets/data/agents.txt", "src/assets/data/tourists.txt"};
	    for (String fileName : fileNames) {
	        try {
	            File file = new File(fileName);
	            BufferedReader reader = new BufferedReader(new FileReader(file));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] lineSplit = line.split("\\|");
	                if(lineSplit[3].equals(JMBG)) {
	                	JOptionPane.showMessageDialog(null, "JMBG already exist!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	                	return false;
	                } else if(lineSplit[5].equals(phone)) {
	                	JOptionPane.showMessageDialog(null, "Phone already exist!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	                	return false;
	                }else if(lineSplit[6].equals(username)) {
	                	JOptionPane.showMessageDialog(null, "Username already exist!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	                	return false;
	                }
	            }    
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		return true;
	}
	
	public static void addUser(Object[] userInput) {
		Integer lastID = null;
	    String path = null;
	    try {
	        File file = null;
	        if ("Administrator".equals(userInput[8])) {
	            file = new File("src/assets/data/admins.txt");
	            path = "src/assets/data/admins.txt";
	        } else if ("Tourist agent".equals(userInput[8])) {
	            file = new File("src/assets/data/agents.txt");
	            path = "src/assets/data/agents.txt";
	        } else if ("Tourist".equals(userInput[8])) {
	            file = new File("src/assets/data/tourists.txt");
	            path = "src/assets/data/tourists.txt";
	        }
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

	    // now send to file
	    String prevContent = ReadFile.readFile(path);
	    String newContent = lastID + "|" + userInput[0] + "|" + userInput[1] + "|" + userInput[2] + "|" + userInput[3] + "|" + userInput[4] + "|" + userInput[5] + "|" + userInput[6]
	            + "|" + userInput[7] + "|" + userInput[8] + "|" + "ACTIVE";
	    try {
	        File currentPathFile = new File(path);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(currentPathFile));
	        writer.write(prevContent + newContent);
	        writer.close();
	        JOptionPane.showMessageDialog(null, "User Added Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
	        UsersDAO.usersHashLoadAll();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
