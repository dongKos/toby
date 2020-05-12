package toby.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import toby.user.domain.Level;
import toby.user.domain.User;
import toby.user.sqlservice.SqlService;

//@Component("userDao")
@Repository
public class UserDaoJdbc implements UserDao {
	
	/* private SimpleDriverDataSource dataSource; */
	
	private JdbcTemplate jdbcTemplate;
	
//	private String sqlAdd;
//	
//	public void setSqlAdd(String sqlAdd) {
//		this.sqlAdd = sqlAdd;
//	}
	
//	private Map<String, String> sqlMap;
//	
//	public void setSqlMap(Map<String, String> sqlMap) {
//		this.sqlMap = sqlMap;
//	}
	
	@Autowired
	private SqlService sqlService;
	
	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate( dataSource);
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
	
	public void add(final User user)   {
		this.jdbcTemplate.update(this.sqlService.getSql("userAdd"),
				user.getId(), user.getName(), user.getPwd(), user.getEmail(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend());
		
	}
	
	public User get(String id)  {
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
				new Object[] {id},
				userMapper
				);
	}
	
	public void deleteAll()   {
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}
	
	public int getCount()  {
		//이중 콜백 필요없이 결과를 int값 하나로 받아주는 콜백 메소드queryForInt() 사용
		return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
	}
	
	public List<User> getAll()  {
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), userMapper);
	}
	
//	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//		
//		try {
//			c = dataSource.getConnection();
//			ps = stmt.makePreparedStatement(c);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if(ps != null) try {ps.close();} catch (SQLException e) {}
//			if(c != null) try {c.close();} catch (SQLException e) {}
//		}
//	}
	
	@Override
	public void update(User user)  {
		this.jdbcTemplate.update(
				this.sqlService.getSql("userUpdate"),
				user.getName(), user.getPwd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
		
		
	}
	
}
