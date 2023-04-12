package groupproject;

import java.sql.*;
import java.util.*;
public class facultyResearchDataLayer{

   private Connection conn;
   private ResultSet rs;
   private PreparedStatement stmt;
   private String sql;
   private int col;

   final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

   public facultyResearchDataLayer(){
   }//end of constructor

   public boolean connect(){
      conn = null;
      String userName = "root";
      String password = "student123";
      String url = "jdbc:mysql://localhost/researchdb";

      url = url + "?serverTimezone=UTC"; //added 8/27

  try{
         Class.forName(DEFAULT_DRIVER);
         conn = DriverManager.getConnection(url, userName, password);
         System.out.println("\nCreated Connection!\n");
      }
      catch(ClassNotFoundException cnfe){
		 System.out.println("ERROR, CAN NOT CONNECT!!");
         System.out.println("Class");
         System.out.println("ERROR MESSAGE-> "+cnfe);
         System.exit(0);
      }
      catch(SQLException sqle){
		 System.out.println("ERROR SQLExcepiton in connect()");
		 System.out.println("ERROR MESSAGE -> "+sqle);
         sqle.printStackTrace();
         System.exit(0);
      }//end of catch

      return (conn!=null);
   } // End of connect method
   public void inputAbstract()
   {Scanner sc = new Scanner(System.in);

    System.out.println("Enter Faculty ID: ");
    int fID = sc.nextInt();
    // verify if the ID belongs to a faculty
    sql = "SELECT COUNT(*) FROM account WHERE accountID =? AND roleID = 2";
    try {
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1,fID);
        rs = stmt.executeQuery();
        int count = rs.getInt(0);
        if (count ==0)
        System.out.println("Invalid ID or account does not belong to faculty");
        else
        {// Get abstract details from faculty
            String abstractTitle = sc.nextLine();
        String abstractContent = sc.nextLine();
        // Add to abstract table
        sql = "INSERT INTO abstract VALUES(?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,abstractTitle);
        stmt.setString(2,abstractContent);
        int rows = stmt.executeUpdate();
        System.out.println("Rows impacted : " + rows);
        // retrieve abstract ID of the specific abstract from the autoincrementing abstract table
        sql = "SELECT abstractID FROM abstract WHERE abstractTitle = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,abstractTitle);
        rs = stmt.executeQuery();
        int abID = rs.getInt(0);
        // insert the retrieved abstract ID into faculty_abstract table
        sql = "INSERT INTO faculty_abstract VALUES (?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1,fID);
        stmt.setInt(2,abID);
        rows = stmt.executeUpdate();
        System.out.println("Rows impacted : " + rows);
        }
    } 
    catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
sc.close();


   }
   public void studentSearch()
   {
    Scanner sc = new Scanner(System.in);
   
    System.out.println("Enter Student ID: ");
    int sID = sc.nextInt();
    //verify if ID belongs to student
    sql = "SELECT COUNT(*) FROM account WHERE accountID =? AND roleID = 1";
    try {
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1,sID);
        rs = stmt.executeQuery();
        int count = rs.getInt(0);
        if (count ==0)
        System.out.println("Invalid ID or account does not belong to a Student");
        else
        {  //retrieve interests of student
             ArrayList<Integer> interests = new ArrayList<>();
            System.out.println("Enter Interests: ");
            int ip = sc.nextInt();
            while (ip!=0)
            {
                interests.add(ip);
                ip=0;
                ip = sc.nextInt();
            }

        }

   }
   catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}}
   
   public void close(){
      try {
         rs.close();
         stmt.close();
         conn.close();
      }
      catch(SQLException sqle){
        System.out.println("ERROR IN METHOD close()");
        System.out.println("ERROR MESSAGE -> "+sqle);
      }// end of catch
   }//end of method close
} // End of Class   DataLayer.java

