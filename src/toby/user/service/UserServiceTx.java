package toby.user.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import toby.user.domain.User;

public class UserServiceTx implements UserService{
	//Ÿ�� ������Ʈ
	UserService userService;	
	PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	@Override
	//�޼ҵ� ����
	public void add(User user)   {
		userService.add(user);	//����
	}
	
	@Override
	public void upgradeLevels()   {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			userService.upgradeLevels();
			
			//�ΰ���� ����
			this.transactionManager.commit(status);
		} catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
	@Override
	public User get(String id)   {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> getAll()   {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteAll()   {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(User user)   {
		// TODO Auto-generated method stub
		
	}

}
