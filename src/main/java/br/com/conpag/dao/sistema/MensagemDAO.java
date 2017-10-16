package br.com.conpag.dao.sistema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.dto.sistema.MensagemDTO;
import br.com.conpag.entity.dto.sistema.MensagemFiltroDTO;

public class MensagemDAO extends BaseDAO{

	private static String SQL_SELECT_LIMIT_OFFSET = "limitOffset";
	
	private static String SQL_SELECT_MENSAGEM_RECEBIDAS_DTO = "mensagem.selectRecebidasDto";
	private static String SQL_SELECT_ROW_COUNT_RECEBIDAS = "mensagem.selectRowCountRecebidas";
	
	private static String SQL_SELECT_FILTRO_REGIONAL  = "mensagem.selectFiltroRegional";
	private static String SQL_SELECT_ORDER_BY = "mensagem.selectOrderBy";
	
	private static String SQL_SELECT_MENSAGEM_ENVIADAS_DTO = "mensagem.selectDtoEnviada";
	private static String SQL_SELECT_ROW_COUNT_ENVIADAS = "mensagem.selectRowCountEnviadas";
	
	@Inject
	private LogController logController;
	
	public List<MensagemDTO> getMensagensRecebidasDto(  MensagemFiltroDTO filtro ){
		List<MensagemDTO> lista = new ArrayList<MensagemDTO>();
		MensagemDTO msg = null;

		try{
			PreparedStatement ps = this.getPSMensagensRecebidas(SQL_SELECT_MENSAGEM_RECEBIDAS_DTO, filtro, false);
			ResultSet rs = ps.executeQuery();
			while( rs.next() ){
				Calendar data = Calendar.getInstance();
				data.setTimeInMillis( rs.getTimestamp("data").getTime() );
				
				msg = new MensagemDTO();
				msg.setId( rs.getInt("id") );
				msg.setUsuario( rs.getString("remetente"));
				msg.setData( data );
				msg.setAssunto( rs.getString("assunto") );
				msg.setTexto( rs.getString("texto") );
				msg.setLida( rs.getInt("msg_lida") == 1 ? true : false );
				
				lista.add( msg );
			}
		}
		catch( SQLException e ){
			logController.printLog(e, true);
		}
		
		return lista;
	}
	
	public int getCountRecebidas( MensagemFiltroDTO filtro ){
		try{
			PreparedStatement ps = this.getPSMensagensRecebidas(SQL_SELECT_ROW_COUNT_RECEBIDAS, filtro, true);
			return this.getRowCount( ps );
		}
		catch( SQLException e ){
			logController.printLog(e, true);
		}
		return 0;
	}
	
	
	public List<MensagemDTO> getMensagensEnviadasDto( MensagemFiltroDTO filtro ){
		List<MensagemDTO> lista = new ArrayList<>();

		try{
			PreparedStatement ps = this.getPSMensagensEnviadas(SQL_SELECT_MENSAGEM_ENVIADAS_DTO, filtro, false);
			ResultSet rs = ps.executeQuery();
			while( rs.next() ){
				Calendar data = Calendar.getInstance();
				data.setTimeInMillis( rs.getTimestamp("data").getTime() );
				
				MensagemDTO msg = new MensagemDTO();
				msg.setId( rs.getInt("id") );
				msg.setUsuario( rs.getString("destinatario"));
				msg.setData( data );
				msg.setAssunto( rs.getString("assunto") );
				msg.setTexto( rs.getString("texto") );
				msg.setLida( rs.getBoolean("msg_lida") );
				
				lista.add( msg );
			}
		}
		catch( SQLException e ){
			logController.printLog(e, true);
		}
		
		return lista;
	}
	
	public int getCountEnviadas( MensagemFiltroDTO filtro ){
		try{
			PreparedStatement ps = this.getPSMensagensRecebidas(SQL_SELECT_ROW_COUNT_ENVIADAS, filtro, true);
			return this.getRowCount( ps );
		}
		catch( SQLException e ){
			logController.printLog(e, true);
		}
		return 0;
	}
	
	private PreparedStatement getPSMensagensRecebidas(String query, MensagemFiltroDTO filtro, boolean isRowCount) throws SQLException{
		int x = 1;
		String sql = this.queryManager.getQuery( query );
		
		if( filtro.getIdRegional() > 0 ){
			sql += this.queryManager.getQuery( SQL_SELECT_FILTRO_REGIONAL );
		}
		
		if( !isRowCount ){
			sql += this.queryManager.getQuery( SQL_SELECT_ORDER_BY );
			sql += this.queryManager.getQuery( SQL_SELECT_LIMIT_OFFSET );
		}
		
		PreparedStatement ps = this.connection.prepareStatement( sql );
		ps.setInt(x++, filtro.getIdUser() );
		ps.setDate(x++, new java.sql.Date( filtro.getInicio().getTime() ));
		ps.setDate(x++, new java.sql.Date( filtro.getFim().getTime() ));
		
		if( filtro.getIdRegional() > 0 ){
			ps.setInt( x++, filtro.getIdRegional() );	
		}
		
		if( !isRowCount ){
			if( filtro.getLimite() > 0 && filtro.getPagina() > 0 ){
				ps.setInt(x++, filtro.getLimite());
				ps.setInt(x++, this.getOffset(filtro.getPagina(), filtro.getLimite() ));
			}
		}
		
		return ps;
	}
	
	private PreparedStatement getPSMensagensEnviadas(String query, MensagemFiltroDTO filtro, boolean isRowCount) throws SQLException{
		int x = 1;
		String sql = this.queryManager.getQuery( query );
		
		if( !isRowCount ){
			sql += this.queryManager.getQuery( SQL_SELECT_LIMIT_OFFSET );
		}
		
		PreparedStatement ps = this.connection.prepareStatement( sql );
		ps.setInt(x++, filtro.getIdUser() );
		ps.setDate(x++, new java.sql.Date( filtro.getInicio().getTime() ));
		ps.setDate(x++, new java.sql.Date( filtro.getFim().getTime() ));
		
		if( !isRowCount ){
			if( filtro.getLimite() > 0 && filtro.getPagina() > 0 ){
				ps.setInt(x++, filtro.getLimite());
				ps.setInt(x++, this.getOffset(filtro.getPagina(), filtro.getLimite() ));
			}
		}
		
		return ps;
	}
	
}
