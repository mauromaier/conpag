package br.com.conpag.controller.sistema;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.entity.sistema.Municipio;

@Stateless
public class MunicipioController extends BaseController<Municipio>{
	
	@SuppressWarnings("unchecked")
	public List<Municipio> selectByUf( String uf ){
        Query q = this.em.createNamedQuery( Municipio.JPQL_SELECT_BY_UF );
        q.setParameter("uf", uf );
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Municipio selectByNome( String nome ){
        Query q = this.em.createNamedQuery( Municipio.JPQL_SELECT_BY_NOME );
        q.setParameter("nome", nome );
        List<Object> l =q.getResultList();
        if ( l != null && l.size() > 0 ){
        	return (Municipio) l.get(0);
        }
        return null;
	}

	@SuppressWarnings("unchecked")
	public Municipio selectByNomeAndUf( String nome, String uf ){
        Query q = em.createNamedQuery( Municipio.JPQL_SELECT_BY_NOME_AND_UF );
        q.setParameter("nome", nome );
        q.setParameter("uf", uf );
        
        List<Object> l =q.getResultList();
        if ( l != null && l.size() > 0 ){
        	return (Municipio) l.get(0);
        }
        return null;
	}
	
	@SuppressWarnings("unchecked")
	public Municipio selectByNomeSC( String nome ){
        Query q = em.createNamedQuery(Municipio.JPQL_SELECT_BY_NOME_SC);
        q.setParameter("nome", nome );
        List<Object> l =q.getResultList();
        if ( l != null && l.size() > 0 ){
        	return (Municipio)l.get(0);
        }
        return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> selectEstados(){
		return this.em.createNamedQuery(Municipio.JPQL_SELECT_UFS).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> getAll(){
		Query q = this.em.createNamedQuery( Municipio.JPQL_SELECT_ALL );
		return q.getResultList();
	}
	
	// TODO GEOREFERENCIAMENTO
//	public void updateGeoData(){
//		List<Municipio> lista = selectMunicipiosSC();
//		for( Municipio m:lista ){
//			if ( m.getLatitude() == null ){
//				Geocoder g = new Geocoder();
//				String xml = g.doGeocode( m.getNome() + "," + m.getUf(), 1 );
//				try {
//					new MunicipioGoogleMapsXMLParser( xml, m );
//				} catch (ParserConfigurationException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (SAXException e) {
//					e.printStackTrace();
//				} catch (ParseCancerXMLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}


}
