package br.com.conpag.websocket;

import java.util.List;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import br.com.conpag.controller.sistema.MensagemController;
import br.com.conpag.entity.sistema.Mensagem;
import br.com.conpag.service.sistema.BaseService;


@ServerEndpoint(value="/mensagens")
public class MensagemWebSocket extends BaseService{
	
	@Inject
	private MensagemController mensagemController;
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("Conexão estabelecida para recebimento de mensagens");
	}
	
	@OnMessage
	public int getTotalMensagensNaoLidas(int idUser){
		List<Mensagem> lista = mensagemController.selectNaoLidas( idUser );
		int totalNaoLidas = lista.size();
		
		return totalNaoLidas;
	}
	
	@OnClose
	public void onClose(Session session, CloseReason c){
		System.out.println("Conexão fechada para mensagens");
	}
}
