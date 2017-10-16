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
import javax.ws.rs.core.Response;

import br.com.conpag.controller.conpag.ContaPagarController;
import br.com.conpag.controller.conpag.EmpresaController;
import br.com.conpag.controller.conpag.FornecedorController;
import br.com.conpag.controller.conpag.GrupoController;
import br.com.conpag.controller.conpag.ParcelasPagasController;
import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.entity.conpag.ContaPagar;
import br.com.conpag.entity.conpag.Empresa;
import br.com.conpag.entity.conpag.Fornecedor;
import br.com.conpag.entity.conpag.Grupo;
import br.com.conpag.entity.conpag.ParcelasPagas;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarDTO;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarFiltroDTO;
import br.com.conpag.entity.exceptions.NoUserException;
import br.com.conpag.service.sistema.BaseService;
import br.com.conpag.util.Log;
import br.com.conpag.util.RestTypes;

@Path("/contapagar")
public class ContaPagarService extends BaseService {

	@EJB
	private LogController logController;

	@EJB
	private ContaPagarController contaPagarController;
	
	@EJB
	private FornecedorController fornecedorController;
	
	@EJB
	private GrupoController grupoController;
	
	@EJB
	private EmpresaController empresaController;
	
	@EJB
	private ParcelasPagasController parcelasPagasController;
		
	@POST
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.TEXTO)
	public boolean insert(ContaPagar p) {

		this.contaPagarController.insert(p);

		try {
			logController.registerLogConpag(this.getUser(), "Conta a pagar cadastrada, id = " + p.getId(), Log.INFO);
		} catch (NoUserException e) {
			logController.registerLogConpag(null, "[Sem usuário na sessão]Conta a pagar cadastrada, id = " + p.getId(),
					Log.ERRO);
		}

		return true;
	}
	
	@POST
	@Path("/quitacao")
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.TEXTO)
	public boolean insertQuitacao(ParcelasPagas p) {

		this.parcelasPagasController.insert(p);

		try {
			logController.registerLogConpag(this.getUser(), "Parcela paga, id = " + p.getId(), Log.INFO);
		} catch (NoUserException e) {
			logController.registerLogConpag(null, "[Sem usuário na sessão]Parcela paga, id = " + p.getId(),
					Log.ERRO);
		}

		return true;
	}

	@PUT
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.TEXTO)
	public boolean update(ContaPagar p) throws Exception {
		this.contaPagarController.update(p);

		try {
			logController.registerLogConpag(this.getUser(), "Conta a pagar alterada, id = " + p.getId(), Log.INFO);
		} catch (NoUserException e) {
			logController.registerLogConpag(null, "[Sem usuário na sessão]Conta a pagar alterada, id = " + p.getId(),
					Log.ERRO);
		}

		return true;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(RestTypes.TEXTO)
	public boolean excluir(@PathParam("id") int id) {
		try {
			this.contaPagarController.delete(id);

			try {
				logController.registerLogConpag(this.getUser(), "Conta a pagar excluída, id = " + id, Log.INFO);
			} catch (NoUserException e) {
				logController.registerLogConpag(null, "[Sem usuário na sessão]Conta a pagar excluída, id = " + id,
						Log.ERRO);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@POST
	@Path("/lista-contas-pagar")
	@Consumes(RestTypes.JSON_UTF8)
	@Produces(RestTypes.JSON_UTF8)
	public Response getPacientes(ContaPagarFiltroDTO filtro) {
		
		List<ContaPagarDTO> lista = this.contaPagarController.getContasPagar(filtro);
		
		int total = this.contaPagarController.getTotalRegistros(filtro);
		
		return Response.ok(lista).header("x-total", total).build();
	}
	
	@GET
	@Path("/by-id/{id}")
	public ContaPagar getById(@PathParam("id") int id) {
		return this.contaPagarController.getById(id);
	}
	
	@GET
	@Path("/lista-fornecedores")
	@Produces(RestTypes.JSON_UTF8)
	public List<Fornecedor> getListaFornecedores() {
		List<Fornecedor> lista = this.fornecedorController.getAll();
		
		return lista;
	}

	@GET
	@Path("/grupos")
	@Produces(RestTypes.JSON_UTF8)
	public List<Grupo> getGrupos() {
		List<Grupo> lista = this.grupoController.getAll();
		
		return lista;
	}

	@GET
	@Path("/empresas")
	@Produces(RestTypes.JSON_UTF8)
	public List<Empresa> getEmpresas() {
		List<Empresa> lista = this.empresaController.getAll();
		
		return lista;
	}
}
