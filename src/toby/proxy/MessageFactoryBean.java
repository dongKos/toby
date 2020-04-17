package toby.proxy;

import org.springframework.beans.factory.FactoryBean;

import toby.user.domain.Message;

public class MessageFactoryBean implements FactoryBean<Message>{

	String text;
	
	//오브젝트 생성에 필요한 정보를 팩토리빈의 프로퍼티로 설정해서
	//DI받는다. 
	public void setText(String text) {
		this.text = text;
	}
	
	//실제 빈으로 사용할 오브젝트 생성
	//생성과 초기화 작업 전부 가능
	@Override
	public Message getObject() throws Exception {
		return Message.newMessage(this.text);
	}

	@Override
	public Class<?> getObjectType() {
		return Message.class;
	}

	//싱글톤인지 아닌지 설정할 수 있다.
	//true로 하면 스프링이 알아서 관리해준다
	@Override
	public boolean isSingleton() {
		return false;
	}

}
