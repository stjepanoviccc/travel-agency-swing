package model;

public abstract class User {
	protected Integer id;
	protected String name;
	protected String surname;
	protected String JMBG;
	protected String address;
	protected int phone;
	protected String username;
	protected String password;
	protected ENUM_Gender gender;
	protected ENUM_Role role;
	
	public User() {
	
	}
	
	public User(Integer id,String name, String surname, String JMBG, String address, int phone, String username, String password, ENUM_Role role, ENUM_Gender gender) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.JMBG = JMBG;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.role = role;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer hashId) {
		this.id = hashId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String i) {
		this.JMBG = i;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int i) {
		this.phone = i;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String cs) {
		this.password = cs;
	}

	public ENUM_Gender getGender() {
		return gender;
	}

	public void setGender(ENUM_Gender gender) {
		this.gender = gender;
	}

	public ENUM_Role getRole() {
		return role;
	}

	public void setRole(ENUM_Role role) {
		this.role = role;
	}
}
