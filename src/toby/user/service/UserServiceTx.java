package toby.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import toby.user.domain.User;

public class UserServiceTx implements UserService{
	//타깃 오브젝트
	UserService userService;	
	PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	@Override
	//메소드 구현
	public void add(User user) {
		userService.add(user);	//위임
	}

	@Override
	public void upgradeLevels() throws Exception {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			userService.upgradeLevels();
			
			//부가기능 수행
			this.transactionManager.commit(status);
		} catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

}
