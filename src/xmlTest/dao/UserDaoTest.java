package xmlTest.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	
	User user1 = new User("id01", "name01", "pwd01");
	User user2 = new User("id02", "name02", "pwd02");
	User user3 = new User("id03", "name03", "pwd03");
	
//	@Before 
//	public void setUp() {
//		this.dao = context.getBean("userDao", UserDao.class);
//	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws Exception {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("id0000");
	}
	
	@Test
	public void addAndGet() throws Exception {
		User user1 = new User("id04", "name04", "pwd04");
		User user2 = new User("id05", "name05", "pwd05");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPwd(), is(user1.getPwd()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPwd(), is(user2.getPwd()));
	}
	
	@Test
	public void count() throws Exception {
		User user = new User("id01", "name01", "pwd01");
		User user2 = new User("id02", "name02", "pwd02");
		User user3 = new User("id03", "name03", "pwd03");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		
		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user1, users3.get(0));
		checkSameUser(user2, users3.get(1));
		checkSameUser(user3, users3.get(2));
		
	}
	
	public void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPwd(), is(user2.getPwd()));
	}

	public static void main(String[] args) throws Exception {
		JUnitCore.main("xmlTest.dao.UserDaoTest");
	}
}

