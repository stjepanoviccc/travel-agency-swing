package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import model.Arrangement;
import model.ENUM_ArrangementType;
import model.ENUM_TypeOfAccomodation;
import model.ENUM_TypeOfRoom;
import model.TouristAgent;

public class ArrangementsDAO {
	public static HashMap<Integer, Arrangement> arrangementsHash = new HashMap<>();
	public static ArrayList<Integer> arrangementsArray = new ArrayList<>();
	
	public static void arrangementsHashLoad() throws NumberFormatException {
		
		try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");                
                Arrangement arr = new Arrangement();
                TouristAgent agent = new TouristAgent();
            	arr.setId(Integer.parseInt(lineSplit[0]));
            	agent.setId(Integer.parseInt(lineSplit[1]));
            	agent.setUsername(UsersDAO.agentHash.get(agent.getId()).getUsername());
            	arr.setSeller(agent);
            	ENUM_ArrangementType arrangementType = ENUM_ArrangementType.valueOf(EnumDAO.modifyENUMTypeName(lineSplit[2]));
            	ENUM_TypeOfAccomodation accomodationType = ENUM_TypeOfAccomodation.valueOf(lineSplit[6]);
            	arr.setTypeOfArrangement(arrangementType);
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            	LocalDateTime dateTime = LocalDateTime.parse(lineSplit[4], formatter);
            	arr.setAvailableDate(dateTime);
            	arr.setCapacity(Integer.parseInt(lineSplit[5]));
            	arr.setTypeOfAccomodation(accomodationType);
            	arr.setPricePerPerson(Float.parseFloat(lineSplit[7]));
            	arr.setFairDiscount(Float.parseFloat(lineSplit[8]));
            	arr.setReservations(lineSplit[9]);
            	ENUM_TypeOfRoom roomType = ENUM_TypeOfRoom.valueOf(EnumDAO.modifyENUMTypeName(lineSplit[10]));
            	arr.setTypeOfRoom(roomType);
            	
            	arrangementsArray.add(Integer.parseInt(lineSplit[0]));
            	arrangementsHash.put(Integer.parseInt(lineSplit[0]), arr);
            	
            	try {
					arrangementsHash.put(Integer.parseInt(lineSplit[0]), arr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
                for (int i = 0; i < lineSplit.length; i++) {
                    if(i==3) {
                    	File imageFile = new File(lineSplit[3]);
                    	arr.setImage(imageFile.getName());
                    }
                }
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> getSpecificArrangements(String loginId, ArrayList<Integer> arrangementIdList) {
		try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");                
                var active_or_inactive = lineSplit[11];
                if(lineSplit[1].equals(loginId) && active_or_inactive.equals("ACTIVE")) {
                	arrangementIdList.add(Integer.parseInt(lineSplit[0]));
                }         
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrangementIdList;
	}
	
	public static int findLastId() {
		int id = 0;
		try {
			File file = new File("src/assets/data/arrangements.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");                
                id = Integer.parseInt(lineSplit[0]);
            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		id +=1;
		return id;
	}
}