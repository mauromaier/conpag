package br.com.conpag.service.sistema;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.controller.sistema.MensagemController;
import br.com.conpag.controller.sistema.UsuarioController;
import br.com.conpag.dao.sistema.UsuarioDAO;
import br.com.conpag.entity.dto.sistema.MensagemDTO;
import br.com.conpag.entity.dto.sistema.MensagemFiltroDTO;
import br.com.conpag.entity.dto.sistema.UsuarioDTO;
import br.com.conpag.entity.exceptions.NoUserException;
import br.com.conpag.entity.sistema.Mensagem;
import br.com.conpag.util.Log;
import br.com.conpag.util.RestTypes;

@Path("/mensagem")
public class MensagemService extends BaseService {
	
	@EJB
	private MensagemController mensagemController;
	
	@EJB
	private LogController logController;
	
	@Inject
	private UsuarioDAO usuarioDao;
	
	@EJB
	private UsuarioController usuarioController;
	
	@POST
	@Consumes( RestTypes.JSON_UTF8 )
	@Produces( RestTypes.TEXTO )
	public int insert( Mensagem msg ){
		mensagemController.insert( msg );
		logController.registerLogSistema( msg.getRemetente(), "Envio de mensagem para usuario com id: " + msg.getIdUserDestino(), Log.INFO );
		return msg.getId();
	}
	
	@PUT
	@Consumes( RestTypes.JSON_UTF8 )
	@Produces( RestTypes.TEXTO )
	public boolean update( Mensagem msg ){
		try {
			logController.registerLogSistema( this.getUser(), "Leitura de mensagem ["+ msg.getId() +"]", Log.INFO );
			mensagemController.update( msg );
			return true;
		}
		catch (NoUserException e) {
			logController.registerLogSistema( "Leitura da mensagem [" + msg.getId() + "] sem estar logado.", Log.ERRO );
		} 
		catch (Exception e) {
			logController.printLog(e, true);
		} 
		return false;
	}
	
	@PUT
	@Path("/update-lida/{id}")
	@Produces( RestTypes.TEXTO )
	public boolean updateLida(@PathParam("id") int id ){
		return mensagemController.updateLida(id);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces( RestTypes.TEXTO )
	public boolean exclui(@PathParam("id") int id ){
		try {
			mensagemController.delete( id );
			logController.registerLogSistema( this.getUser(), "Exclusão de mensagem, id =" + id, Log.INFO );
			return true;
		} catch (Exception e) {
			logController.printLog(e, true);
		}
		return false;
	}
	
	@GET
	@Path("/{id}")
	@Produces( RestTypes.JSON_UTF8 )
	public Mensagem getById(@PathParam("id") int id ){
		return mensagemController.getById( id );
	}
	
	@GET
	@Path("/naolidas/{iduser}")
	@Produces( RestTypes.JSON_UTF8 )
	public Response selectNaoLidas(@PathParam("iduser") int idUser ){
		List<Mensagem> lista = mensagemController.selectNaoLidas( idUser );
		int totalNaoLidas = lista.size();
		
		return Response.ok( lista ).header("x-naolidas", totalNaoLidas).build();
	}
	
	@POST
	@Path("/recebidas")
	@Consumes( RestTypes.JSON_UTF8 )
	@Produces( RestTypes.JSON_UTF8 )
	public Response getRecebidasByData( MensagemFiltroDTO filtro ){
		int total = mensagemController.getCountRecebidas(filtro);
		List<MensagemDTO> lista = mensagemController.selectRecebidasByData(filtro);
		
		return Response.ok( lista ).header("x-count", total ).build();
	}

	@POST
	@Path("/enviadas")
	@Consumes( RestTypes.JSON_UTF8 )
	@Produces( RestTypes.JSON_UTF8 )
	public Response getEnviadasByData( MensagemFiltroDTO filtro ){
		int total = mensagemController.getCountEnviadas(filtro);
		List<MensagemDTO> lista = mensagemController.selectEnviadasByData( filtro );
		
		return Response.ok( lista ).header("x-count", total ).build();
	}
	
	public List<UsuarioDTO> selectUsuariosDTO(){
		return usuarioDao.getUsers( null, 0 );
	}
	
}
