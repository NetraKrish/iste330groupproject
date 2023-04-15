package groupproject;

// AUthors Evan Jurdan, Miles Krassen, Tim Lonergan, Kaszuba Daniel, Krassen Miles, Krishnan Netra, Landers Seth


import groupproject.objects.*;

import java.sql.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class iste330Group4DataLayer {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stmt;
    private String sql;
    private int col;

    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    public iste330Group4DataLayer(){
        this.conn = null;
        this.rs = null;
        this.stmt = null;
    }//end of constructor


    public boolean connect(String user, String pass, String db) {

        String url = "jdbc:mysql://localhost/" + db;

        try {

            Class.forName(DEFAULT_DRIVER);
            System.out.println(">> DEFAULT_DRIVER set");

            this.conn = DriverManager.getConnection(url, user, pass);
            System.out.println(">> Database connected.");

        }catch (ClassNotFoundException e) {

            System.out.println(">> Database connection failed.\n>> " + e);
            System.exit(0);

        }catch (SQLException e) {

            System.out.println(">> Database connection failed.\n>> " + e);
            System.exit(0);
        }

        return (this.conn != null);
    }

    public void reset() {

        try {
            if(this.rs != null)
                this.rs.close();

            if(this.stmt != null)
                this.stmt.close();

        }catch (SQLException e) {

            System.out.println(">> DataLayer:reset(): failed\n>> " + e);
        }
    }

    public void close() {

        try {

            reset();

            if(this.conn != null)
                this.conn.close();

            System.out.println(">> Database connection closed.");

        }catch (SQLException e) {

            System.out.println(">> DataLayer:close(): failed\n>> " + e);
        }
    }

    private void SQLExceptionMsg(String e) {

        System.out.println(">> SQLException caught\n>>>> " + e);
    }

///////////////////////////////////////////
//Evan's code, sorry for poor documentation

    public int accID(String fNam, String lNam, String pass){
        // finds accountID with first and last name as well as their password
        int acID = 0;
        String Password = "";

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("Error Hashing");
        }
        try {

            String sql = "SELECT accountID from account WHERE firstName = ? AND lastName = ? AND password = ? LIMIT 1";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setString(1, fNam);
            this.stmt.setString(2, lNam);
            this.stmt.setString(3, Password);
            this.stmt.executeQuery();
            this.rs = this.stmt.getResultSet();
            if(this.rs.next()){
                acID = this.rs.getInt("accountID");
            }
        }catch (SQLException e) {
            SQLExceptionMsg(e.getMessage());
        }
        return(acID);
    }

    public void addTeachAcc(String fNam,String lNam,String pass,String prefCon,String emailAdd,String PhoNum,String BuilNum, String offNum){
        int affected = 0;
        int affected2 = 0;
        int affected3 = 0;
        int acID = 0;
        String Password = "";

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e){

        }


        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt1;
            stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt1.setString(1,fNam);
            stmt1.setString(2,lNam);
            stmt1.setString(3,Password);
            stmt1.setString(4,prefCon);
            stmt1.setInt(5,2);

            affected = stmt1.executeUpdate();
            this.rs = stmt1.getGeneratedKeys();

            if(this.rs.next()){

                acID = this.rs.getInt(1);
            }

            System.out.print("\n "+affected+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }

        ////////////////get accountID

        System.out.println(acID);


        ////////////////////
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt2;
            stmt2 = conn.prepareStatement("INSERT INTO iste330group4.office(accountID, building, number) VALUES (?,?,?)");
            stmt2.setInt(1,acID);
            stmt2.setString(2,BuilNum);
            stmt2.setString(3,offNum);
            affected2 = stmt2.executeUpdate();
            System.out.print("\n "+affected2+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("INSERT INTO iste330group4.contact(accountID, email, phone) VALUES (?,?,?)");
            stmt3.setInt(1,acID);
            stmt3.setString(2,emailAdd);
            stmt3.setString(3,PhoNum);
            affected3 = stmt3.executeUpdate();
            System.out.print("\n "+affected3+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }

    }

    public void addStudAcc(String fNam,String lNam,String pass,String prefCon,String emailAdd,String PhoNum){
        int affected = 0;
        int affected3 = 0;
        int acID = 0;
        String Password = "";

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e){

        }
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt1;
            stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt1.setString(1,fNam);
            stmt1.setString(2,lNam);
            stmt1.setString(3,Password);
            stmt1.setString(4,prefCon);
            stmt1.setInt(5,1);

            affected = stmt1.executeUpdate();
            this.rs = stmt1.getGeneratedKeys();

            if(this.rs.next()){

                acID = this.rs.getInt(1);
            }

            System.out.print("\n "+affected+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }

        ////////////////get accountID
        //System.out.println(acID);
        ////////////////////

        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("INSERT INTO iste330group4.contact(accountID, email, phone) VALUES (?,?,?)");
            stmt3.setInt(1,acID);
            stmt3.setString(2,emailAdd);
            stmt3.setString(3,PhoNum);
            affected3 = stmt3.executeUpdate();
            System.out.print("\n "+affected3+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
    }

    public void addPubAcc(String fNam,String lNam,String pass,String prefCon,String emailAdd,String PhoNum){
        int affected = 0;
        int affected3 = 0;
        int acID = 0;
        String Password = "";

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e){

        }
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt1;
            stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt1.setString(1,fNam);
            stmt1.setString(2,lNam);
            stmt1.setString(3,Password);
            stmt1.setString(4,prefCon);
            stmt1.setInt(5,1);

            affected = stmt1.executeUpdate();
            this.rs = stmt1.getGeneratedKeys();

            if(this.rs.next()){

                acID = this.rs.getInt(1);
            }

            System.out.print("\n "+affected+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }

        ////////////////get accountID

        System.out.println(acID);


        ////////////////////

        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("INSERT INTO iste330group4.contact(accountID, email, phone) VALUES (?,?,?)");
            stmt3.setInt(1,acID);
            stmt3.setString(2,emailAdd);
            stmt3.setString(3,PhoNum);
            affected3 = stmt3.executeUpdate();
            System.out.print("\n "+affected3+" record(s) inserted\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
    }

    public void editContact(int acID, String emailAdd,String PhoNum){
        int affected = 0;
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("UPDATE contact set email = ? , phone = ? WHERE accountID = ? ");
            stmt3.setString(1,emailAdd);
            stmt3.setString(2,PhoNum);
            stmt3.setInt(3,acID);
            affected = stmt3.executeUpdate();
            System.out.print("\n "+affected+" record(s) updated\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
    }

    public void editOffice(int acID, String BuilNum, String offNum){
        int affected = 0;
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("UPDATE office set building = ? , number = ? WHERE accountID = ? ");
            stmt3.setString(1,BuilNum);
            stmt3.setString(2,offNum);
            stmt3.setInt(3,acID);
            affected = stmt3.executeUpdate();
            System.out.print("\n "+affected+" record(s) updated\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
    }

    public void editAcc(int acID, String fNam,String lNam,String prefCon){
        int affected = 0;
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("UPDATE account set firstName = ? , lastName = ? , preferredContact = ? WHERE accountID = ? ");
            stmt3.setString(1,fNam);
            stmt3.setString(2,lNam);
            stmt3.setString(3,prefCon);
            stmt3.setInt(4,acID);
            affected = stmt3.executeUpdate();
            System.out.print("\n "+affected+" record(s) updated\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }
    }

    public void editPas(int acID, String pass){
        int affected = 0;
        String Password = "";

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));
            Password = Base64.getEncoder().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e){

        }
        try{
            Statement stmt = conn.
                    createStatement();;
            PreparedStatement stmt3;
            stmt3 = conn.prepareStatement("UPDATE account set password = ? WHERE accountID = ? ");
            stmt3.setString(1,Password);
            stmt3.setInt(2,acID);
            affected = stmt3.executeUpdate();
            System.out.print("\n "+affected+" record(s) updated\n");
        }
        catch(Exception e)
        {
            System.out.println("Error!");
            System.out.println("Error message is --> "+e);
        }

    }

    public String facultySearchOld(String interestsInput){
        String ret = "";
        String sql = "SELECT concat(a.firstName,', ' , a.lastName) as 'Name',afi.accountID as 'Account ID', count(fi.interest) AS 'Interests in Common', group_concat(' ', fi.interest) AS 'Interests' FROM faculty_interest as fi JOIN account_faculty_interest as afi on fi.interestID=afi.interestID JOIN account as a on afi.accountID=a.accountID WHERE fi.interest IN (";
        String[] interests = interestsInput.split(",");
        for(String interest : interests){
            sql += "?,";
        }

        sql = sql.substring(0, sql.length() - 1);
        sql += ") group by afi.accountID order by count(fi.interest) desc";
        try {
            this.stmt = this.conn.prepareStatement(sql);
            // int x = 1;
            // for(String interest : interests){
            //     this.stmt.setString(1, interest);
            //     x++;
            // }
            System.out.println(interests);
            for(int i = 0; i < interests.length; i++) {

                this.stmt.setString((i+1), interests[i]);
            }


            this.stmt.executeQuery();
            this.rs = this.stmt.getResultSet();
            while (this.rs.next()) {
                ret += this.rs.getString("Name")+" | "+this.rs.getString("Account ID")+" | "+this.rs.getString("Interests in Common")+" | "+this.rs.getString("Interests")+"\n";

            }
        }catch (SQLException e) {
            SQLExceptionMsg(e.getMessage());
        }


        return(ret);
    }

