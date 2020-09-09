package objectUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DBManager {
	private static final String ADDRESS = System.getenv("APPDB_SERVICE_HOST");
	private static final String DBNAME = System.getenv("APPDB_NAME");
	private static final String ROOT_HOSTNAME = System.getenv("APPDB_USER");
	private static final String ROOT_PASSWORD = System.getenv("APPDB_PASS");
	private static final String PORT = System.getenv("APPDB_SERVICE_PORT");
	public static final String JDBCDriverMySQL = "com.mysql.jdbc.Driver";
	public static final String JDBCURLMySQL = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/"+ DBNAME + "?user=" + ROOT_HOSTNAME + "&password=" + ROOT_PASSWORD + "&useSSL=false";
	protected Statement statement;
	protected Connection connection;
	private static DBManager uniqueInstance;
	
	public static DBManager getInstance(String JDBCDriver, String JDBCURL) throws ClassNotFoundException, SQLException {
		if (uniqueInstance == null) {
			uniqueInstance = new DBManager(JDBCDriver, JDBCURL);
		}
		return uniqueInstance;
	}
	
	public static DBManager getInstance(String JDBCDriver, String JDBCURL, int resultSetType, int resultSetConcurrency) throws ClassNotFoundException, SQLException {
		System.out.println(JDBCURL);
		if (uniqueInstance == null) {
			uniqueInstance = new DBManager(JDBCDriver, JDBCURL, resultSetType, resultSetConcurrency);
		}
		return uniqueInstance;
	}
	
	private DBManager(String JDBCDriver, String JDBCURL, int resultSetType, int resultSetConcurrency) throws ClassNotFoundException, SQLException {
		Class.forName(JDBCDriver);
		try {
			connection = DriverManager.getConnection(JDBCURL);
			statement = connection.createStatement(resultSetType, resultSetConcurrency);
		} catch (Exception e) {
			connection = DriverManager.getConnection("jdbc:mysql://" + ADDRESS + ":" + PORT + "/mysql", ROOT_HOSTNAME, ROOT_PASSWORD);
			statement = connection.createStatement(resultSetType, resultSetConcurrency);
			statement.executeUpdate("CREATE DATABASE " + DBNAME);
			statement.executeUpdate("USE " + DBNAME);
		}
		statement.setQueryTimeout(30);
		showMetadata();
	}
	
	private DBManager(String JDBCDriver, String JDBCURL) throws ClassNotFoundException, SQLException {
		Class.forName(JDBCDriver);
		try {
			connection = DriverManager.getConnection(JDBCURL);
			statement = connection.createStatement();
		} catch (Exception e) {
			connection = DriverManager.getConnection("jdbc:mysql://" + ADDRESS + ":" + PORT + "/mysql", ROOT_HOSTNAME, ROOT_PASSWORD);
			statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + DBNAME);
			statement.executeUpdate("USE " + DBNAME);
		}
		statement.setQueryTimeout(30);
		showMetadata();
	}

	public void showMetadata() throws SQLException {
		DatabaseMetaData md = connection.getMetaData();

		System.out.println("-- ResultSet Type --");
		System.out.println("Supports TYPE_FORWARD_ONLY: " + md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
		System.out.println(
				"Supports TYPE_SCROLL_INSENSITIVE: " + md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
		System.out.println(
				"Supports TYPE_SCROLL_SENSITIVE: " + md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));

		System.out.println("-- ResultSet Concurrency --");
		System.out.println("Supports CONCUR_READ_ONLY: " + md.supportsResultSetType(ResultSet.CONCUR_READ_ONLY));
		System.out.println("Supports CONCUR_UPDATABLE: " + md.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));
	}

	public ResultSet executeQuery(String query) throws SQLException {
		return statement.executeQuery(query);
	}

	public int executeUpdate(String query) throws SQLException {
		return statement.executeUpdate(query);
	}

	public void close() throws SQLException {
		if (connection != null) {
			statement.close();
			connection.close();
		}
	}
		
	public int randInt() {
		Random rand = new Random();
		int randomNum = rand.nextInt((10000 - 1) + 1) + 1;
		return randomNum;
	}
}
