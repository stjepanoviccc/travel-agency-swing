package controller.crud.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import dao.UsersDAO;

public class EditUser {
	private static String input1;
	private static String input2;
	private static String input3;
	private static String input4;
	private static String input5;
	private static String input6;
	private static String input7;
	private static String input8;
	private static String input9;
	private static String newLine;
	public static void editUser(Object[] userInput, Integer userId) {
		input1 = userInput[0].toString();
		input2 = userInput[1].toString();
		input3 = userInput[2].toString();
		input4 = userInput[3].toString();
		input5 = userInput[4].toString();
		input6 = userInput[5].toString();
		input7 = userInput[6].toString();
		input8 = userInput[7].toString();
		input9 = userInput[8].toString();
		newLine = userId.toString() + "|" + input1 + "|" + input2 + "|" + input3 + "|" + input4 + "|" + input5 + "|" + input6 + "|" + input7 + "|" + input8 + "|" + input9 +"|ACTIVE";
		
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
				if(lineSplit[0].equals(userId.toString())) {
					line = newLine;
				}
	            content += line + System.lineSeparator();
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
			JOptionPane.showMessageDialog(null, "User Updated Successfully!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
			UsersDAO.usersHashLoadAll();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error!", "FileWriter Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
