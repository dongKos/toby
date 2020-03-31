package xmlTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class UserDao {

//	private ConnectionMaker connectionMaker;
//	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	private SimpleDriverDataSource dataSource;
	//DataSource ����ü�� ����ؼ� connection����
	public void setDataSource(SimpleDriverDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws Exception {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into user(id, name, pwd) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPwd());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws Exception {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from user where id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		
		User user = null;
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPwd(rs.getString("pwd"));
		}
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		ps.close();
		c.close();
		
		return user;
	}
	
	public void deleteAll() throws Exception {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("DELETE FROM user");
		
		ps.execute();
		
		ps.close();
		c.close();
	}
	
	public int getCount() throws Exception {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM user");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		rs.close();
		ps.close();
		c.close();
		return count;
	}
}
