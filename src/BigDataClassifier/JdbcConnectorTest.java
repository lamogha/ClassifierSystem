package src.BigDataClassifier;
import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnectorTest {
	
	public static void main(String[] args){
		Connection conn = null;
       try
       {

           String url = "jdbc:mysql://adegokeobasa.me:3306/classic_models";
           Class.forName ("org.gjt.mm.mysql.Driver");
           System.out.println("Driver loaded...");
           conn = DriverManager.getConnection (url,"lamogha","l@mmyPHD");
           System.out.println ("Database connection established");
       }
       catch (Exception e)
       {
           e.printStackTrace();

       }
        finally
       {
           if (conn != null)
           {
               try
               {
                   conn.close ();
                   System.out.println ("Database connection terminated");
               }
               catch (Exception e) { /* ignore close errors */ }
           }
       }
		
    }

}
