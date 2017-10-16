package br.com.conpag.util.mail;

public class EmailUtils {
	
	private static String MAIL_REMETENTE = "vigilantos@saude.sc.gov.br";
	
	private static String GMAIL_USERNAME = "vigilantos@gmail.com";
	private static String GMAIL_PASSWD = "vigilantos";
	
	private EmailUtils(){
	}
	
	public Email generateMailRecuperaSenha( String email, String login, String senha ){
		
		String assunto = "[Sistema Vigilantos] nova senha de acesso";
		
		StringBuilder corpo = new StringBuilder("<html>");
		corpo.append("<p>Prezado usuário,</p>");
		corpo.append("<p>Foi gerada uma nova senha de acesso ao sistema Vigilantos. Segue abaixo:</p>");
		corpo.append("<p>Usuário:");
		corpo.append( login );
		corpo.append("</p><p>Senha:");
		corpo.append( senha );
		corpo.append("</p><br /><p>Ao acessar o vigilantos, a mesma deverá ser alterada.</p>");
		
		Email mail = Email.getCiascInstance();
		mail.setFromMail( MAIL_REMETENTE );
		
		mail.setFromName( "Vigilantos" );
		mail.addTo( email );
		mail.setAssunto( assunto );
		mail.setHtmlBody( corpo.toString() );
		
		return mail;
	}
	
	
}
