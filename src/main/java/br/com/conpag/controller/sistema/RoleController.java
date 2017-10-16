package br.com.conpag.controller.sistema;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.entity.sistema.Modulo;
import br.com.conpag.entity.sistema.Role;

@Stateless
public class RoleController extends BaseController<Role>{
	
	private RoleController(){
	}

	@SuppressWarnings( "unchecked" )
	public List<Role> selectByModulo( Modulo m ){
        Query q = em.createNamedQuery(Role.JPQL_SELECT_BY_MODULO);
        q.setParameter( "modulo", m );
        return q.getResultList();
	}
	
}
