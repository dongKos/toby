package toby.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor{
	//6-43
	PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		System.out.println("tr advice 생성");
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status = 
			this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		System.out.println("invoke");
		try {
			Object ret = invocation.proceed();
			this.transactionManager.commit(status);
			return ret;
		} catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

}
