package br.com.conpag.security;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.controller.sistema.UsuarioController;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.Criptografia;
import br.com.conpag.util.Log;



@WebServlet(urlPatterns="/login.action")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = -843265628956982171L;
	
	@EJB
	UsuarioController controller;

	@Inject
	private Criptografia criptografia;
	
	@Inject
	private LogController logController;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		//validar usuario
		Usuario user = controller.doLogin(login, senha);
		
		//cria a a sessao
		if (user != null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			

				response.sendRedirect("/conpag");
				logController.registerLogSistema( user, "[ConPag] Log In ", Log.INFO );
			
		}
		else{
			String msg = "Usuário ou senha invalidos";
			response.sendRedirect("/conpag/login.jsp?msg="+msg);
//			request.setAttribute("mensagem", "");
			request.getRequestDispatcher("").forward(request, response);
		}
	}

}
