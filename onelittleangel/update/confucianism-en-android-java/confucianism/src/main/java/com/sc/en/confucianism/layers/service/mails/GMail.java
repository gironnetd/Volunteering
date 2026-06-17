package com.sc.en.confucianism.layers.service.mails;

import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.confucianism.transverse.orms.realm.models.Account;
import com.sc.en.confucianism.layers.mvp.common.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GMail {

  final String fromUser = "giftvincy@gmail.com";
	 final String fromUserEmailPassword = "jk2008gv";

	//String fromEmail;
	//String fromPassword;
  private List<String> toEmailList;
	private String emailSubject;
	private String emailBody;

	private Properties emailProperties;
	private Session mailSession;
	private MimeMessage emailMessage;
	private SharedPreferences settings;


	public GMail() {

	}

	public GMail(/*String fromEmail, String fromPassword,*/
                 List<String> toEmailList, String emailSubject, String emailBody) {
		//this.fromEmail = fromEmail;
		//this.fromPassword = fromPassword;
		this.toEmailList = toEmailList;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;

		emailProperties = System.getProperties();
    String emailPort = "587";
    emailProperties.put("mail.smtp.port", emailPort);
    String smtpAuth = "true";
    emailProperties.put("mail.smtp.auth", smtpAuth);
    String starttls = "true";
    emailProperties.put("mail.smtp.starttls.enable", starttls);
		//Log.i("GMail", "Mail server properties set.");
		settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
//		OnelittleAngelApplication.instance.getServiceManager().getMailService().loadMailAccountAsync().subscribe(this::initMailAccount);

	}

	private void initMailAccount(Account mailAccount) {
	}

	public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException {

		mailSession = Session.getDefaultInstance(emailProperties, new GMailAuthenticator());
		emailMessage = new MimeMessage(mailSession);

		emailMessage.setFrom(new InternetAddress(settings.getString(Constants.MAIL_ACCOUNT, ""),settings.getString(Constants.MAIL_ACCOUNT, "")));
		for (String toEmail : toEmailList) {
			//Log.i("GMail","toEmail: "+toEmail);
			emailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toEmail));
		}

		emailMessage.setSubject(Html.fromHtml(emailSubject).toString());
		//emailMessage.setContent(Html.fromHtml(emailBody), "text/html");// for a html email
		emailMessage.setText(emailBody);// for a text email
		//Log.i("GMail", "Email Message created.");
		return emailMessage;
	}

	public void sendEmail() throws AddressException, MessagingException {

		Transport transport = mailSession.getTransport("smtp");
    String emailHost = "smtp.gmail.com";
    transport.connect(emailHost, "onelittleangellapplication@gmail.com", "Shankarananda1234");
		//Log.i("GMail","allrecipients: "+ Arrays.toString(emailMessage.getAllRecipients()));
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		//Log.i("GMail", "Email sent successfully.");
	}

}
