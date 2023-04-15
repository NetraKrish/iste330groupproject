package GroupProject;

// AUthors Evan Jurdan, Miles Krassen, Tim Lonergan, Kaszuba Daniel, Krassen Miles, Krishnan Netra, Landers Seth


import GroupProject.objects.Abstract;
import GroupProject.objects.Interest;
import GroupProject.objects.Contact;
import GroupProject.objects.SearchRecord;

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

        try{

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


} // End of Class 
