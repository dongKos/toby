package xmlTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class UserDao {

//	private ConnectionMaker connectionMaker;
//	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	private SimpleDriverDataSource dataSource;
////	//DataSource 占쏙옙占쏙옙체占쏙옙 占쏙옙占쏙옙漫占� connection占쏙옙占쏙옙
////	public void setDataSource(SimpleDriverDataSource dataSource) {
////		this.dataSource = dataSource;
////	}
//	
	public void setDataSource (SimpleDriverDataSource dataSource) {
		//UserDao가 임시적으로 JdbcContext를 만드는 facotry 역할을 한다
		//userDao와 db 연결 정보는 사실 긴밀한 관계여도 되기 때문에 
		//예외적으로 이렇게도 한다
//		this.jdbcContext = new JdbcContext();
//		this.jdbcContext.SetDataSource(dataSource);
		this.dataSource = dataSource;
	}
	
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		//xml 설정 파일을 이용해서
		//userDao -> jdbcContext -> dataSource 로 하도록 DI 하는방식
	
		this.jdbcContext = jdbcContext;
	}
	
	
	
	
	public void add(final User user) throws Exception {
		
		//3. local Class - can delete all class file
		//매개변수 user를 final로 선언하면 내부클래스 에서도 접근할 수 있다.
		class AddStatement implements StatementStrategy {
//			User user;
//			
//			public AddStatement(User user) {
//				this.user = user;
//			}
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps =
						c.prepareStatement("insert into user(id, name, pwd) values(?, ?, ?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPwd());
				
				return ps;
			}
		}
		
//		StatementStrategy strategy = new AddStatement(user);
		StatementStrategy strategy = new AddStatement();
		jdbcContextWithStatementStrategy(strategy);
	}
	
	public User get(String id) throws Exception {
		//Connection c = dataSource.getConnection();
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
		//1. original code
//		Connection c = null;
//		PreparedStatement ps = null;
//		
//		try {
//			c = dataSource.getConnection();
//			ps = c.prepareStatement("delete from user");
//			ps.executeUpdate();
//		} catch(SQLException e) {
//			throw new SQLException();
//		} finally {
//			if(ps != null)  
//			{
//				try {ps.close();} catch (SQLException e) {}
//			}
//			if(c != null) 
//			{
//				try {c.close();} catch (SQLException e) {}
//			}
//		}
		
		//2. deal with interface, method - a lot of class 
//		StatementStrategy strategy = new DeleteAllStatement();
//		jdbcContextWithStatementStrategy(strategy);
		
		
		//4. 익명 내부 클래스
//		jdbcContextWithStatementStrategy(
//			new StatementStrategy() {
//				@Override
//				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//					return c.prepareStatement("delete from user");
//				}
//			}
//		);
		
		//di 받은  jdbcContext bean을 이용
//		this.jdbcContext.workWithStatementStrategy(
//			new StatementStrategy() {
//				@Override
//				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//					return c.prepareStatement("delete from user");
//				}
//			}
//		);
		
		//템플릿/ 콜백패턴 - 클래스 내부에 템플릿 존재
		//executeSql("delete from user");
		
		//jdbcContext 에서 콜백을 전부 관리
		this.jdbcContext.executeSql("delete from user");
	}
	
	//클래스 내부에 템플릿 존재
	private void executeSql(final String query) throws Exception{
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					return c.prepareStatement(query);
				}
			}
		);
		
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
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null) try {ps.close();} catch (SQLException e) {}
			if(c != null) try {c.close();} catch (SQLException e) {}
		}
	}
}
