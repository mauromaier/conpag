package br.com.conpag.util.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
	
	private static String SMTP_GMAIL = "smtp.gmail.com";
	private static String SMPTPORT_GMAIL = "465";
	
	private static String SMTP_LOCALHOST = "localhost";
	private static String SMPTPORT_LOCALHOST = "25";
	
	private static String SMTP_CIASC = "relay.ciasc.gov.br";
	private static String SMPTPORT_CIASC = "25";
	
	private String smtp;
	private String smtpPort;
	
	private String fromMail;
	private String fromName;
	private String assunto;
	
	private List<Address> to;
	private List<Address> cc;
	private List<Address> bcc;
	
	private String stringBody;
	private String htmlBody;
	
	private Message message;
	
	private List<MimeBodyPart> anexos;
	
	private boolean autenticacao;
	
	private String username;
	private String password;
	
	public static Email getGmailInstance(){
		Email mail = new Email();
		mail.smtp = SMTP_GMAIL;
		mail.smtpPort = SMPTPORT_GMAIL;
		mail.setAutenticacao( true );
		return mail;
	}
	
	public static Email getLocalhostInstance(){
		Email mail = new Email();
		mail.smtp = SMTP_LOCALHOST;
		mail.smtpPort = SMPTPORT_LOCALHOST;
		return mail;
	}
	
	public static Email getCiascInstance(){
		Email mail = new Email();
		mail.smtp = SMTP_CIASC;
		mail.smtpPort = SMPTPORT_CIASC;
		return mail;
	}
	
	public static Email getSaudeInstance(){
		Email mail = new Email();
		mail.smtp = "smtp.sc.gov.br";
		mail.smtpPort = SMPTPORT_CIASC;
		return mail;
	}
	
	private Email( String remetente, String destinatario, String assunto, String mensagem ){
		this();
		this.fromMail = remetente;
		try {
			this.to.add( new InternetAddress( destinatario ) );
		} catch (AddressException e) {
			e.printStackTrace();
		}
		this.assunto = assunto;
		this.stringBody = mensagem;
	}
	
	private Email(){
		this.smtp = SMTP_LOCALHOST;
		this.smtpPort = SMPTPORT_LOCALHOST;
		this.to = new ArrayList<Address>();
		this.cc = new ArrayList<Address>();
		this.bcc = new ArrayList<Address>();
		this.anexos = new ArrayList<MimeBodyPart>();
	}
	
	public void addTo( String email ){
		try {
			Address address = new InternetAddress(email);
			this.to.add(address);
		} catch(Exception e) {
			System.out.println("[Email] - ERRO ao Adicionar Novo Destinatario "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}	
	}
	
	public void addCC( String email ){
		try {
			Address address = new InternetAddress(email);
			this.cc.add(address);
		} catch(Exception e) {
			System.out.println("[Email] - ERRO ao Adicionar Novo Destinatario"+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}	
	}
	
	public void addBCC( String email ){
		try {
			Address address = new InternetAddress(email);
			this.bcc.add(address);
		} catch(Exception e) {
			System.out.println("[Email] - ERRO ao Adicionar Novo Destinatario"+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}	
	}
	
	/**
	 * Adiciona um Novo Arquivo Anexo
	 * 
	 * @param
	 * filePath = Caminho do arquivo (incluindo o nome do arquivo)
	 * @param
	 * fileName = Nome do arquivo que aparecerá na mensagem
	 */
	public void addAnexo(String filePath, String fileName) {
		MimeBodyPart anexo = new MimeBodyPart();
		DataSource fds = new FileDataSource(filePath);  
		try {
			anexo.setDisposition(Part.ATTACHMENT);
			anexo.setDataHandler(new DataHandler(fds));
			anexo.setFileName(fileName);
			this.anexos.add(anexo);
		} catch(Exception e) {
			System.out.println("[EmailSender] - ERRO ao Adicionar Arquivo Anexo ("+filePath+") "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * Adiciona um Novo Arquiv (ByteArray) como Anexo
	 * 
	 * @param
	 * arquivo = InputStrem do arquivo a ser enviado
	 * @param
	 * fileName = Nome do arquivo que aparecerá na mensagem
	 */
	public void addAnexo(InputStream arquivo, String fileName) {
		MimeBodyPart anexo = new MimeBodyPart();
		try {
			DataSource isds = new InputStreamDataSource(fileName,"application/octet-stream",arquivo);
			anexo.setDisposition(Part.ATTACHMENT);
			anexo.setDataHandler(new DataHandler(isds));
			anexo.setFileName(fileName);
			this.anexos.add(anexo);
		} catch(Exception e) {
			System.out.println("[EmailSender] - ERRO ao Adicionar Arquivo Anexo ("+fileName+") "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
	}
	
	public void addAnexo(byte [] arquivo, String fileName) {
		try {
			InputStream is = (arquivo!=null) ? new ByteArrayInputStream(arquivo) : null;
			this.addAnexo(is, fileName);
		} catch(Exception e) {
			System.out.println("[EmailManager] - ERRO ao Adicionar Arquivo Anexo ("+fileName+") "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
	}
	
	private MimeMultipart getBody() {
		MimeMultipart body; //htmlBody + Anexos
		body = new MimeMultipart();
		try {
			MimeBodyPart b = new MimeBodyPart();
			if ( this.htmlBody != null && this.htmlBody != ""){
				b.setContent( this.htmlBody, "text/html");
			}
			else{
				if ( this.stringBody != null ){
					b.setContent( this.stringBody, "text/plain" );
				}
				else{
					b.setContent("","text/plain");
				}
			}
			body.addBodyPart(b);
		} catch(Exception e) {
			System.out.println("[EmailSender] - ERRO ao Adicionar Corpo da Mensagem: "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
		for (MimeBodyPart anexo: this.anexos) {
			try {
				body.addBodyPart(anexo);
			} catch(Exception e) {
				System.out.println("[EmailSender] - ERRO ao Adicionar Anexo: "+ "-> " + e.getClass().toString() + ": "
						+ e.getMessage());
			}
		}
		return body;
	}
	
	public boolean enviar(){
		//inicia o envio da mensagem
		Properties props = new Properties();
		
		props.put("mail.smtp.host", this.smtp );
		
		Session sessao = null;
		if ( autenticacao ){
			props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP  
			props.put("mail.smtp.starttls.enable","true");   
			props.put("mail.smtp.auth", "true"); //ativa autenticacao  
			props.put("mail.smtp.user", this.username ); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)  
			props.put("mail.debug", "true");  
			props.put("mail.smtp.port", this.smtpPort ); //porta  
			props.put("mail.smtp.socketFactory.port", this.smtp ); //mesma porta para o socket  
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
			props.put("mail.smtp.socketFactory.fallback", "false");  
			SimpleAuth auth =  new SimpleAuth ( this.username, this.password );  
			sessao = Session.getDefaultInstance( props, auth );
			sessao.setDebug(true);
		}
		else {
			sessao = Session.getInstance( props );
		}
		this.message = new MimeMessage( sessao );
		
		//seta o remetente
		try {
			Address adFrom = new InternetAddress( this.fromMail, this.fromName);
			this.message.setFrom(adFrom);
		} catch(Exception e) {
			System.out.println("[EmailSender] - ERRO ao Adicionar Endereco FROM"+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
		
		//seta os destinatarios
		try {
			if ( this.to.size() > 0 ){
				for ( Address a: this.to )
					this.message.addRecipient(Message.RecipientType.TO, a );
			}
			if ( this.cc.size() > 0 ){
				for ( Address a: this.cc )
					this.message.addRecipient(Message.RecipientType.CC, a );
			}
			if ( this.bcc.size() > 0 ){
				for ( Address a: this.bcc )
					this.message.addRecipient(Message.RecipientType.BCC, a );
			}
		} catch (MessagingException e) {
			System.out.println("[Email] - ERRO ao Adicionar Endereco"+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
		
		//seta o conteudo
		try {
			message.setSubject( this.assunto );
			message.setContent( this.getBody() );
		} catch (Exception e) {
			System.out.println("[EmailSender] - ERRO ao Popular Mensagem: "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
		}
		
		//envia o email
		try {
			if ( this.autenticacao ){
				Transport  tr = sessao.getTransport("smtp"); //define smtp para transporte  
				tr.connect( this.smtp, this.username, this.password);  
				this.message.saveChanges(); // don't forget this  
				//envio da mensagem  
				tr.sendMessage( this.message, this.message.getAllRecipients() );  
				tr.close();
			}
			else{
				Transport.send(message);
				System.out.println("[EmailSender] - Mensagem Enviada com Sucesso!");
			}
			return true;
		} catch(MessagingException e) {
			System.out.println("[EmailSender] - ERRO ao Enviar Mensagem "+ "-> " + e.getClass().toString() + ": "
					+ e.getMessage());
			return false;
		}
	}
	
	

	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getFromMail() {
		return fromMail;
	}
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getStringBody() {
		return stringBody;
	}
	public void setStringBody(String stringBody) {
		this.stringBody = stringBody;
	}
	public boolean isAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(boolean autenticacao) {
		this.autenticacao = autenticacao;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDestinatarios(){
		return this.to.toString();
	}
	public List<Address> getTo() {
		return to;
	}
	public void setTo(List<Address> to) {
		this.to = to;
	}
	public List<Address> getCc() {
		return cc;
	}
	public void setCc(List<Address> cc) {
		this.cc = cc;
	}
	
}


class InputStreamDataSource implements DataSource {
	private String name;  
	private String contentType;  
	private ByteArrayOutputStream baos;  

    public InputStreamDataSource(String name, String contentType,
    		InputStream inputStream) throws IOException {
    	int read;
    	this.name = name;  
	    this.contentType = contentType;  

        baos = new ByteArrayOutputStream();  

        byte[] buff = new byte[256];              
        while ((read = inputStream.read(buff)) != -1) {
        	baos.write(buff, 0, read);
        	}
    }  

    public String getContentType() {
    	return contentType;
    }  

    public InputStream getInputStream() throws IOException {
    	return new ByteArrayInputStream(baos.toByteArray());
    }  

    public String getName() {
    	return name;
    }  

    public OutputStream getOutputStream() throws IOException {
    	throw new IOException("Cannot write to this read-only resource");
    }
}

//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp  
class SimpleAuth extends Authenticator {  
     public String username = null;  
     public String password = null;  
   
  
     public SimpleAuth(String user, String pwd) {  
         username = user;  
         password = pwd;  
     }  
   
     protected PasswordAuthentication getPasswordAuthentication() {  
         return new PasswordAuthentication ( username, password );  
     }  
}   
