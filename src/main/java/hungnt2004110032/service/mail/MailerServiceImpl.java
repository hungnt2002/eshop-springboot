package hungnt2004110032.service.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hungnt2004110032.model.Account;
import hungnt2004110032.model.Order;

@Service
public class MailerServiceImpl implements MailerService{
	@Autowired
	JavaMailSender sender; 

	@Override
	public void send(Mail mail) throws MessagingException {
		MimeMessage msg = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getText(), true);
		
		String from = mail.getFrom();
		if(from == null || from.trim().length() == 0) {
			from = "Web Store <vulinh3609@gmail.com>";
		}
		if(!from.contains("<")) {
			from = "%s <%s>".formatted(from, from);
		}
		helper.setFrom(from);
		helper.setReplyTo(from);
		
		String cc = mail.getCc();
		if(cc != null && cc.trim().length() > 0) {
			helper.setCc(cc);
		}
		
		String bcc = mail.getBcc();
		if(bcc != null && bcc.trim().length() > 0) {
			helper.setBcc(bcc);
		}
		
		String files = mail.getAttachments();
		if(files != null && files.trim().length() > 0) {
			Stream.of(files.split("[,;]+")).forEach(filename -> {
				try {
					File file = new File(filename);
					helper.addAttachment(file.getName(), file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		sender.send(msg);
	}

	List<Mail> queue = new ArrayList<>();
	
	@Override
	public void addToQueue(Mail mail) {
		queue.add(mail);
	}

	@Scheduled(fixedDelay = 2000) // lịch hẹn 2s gọi hàm sendingScheduler() 1 lần
	public void sendingScheduler() {
		while(!queue.isEmpty()) {
			Mail mail = queue.remove(0);
			try {
				this.send(mail);
				System.out.println("Success: " + mail.getTo());
			} catch (Exception e) {
				System.out.println("Error: " + mail.getTo());
				e.printStackTrace();
			}
		}
	}
	
	// gui mail khi mua hang -> mail server
	@Override
	public void sendOrder(Order order) {
		// TODO Auto-generated method stub
		String url="http://localhost:8080/order/detail/" + order.getId();
		try {
			String text = "<br><a href='%s'>Xem chi tiết.</a>".formatted(url);
			String to = order.getAccount().getEmail();
			Mail mail = new Mail(to,"Đơn đặt hàng của bạn", text); 
			//this.send(mail);
			this.addToQueue(mail);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}

	@Override
	public void sendWelcome(Account account) {
		// TODO Auto-generated method stub
		String url="http://localhost:8080/account/activate/" + account.getUsername();
		try {
			String text = "<br><a href='%s'>Nhấn vào để kích hoạt tài khoản.</a>".formatted(url);
			String to = account.getEmail();
			
			 // tiêu đề email
			Mail mail = new Mail(to,"Chào mừng bạn đến với website bán hàng", text);
			//this.send(mail);
			this.addToQueue(mail); // gửi email tới người dùng.
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}

	@Override
	public void sendToken(String token, String email) {
		// TODO Auto-generated method stub
		try {
			
			String text = "Mã xác nhận của bạn: <b> %s </b>".formatted(token);
			//tiêu đề mail
			Mail mail = new Mail(email, "THÔNG BÁO NHẬN MÃ XÁC NHẬN", text);
			//this.send(mail);
			this.addToQueue(mail); // gửi email tới người dùng.
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}
}