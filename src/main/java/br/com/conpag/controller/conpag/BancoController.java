package br.com.conpag.controller.conpag;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.entity.conpag.Bancos;

@Stateless
public class BancoController extends BaseController<Bancos> {

	@SuppressWarnings("unchecked")
	public List<Bancos> getBancos() {
		Query q = em.createNamedQuery(Bancos.JPQL_GET_BANCOS);
		return q.getResultList();

	}
}
