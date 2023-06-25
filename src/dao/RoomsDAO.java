package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class RoomsDAO {
	private static int arrangementId;
	private static int roomsNumber;
	private static Integer lastId;
    static String content;
    static String newContent;
	
	public static HashMap<Integer, Integer> roomsHash = new HashMap<>();
	
	public static void roomsHashLoad() throws NumberFormatException {
		try {
			File file = new File("src/assets/data/ROOMS.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineSplit = line.split("\\|");
				arrangementId = Integer.parseInt(lineSplit[0]);
				roomsNumber = Integer.parseInt(lineSplit[1]);
				roomsHash.put(arrangementId, roomsNumber);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void roomsHashWrite(Integer value, Integer id) {
		content = "";
		newContent = "";
		String fakeId = id.toString();
		String fakeSubStrId = fakeId.substring(0,1);

	    String path = "src/assets/data/ROOMS.txt";
	    try {
	        File file = new File(path);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] lineSplit = line.split("\\|");
	            content +=lineSplit[0] + "|" + lineSplit[1] + System.lineSeparator();;
	            
	            String subStrId = lineSplit[0].substring(0,1);            
	            if(subStrId.equals(fakeSubStrId)) {
	            	lastId = Integer.parseInt(lineSplit[0]);	            	
	            }
	        }
	        newContent= (lastId+1) + "|" + value + System.lineSeparator();;
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		try {
	        File file = new File("src/assets/data/ROOMS.txt");
	        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	        writer.write(content + newContent);
	        writer.close();
	        RoomsDAO.roomsHashLoad();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public static void roomsHashUpdate(Integer value, Integer id) {
		content = "";
		String idString = id.toString();
	    String path = "src/assets/data/ROOMS.txt";
	    try {
	        File file = new File(path);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	String[] lineSplit = line.split("\\|");
	        	if(idString.equals(lineSplit[0])) {
	        		content += lineSplit[0] + "|" + value + System.lineSeparator();
	        	} else {
	        		content +=lineSplit[0] + "|" + lineSplit[1] + System.lineSeparator();;	        		
	        	}
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		try {
	        File file = new File("src/assets/data/ROOMS.txt");
	        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	        writer.write(content);
	        writer.close();
	        RoomsDAO.roomsHashLoad();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "FileWriter Error!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	
	
}

