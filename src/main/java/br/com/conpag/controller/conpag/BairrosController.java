package br.com.conpag.controller.conpag;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.entity.conpag.Bairros;
import br.com.conpag.entity.sistema.Municipio;

@Stateless
public class BairrosController extends BaseController<Bairros> {

	@SuppressWarnings("unchecked")
	public List<Bairros> getBairrosByMunicipio(Municipio m) {
		Query q = em.createNamedQuery(Bairros.JPQL_GET_BAIRROS_BY_MUNICIPIO);
		q.setParameter("municipio", m);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Bairros> getBairros() {
		Query q = em.createNamedQuery(Bairros.JPQL_GET_BAIRROS);
		return q.getResultList();
	}
}
