package br.com.conpag.controller.sistema;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.dao.sistema.MensagemDAO;
import br.com.conpag.entity.dto.sistema.MensagemDTO;
import br.com.conpag.entity.dto.sistema.MensagemFiltroDTO;
import br.com.conpag.entity.sistema.Mensagem;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.Log;
import br.com.conpag.util.mail.Email;
import br.com.conpag.util.mail.EmailSender;

@Stateless
public class MensagemController extends BaseController<Mensagem>{
	
	@Inject
	private MensagemDAO mensagemDao;
	
	@EJB
	private UsuarioController usuarioController;
	
	@EJB
	private LogController logController;
	
	@EJB
	private EmailSender emailSender;
	
	@Override
	public void insert(Mensagem m) {
		super.insert( m );
		this.sendEmailNovaMensagem(m);
	}
	
	public boolean updateLida( int id ){
		Query q = this.em.createNamedQuery( Mensagem.JPQL_UPDATE_LIDAS );
        q.setParameter("id", id );
        q.executeUpdate();
        
        return true;
	}
	
	@SuppressWarnings( "unchecked" )
	public List<Mensagem> selectNaoLidas( int idUser ){
		Query q = this.em.createNamedQuery( Mensagem.JPQL_SELECT_PENDENTES_BY_DESTINO );
        q.setParameter("id_user_dest", idUser );
        return q.getResultList();
	}
	
	
	public List<MensagemDTO> selectRecebidasByData( MensagemFiltroDTO filtro ){
		return mensagemDao.getMensagensRecebidasDto(filtro);
	}
	
	public int getCountRecebidas( MensagemFiltroDTO filtro ){
		return mensagemDao.getCountRecebidas(filtro);
	}
	
	public List<MensagemDTO> selectEnviadasByData( MensagemFiltroDTO filtro ){
        return mensagemDao.getMensagensEnviadasDto(filtro);
	}
	
	public int getCountEnviadas( MensagemFiltroDTO filtro ){
		return mensagemDao.getCountEnviadas(filtro);
	}
	
	@Asynchronous
	private void sendEmailNovaMensagem( Mensagem m ){
		
		Usuario destinatario = usuarioController.getById( m.getIdUserDestino() );
		
		//mandar email
		String assunto = "[Sistema Vigilantos] Nova mensagem";
		
		StringBuilder corpo = new StringBuilder("<html>");
		corpo.append("<p>Atenção,</p>");
		corpo.append("<p>Você recebeu uma nova mensagem através do sistema Vigilantos:</p>");
		corpo.append( m.getTexto() );
		corpo.append("<p>Obs: Esta é uma mensagem automática.</p>");
		
		Email mail = Email.getCiascInstance();
		mail.setFromMail( "vigilantos@saude.sc.gov.br" );
		
		mail.setFromName( "Vigilantos" );
		mail.addTo( destinatario.getEmail() );
		mail.addBCC("vigilantos@saude.sc.gov.br");
		
		mail.setAssunto( assunto );
		mail.setHtmlBody( corpo.toString() );
		
		this.emailSender.sendEmails( mail );
		
		logController.registerLogSistema("E-mail: \""+ mail.getAssunto() +"\" enviado com Sucesso para " + mail.getTo(), Log.INFO );
	}
}
