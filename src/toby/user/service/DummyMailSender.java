package toby.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender{

	@Override
	public void send(SimpleMailMessage msg) throws MailException {
		System.out.println("메일 발송자 : " + msg.getFrom());
		System.out.print("메일 수신자 : ");
		
		for(String receiver : msg.getTo()) {
			System.out.println(receiver);
		}
		
		System.out.println("메일 제목   : " + msg.getSubject());
		System.out.println("메일 내용   : " + msg.getText());
		System.out.println("-----------------------------------");
		
	}

	@Override
	public void send(SimpleMailMessage[] arg0) throws MailException {
		// TODO Auto-generated method stub
		
	}

}
