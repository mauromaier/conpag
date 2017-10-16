package br.com.conpag.security;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import br.com.conpag.entity.sistema.Role;
import br.com.conpag.entity.sistema.Usuario;



@WebFilter(urlPatterns= { "/", "/rest/*", "*.html", "*.js" } )
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) resp; 
		
		
		if ( request.getSession(false) == null ||
			request.getSession().getAttribute("user") == null ){
			response.sendRedirect("/conpag/login.jsp");
		}
		else{
			Usuario user = (Usuario) request.getSession().getAttribute("user");
			
			VigilantosRequestWrapper vigRequest = new VigilantosRequestWrapper(request, user);
			chain.doFilter(vigRequest, resp);
		}
	}

	@Override
	public void destroy() {
	}

}


class VigilantosRequestWrapper extends HttpServletRequestWrapper {

	private Usuario user;
	
	public VigilantosRequestWrapper(HttpServletRequest request, Usuario u) {
		super(request);
		this.user = u;
	}

	@Override
	public boolean isUserInRole(String role) {
		
		if ( this.user.getRoles() != null && !this.user.getRoles().isEmpty() ){
			for (Role r: this.user.getRoles()){
				
				String moduloRole = r.getModulo().getNome() +"_"+ r.getDescricao();
				if ( moduloRole.equals(role)){
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		return this.user;
	}
	
	
	
}