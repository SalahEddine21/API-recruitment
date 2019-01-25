package com.gestion.rh.mailing;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.gestion.rh.models.ApplicationContextProvider;

@Service("emailService")   
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ApplicationContextProvider provider;
	
	public boolean sendEmail(String to, String subject, String body) {
		try {
			SimpleMailMessage mail = (SimpleMailMessage) provider.getApplicationContext().getBean("simpleMailmessage", SimpleMailMessage.class);
			mail.setTo(to);
			mail.setFrom("noreply@seltechno.com");
			mail.setSubject(subject);
			mail.setText(body);
			mailSender.send(mail);
			return true;
		} catch (Exception e) {
			System.out.println("EXCEPTION SENDING MAIL: "+ e.getMessage());
			return false;
		}
	}
	
	public boolean applyJob(String subject, String from, String to, String body, File cv) {
		try {
			 MimeMessagePreparator messagePreparator = mimeMessage -> {
		            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, 
		            		MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
			                StandardCharsets.UTF_8.name());
		            FileSystemResource file = new FileSystemResource(cv);
		            messageHelper.addAttachment("CV_CANDIDATE", file);
		            messageHelper.setFrom(from);
		            messageHelper.setTo(to);
		            messageHelper.setSubject(subject);
		      	    messageHelper.setText(body);

		     };
		     mailSender.send(messagePreparator);
		     return true;
		} catch (Exception e) {
			System.out.println("EXCEPTION SENDING MAIL: "+ e.getMessage());
			return false;
		}
	}
	
	public boolean mailCandidate(String subject, String from, String to, String body) {
		try {
			SimpleMailMessage mail = (SimpleMailMessage) provider.getApplicationContext().getBean("simpleMailmessage", SimpleMailMessage.class);
			mail.setTo(to);
			mail.setFrom(from);
			mail.setSubject(subject);
			mail.setText(body);
			mailSender.send(mail);
			return true;
		} catch (Exception e) {
			System.out.println("EXCEPTION SENDING MAIL: "+ e.getMessage());
			return false;
		}
	}
}
