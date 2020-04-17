package toby.test.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

import net.sf.cglib.proxy.Proxy;


public class ReflectionTest {

	@Test
	public void invokeMethod() throws Exception {
		String name = "Spring";
		
		//length()
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer) lengthMethod.invoke(name), is(6));
		
		
		//charAt()
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character) charAtMethod.invoke(name, 0), is('S'));
	}
	
	@Test
	public void simpleProxy() {
//		Hello hello = new HelloTarget();
//		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
//		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
//		assertThat(hello.sayThankYou("Toby"), is("Thank you Toby"));
		
		//6-22 proxy 적용 - 두 가지 단점이 모두 드러난다
		//Hello proxiedHello = new HelloUppercase(new HelloTarget());
		
		//6-24 다이내믹 프록시 적용 - UppercaseHandler 
//		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
//				//동적 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
//				getClass().getClassLoader(),
//				
//				//구현할 인터페이스
//				new Class[] {Hello.class},
//				
//				//부가기능과 위임 코드를 담은 InvocationHandler
//				new UppercaseHandler(new HelloTarget()));
		//6-41
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {Hello.class}, 
				new UppercaseHandler(new HelloTarget()));
		
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
		
	}
}
