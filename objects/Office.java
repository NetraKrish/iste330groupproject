package groupproject.objects;

public class Office {

    private int accountID;
    private String building;
    private String number;

    public Office(int accountID, String building, String number) {
        this.accountID = accountID;
        this.building = building;
        this.number = number;
    }

    public Office(String building, String number) {
        this.building = building;
        this.number = number;
    }

    public Office() {
        this.building = null;
        this.number = null;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Office{" +
                "accountID=" + accountID +
                ", building='" + building + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
