package xmlTest.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
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
		
<<<<<<< HEAD
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
=======
		UserDao dao = context.getBean("userDao", UserDao.class);
		UserDao dao2 = context.getBean("userDao", UserDao.class);
		
		User user = dao.get("id01");
		User user2 = dao.get("id01");
		System.out.println(user.getId() + user.getName() + user.getPwd()); 
		System.out.println(user.toString());
		System.out.println(user.hashCode());
		System.out.println(user2.hashCode());
		
		System.out.println(dao.hashCode());
		System.out.println(dao2.hashCode());

>>>>>>> 861342a37498c3cc324ab7cdc94d031b3c8ce492
	}

	public static void main(String[] args) throws Exception {
		JUnitCore.main("xmlTest.dao.UserDaoTest");
	}
}

