

// AUthors Evan Jurdan, Miles Krassen, Tim Lonergan, Kaszuba Daniel, Krassen Miles, Krishnan Netra, Landers Seth

import objects.*;

import java.sql.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class iste330Group4DataLayer {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stmt;

    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    public iste330Group4DataLayer() {
        this.conn = null;
        this.rs = null;
        this.stmt = null;
    }// end of constructor

    public boolean connect(String user, String pass, String db) {

        String url = "jdbc:mysql://localhost/" + db.trim() +"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {

            Class.forName(DEFAULT_DRIVER);
            System.out.println(">> DEFAULT_DRIVER set");

            this.conn = DriverManager.getConnection(url, user.trim(), pass.trim());
            System.out.println(">> Database connected");

        } catch (ClassNotFoundException e) {

            System.out.println("[!] Driver not found\n>>>> " + e);
            System.exit(0);

        } catch (SQLException e) {

            System.out.println("[!] Database connection failed.\n>>>> " + e);
            System.exit(0);
        }

        return (this.conn != null);
    }
    //resets db instances
    public void reset() {

        try {
            if (this.rs != null)
                this.rs.close();

            if (this.stmt != null)
                this.stmt.close();

        } catch (SQLException e) {

            System.out.println("\n[!] ResultSet or Statement failed to close\n>> " + e);
        }
    }

    public void close() {

        try {

            reset();

            if (this.conn != null)
                this.conn.close();

//            System.out.println("\n>> Database connection closed");

        } catch (SQLException e) {

            System.out.println("\n[!] Database failed to close\n>> " + e);
        }
    }

    private void SQLExceptionMsg(String e) {

        System.out.println("[!] SQLException caught\n>>>> " + e);
    }

    private String hash(String pass) {
        String Password = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
            
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return Password;

    }

    ///////////////////////////////////////////
    // Evan's code, sorry for poor documentation

   /********************
     * CREATE ACCOUNT
     ********************/
    public int addAccount(String fNam, String lNam, String pass, String prefCon, String emailAdd, String PhoNum,
                           String BuilNum, String offNum, int Role) {
        int affected = 0;
        int affected2 = 0;
        int affected3 = 0;
        int acID = -1;
        String Password = "";
        Password = hash(pass);


        try {

            stmt = conn.prepareStatement(
                    "INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fNam.trim());
            stmt.setString(2, lNam.trim());
            stmt.setString(3, Password.trim());
            stmt.setString(4, prefCon.trim());
            stmt.setInt(5, Role);

            affected = stmt.executeUpdate();
            // gets any auto-generated keys after update
            this.rs = stmt.getGeneratedKeys();

            if (this.rs.next()) {

                acID = this.rs.getInt(1);
            }

            System.out.print("\n " + affected + " record(s) inserted\n");
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }
        reset();

//////////////// get accountID

//        System.out.println(acID);

        // adds contact details
        try {


            stmt = conn.prepareStatement("INSERT INTO iste330group4.contact(accountID, email, phone) VALUES (?,?,?)");
            stmt.setInt(1, acID);
            stmt.setString(2, emailAdd.trim());
            stmt.setString(3, PhoNum.trim());
            affected3 = stmt.executeUpdate();
//            System.out.print("\n " + affected3 + " record(s) inserted\n");
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }

        reset();

        /********************
             * FACULTY SECTION
             ********************/
        //if role belongs to faculty proceed
        if (Role == 2){
            ////////////////////
            // adds office details for created faculty
            try {

                stmt = conn.prepareStatement("INSERT INTO iste330group4.office(accountID, building, number) VALUES (?,?,?)");
                stmt.setInt(1, acID);
                stmt.setString(2, BuilNum.trim());
                stmt.setString(3, offNum.trim());
                affected2 = stmt.executeUpdate();
            } catch (Exception e) {

                SQLExceptionMsg(e.getMessage());
            }
            reset();
        }

        return acID;
}


  /********************
     * EDIT CONTACT
     ********************/
    public int updateContact(int acID, String emailAdd, String PhoNum) {
        int affected = 0;
        try {

            stmt = conn.prepareStatement("UPDATE contact set email = ? , phone = ? WHERE accountID = ? ");
            stmt.setString(1, emailAdd);
            stmt.setString(2, PhoNum);
            stmt.setInt(3, acID);
            affected = stmt.executeUpdate();
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }
        reset();

        return affected;
    }

    /********************
     * EDIT OFFICE DETAILS
     ********************/
    public int updateOffice(int acID, String BuilNum, String offNum) {
        int affected = 0;
        try {

            stmt = conn.prepareStatement("UPDATE office set building = ? , number = ? WHERE accountID = ? ");
            stmt.setString(1, BuilNum);
            stmt.setString(2, offNum);
            stmt.setInt(3, acID);
            affected = stmt.executeUpdate();
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }
        reset();

        return affected;
    }

    /********************
     * EDIT ACC DETAILS
     ********************/
    public int updateAccount(int acID, String fNam, String lNam, String prefCon) {
        int affected = 0;
        try {

            stmt = conn.prepareStatement("UPDATE account set firstName = ? , lastName = ? , preferredContact = ? WHERE accountID = ? ");
            stmt.setString(1, fNam);
            stmt.setString(2, lNam);
            stmt.setString(3, prefCon);
            stmt.setInt(4, acID);
            affected = stmt.executeUpdate();
//            System.out.print("\n " + affected + " record(s) updated\n");
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }
        reset();

        return affected;
    }
    /********************
     * PASSWORD CHANGE
     ********************/
    public int updatePassword(int acID, String pass) {
        int affected = 0;
        String Password = "";

        Password = hash(pass.trim());
     
        try {

            
            stmt = conn.prepareStatement("UPDATE account set password = ? WHERE accountID = ? ");
            stmt.setString(1, Password);
            stmt.setInt(2, acID);
            affected = stmt.executeUpdate();
//            System.out.print("\n " + affected + " record(s) updated\n");
        } catch (Exception e) {
            SQLExceptionMsg(e.getMessage());
        }
        reset();

        return affected;
    }


    /////////////////////////////////

    /***************************
     * MILES KRASSEN CODE BELOW
     ***************************/

    /************************
     * SEARCH RECORDS SECTION
     ************************/

    /**************************
     * SEARCH FACULTY ABSTRACTS
     **************************/

    public List<SearchRecord> searchByFacultyAbstract(String input) {

        List<SearchRecord> searchRecords = new LinkedList<>();

        try {

            String sql = "SELECT CONCAT(a.firstName, ', ', a.lastName) AS name, a.accountID FROM account AS a JOIN faculty_abstract USING (accountID) JOIN abstract USING (abstractID) WHERE LOWER(abstract.title) LIKE CONCAT('%', ?, '%') GROUP BY a.accountID";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setString(1, input.toLowerCase().trim());

            this.rs = this.stmt.executeQuery();

            while (this.rs.next()) {

                searchRecords.add(new SearchRecord(
                        this.rs.getInt("accountID"),
                        this.rs.getString("name")
                ));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return searchRecords;
    }

    /*****************
     * SEARCH BY NAME
     *****************/

    private List<SearchRecord> searchByName(int roleID, String input) {

        List<SearchRecord> searchRecords = new LinkedList<>();

        try {

            String[] explodedInput = input.toLowerCase().split("\\s+");

            String sql = "SELECT CONCAT(a.firstName, ', ', a.lastName) AS name, a.accountID FROM account AS a WHERE a.roleID = " + roleID + " AND ";

            for (int i = 0; i < explodedInput.length; i++) {

                explodedInput[i] = explodedInput[i].trim();
                sql += "LOWER(CONCAT(firstName, ' ', lastName)) LIKE CONCAT('%', ?, '%') OR ";
            }

            sql = sql.substring(0, sql.length() - 4);

            this.stmt = this.conn.prepareStatement(sql);

            for (int i = 1; i <= explodedInput.length; i++) {

                this.stmt.setString(i, explodedInput[i - 1]);
            }

            this.rs = this.stmt.executeQuery();

            while (this.rs.next()) {

                searchRecords.add(new SearchRecord(
                        this.rs.getInt("accountID"),
                        this.rs.getString("name")
                ));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return searchRecords;
    }

    public List<SearchRecord> searchByFacultyName(String name) {

        return searchByName(2, name);
    }

    public List<SearchRecord> searchByStudentName(String name) {

        return searchByName(1, name);
    }

    /*******************
     * SEARCH INTERESTS
     *******************/

    private List<SearchRecord> searchByInterests(String role, String input) {

        List<SearchRecord> searchRecords = new LinkedList<>();

        try {

            String sql = "SELECT concat(a.firstName,', ' , a.lastName) as 'name', afi.accountID as 'accountID', group_concat(' ', fi.interest) AS 'commonInterests' FROM "
                    + role + "_interest as fi JOIN account_" + role
                    + "_interest as afi on fi.interestID=afi.interestID JOIN account as a on afi.accountID=a.accountID WHERE LOWER(fi.interest) IN (";

            String[] explodedInput = input.toLowerCase().split(",");

            for (int i = 0; i < explodedInput.length; i++) {

                explodedInput[i] = explodedInput[i].trim();
                sql += "?,";
            }

            sql = sql.substring(0, sql.length() - 1);
            sql += ") group by afi.accountID order by count(fi.interest) desc";

            this.stmt = this.conn.prepareStatement(sql);

            for (int i = 1; i <= explodedInput.length; i++) {

                this.stmt.setString(i, explodedInput[i - 1]);
            }

            this.rs = this.stmt.executeQuery();

            while (this.rs.next()) {

                searchRecords.add(new SearchRecord(
                        this.rs.getInt("accountID"),
                        this.rs.getString("name"),
                        this.rs.getString("commonInterests")
                ));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return searchRecords;
    }

    public List<SearchRecord> searchByFacultyInterest(String interestsInput) {

        return searchByInterests("faculty", interestsInput);
    }

    public List<SearchRecord> searchByStudentInterest(String interestsInput) {

        return searchByInterests("student", interestsInput);
    }

    /******************
     * ACCOUNT SECTION
     ******************/

    public Account getAccount(int accountID, String password) {

        Account account = new Account();

        try {

            String sql = "SELECT * FROM account WHERE accountID = ? AND password = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);
            this.stmt.setString(2, hash(password.trim()));

            this.rs = this.stmt.executeQuery();

            if (this.rs.next()) {

                account.setAccountID(this.rs.getInt("accountID"));
                account.setFirstName(this.rs.getString("firstName"));
                account.setLastName((this.rs.getString("lastName")));
                account.setPreferedContact(this.rs.getString("preferredContact"));
                account.setRoleID(this.rs.getInt("roleID"));

                reset();

            }else{

                reset();
                return null; //if account not found.
            }

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return account;
    }

    /*******************
     * OFFICE SECTION
     *******************/

    public Office getOffice(int accountID) {

        Office office = new Office();

        try {

            String sql = "SELECT * FROM office WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            if (this.rs.next()) {

                office.setAccountID(accountID);
                office.setBuilding(this.rs.getString("building"));
                office.setNumber(this.rs.getString("number"));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return office;
    }

    /*******************
     * CONTACT SECTION
     *******************/

    public Contact getContact(int accountID) {

        Contact contact = new Contact();

        try {

            String sql = "SELECT * FROM contact WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            if (this.rs.next()) {

                contact.setAccountID(accountID);
                contact.setEmail(this.rs.getString("email"));
                contact.setPhone(this.rs.getString("phone"));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return contact;
    }

    /***************************
     * FACULTY ABSTRACT SECTION
     ***************************/

    public List<Abstract> getFacultyAbstracts(int accountID) {

        List<Abstract> abstracts = new LinkedList<>();

        try {

            String sql = "SELECT * FROM abstract JOIN faculty_abstract USING (abstractID) WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            while (this.rs.next()) {

                abstracts.add(new Abstract(
                        this.rs.getInt("abstractID"),
                        this.rs.getString("title"),
                        this.rs.getString("body")
                ));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return abstracts;
    }

    public int addFacultyAbstract(int accountID, String title, String body) {

        int effected = 0;

        try {

            String sql = "INSERT INTO abstract (title, body) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.stmt.setString(1, title);
            this.stmt.setString(2, body);

            this.stmt.executeUpdate();
            this.rs = this.stmt.getGeneratedKeys();

            int abstractID = -1;

            if (this.rs.next()) {

                abstractID = this.rs.getInt(1);
            }

            reset();

            if (abstractID == -1)
                return effected;

            sql = "INSERT INTO faculty_abstract (accountID, abstractID) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);
            this.stmt.setInt(2, abstractID);

            effected = this.stmt.executeUpdate();

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    public int removeFacultyAbstract(int abstractID) {

        int effected = 0;

        try {

            String sql = "DELETE FROM abstract WHERE abstractID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, abstractID);

            effected = this.stmt.executeUpdate();

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    /********************
     * INTERESTS SECTION
     ********************/

    private List<Interest> getInterests(String role, int accountID) {

        List<Interest> interests = new LinkedList<>();

        try {

            String sql = "SELECT * FROM " + role + "_interest JOIN account_" + role + "_interest USING (interestID) WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            while (this.rs.next()) {

                interests.add(new Interest(
                        this.rs.getInt("interestID"),
                        this.rs.getString("interest")
                ));
            }

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return interests;
    }

    public List<Interest> getFacultyInterests(int accountID) {

        return getInterests("faculty", accountID);
    }

    public List<Interest> getStudentInterests(int accountID) {

        return getInterests("student", accountID);
    }

    public List<Interest> getGuestInterests(int accountID) {

        return getInterests("guest", accountID);
    }

    private int removeInterest(String role, int interestID) {

        int effected = 0;

        try {

            String sql = "DELETE FROM " + role + "_interest WHERE interestID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, interestID);

            effected = this.stmt.executeUpdate();

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    public int removeStudentInterest(int interestID) {

        return removeInterest("student", interestID);
    }

    public int removeFacultyInterest(int interestID) {

        return removeInterest("faculty", interestID);
    }

    public int removeGuestInterest(int interestID) {

        return removeInterest("guest", interestID);
    }

    private int addInterest(String role, int accountID, String interest) {

        int effected = 0;

        try {

            String sql = "INSERT INTO " + role + "_interest (interest) VALUES (?)";

            this.stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.stmt.setString(1, interest);

            this.stmt.executeUpdate();
            this.rs = this.stmt.getGeneratedKeys();

            int interestID = -1;

            if (this.rs.next()) {

                interestID = this.rs.getInt(1);
            }

            reset();

            if (interestID == -1)
                return effected;

            sql = "INSERT INTO account_" + role + "_interest (accountID, interestID) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);
            this.stmt.setInt(2, interestID);

            effected = this.stmt.executeUpdate();

            reset();

        } catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    public int addStudentInterest(int accountID, String interest) {

        return addInterest("student", accountID, interest);
    }

    public int addFacultyInterest(int accountID, String interest) {

        return addInterest("faculty", accountID, interest);
    }

    public int addGuestInterest(int accountID, String interest) {

        return addInterest("guest", accountID, interest);
    }

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        iste330Group4DataLayer dl = new iste330Group4DataLayer();

        System.out.println("username: ");
        String user = reader.nextLine();

        System.out.println("password: ");
        String pass = reader.nextLine();

        System.out.println("database (TYPE:'iste330group4'): ");
        String db = reader.nextLine();

        dl.connect(user, pass, db);

        // test functions below

        System.out.println("\n>> ADDING ACCOUNT TESTS");
        dl.addAccount("john", "constantine", "yobro", "phone", "Johnny@this.dontmatter", "9999999", "somewhere",
                "over the rainbow",2);
        dl.addAccount("sarah", "connor", "johnconnor", "in person visit", "3000@this.dontmatter", "55125851", "bunker",
                "z892e",2);
        dl.addAccount("Evan", "Jurdan", "Meow", "phone", "e@some.com", "my#",null,null,1);
        dl.addAccount("harry", "Styles", "song", "email", "AAAA@A.com", "908264348",null,null,3);

        System.out.println("\n>> ADDING INTEREST TESTS");
        dl.addStudentInterest(3, "Biology");
        dl.addStudentInterest(3, "Cars");
        dl.addStudentInterest(3, "trees");
        dl.addStudentInterest(4, "birds");

        dl.addFacultyInterest(1, "Biology");
        dl.addFacultyInterest(2, "Biology");

        System.out.println("\n>> Get Faculty Interest accountID = 1");
        dl.getFacultyInterests(1).forEach(item -> System.out.println(item));

        // dl.removeFacultyInterest(2); //interest ID

        System.out.println("\n>> ADDING FACULTY ABSTRACTS");
        dl.addFacultyAbstract(1, "cool", "even cooler.");
        dl.addFacultyAbstract(1, "coolest", "even beans.");
        dl.addFacultyAbstract(1, "cooler", "even test.");
        dl.addFacultyAbstract(1, "Hey Ho",
                "Hey! Come merry dol! derry dol! My darling! Light goes the weather-wind and the feathered starling. Down along under Hill, shining in the sunlight, Waiting on the doorstep for the cold starlight, There my pretty lady is, River-woman's daughter, Slender as the willow-wand, clearer than the water. Old Tom Bombadil water-lilies bringing Comes hopping home again. Can you hear him singing? Hey! Come merry dol! derry dol! and merry-o! Goldberry, Goldberry, merry yellow berry-o! Poor old Willow-man, you tuck your roots away! Tom's in a hurry now. Evening will follow day. Tom's going home again water-lilies bringing. Hey! Come derry dol! Can you hear me singing?");

        System.out.println("\n>> Get Faculty Abstracts accountID = 1");
        dl.getFacultyAbstracts(1).forEach(item -> System.out.println(item));

        System.out.println("\n>> Search By Faculty Interest: songs, trees");
        dl.searchByFacultyInterest("songs,trees").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Faculty Interest: songs, trees, birds");
        dl.searchByFacultyInterest("songs,trees,birds").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Faculty Interest: Biology");
        dl.searchByFacultyInterest("Biology").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Faculty Interest: trees");
        dl.searchByFacultyInterest("trees").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Student Interest: songs, trees");
        dl.searchByStudentInterest("songs,trees").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Student Interest: Biology");
        dl.searchByStudentInterest("Biology").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Student Interest: Cars");
        dl.searchByStudentInterest("Cars").forEach(item -> System.out.println(item));

        System.out.println("\n>> Search By Faculty Abstract: name = 'cool'");
        dl.searchByFacultyAbstract("cool").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Faculty Name: name = 'Con'");
        dl.searchByFacultyName("Con").forEach(item -> System.out.println(item));
        System.out.println("\n>> Search By Student Name: name = 'Evan'");
        dl.searchByStudentName("Evan").forEach(item -> System.out.println(item));

        // dl.removeFacultyAbstract(1); //abstract ID

        System.out.println("\n>> Get Contact accountID = 1");
        System.out.println(dl.getContact(1));
        System.out.println("\n>> Get Faculty Office accountID = 1");
        System.out.println(dl.getOffice(1));

        dl.close();
    }

} // End of Class
