package br.com.conpag.controller.conpag;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.dao.conpag.FornecedorDAO;
import br.com.conpag.entity.conpag.Fornecedor;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorDTO;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorFiltroDTO;

@Stateless
public class FornecedorController extends BaseController<Fornecedor> {

	@Inject
	private FornecedorDAO dao;

	public void insert(Fornecedor f) {

		if (f != null) {

			if (f.getEndereco() != null && f.getEndereco().getId() < 1) {
				if (f.getEndereco().getBairro() != null && f.getEndereco().getBairro().getId() < 1) {
					this.em.persist(f.getEndereco().getBairro());
				}
				this.em.persist(f.getEndereco());
			}

			if (f.getBanco().getId() < 1) {
				em.persist(f.getBanco());
			} else {
				em.merge(f.getBanco());
			}

		}

		em.persist(f);
	}

	public void update(Fornecedor f) {

		if (f != null) {

			if (f.getEndereco() != null && f.getEndereco().getId() < 1) {
				if (f.getEndereco().getBairro() != null && f.getEndereco().getBairro().getId() < 1) {
					this.em.persist(f.getEndereco().getBairro());
				}
				this.em.persist(f.getEndereco());
			}

			if (f.getBanco().getId() < 1) {
				em.persist(f.getBanco());
			} else {
				em.merge(f.getBanco());
			}
		}

		f = em.merge(f);
	}

	public Fornecedor getByIdFornecefor(int id) {

		Fornecedor f = em.find(Fornecedor.class, id);
		return f;
	}

	public Fornecedor getFornecedorByParams(String cnpjCpf) {

		Query q = em.createNamedQuery(Fornecedor.JPQL_GET_FORNECEDOR_BY_PARAMS);
		q.setParameter("cnpjCpf", cnpjCpf);

		@SuppressWarnings("unchecked")
		List<Fornecedor> lista = q.getResultList();

		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}
	
	public List<FornecedorDTO> getFornecedores(FornecedorFiltroDTO filtro) {
		ArrayList<FornecedorDTO> lista = this.dao.getFornecedores(filtro);
		return lista;
	}

	public int getTotalRegistros(FornecedorFiltroDTO filtro) {
		return this.dao.getTotalRegistros(filtro);
	}

}
