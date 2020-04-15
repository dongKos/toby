package toby.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import toby.user.domain.Level;
import toby.user.domain.User;

public class UserDaoJdbc implements UserDao{

//	private ConnectionMaker connectionMaker;
//	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
//	private SimpleDriverDataSource dataSource;
////	//DataSource 占쏙옙占쏙옙체占쏙옙 占쏙옙占쏙옙漫占� connection占쏙옙占쏙옙
////	public void setDataSource(SimpleDriverDataSource dataSource) {
////		this.dataSource = dataSource;
////	}
//	
//	public void setDataSource (SimpleDriverDataSource dataSource) {
//		//UserDao가 임시적으로 JdbcContext를 만드는 facotry 역할을 한다
//		//userDao와 db 연결 정보는 사실 긴밀한 관계여도 되기 때문에 
//		//예외적으로 이렇게도 한다
////		this.jdbcContext = new JdbcContext();
////		this.jdbcContext.SetDataSource(dataSource);
//		this.dataSource = dataSource;
//	}
//	
//	private JdbcContext jdbcContext;
//	
////	public void setJdbcContext(JdbcContext jdbcContext) {
////		//xml 설정 파일을 이용해서
////		//userDao -> jdbcContext -> dataSource 로 하도록 DI 하는방식
////	
////		this.jdbcContext = jdbcContext;
////	}
//	
	private SimpleDriverDataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(SimpleDriverDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//rowMapper 콜백도 하나로 만들어서 공유해버리기!
	private RowMapper<User> userMapper = 
		new RowMapper<User> () {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setEmail(rs.getString("EMAIL"));
				//
				user.setLevel(Level.valueOf(rs.getInt("LEVEL")));
				user.setLogin(rs.getInt("LOGIN"));
				user.setRecommend(rs.getInt("RECOMMEND"));
				return user;
			}
		
	};
	
	public void add(final User user) {
		//3. local Class - can delete all class file
//		매개변수 user를 final로 선언하면 내부클래스 에서도 접근할 수 있다.
//		class AddStatement implements StatementStrategy {
////			User user;
////			public AddStatement(User user) {
////				this.user = user;
////			}
//			@Override
//			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//				PreparedStatement ps =
//						c.prepareStatement("insert into user(id, name, pwd) values(?, ?, ?)");
//				ps.setString(1, user.getId());
//				ps.setString(2, user.getName());
//				ps.setString(3, user.getPwd());
//				
//				return ps;
//			}
//		}
		
//		StatementStrategy strategy = new AddStatement(user);
		
		//1. error 처리 - 중복키 상황에 대한 사용자 정의 에러 설정
		//나머지는 RuntimeException으로 포장
//		try {
//			StatementStrategy strategy = new AddStatement();
//			jdbcContextWithStatementStrategy(strategy);
//		} catch (SQLException e) {
//			
//			if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
//				throw new DuplicateUserIdException(e);
//			} else {
//				throw new RuntimeException(e);
//			}
//		}
		
		
		//jdbc Template 적용
		//jdbcTemplte이 제공하는 DuplicateKeyException을 메소드 정의 부에 사용 할 수도 있고
		//강제화 하여 로그를 남기는 등의 작업을 시킬 수도 있다.
//		try {
//			this.jdbcTemplate.update(
//					"insert into user values (?, ?, ?)",
//					user.getId(), user.getName(), user.getPwd());
//		} catch(DuplicateKeyException e) {
//			throw new DuplicateUserIdException(e);
//		}

		this.jdbcTemplate.update(
			"insert into user values (?, ?, ?, ?, ?, ?, ?)",
				user.getId(), user.getName(), user.getPwd(), user.getEmail(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend());
		
	}
	
	public User get(String id) {
		//Connection c = dataSource.getConnection();
//		Connection c = dataSource.getConnection();
//		
//		PreparedStatement ps = c.prepareStatement("select * from user where id = ?");
//		ps.setString(1, id);
//		ResultSet rs = ps.executeQuery();
//		
//		User user = null;
//		if(rs.next()) {
//			user = new User();
//			user.setId(rs.getString("id"));
//			user.setName(rs.getString("name"));
//			user.setPwd(rs.getString("pwd"));
//		}
//		
//		if(user == null) throw new EmptyResultDataAccessException(1);
//		
//		ps.close();
//		c.close();
//		
//		return user;
		
		//queryForInt 보다 복잡한 Object 타입의 결과를 받아주는
		//queryForObject();
		
		return this.jdbcTemplate.queryForObject("select * from user where id=?",
			new Object[] {id},
			//resultSet 결과를 object(User)에 매핑해주는 RowMapper
//			new RowMapper<User> () {
//				@Override
//				public User mapRow(ResultSet rs, int rownum) throws SQLException {
//					User user = new User();
//					user.setId(rs.getString("id"));
//					user.setName(rs.getString("name"));
//					user.setPwd(rs.getString("pwd"));
//					return user;
//				}
//			}
			userMapper
		);
	}
	
	public void deleteAll() {
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
		
		//템플릿/ 콜백패턴 - 클래스 내부에 콜백 존재
		//executeSql("delete from user");
		
		//jdbcContext 에서 콜백을 전부 관리
		//this.jdbcContext.executeSql("delete from user");
		
		//jdbc template 적용 - interface 만들 필요없는 내장 객체
//		this.jdbcTemplate.update(
//				new PreparedStatementCreator() {
//					public PreparedStatement createPreparedStatement(Connection con) 
//							throws SQLException {
//						return con.prepareStatement("delete from user");
//					}
//				}
//			);
		
		
		this.jdbcTemplate.update("delete from user");
	}
	
	//클래스 내부에 템플릿 존재
//	private void executeSql(final String query) throws Exception{
//		this.jdbcContext.workWithStatementStrategy(
//			new StatementStrategy() {
//				@Override
//				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//					return c.prepareStatement(query);
//				}
//			}
//		);
//		
//	}

	public int getCount() {
//		Connection c = dataSource.getConnection();
//		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM user");
//		ResultSet rs = ps.executeQuery();
//		
//		rs.next();
//		int count = rs.getInt(1);
//		rs.close();
//		ps.close();
//		c.close();
//		return count;
		
		
		//리턴 타입이 있는경우 2개의 콜백이 필요하다
		//쿼리를 만드는 PreparedStatementCreator, 
		//결과를 받아서 전달하는 ResultSetExtractor
//		return this.jdbcTemplate.query(
//			new PreparedStatementCreator() {
//				public PreparedStatement createPreparedStatement (Connection con) 
//						throws SQLException {
//					return con.prepareStatement("select count(*) from user");
//				}
//		},new ResultSetExtractor<Integer> () {
//				@Override
//				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//					rs.next();
//					return rs.getInt(1);
//				}
//			
//		});
		
		//이중 콜백 필요없이 결과를 int값 하나로 받아주는 콜백 메소드queryForInt() 사용
		return this.jdbcTemplate.queryForInt("select count(*) from user");
	}
	
	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from user order by id",
//			new RowMapper<User> () {
//				@Override
//				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//					User user = new User();
//					user.setId(rs.getString("id"));
//					user.setName(rs.getString("name"));
//					user.setPwd(rs.getString("pwd"));
//					return user;
//				}
//			}
			userMapper
		);
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

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				"UPDATE USER "
				+ "SET NAME = ?, "
				+ "PWD = ?, "
				+ "EMAIL = ?, "
				+ "LEVEL = ?, "
				+ "LOGIN = ?, "
				+ "RECOMMEND = ? "
				+ "WHERE ID = ?",
				user.getName(), user.getPwd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
		
		
	}
	
}
