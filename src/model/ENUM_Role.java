package model;

public enum ENUM_Role {
    Administrator,
    Tourist_agent,
    Tourist;
	
	public String getModifiedName() {
        String originalName = this.name();
        return originalName.replace("_", " ");
    }
	
	public String returnName() {
		String originalName = this.name();
		return originalName.replace(" ", "_");
	}
}
