package model;

import java.util.ArrayList;

public class TouristAgent extends User{
	ArrayList<Reservation> reservationList;
	ArrayList<Arrangement> myArrangements;
	
	public TouristAgent() {
		
	}
	
	public TouristAgent(Integer id,String name, String surname, String JMBG, String address, int phone,
			String username, String password, ENUM_Role role, ENUM_Gender gender, ArrayList<Reservation> reservationList, ArrayList<Arrangement> myArrangements) {	
		super(id, name, surname, JMBG, address, phone, username, password, role, gender);
		this.reservationList = reservationList;
		this.myArrangements = myArrangements;
	}
	
	
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public ArrayList<Arrangement> getmyArrangements() {
		return myArrangements;
	}

	public void setmyArrangements(ArrayList<Arrangement> myArrangements) {
		this.myArrangements = myArrangements;
	}

	public static void createArrangement(Arrangement arr) {
		controller.crud.arrangement.AddArrangement.createArrangement(arr);
	}
	
	public void deleteArrangement(Integer userId) {
		controller.crud.arrangement.DeleteArrangement.deleteArrangement(userId);
	}
	
	public static void editArrangement(Arrangement arr) {
		controller.crud.arrangement.EditArrangement.editArrangement(arr);
	}
	
	public static void createReservation(Reservation res, String state) {
		controller.crud.reservation.AddReservation.addReservation(res, state);
	}
	
	public void cancelReservation(Reservation reservation, String state) {
		controller.crud.reservation.CancelReservation.cancelReservation(reservation, state);
	}

	@Override
	public String toString() {
		return "TouristAgent [reservationList=" + reservationList + ", myArrangements=" + myArrangements + ", name=" + name
				+ ", surname=" + surname + ", JMBG=" + JMBG + ", address=" + address + ", phone=" + phone
				+ ", username=" + username + ", password=" + password + ", gender=" + gender + ", role=" + role
				+ "]";
	}
}
