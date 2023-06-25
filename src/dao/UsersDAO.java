package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Administrator;
import model.ENUM_Gender;
import model.ENUM_Role;
import model.Tourist;
import model.TouristAgent;

public class UsersDAO {
	// for update and del
	
	public static HashMap<Integer, Administrator> adminHash = new HashMap<>();
	public static HashMap<Integer, TouristAgent> agentHash = new HashMap<>();
	public static HashMap<Integer, Tourist> touristHash = new HashMap<>();
	
	// these 2 hashes are based on ID and USERNAME! // for reading;
	public static HashMap<Integer, String> usersHash = new HashMap<>();
	public static HashMap<String, Integer> reverseUsersHash = new HashMap<>();
	public static List<String> onlyAgents = new ArrayList<>();
	public static List<String> onlyTourists = new ArrayList<>();
//	public static ArrayList<Arrangement> arrayOfArrangements = new ArrayList<>();
	
	// loading hash after success login
	public static String usersHashLoadAll() {
		adminHash.clear();
		agentHash.clear();
		touristHash.clear();
		usersHash.clear();
		reverseUsersHash.clear();
		onlyAgents.clear();
		onlyTourists.clear();
	    String[] fileNames = {"src/assets/data/admins.txt", "src/assets/data/tourists.txt", "src/assets/data/agents.txt"};
	    for (String fileName : fileNames) {
	        try {
	            File file = new File(fileName);
	            BufferedReader reader = new BufferedReader(new FileReader(file));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] lineSplit = line.split("\\|");

	        		Administrator admin = new Administrator();
	        		TouristAgent agent = new TouristAgent();
	        		Tourist tourist = new Tourist();
	                String _id = lineSplit[0];
	                String _firstName = lineSplit[1];
	                String _lastName = lineSplit[2];
	                String _JMBG = lineSplit[3];
	                String  _address = lineSplit[4];
	                String _phoneNum = lineSplit[5];
	                String _username = lineSplit[6];
	                String _password = lineSplit[7];
	                String _gender = lineSplit[8];
	                String _role = lineSplit[9];	                
	                int phoneNum = Integer.parseInt(_phoneNum);
	                ENUM_Gender gender = ENUM_Gender.valueOf(_gender);
	                _role = EnumDAO.modifyENUMTypeName(_role);
	                ENUM_Role role = ENUM_Role.valueOf(_role);
	                Integer hashId = Integer.parseInt(_id);
	                
	                usersHash.put(hashId, _username);
	                reverseUsersHash.put(_username, hashId);
	                if(lineSplit[9].equals("Tourist agent") && lineSplit[10].equals("ACTIVE")) {
	                	onlyAgents.add(_username);
	                } else if(lineSplit[9].equals("Tourist") && lineSplit[10].equals("ACTIVE")) {
	                	onlyTourists.add(_username);
	                }
	                	
	                // global FULL-TYPE hashes
	                if(_role.equals("Administrator")) {
	                	admin.setId(hashId);
	                	admin.setName(_firstName);
	                	admin.setSurname(_lastName);
	                	admin.setJMBG(_JMBG);
	                	admin.setAddress(_address);
	                	admin.setPhone(phoneNum);
	                	admin.setUsername(_username);
	                	admin.setPassword(_password);
	                	admin.setGender(gender);
	                	admin.setRole(role);
	                	adminHash.put(hashId, admin);
	                }if(_role.equals("Tourist_agent")) {
	                	agent.setId(hashId);
	                	agent.setName(_firstName);
	                	agent.setSurname(_lastName);
	                	agent.setJMBG(_JMBG);
	                	agent.setAddress(_address);
	                	agent.setPhone(phoneNum);
	                	agent.setUsername(_username);
	                	agent.setPassword(_password);
	                	agent.setGender(gender);
	                	agent.setRole(role);
	                	agentHash.put(hashId, agent);
	                } if(_role.equals("Tourist")) {
	                	tourist.setId(hashId);
	                	tourist.setName(_firstName);
	                	tourist.setSurname(_lastName);
	                	tourist.setJMBG(_JMBG);
	                	tourist.setAddress(_address);
	                	tourist.setPhone(phoneNum);
	                	tourist.setUsername(_username);
	                	tourist.setPassword(_password);
	                	tourist.setGender(gender);
	                	tourist.setRole(role);
	                	touristHash.put(hashId, tourist);
	                }
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	// implemented to avoid nesting while reading from files - only administrator can see all IDs, rest shuoldn't see ID's
	public static String getUserHash(Integer id) {
		return usersHash.get(id);
	}  
	public static Integer getReverseUserHash(Object userInput) {
		return reverseUsersHash.get(userInput);
	}
	public static List<String> getOnlyAgents() {
		return onlyAgents;
	}	
	public static List<String> getOnlyTourists() {
		return onlyTourists;
	}
}
