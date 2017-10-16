package br.com.conpag.service.sistema;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.conpag.controller.sistema.MunicipioController;
import br.com.conpag.entity.sistema.Municipio;
import br.com.conpag.util.RestTypes;

@Path("/municipio")
public class MunicipioService {

	@EJB
	MunicipioController municipioController;
	
	@POST
	@Consumes( RestTypes.JSON_UTF8 )
	@Produces( RestTypes.TEXTO )
	public String insert(Municipio m){
		municipioController.insert( m );
		return "Cadastrado com sucesso! Id="+ m.getId();
	}
	
	@PUT
	@Consumes(RestTypes.JSON_UTF8)
	public void update(Municipio m){
		municipioController.update( m );
	}
	
	@DELETE
	@Path("/{id}")
	public void excluir(@PathParam("id")int id ){
		municipioController.delete( id );
	}
	
	@GET
	@Path("/{id}")
	@Produces( RestTypes.JSON_UTF8 )
	public Municipio selectById(@PathParam("id")int id){
		return municipioController.getById( id );
	}
	
	@GET
	@Path("/nome/{nome_municipio}")
	@Produces( RestTypes.JSON_UTF8 )
	public Municipio selectByNome( @PathParam("nome_municipio")String nome ){
		return municipioController.selectByNome(nome);
	}
	
	@GET
	@Path("/nomeuf")
	@Produces( RestTypes.JSON_UTF8 )
	public Municipio selectByNomeAndUf( @QueryParam("nome")String nome, 
										@QueryParam("uf")String uf ){
		return municipioController.selectByNomeAndUf(nome, uf);
	}
	
	@GET
	@Produces(RestTypes.JSON_UTF8)
	public List<Municipio> getAll(){
		return municipioController.getAll();
	}
	
	@GET
	@Path("/municipios/{uf}")
	@Produces( RestTypes.JSON_UTF8 )
	public List<Municipio> selectByUf( @PathParam("uf")String uf ){
		return municipioController.selectByUf( uf );
	}
	
	@GET
	@Path("/estados")
	@Produces( RestTypes.JSON_UTF8 )
	public List<String> selectEstados(){
		return municipioController.selectEstados();
	}
}
