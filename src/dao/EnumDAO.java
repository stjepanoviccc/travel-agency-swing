package dao;

import model.ENUM_ArrangementType;
import model.ENUM_Role;
import model.ENUM_TypeOfRoom;

public class EnumDAO {
	// returning name without underscore
	public static String[] returnModifiedName(ENUM_ArrangementType[] enumType) {
	       String[] modifiedNames = new String[ENUM_ArrangementType.values().length];
	       for (int i = 0; i < ENUM_ArrangementType.values().length; i++) {
	           modifiedNames[i] = ENUM_ArrangementType.values()[i].getModifiedName();
	       }
	       return modifiedNames;
	}

	public static String[] returnModifiedName(ENUM_TypeOfRoom[] enumType) {
	       String[] modifiedNames = new String[ENUM_TypeOfRoom.values().length];
	       for (int i = 0; i < ENUM_TypeOfRoom.values().length; i++) {
	           modifiedNames[i] = ENUM_TypeOfRoom.values()[i].getModifiedName();
	       }
	       return modifiedNames;
	}

	
	public static String[] returnModifiedName(ENUM_Role[] enumType) {
	       String[] modifiedNames = new String[ENUM_Role.values().length];
	       for (int i = 0; i < ENUM_Role.values().length; i++) {
	           modifiedNames[i] = ENUM_Role.values()[i].getModifiedName();
	       }
	       return modifiedNames;
	}
	
	public static String modifyENUMTypeName(String typeName) {
	    String modifiedName = typeName.replace(" ", "_");
	    return modifiedName;
	}
}