/////////////////////////////////

    /***************************
     * MILES KRASSEN CODE BELOW
     ***************************/

    /***********************
     * SEARCH RECORDS THING
     ***********************/

    private List<SearchRecord> searchRecords(String role, String interestsInput) {

        List<SearchRecord> searchRecords = new LinkedList<>();

        try {

            String sql = "SELECT concat(a.firstName,', ' , a.lastName) as 'name', afi.accountID as 'accountID', group_concat(' ', fi.interest) AS 'commonInterests' FROM " + role + "_interest as fi JOIN account_" + role + "_interest as afi on fi.interestID=afi.interestID JOIN account as a on afi.accountID=a.accountID WHERE fi.interest IN (";
            String[] interests = interestsInput.split(",");

            for(String interest : interests) {

                sql += "?,";
            }

            sql = sql.substring(0, sql.length() - 1);
            sql += ") group by afi.accountID order by count(fi.interest) desc";

            this.stmt = this.conn.prepareStatement(sql);

            for(int i = 1; i <= interests.length; i++) {

                this.stmt.setString(i, interests[i - 1]);
            }

            this.rs = this.stmt.executeQuery();

            while(this.rs.next()) {

                searchRecords.add(new SearchRecord(
                        this.rs.getInt("accountID"),
                        this.rs.getString("name"),
                        this.rs.getString("commonInterests")
                ));
            }

            reset();

        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return searchRecords;
    }

    public List<SearchRecord> facultySearch(String interestsInput) {

        return searchRecords("faculty", interestsInput);
    }

    public List<SearchRecord> studentSearch(String interestsInput) {

        return searchRecords("student", interestsInput);
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

            if(this.rs.next()) {

                office.setAccountID(accountID);
                office.setBuilding(this.rs.getString("building"));
                office.setNumber(this.rs.getString("number"));
            }

            reset();

        }catch (SQLException e) {

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

            if(this.rs.next()) {

                contact.setAccountID(accountID);
                contact.setEmail(this.rs.getString("email"));
                contact.setPhone(this.rs.getString("phone"));
            }

            reset();

        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return contact;
    }


    /***************************
     * FACULTY ABSTRACT SECTION
     ***************************/

    public List<Abstract> getFacultyAbstracts(int accountID) {

        List<Abstract> abstracts = new LinkedList<>();

        try{

            String sql = "SELECT * FROM abstract JOIN faculty_abstract USING (abstractID) WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            while(this.rs.next()) {

                abstracts.add(new Abstract(
                        this.rs.getInt("abstractID"),
                        this.rs.getString("title"),
                        this.rs.getString("body")
                ));
            }

            reset();

        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return abstracts;
    }

    public int addFacultyAbstract(int accountID, String title, String body) {

        int effected = 0;

        try{

            String sql = "INSERT INTO abstract (title, body) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.stmt.setString(1, title);
            this.stmt.setString(2, body);

            this.stmt.executeUpdate();
            this.rs = this.stmt.getGeneratedKeys();

            int abstractID = -1;

            if(this.rs.next()){

                abstractID = this.rs.getInt(1);
            }

            reset();

            if(abstractID == -1) return effected;

            sql = "INSERT INTO faculty_abstract (accountID, abstractID) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);
            this.stmt.setInt(2, abstractID);

            effected = this.stmt.executeUpdate();

            reset();

        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    public int removeFacultyAbstract(int abstractID) {

        int effected = 0;

        try{

            String sql = "DELETE FROM abstract WHERE abstractID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, abstractID);

            effected = this.stmt.executeUpdate();

            reset();

        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return effected;
    }

    /********************
     * INTERESTS SECTION
     ********************/

    private List<Interest> getInterests(String role, int accountID) {

        List<Interest> interests = new LinkedList<>();

        try{

            String sql = "SELECT * FROM " + role + "_interest JOIN account_" + role + "_interest USING (interestID) WHERE accountID = ?";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);

            this.rs = this.stmt.executeQuery();

            while(this.rs.next()) {

                interests.add(new Interest(
                        this.rs.getInt("interestID"),
                        this.rs.getString("interest")
                ));
            }

            reset();

        }catch (SQLException e){

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

        }catch (SQLException e) {

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

        try{

            String sql = "INSERT INTO " + role + "_interest (interest) VALUES (?)";

            this.stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.stmt.setString(1, interest);

            this.stmt.executeUpdate();
            this.rs = this.stmt.getGeneratedKeys();

            int interestID = -1;

            if(this.rs.next()){

                interestID = this.rs.getInt(1);
            }

            reset();

            if(interestID == -1) return effected;

            sql = "INSERT INTO account_" + role + "_interest (accountID, interestID) VALUES (?,?)";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setInt(1, accountID);
            this.stmt.setInt(2, interestID);

            effected = this.stmt.executeUpdate();

            reset();

        }catch (SQLException e) {

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

        //test functions below
        dl.addTeachAcc("john", "constantine", "yobro", "phone", "Johnny@this.dontmatter", "9999999", "somewhere", "over the rainbow");
        dl.addTeachAcc("sarah", "connor", "johnconnor", "in person visit", "3000@this.dontmatter", "55125851", "bunker", "z892e");
        dl.addStudAcc("Evan", "Jurdan", "Meow", "phone", "e@some.com", "my#");
        dl.addPubAcc("harry", "Styles", "song", "email", "AAAA@A.com", "908264348");

        dl.addStudentInterest(3, "Biology");
        dl.addStudentInterest(3, "Cars");
        dl.addStudentInterest(3, "trees");
        dl.addStudentInterest(4, "birds");

        dl.addFacultyInterest(1, "Biology");
        dl.addFacultyInterest(2, "Biology");

        dl.getFacultyInterests(1).forEach(item -> System.out.println(item));

//        dl.removeFacultyInterest(2); //interest ID

        dl.addFacultyAbstract(1, "cool", "even cooler.");
        dl.addFacultyAbstract(1, "coolest", "even beans.");
        dl.addFacultyAbstract(1, "cooler", "even test.");
        dl.addFacultyAbstract(1, "Hey Ho", "Hey! Come merry dol! derry dol! My darling! Light goes the weather-wind and the feathered starling. Down along under Hill, shining in the sunlight, Waiting on the doorstep for the cold starlight, There my pretty lady is, River-woman's daughter, Slender as the willow-wand, clearer than the water. Old Tom Bombadil water-lilies bringing Comes hopping home again. Can you hear him singing? Hey! Come merry dol! derry dol! and merry-o! Goldberry, Goldberry, merry yellow berry-o! Poor old Willow-man, you tuck your roots away! Tom's in a hurry now. Evening will follow day. Tom's going home again water-lilies bringing. Hey! Come derry dol! Can you hear me singing?");


        dl.getFacultyAbstracts(1).forEach(item -> System.out.println(item));

        dl.facultySearch("songs,trees").forEach(item -> System.out.println(item));
        dl.facultySearch("songs,trees,birds").forEach(item -> System.out.println(item));
        dl.facultySearch("Biology").forEach(item -> System.out.println(item));
        dl.facultySearch("trees").forEach(item -> System.out.println(item));
        dl.studentSearch("songs,trees").forEach(item -> System.out.println(item));
        dl.studentSearch("Biology").forEach(item -> System.out.println(item));
        dl.studentSearch("Cars").forEach(item -> System.out.println(item));
        //        dl.removeFacultyAbstract(1); //abstract ID

        System.out.println(dl.getContact(1));
        System.out.println(dl.getOffice(1));
    }


} // End of Class 
