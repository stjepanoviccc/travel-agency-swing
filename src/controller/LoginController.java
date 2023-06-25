package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import dao.UsersDAO;
import view.AdminMain_Frame;
import view.AgentMain_Frame;
import view.Login_Frame;
import view.TouristMain_Frame;

public class LoginController {
	public static String login(String username, String password) {
	    String[] fileNames = {"src/assets/data/admins.txt", "src/assets/data/tourists.txt", "src/assets/data/agents.txt"};
	    for (String fileName : fileNames) {
	        try {
	            File file = new File(fileName);
	            BufferedReader reader = new BufferedReader(new FileReader(file));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] lineSplit = line.split("\\|");
	                // IMPORTANT: *** ID IS ACTUALLY INTEGER BUT JUST MANIPULATED AS STRING WHILE READ FROM FILE! ***
	                String _id = lineSplit[0];
	                String _username = lineSplit[6];
	                String _password = lineSplit[7];
	                
	                if (_username.equals(username) && _password.equals(password)) {
	                	if(lineSplit[10].equals("ACTIVE")) {
	                		reader.close();
	                		return _id;	                		
	                	}
	                }
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	public static void tryLogin() {
		Login_Frame loginGUI = new Login_Frame();
        loginGUI.setVisible(true);
	}
	
    public static void checkRole(String loginId, String username) {
    	dao.UsersDAO.usersHashLoadAll();
    	dao.ArrangementsDAO.arrangementsHashLoad();
    	System.out.println(UsersDAO.adminHash);
    	String role = loginId.substring(0,1);
        // pass role to decide what to show.
    	// IMPORTANT NOTE: id's are stored as integer but read as string, prefix '1' is for administrator(111111+), '2' for agent(222221+), '3' four tourist(333331+)
        if (role.equals("1")) {
        	AdminMain_Frame adminGUI = new AdminMain_Frame(loginId, username);
            adminGUI.setVisible(true);           
        }
        if (role.equals("2")) {
        	AgentMain_Frame agentGUI = new AgentMain_Frame(loginId, username);
        	agentGUI.setVisible(true);
        }
        if (role.equals("3")) {
        	TouristMain_Frame touristGUI = new TouristMain_Frame(loginId, username);
        	touristGUI.setVisible(true);
        }
    }
}