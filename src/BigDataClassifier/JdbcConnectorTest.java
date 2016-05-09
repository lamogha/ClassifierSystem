package BigDataClassifier;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConnectorTest {
	
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/test";
	
	static final String USERNAME = "root";
	static final String PASSWORD = "";
			
	public static void main(String[] args){
		
		JdbcConnectorTest jdbctest = new JdbcConnectorTest();
		jdbctest.getResult();
	}
	
	private void getResult(){
		Connection connection = null;
		java.sql.Statement stmt = null;
		try
		{
			Class.forName(JDBC_DRIVER);
			
			System.out.println("connecting....");
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	
			System.out.println("connection successful");
			System.out.println("Creating statment........");
			
			stmt = connection.createStatement();
			String sql = "select * from LamoghaTest";
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);
			
			while (rs.next())
			{
				int id = rs.getInt("id");
				String firstname = rs.getString("firstName");
				String lastname = rs.getString("surName");
				
				System.out.println(id + " "+ firstname +" "+ lastname);

			}
			rs.close();
		}
		catch (SQLException se){
			se.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
		finally{
			try{
				if (stmt != null){
					((java.sql.Statement) stmt).close();
				}
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
			}
			try{
				if(connection!=null){
					connection.close();
				}
			}
			catch (SQLException sqlException){
				
			}
		}
		
	}

}
