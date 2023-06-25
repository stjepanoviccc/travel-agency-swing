package model;

public enum ENUM_TypeOfRoom {
	Single_bed;
	
	public String getModifiedName() {
        String originalName = this.name();
        return originalName.replace("_", " ");
    }
}
