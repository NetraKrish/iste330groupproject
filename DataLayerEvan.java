package HW.GroupProject;
// AUthors Evan Jurdan, 

import java.sql.*;
import java.util.*;
public class DataLayerEvan {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stmt;
    private String sql;
    private int col;

    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    public DataLayerEvan(){
        this.conn = null;
        this.rs = null;
        this.stmt = null;
    }//end of constructor


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

    public int accID(String fNam, String lNam){
        int acID = 0;
    try {

            String sql = "SELECT accountID from account WHERE firstName = ? AND lastName = ? LIMIT 1";

            this.stmt = this.conn.prepareStatement(sql);
            this.stmt.setString(1, fNam);
            this.stmt.setString(2, lNam);
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
        try{
            Statement stmt = conn.
             createStatement();;
            PreparedStatement stmt1;
            stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt1.setString(1,fNam);      
            stmt1.setString(2,lNam);   
            stmt1.setString(3,pass);
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
    try{
        Statement stmt = conn.
         createStatement();;
        PreparedStatement stmt1;
        stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmt1.setString(1,fNam);      
        stmt1.setString(2,lNam);   
        stmt1.setString(3,pass);
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

   public void addPubAcc(String fNam,String lNam,String pass,String prefCon,String emailAdd,String PhoNum){
    int affected = 0;
    int affected3 = 0;
    int acID = 0; 
    try{
        Statement stmt = conn.
         createStatement();;
        PreparedStatement stmt1;
        stmt1 = conn.prepareStatement("INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmt1.setString(1,fNam);      
        stmt1.setString(2,lNam);   
        stmt1.setString(3,pass);
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






























} // End of Class   DataLayer.java