package br.com.conpag.controller.conpag;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.conpag.controller.BaseController;
import br.com.conpag.dao.conpag.ContaPagarDAO;
import br.com.conpag.entity.conpag.ContaPagar;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarDTO;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarFiltroDTO;

@Stateless
public class ContaPagarController extends BaseController<ContaPagar>{
	
	@Inject
	private ContaPagarDAO contaPagarDAO;

	public List<ContaPagarDTO> getContasPagar(ContaPagarFiltroDTO filtro) {
		ArrayList<ContaPagarDTO> lista = this.contaPagarDAO.getContasPagar(filtro);
		return lista;
	}

	public int getTotalRegistros(ContaPagarFiltroDTO filtro) {
		return this.contaPagarDAO.getTotalRegistros(filtro);
	}
}
