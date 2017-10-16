package br.com.conpag.dao.sistema;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.sistema.Municipio;

public class MunicipioDAO extends BaseDAO {
	
	private static final String SQL_UPDATE_GEODATA = "municipio.updateGeodata";

	@Inject
	private LogController logController;
	
	
	public void updateGeoData( Municipio municipio ){
		PreparedStatement ps = null;
		try{
			String sql = this.queryManager.getQuery( SQL_UPDATE_GEODATA );
			ps = this.connection.prepareStatement( sql );
			
			ps.setDouble(1, municipio.getLatitude() );
			ps.setDouble(2, municipio.getLongitude() );
			ps.setInt(3, municipio.getId() );
			ps.execute();
		}
		catch (SQLException e){
			logController.printLog(e, true);
		}
	}

}
