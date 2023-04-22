package objects;

public class Account {

    private int accountID;
    private String firstName;
    private String lastName;
    private String preferedContact;
    private int roleID;

    public Account(int accountID, String firstName, String lastName, String preferedContact, int roleID) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferedContact = preferedContact;
        this.roleID = roleID;
    }

    public Account() {
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferedContact() {
        return preferedContact;
    }

    public void setPreferedContact(String preferedContact) {
        this.preferedContact = preferedContact;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
