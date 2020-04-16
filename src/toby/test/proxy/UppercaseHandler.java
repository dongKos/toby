package toby.test.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

public class UppercaseHandler implements InvocationHandler{

	
//	Hello target;
//	
//	public UppercaseHandler(Hello target) {
//		this.target = target;
//	}
//	
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) 
//			throws Throwable {
//		String ret = (String) method.invoke(target, args);
//		return ret.toUpperCase();
//	}

	//6-25 확장된 다이내믹 프록시
	
	Object target;
	
	public UppercaseHandler(Object target) {
		this.target = target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) 
		throws Throwable {
		Object ret = method.invoke(target, args);
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase(); 
		} else {
			return ret;
		}
	}
}
