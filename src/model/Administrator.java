package model;

public class Administrator extends User{
	
	public Administrator() {
		
	}
	
	public Administrator(Integer id, String name, String surname, String JMBG, String address, int phone,
			String username, String password, ENUM_Role role, ENUM_Gender gender) {
		super(id, name, surname, JMBG, address, phone, username, password, role, gender);
	}

	public static void addUser(Object[] userInput) {
		controller.crud.user.AddUser.addUser(userInput);
    }
	
	public void deleteUser(Integer userId) {
		controller.crud.user.DeleteUser.deleteUser(userId);
	}
	
	public static void editUser(Object[] userInput, Integer userId) {
		controller.crud.user.EditUser.editUser(userInput, userId);
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
		
	public void cancelReservation(Reservation res, String state) {
		controller.crud.reservation.CancelReservation.cancelReservation(res, state);
	}

	@Override
	public String toString() {
		return "Administrator [name=" + name + ", surname=" + surname + ", JMBG=" + JMBG + ", address=" + address
				+ ", phone=" + phone + ", username=" + username + ", password=" + password
				+ ", gender=" + gender + ", role=" + role + "]";
	}
}
