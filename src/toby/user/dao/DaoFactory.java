package toby.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

//@Configuration
public class DaoFactory {
	
//	@Bean
//	public UserDao userDao() {
//		UserDao userDao = new UserDaoJdbc();
//		//userDao.setDataSource(dataSource());
//		return userDao;
//	}
//
//	@Bean
//	public SimpleDriverDataSource dataSource() {
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		
//		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
//		dataSource.setUrl("jdbc:mysql://localhost:3306/dongko?characterEncoding=UTF-8&serverTimezone=UTC");
//		dataSource.setUsername("root");
//		dataSource.setPassword("root");
//		
//		return dataSource;
//	}
}
