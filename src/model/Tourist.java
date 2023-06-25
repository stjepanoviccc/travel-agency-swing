package model;

import java.util.ArrayList;

public class Tourist extends User{
	ArrayList<Reservation> reservationList;
	
	public Tourist() {
		
	}
	
	public Tourist(Integer id, String name, String surname, String JMBG, String address, int phone, String username,
			String password, ENUM_Gender gender, ENUM_Role role, ArrayList<Reservation> reservationList) {
		super(id, name, surname, JMBG, address, phone, username, password, role, gender);
		this.reservationList = reservationList;
	}

	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	
	public static void createReservation(Reservation res, String state) {
		controller.crud.reservation.AddReservation.addReservation(res, state);
	}
	
	public void cancelReservation(Reservation res, String state) {
		controller.crud.reservation.CancelReservation.cancelReservation(res, state);
	}
	
	public void editReservation(Integer reservationId, int passengers) {
		controller.crud.reservation.EditReservation.editReservation(reservationId, passengers);
	}

	@Override
	public String toString() {
		return "Tourist [reservationList=" + reservationList + ", name=" + name + ", surname=" + surname + ", JMBG="
				+ JMBG + ", address=" + address + ", phone=" + phone + ", username=" + username
				+ ", password=" + password + ", gender=" + gender + ", role=" + role + "]";
	}
}
