package model;

import java.time.LocalDateTime;

public class Reservation {
	protected Integer id;
	protected Tourist reservationCustomer;
	protected TouristAgent reservationSeller;
	protected int numberOfPassengers;
	protected int numberOfDays;
	protected float pricePerDayPerPerson;
	protected ENUM_ReservationStatus reservationStatus;
	protected LocalDateTime date;
	protected int numberOfRooms;
	protected int arrangementId;
	protected float fullPrice;
	
	public Reservation() {
		
	}
	
	public Reservation(Integer id, Tourist reservationCustomer, TouristAgent reservationSeller, int numberOfPassengers, int numberOfDays,
		float pricePerDayPerPerson, ENUM_ReservationStatus reservationStatus, LocalDateTime date, int numberOfRooms, int arrangementId, float fullPrice) {
		
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer lastID) {
		this.id = lastID;
	}
	
	public Tourist getReservationCustomer() {
		return reservationCustomer;
	}
	
	public void setReservationCustomer(Tourist reservationCustomer) {
		this.reservationCustomer = reservationCustomer;
	}
	
	public TouristAgent getReservationSeller() {
		return reservationSeller;
	}
	
	public void setReservationSeller(TouristAgent reservationSeller) {
		this.reservationSeller = reservationSeller;
	}
	
	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}
	
	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}
	
	public int getNumberOfDays() {
		return numberOfDays;
	}
	
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	
	public float getPricePerDayPerPerson() {
		return pricePerDayPerPerson;
	}
	
	public void setPricePerDayPerPerson(float pricePerDayPerPerson) {
		this.pricePerDayPerPerson = pricePerDayPerPerson;
	}
	
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	public int getArrangementId() {
		return arrangementId;
	}
	
	public void setArrangementId(int arrId) {
		this.arrangementId = arrId;
	}
	
	public ENUM_ReservationStatus getReservationStatus() {
		return reservationStatus;
	}
	
	public void setReservationStatus(ENUM_ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public float calculateThePrice(float perPerson, int days, int passengers, float fairDiscount) {
		float totalPrice = perPerson*days*passengers;
		
		if(fairDiscount>0) {
			float discountAmmount = (fairDiscount/100)*totalPrice;
			totalPrice -= discountAmmount;
			return totalPrice;
		} else {
			return totalPrice;		
		}
	}
	
	public void setFullPrice(float p, int d, int p2)  {
		this.fullPrice = (p*d*p2);
	}
	public float getFullPrice() {
		return this.fullPrice;
	}
	
	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", reservationCustomer=" + reservationCustomer + ", reservationSeller="
				+ reservationSeller + ", numberOfPassengers=" + numberOfPassengers + ", numberOfDays=" + numberOfDays + ", pricePerDayPerPerson="
				+ pricePerDayPerPerson + ", reservationStatus=" + reservationStatus + ", date=" + date + "]";
	}
}
