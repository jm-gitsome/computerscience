// Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.json.*;
 
public class WemsServlet extends HttpServlet{
    
      // JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/WEMS_DATA_LOG";

      //  Database credentials
      static final String USER = "pi";
      static final String PASS = "";
      

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      
      String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      
      out.println(docType +
         "<html>\n" +
         "<head><title>" + title + "</title></head>\n" +
         "<body bgcolor = \"#f0f0f0\">\n" +
         "<h1 align = \"center\">" + title + "</h1>\n");
 
      Statement stmt = null;
      Connection conn = null;   
      try {
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");
         
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, USER, PASS);

         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT id, Voltage, Current, RealPower, ReactivePower FROM PowerTest";
         ResultSet rs = stmt.executeQuery(sql);

         // Extract data from result set
         while(rs.next() && rs.getInt("id") < 10){
            //Retrieve by column name
            double Voltage  = rs.getDouble("Voltage");
            double Current = rs.getDouble("Current");
            double RealPower  = rs.getDouble("RealPower");
            double ReactivePower = rs.getDouble("ReactivePower");

            //Display values
            out.println("Voltage:" + Voltage + " Current:" + Current + " RealPower:" + RealPower + " Reactive Power:" + ReactivePower + "<br>");

         }
         out.println("</body></html>");

         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      } catch(SQLException se) {
         //Handle errors for JDBC
         out.println("SQLException"+ "<br>");
         se.printStackTrace();
      } catch(Exception e) {
         //Handle errors for Class.forName
        out.println("Exception e"+ "<br>");
        e.printStackTrace();
      } finally {
         //finally block used to close resources
         try {
            if(stmt !=null)
               stmt.close();
         } catch(SQLException se2) {
         } // nothing we can do
         try {
            if(conn !=null)
            conn.close();
         } catch(SQLException se) {
            se.printStackTrace();
         } //end finally try
      } //end try
   }
   
   int Reference = 0;
   
    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("application/json");
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST");
      PrintWriter out = response.getWriter();
         

      Statement stmt = null;
      Connection conn = null;   
      try {
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");
      
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, USER, PASS);

         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT id, Voltage, Current, RealPower, ReactivePower FROM PowerTest";
         ResultSet rs = stmt.executeQuery(sql);
         //rs.next();

         // Extract data from result set
         //while(rs.next() && rs.getInt("id") < 10){
         //Retrieve by column name

               
         JSONObject mainobj = new JSONObject();
         JSONArray array = new JSONArray();
         
         try {
         
               
            while(rs.next() && rs.getInt("id") <= 100){
         //Retrieve by column name
         
               int ID = rs.getInt("id");
               double Voltage  = rs.getDouble("Voltage");
               double Current = rs.getDouble("Current");
               double RealPower  = rs.getDouble("RealPower");
               double ReactivePower = rs.getDouble("ReactivePower");
               JSONObject temp = new JSONObject();
               
               temp.put("Voltage", Voltage);
               temp.put("Current", Current);
               temp.put("RealPower", RealPower);
               temp.put("ReactivePower", ReactivePower);
               temp.put("ID", ID);
               array.put(temp);
            }
            mainobj.put( "PowerData", array);

         } catch (JSONException e) {}
           
          out.print(mainobj);
        

         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      } catch(SQLException se) {
      //Handle errors for JDBC
      } catch(Exception e) {
      //Handle errors for Class.forName
      } finally {
      //finally block used to close resources
         try {
            if(stmt !=null)
               stmt.close();
         } catch(SQLException se2) {
         } // nothing we can do
         try {
            if(conn !=null)
            conn.close();
         } catch(SQLException se) {
         } //end finally try
      } //end try 
      
   }
} 
