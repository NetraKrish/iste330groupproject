package groupproject.objects;

import java.util.List;

public class SearchRecord {

    private int accountID;
    private String name;
    private String commonInterests;

    public SearchRecord(int accountID, String name, String commonInterests) {
        this.accountID = accountID;
        this.name = name;
        this.commonInterests = commonInterests;
    }

    public SearchRecord(int accountID, String name) {
        this.accountID = accountID;
        this.name = name;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonInterests() {
        return commonInterests;
    }

    public void setCommonInterests(String commonInterests) {
        this.commonInterests = commonInterests;
    }

    @Override
    public String toString() {
        return "SearchRecord{" +
                "accountID=" + accountID +
                ", name='" + name + '\'' +
                ", commonInterests='" + commonInterests + '\'' +
                '}';
    }
}
