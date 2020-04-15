package toby.user.service;

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
	public void add(User user) {
		userService.add(user);	//����
	}

	@Override
	public void upgradeLevels() throws Exception {
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

}
