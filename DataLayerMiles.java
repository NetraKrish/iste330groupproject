package groupproject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;
import java.security.MessageDigest;

public class DataLayerMiles {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stmt;

    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    public DataLayerMiles() {

        this.conn = null;
        this.rs = null;
        this.stmt = null;
    }

    // UTILITIES

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

    public void SQLExceptionMsg(String e) {

        System.out.println(">> SQLException caught\n>> " + e);
    }

    /**
     * Whole idea of project is to search for students & faculty.
     *
     * Can be searched by:
     *
     * Students:
     * - First/Last name
     * - Interests
     *
     * Faculty:
     * - First/Last name
     * - Interests
     * - Abstracts
     *
     * Accounts that can search:
     * Everyone.
     *
     * Interests (keyword) are a short phrase, or title. (ex. Backend Programming)
     */

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

    public static void main(String[] args) throws NoSuchAlgorithmException {

        Scanner reader = new Scanner(System.in);

        DataLayerMiles dl = new DataLayerMiles();
//        DataLayerEvan dl = new DataLayerEvan();

//        System.out.println("username: ");
//        String user = reader.nextLine();
//
//        System.out.println("password: ");
//        String pass = reader.nextLine();
//
//        System.out.println("database: ");
//        String db = reader.nextLine();
//
//        dl.connect(user, pass, db);

        //test functions below

//        dl.addFacultyInterest(1, "Java Coding");
//        dl.addStudentInterest(3, "Biology");

//        dl.removeStudentInterest(1);
//        dl.removeFacultyInterest(1);

//        dl.addFacultyAbstract(1, "cool", "even cooler.");
//        dl.removeFacultyAbstract(1);

//        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//        messageDigest.update("password".getBytes());
//        String hash = Base64.getEncoder().encodeToString(messageDigest.digest());

//        System.out.println(hash);
    }


}
