package groupproject.objects;

public class Contact {

    private int accountID;
    private String email;
    private String phone;

    public Contact(int accountID, String email, String phone) {
        this.accountID = accountID;
        this.email = email;
        this.phone = phone;
    }

    public Contact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public Contact() {
        this.email = null;
        this.phone = null;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "accountID=" + accountID +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
