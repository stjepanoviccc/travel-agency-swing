package model;

public enum ENUM_ArrangementType {
    Summer_vacation,
    Wintering,
    European_cities,
    Long_journeys,
    First_minute,
    Last_minute,
    Travel_during_the_holidays;
	
	public String getModifiedName() {
        String originalName = this.name();
        return originalName.replace("_", " ");
    }
}