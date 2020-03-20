package xmlTest.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {

	public static void main(String[] args) throws Exception {
		//xml 을 이용한 설정 방식
		ApplicationContext context = 
				new GenericXmlApplicationContext("applicationContext.xml");
		
		//DataSource 구현체를 이용한 설정 방식
//		AnnotationConfigApplicationContext context = 
//				new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = dao.get("id01");
		System.out.println(user.getId() + user.getName() + user.getPwd()); 
		System.out.println(user.toString());

	}

}

