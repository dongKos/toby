package toby.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;
import toby.user.exception.TestUserServiceException;
import toby.user.service.UserService;
import toby.user.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserServiceTest {
	@Autowired
	UserService userService;
	
//	@Autowired
//	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	MailSender mailSender;
	
	List<User> users;
	
	public static final int MIN_LOGCONUT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	@Before
	public void setUp() {
		users = Arrays.asList(
				new User("user1", "name1", "pwd01", "email01@email.co.kr", Level.BASIC, MIN_LOGCONUT_FOR_SILVER - 1, 0),
				new User("user2", "name2", "pwd02", "email02@email.co.kr", Level.BASIC, MIN_LOGCONUT_FOR_SILVER, 0),
				new User("user3", "name3", "pwd03", "email03@email.co.kr", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
				new User("user4", "name4", "pwd04", "email04@email.co.kr", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
				new User("user5", "name5", "pwd05", "email05@email.co.kr", Level.GOLD, 100, Integer.MAX_VALUE)
				);
	}
	
	@Test
	@DirtiesContext
	public void upgradeLevels() throws Exception {
//		userDao.deleteAll();
//		
//		for(User user: users) {
//			userDao.add(user);
//		}
//		
//		MockMailSender mockMailSender = new MockMailSender();
//		userServiceImpl.setMailSender(mockMailSender);
//		
//		userService.upgradeLevels();
//		
//		checkLevelUpgraded(users.get(0), false);
//		checkLevelUpgraded(users.get(1), true);
//		checkLevelUpgraded(users.get(2), false);
//		checkLevelUpgraded(users.get(3), true);
//		checkLevelUpgraded(users.get(4), false);
//		
//		List<String> request = mockMailSender.getRequests();
//		assertThat(request.size(), is(2));
//		assertThat(request.get(0), is(users.get(1).getEmail()));
//		assertThat(request.get(1), is(users.get(3).getEmail()));
		
		//6-13 - �� ������Ʈ�� �̿��� ���� �׽�Ʈ : �׽�Ʈ ���� ���
		/*
		 * [���� �׽�Ʈ]
		 *  �׽�Ʈ ��� Ŭ������ �� ������Ʈ ���� �׽�Ʈ �뿪�� �̿��� ���� ������Ʈ��
		 * �ܺ� ���ҽ��� ������� �ʵ��� �����Ѽ� �׽�Ʈ�ϴ� ��
		 */
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
//		MockUserDao mockUserDao = new MockUserDao(this.users);
//		userServiceImpl.setUserDao(mockUserDao);
//		
//		MockMailSender mockMailSender = new MockMailSender();
//		userServiceImpl.setMailSender(mockMailSender);
		
//		userServiceImpl.upgradeLevels();
//		
//		List<User> updated = mockUserDao.getUpdated();
//		assertThat(updated.size(), is(2));
//		checkUserAndLevel(updated.get(0), "user2", Level.SILVER);
//		checkUserAndLevel(updated.get(1), "user4", Level.GOLD);
//		
//		List<String> request = mockMailSender.getRequests();
//		assertThat(request.size(), is(2));
//		assertThat(request.get(0), is(users.get(1).getEmail()));
//		assertThat(request.get(1), is(users.get(3).getEmail()));
		
		
		//6-14 Mockito framework�� �̿��� Mock ������Ʈ ����
		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		 
		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));
		
		ArgumentCaptor<SimpleMailMessage> mailMessageArg = 
			ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
	}
		
	
	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertThat(updated.getId(), is(expectedId));
		assertThat(updated.getLevel(), is(expectedLevel));
	}
//
//	@Test
//	public void upgradeAllOrNothing() throws Exception {
//		TestUserService testUserService = new TestUserService(users.get(3).getId());
//		testUserService.setUserDao(userDao);
//		testUserService.setMailSender(mailSender);
//		
////		UserServiceTx txUserService = new UserServiceTx();
////		txUserService.setTransactionManager(transactionManager);
////		txUserService.setUserService(testUserService);
//		
//		//6-28
//		TransactionHandler txHandler = new TransactionHandler();
//		txHandler.setTarget(testUserService);
//		txHandler.setTransactionManager(transactionManager);
//		txHandler.setPattern("upgradeLevels");
//		UserService txUserService = (UserService) Proxy.newProxyInstance(
//				getClass().getClassLoader(), new Class[] {UserService.class}, txHandler);
//		
//		userDao.deleteAll();
//		for(User user: users) userDao.add(user);
//		
//		try {
//			txUserService.upgradeLevels();
//			fail("TestUserServiceException expected");
//		} catch(TestUserServiceException e) {
//			
//		}
//		
//		checkLevelUpgraded(users.get(1), false);
//	}
	
	
	//6-37
//	@Autowired
//	ApplicationContext context;
//	@Test
//	@DirtiesContext
//	public void upgradeAllorNothing() throws Exception {
//		TestUserService testUserService = new
//			TestUserService(users.get(3).getId());
//		testUserService.setUserDao(userDao);
//		testUserService.setMailSender(mailSender);
//		
////		TxProxyFactoryBean txProxyFactoryBean = 
////			context.getBean("&userService", TxProxyFactoryBean.class);
////		txProxyFactoryBean.setTarget(testUserService);
//		
//		//6-48
//		ProxyFactoryBean txProxyFactoryBean = 
//			context.getBean("&userService", ProxyFactoryBean.class);
//		UserService txUSerService = (UserService) txProxyFactoryBean.getObject();
//		
//		userDao.deleteAll();
//		for(User user: users) userDao.add(user);
//		
//		try {
//			txUSerService.upgradeLevels();
//			fail("TestUserServiceException expected");
//		} catch(TestUserServiceException e) {
//			
//		}
//		
//		checkLevelUpgraded(users.get(1), false);
//	}
	
	@Autowired
	UserService testUserService;
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		userDao.deleteAll();
		for(User user: users) userDao.add(user);
		
		try {
			this.testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		}  catch (TestUserServiceException e) {
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	static class MockMailSender implements MailSender
	{
		private List<String> requests = new ArrayList<String> ();
		public List<String> getRequests() {
			return requests;
		}
		
		@Override
		public void send(SimpleMailMessage msg) throws MailException {
			requests.add(msg.getTo()[0]);
		}
		
		@Override
		public void send(SimpleMailMessage[] arg0) throws MailException {
			// TODO Auto-generated method stub
		}
		
	}
	
	static class MockUserDao implements UserDao {
		private List<User> users;
		
		private List<User> updated = new ArrayList<User>();
		
		private MockUserDao(List<User> users) {
			this.users = users;
		}
		
		public List<User> getUpdated() {
			return this.updated;
		}

		@Override
		public void update(User user) {
			updated.add(user);
		}

		@Override
		public List<User> getAll() {
			return this.users;		
		}
		
		public User get(String id) {throw new UnsupportedOperationException();}
		public void deleteAll() {throw new UnsupportedOperationException();}
		public int getCount() {throw new UnsupportedOperationException();}
		public void add(User user) {throw new UnsupportedOperationException();}
	}

	@Test (expected=TransientDataAccessResourceException.class) 
	public void readOnlyTransactionAttribute() {
		testUserService.getAll();
	}

	static class TestUserService extends UserServiceImpl {
		private String id = "user4";
		
		protected void upgradeLevel(User user) {
			if(user.getId().equals(this.id)) throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
		
		public List<User> getAll() {
			for(User user: super.getAll()) {
				super.update(user);
			}
			
			return null;
		}
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void transactionSync() {
		userDao.deleteAll();
		userService.add(users.get(0));
		userService.add(users.get(1));
	}
}
