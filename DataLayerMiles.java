package groupproject;

import java.sql.*;

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
            if(this.rs != null)
                this.rs.close();

            if(this.stmt != null)
                this.stmt.close();

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

    public int addStudentInterest(String interest) {

        try{

            String sql = "INSERT INTO student_interest (interest) VALUES (?)";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setString(1, interest);



        }catch (SQLException e) {

            SQLExceptionMsg(e.getMessage());
        }

        return 0;
    }

    public int addFacultyInterest() {

        return 0;
    }

    public int addGuestInterest() {

        return 0;
    }


}
