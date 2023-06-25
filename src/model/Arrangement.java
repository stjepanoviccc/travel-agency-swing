package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Arrangement {
	protected Integer id;
	protected TouristAgent seller;
	protected ENUM_ArrangementType typeOfArrangement;
	protected String image;
	protected LocalDateTime availableDate;
	protected int capacity;
	protected ENUM_TypeOfAccomodation typeOfAccomodation;
	protected float pricePerPerson;
	protected float fairDiscount;
	protected String reservations;
	protected ENUM_TypeOfRoom typeOfRoom;
	
	public Arrangement() {
		
	}
	
	public Arrangement(Integer id, TouristAgent seller, ENUM_ArrangementType typeOfArrangement, String image, LocalDateTime availableDate, int capacity,
			ENUM_TypeOfRoom typeOfRoom, ENUM_TypeOfAccomodation typeOfAccomodation, float pricePerPerson, int fairDiscount, String reservations) {
		this.id = id;
		this.seller = seller;
		this.typeOfArrangement = typeOfArrangement;
		this.image = image;
		this.availableDate = availableDate;
		this.capacity = capacity;
		this.typeOfAccomodation = typeOfAccomodation;
		this.typeOfRoom = typeOfRoom;
		this.pricePerPerson = pricePerPerson;
		this.fairDiscount = fairDiscount;
		this.reservations = reservations;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TouristAgent getSeller() {
		return seller;
	}

	public void setSeller(TouristAgent seller) {
		this.seller = seller;
	}

	public ENUM_ArrangementType getTypeOfArrangement() {
		return typeOfArrangement;
	}

	public void setTypeOfArrangement(ENUM_ArrangementType typeOfArrangement) {
		this.typeOfArrangement = typeOfArrangement;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(LocalDateTime availableDate2) {
		this.availableDate = availableDate2;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ENUM_TypeOfAccomodation getTypeOfAccomodation() {
		return typeOfAccomodation;
	}

	public void setTypeOfAccomodation(ENUM_TypeOfAccomodation typeOfAccomodation) {
		this.typeOfAccomodation = typeOfAccomodation;
	}

	public float getPricePerPerson() {
		return pricePerPerson;
	}

	public void setPricePerPerson(float pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
	}

	public float getFairDiscount() {
		return fairDiscount;
	}

	public void setFairDiscount(float fairDiscount2) {
		this.fairDiscount = fairDiscount2;
	}

	public String getReservations() {
		if(reservations == null) {
			return "/";
		}else {
			return reservations;			
		}
	}

	public void setReservations(String lineSplit) {
		this.reservations = lineSplit;
	}
	
	public ENUM_TypeOfRoom getTypeOfRoom() {
		return typeOfRoom;
	}
	
	public void setTypeOfRoom(ENUM_TypeOfRoom roomType) {
		this.typeOfRoom = roomType;
	}

	@Override
	public String toString() {
		return "Arrangement [id=" + id + ", seller=" + seller + ", typeOfArrangement=" + typeOfArrangement + ", image=" + image
				+ ", availableDate=" + availableDate + ", capacity=" + capacity + ", typeOfAccomodation=" + typeOfAccomodation
				+ ", pricePerPerson=" + pricePerPerson + ", fairDiscount=" + fairDiscount + ", reservations=" + reservations
				+ "]";
	}


}
