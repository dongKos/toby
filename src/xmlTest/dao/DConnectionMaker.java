package xmlTest.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DConnectionMaker implements ConnectionMaker{

	@Override
	public Connection makeConnection() throws Exception{
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/dongko?characterEncoding=UTF-8&serverTimezone=UTC", "root", "root");
		return c;
	}

}
