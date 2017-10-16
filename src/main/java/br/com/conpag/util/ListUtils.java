package br.com.conpag.util;

import java.util.List;

import javax.inject.Singleton;

import br.com.conpag.entity.dto.sistema.GenericTipoDTO;

@Singleton
public class ListUtils {
	
	private ListUtils(){
	}

	public void setPercentual ( List<? extends GenericTipoDTO> lista ){
		double total = 0;
		for ( GenericTipoDTO dto: lista ){
			total += dto.getTotal();
		}
		for ( GenericTipoDTO dto: lista ){
			dto.setPercentual(  ( (double)dto.getTotal() * 100d / total)  );		
		}
	}

}
