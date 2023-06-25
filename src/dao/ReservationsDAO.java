package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import model.ENUM_ReservationStatus;
import model.Reservation;
import model.Tourist;
import model.TouristAgent;

public class ReservationsDAO {
	public static HashMap<Integer, Reservation> reservationsHash = new HashMap<>();
	
	public static HashMap<Integer, Reservation> reservationsHashLoad() throws NumberFormatException {
		
		try {
			File file = new File("src/assets/data/reservations.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\\|");                
                Integer resId = Integer.parseInt(lineSplit[0]);
                Integer touristId = Integer.parseInt(lineSplit[1]);
                Integer agentId = Integer.parseInt(lineSplit[2]);
                int passengersNum = Integer.parseInt(lineSplit[3]);
                int daysNum = Integer.parseInt(lineSplit[4]);
                float perPersonPrice = Float.parseFloat(lineSplit[5]);
                String status = lineSplit[6];
                String dateString = lineSplit[7];
                int arrId = Integer.parseInt(lineSplit[8]);
                Float fullPrice = Float.parseFloat(lineSplit[9]);
                Integer room = RoomsDAO.roomsHash.get(resId);
                ENUM_ReservationStatus enumStatus = ENUM_ReservationStatus.valueOf(status);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
                LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
                
                Reservation res = new Reservation();
                Tourist tourist = new Tourist();
                TouristAgent agent = new TouristAgent();
                
                // making reference if need in future.
                tourist.setId(touristId);
                agent.setId(agentId);
                res.setId(resId);
                res.setReservationSeller(agent);
                res.setReservationCustomer(tourist);
                res.setNumberOfPassengers(passengersNum);
                res.setNumberOfDays(daysNum);
                res.setPricePerDayPerPerson(perPersonPrice);
                res.setReservationStatus(enumStatus);
                res.setDate(localDateTime);
                res.setArrangementId(arrId);
                res.setNumberOfRooms(room);
                res.setFullPrice(perPersonPrice, daysNum, passengersNum);
                
            	try {
            		 reservationsHash.put(resId, res);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reservationsHash;
	}
}