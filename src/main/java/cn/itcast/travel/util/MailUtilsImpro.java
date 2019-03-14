package cn.itcast.travel.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtilsImpro {
	
	public static void sendMail(String email, String emailMsg)
	{

		try {
			// 1.创建一个程序与邮件服务器会话对象 Session
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "SMTP");// 定义用户发邮件的协议，SMTP
			props.setProperty("mail.host", "smtp.126.com");// 用户的邮件发送到SMTP邮件服务器
			props.setProperty("mail.smtp.auth", "true");// 指定验证为true
			// 创建验证器
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("Astasong", "Sp0112");// 登录到SMTP邮箱服务器，把邮件放进去
				}
			};
			Session session = Session.getInstance(props, auth);
			// 2.创建一个Message，它相当于是邮件内容
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Astasong@126.com")); // 设置发送者
			message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
			message.setSubject("用户激活");
			// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");
			message.setContent(emailMsg, "text/html;charset=utf-8");
			// 3.创建 Transport用于将邮件发送
			Transport.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	/**
	 * 测试发送用户注册激活邮件到邮箱
	 * @param args
	 */
	public static void main(String[] args) {
	
			sendMail("210097681@qq.com", "这是一封激活邮件，请<a href='#'>点击</a>");
			
			System.out.println("发送成功");
	}
}
