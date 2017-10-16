package br.com.conpag.controller.conpag;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.entity.conpag.Bairros;
import br.com.conpag.entity.conpag.Enderecos;

@Stateless
public class EnderecosController extends BaseController<Enderecos> {

	@SuppressWarnings("unchecked")
	public List<Enderecos> getEnderecosByBairro(Bairros b) {

		Query q = em.createNamedQuery(Enderecos.JPQL_GET_ENDERECOS_BY_BAIRRO);
		q.setParameter("bairro", b);
		return q.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Enderecos> getEnderecos() {
		Query q = em.createNamedQuery(Enderecos.JPQL_GET_ENDERECOS);
		return q.getResultList();

	}

}
