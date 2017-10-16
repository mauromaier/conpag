package br.com.conpag.controller.sistema;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.conpag.dao.sistema.SistemaDAO;

@Stateless
public class SistemaController {
	
	@Inject
	private SistemaDAO sistemaDao;
	
	public String getParametro( String chave){
		return sistemaDao.getParameter( chave );
	}
	
	public boolean setParametro( String key, String value ){
		return sistemaDao.setParameter(key, value);
	}
	
}
