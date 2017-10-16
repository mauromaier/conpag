package br.com.conpag.util.mail;

import javax.ejb.Stateless;

@Stateless
public class EmailSender{

	public void sendEmails( Email email ){
		if ( email != null ){
			email.enviar();
		}
	}

}
