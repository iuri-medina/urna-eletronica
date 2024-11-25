package urna_eletronica.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	private static String db_host = "127.0.0.1";
	private static String db_port = "5432";
	private static String db_name = "urna_eletronica";
	private static String db_password = "123456";
	private static String db_username = "postgres";
	
	

	public static Connection conectar() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection("jdbc:postgresql://" + db_host + ":" + db_port + "/" + db_name, db_username,
					db_password);

		}
		catch (ClassNotFoundException ex) {
			System.out.println("Driver do banco de dados não encontrado.");
		}
		return null;
	}
}