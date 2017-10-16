package br.com.conpag.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.Log;

@WebServlet(urlPatterns="/logout.action")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -8343992141359628424L;

	@Inject
	private LogController logController;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		
		response.sendRedirect("/conpag/login.jsp");
		logController.registerLogSistema( user, "[ConPag] Log off", Log.INFO );
	}

}
