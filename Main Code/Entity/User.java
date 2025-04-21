package Entity;

import java.io.Serializable;

public abstract class User implements Serializable{
    protected String NRIC;
    protected String password;
    protected int age;
    protected String maritalStatus;
    protected String name; // new name attribute
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public User(String NRIC, String password, int age, String maritalStatus, String name) {
        this.NRIC = NRIC;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.name = name; // initialize name
    }

    public String getNRIC() {
        return NRIC;
    }

    public String getName() {
        return name; // getter for name
    }
    
    public abstract String getRole();

    public void changePassword(String newPassword) {
    	setPassword(newPassword);
    	System.out.println("Password updated successfully.");
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setNRIC(String nRIC) {
		NRIC = nRIC;
	}

	public void setName(String name) {
		this.name = name;
	}
}
