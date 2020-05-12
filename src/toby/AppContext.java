package toby;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.Driver;

import toby.test.UserServiceTest.TestUserServiceImpl;
import toby.user.dao.UserDao;
import toby.user.service.DummyMailSender;
import toby.user.service.UserService;

@Configuration
@EnableTransactionManagement // <tx:annotation-driven/>
@ComponentScan(basePackages = "toby.user")
//@Import(SqlServiceContext.class)
@EnableSqlService
@PropertySource("/database.properties")
public class AppContext implements SqlMapConfig {

//	@Autowired
//	UserDao userDao;
	
	@Value("${db.driverClass}") Class<? extends Driver> driverClass; 
	@Value("${db.url}") String url; 
	@Value("${db.username}") String username; 
	@Value("${db.password}") String password; 
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Autowired Environment env;
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
//		ds.setDriverClass(Driver.class);
//		ds.setUrl("jdbc:mysql://localhost/testdb?characterEncoding=UTF-8");
//		ds.setUsername("root");
//		ds.setPassword("root");
		
//		try {
//			ds.setDriverClass((
//					Class<? extends java.sql.Driver>) Class.forName(env.getProperty("db.driverClass")));
//		} catch(ClassNotFoundException e) {
//			throw new RuntimeException();
//		}
//		
//		ds.setUrl(env.getProperty("db.url"));
//		ds.setUsername(env.getProperty("db.username"));
//		ds.setPassword(env.getProperty("db.password"));
		
		ds.setDriverClass(this.driverClass);
		ds.setUrl(this.url);
		ds.setUsername(this.username);
		ds.setPassword(this.password);
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Configuration
	@Profile("production")
	public static class ProductionAppContext {

		@Bean
		public MailSender mailSender() {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost("localhost");
			return mailSender;
		}
	}
	
	@Configuration
	@Profile("test")
	public static class TestAppContext {
		
		@Bean
		public UserService testUserService() {
			return new TestUserServiceImpl();
		}
		
		@Bean
		public MailSender mailSender() {
			return new DummyMailSender();
		}
		
	}
	
	@Override
	public Resource getSqlMapResource() {
		return new ClassPathResource("sqlmap.xml", UserDao.class);
	}

}
