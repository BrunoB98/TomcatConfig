package objectUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDatabase {
	protected DBManager db;
	protected ResultSet rs;
	
	public MyDatabase() {
		try {
			db = DBManager.getInstance(DBManager.JDBCDriverMySQL, DBManager.JDBCURLMySQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			db.executeQuery("SELECT * FROM user LIMIT 1");
		} catch (SQLException e) {
			try {
			db.executeUpdate("DROP TABLE IF EXISTS user");
			db.executeUpdate("CREATE TABLE user (username VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL, age INTEGER, admin BIT DEFAULT 0, PRIMARY KEY (username, password), CHECK (age>=18))");
			db.executeUpdate("INSERT INTO user (username, password, age, admin) VALUES('root', 'root', 30, 1)");
			
			db.executeUpdate("CREATE TABLE product (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(30) UNIQUE NOT NULL, price DOUBLE NOT NULL, description VARCHAR(200) DEFAULT 'Description not available.', CHECK (price>0.0))");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (12, 'Lampada', 12.99, 'Lampada nera per ambiente dark.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (144, 'Asciugamano', 7.99, 'Asciugamano da mare in microfibra.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (11, 'Sdraio', 25.50, 'Sdraio da giardino.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (110, 'Tavolo', 150.0, 'Tavolo da cucina 160x70x80.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (132, 'Telo mare', 5.0, 'Telo mare 80x90 in cotone.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (155, 'Sedia', 40.0, 'Sedia in legno di ciliegio.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (33, 'Ombrellone', 10.50, 'Ombrellone giallo, altezza 180cm.')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (13, 'Sedile', 25.50, 'Sedile auto opel')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (14, 'Computer', 150.0, 'Asus 12 GB ram')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (233, 'Porta scorrevole', 50.0, 'Altezza 190 cm')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (333, 'Pinocchio', 40.0, 'Libro vecchio')");
			db.executeUpdate("INSERT INTO product (id, name, price, description) VALUES (197, 'Chiavetta USB', 10.50, 'Chiavetta USB 10 GB')");
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertUser(String username, String password, int age) {
		try {
			String query = String.format("INSERT INTO user (username, password, age, admin) VALUES('%s','%s', %d, 0)", username, password, age);
			db.executeUpdate(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isPresent(String username, String password) {
		try {
			String query = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'", username, password);
			rs = db.executeQuery(query);
			if(rs.first()) {
				while (rs.next()) {
					printRow(rs);
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet selectAllProducts() {
		try {
			rs = db.executeQuery("SELECT * FROM product");
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
/*	public boolean insertOrder(Order o) {
		try {
			int ido, idp;
			do {
				ido = db.randInt();
			} while (orderPresent(ido));
			String query = String.format("INSERT INTO ordini (id, total, date, receiver) VALUES (%d, %5.2f, '%s', '%s')", ido, o.total, o.date, o.receiver);
			db.executeUpdate(query);
			for (Product p : o.list) {
				idp = selectProduct(p.name);
				query = String.format("SELECT * FROM ordini WHERE idp = %d AND ido = %d", idp, ido);
				rs = db.executeQuery(query);
				if(rs.first()) {
					int count = rs.getInt("quantity");
					count++;
					query = String.format("UPDATE po SET quantity = %d WHERE idp = %d AND ido = %d", count, idp, ido);
					
				} else {
					query = String.format("INSERT INTO po (idp, ido, quantity) VALUES (%d, %d, %d)", idp, ido, 1);
				}
				db.executeUpdate(query);	
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	public int selectProduct(String name) {
		try {
			String query = String.format("SELECT id FROM product WHERE name LIKE '%s'", name);
			rs = db.executeQuery(query);
			return rs.getInt("id");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/*public boolean orderPresent (int id) {
		try {
			String query = String.format("SELECT * FROM ordini WHERE id = %d", id);
			rs = db.executeQuery(query);
			if(rs.first()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void testSelect() {
		try {
			rs = db.executeQuery("SELECT * FROM user");
			while (rs.next()) {
				printRow(rs);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void printRow(ResultSet rs) throws SQLException {
		System.out.println("username=" + rs.getString("username") + ", password="
				+ rs.getString("password") + ", age=" + rs.getInt("age") + ", admin=" + rs.getBoolean("admin"));

	}
	
	/*public static void main(String[] args) {
		try {
			String s = System.getenv("MY_SQLBB");
			System.out.println(s);
			MyDatabase d = new MyDatabase();
			d.insert("cane", "cazzo", 44);
			d.testSelect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
