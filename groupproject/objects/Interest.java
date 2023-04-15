package groupproject.objects;

public class Interest {

    private int interestID;
    private String interest;

    public Interest(String interest) {
        this.interest = interest;
    }

    public Interest(int interestID, String interest) {
        this.interestID = interestID;
        this.interest = interest;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "interestID=" + interestID +
                ", interest='" + interest + '\'' +
                '}';
    }
}
