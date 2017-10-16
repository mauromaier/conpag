package br.com.conpag.service.sistema;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.conpag.controller.sistema.SistemaController;
import br.com.conpag.util.RestTypes;

@Path("/sistema")
public class SistemaService {

	@EJB
	private SistemaController controller;

	@GET
	@Path("/{key}")
	@Produces( RestTypes.TEXTO )
	public String getParametro(@PathParam("key") String key){
		return controller.getParametro( key );
	}
	
	@POST
	@Path("/{key}")
	@Consumes( RestTypes.JSON_UTF8  )
	@Produces( RestTypes.TEXTO )
	public boolean setParametro(@PathParam("key") String key, String value ){
		return controller.setParametro(key, value);
	}
	
}
