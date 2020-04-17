package toby.user.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

@Transactional(readOnly=false)
public class UserServiceImpl implements UserService{
	UserDao userDao;	
	
	private DataSource dataSource;
	private PlatformTransactionManager transactionManager;
	private MailSender mailSender;
	
	public static final int MIN_LOGCONUT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void upgradeLevels() throws Exception {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
			
		}

	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		
//		if(user.getLevel() == Level.BASIC) {
//			user.setLevel(Level.SILVER);
//		} else if(user.getLevel() == Level.SILVER) {
//			user.setLevel(Level.GOLD);
//		}
		
		userDao.update(user);
		sendUpgradeEmail(user);
	}

	private void sendUpgradeEmail(User user) {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "mail.ksug.org");
//		
//		Session s = Session.getInstance(props, null);
//		
//		MimeMessage message = new MimeMessage(s);
//		try {
//			message.setFrom(new InternetAddress("useradmin@ksug.org"));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
//			
//			message.setSubject("Upgrade 안내");
//			message.setText("사용자님의 등급이 " + user.getLevel().name() + "으로 업그레이드 되었습니다");
//			
//			Transport.send(message);
//		} catch(AddressException e) {
//			throw new RuntimeException(e);
//		} catch(MessagingException e) {
//			throw new RuntimeException(e);
//		}
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("mail.server.com");
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@co.kr");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "으로 업그레이드 되었습니다");
		
		mailSender.send(mailMessage);
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();

		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGCONUT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}

	@Override
	public void add(User user) {
		userDao.add(user);
	}

	@Override
	public User get(String id) {
		return userDao.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public void deleteAll() {
		userDao.deleteAll();
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

}
