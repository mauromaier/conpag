package br.com.conpag.service.conpag;

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
import javax.ws.rs.core.Response;

import br.com.conpag.controller.conpag.BairrosController;
import br.com.conpag.controller.conpag.BancoController;
import br.com.conpag.controller.conpag.EnderecosController;
import br.com.conpag.controller.conpag.FornecedorController;
import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.controller.sistema.MunicipioController;
import br.com.conpag.entity.conpag.Bairros;
import br.com.conpag.entity.conpag.Bancos;
import br.com.conpag.entity.conpag.Enderecos;
import br.com.conpag.entity.conpag.Fornecedor;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorDTO;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorFiltroDTO;
import br.com.conpag.entity.exceptions.NoUserException;
import br.com.conpag.entity.sistema.Municipio;
import br.com.conpag.service.sistema.BaseService;
import br.com.conpag.util.Log;
import br.com.conpag.util.RestTypes;

@Path("/fornecedores")
public class FornecedorService extends BaseService {

	@EJB
	private LogController logController;

	@EJB
	private FornecedorController fornecedorController;

	@EJB
	private MunicipioController municipioController;
	
	@EJB
	private BairrosController bairrosController;

	@EJB
	private EnderecosController enderecosController;
	
	@EJB
	private BancoController bancoController;
	
	@POST
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.TEXTO)
	public boolean insert(Fornecedor f) {

		this.fornecedorController.insert(f);

		try {
			logController.registerLogConpag(this.getUser(), "Fornecedor cadastrado, id = " + f.getId(), Log.INFO);
		} catch (NoUserException e) {
			logController.registerLogConpag(null, "[Sem usuário na sessão]Fornecedor cadastrado, id = " + f.getId(),
					Log.ERRO);
		}

		return true;
	}

	@PUT
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.TEXTO)
	public boolean update(Fornecedor f) throws Exception {
		this.fornecedorController.update(f);

		try {
			logController.registerLogConpag(this.getUser(), "Fornecedor alterado, id = " + f.getId(), Log.INFO);
		} catch (NoUserException e) {
			logController.registerLogConpag(null, "[Sem usuário na sessão]Fornecedor alterado, id = " + f.getId(),
					Log.ERRO);
		}

		return true;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(RestTypes.TEXTO)
	public boolean excluir(@PathParam("id") int id) {
		try {
			this.fornecedorController.desativa(id);

			try {
				logController.registerLogConpag(this.getUser(), "Fornecedor excluído, id = " + id, Log.INFO);
			} catch (NoUserException e) {
				logController.registerLogConpag(null, "[Sem usuário na sessão]Fornecedor excluído, id = " + id,
						Log.ERRO);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@GET
	@Path("/by-id/{id}")
	public Fornecedor getById(@PathParam("id") int id) {
		return this.fornecedorController.getById(id);
	}

	@POST
	@Path("/lista-dto")
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.JSON_UTF8)
	public Response getFornecedores(FornecedorFiltroDTO filtro) {

		List<FornecedorDTO> lista = this.fornecedorController.getFornecedores(filtro);

		int total = this.fornecedorController.getTotalRegistros(filtro);

		return Response.ok(lista).header("x-total", total).build();
	}

	@GET
	@Path("/fornecedor-existente")
	@Produces(RestTypes.TEXTO)
	public Boolean getFornecedorByParams( @QueryParam("cnpjCpf") String cnpjCpf) {

		Fornecedor f = this.fornecedorController.getFornecedorByParams(cnpjCpf);
		
		if (f != null) {
			return true;
		} else {
			return false;
		}
	}

	
	@POST
	@Path("/bairros")
	@Produces(RestTypes.JSON_UTF8)
	@Consumes(RestTypes.JSON_UTF8)
	public List<Bairros> getBairrosByMunicipio(Municipio municipio) {
		List<Bairros> lista = this.bairrosController.getBairrosByMunicipio(municipio);
		
		return lista;
	}

	@POST
	@Path("/enderecos")
	@Produces(RestTypes.JSON_UTF8)
	@Consumes(RestTypes.JSON_UTF8)
	public List<Enderecos> getEnderecosByBairro(Bairros bairro) {
		List<Enderecos> lista = this.enderecosController.getEnderecosByBairro(bairro);
		return lista;
	}

	@GET
	@Path("/bancos")
	@Produces(RestTypes.JSON_UTF8)
	public List<Bancos> getBancos() {
		List<Bancos> lista = this.bancoController.getAll();
		return lista;
	}

}
