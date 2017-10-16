package br.com.conpag.dao.conpag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarDTO;
import br.com.conpag.entity.dto.sistema.conpag.ContaPagarFiltroDTO;


public class ContaPagarDAO extends BaseDAO{
	
	
	private static final String SQL_SELECT_ALL = "conpag.selectAll";
	private static final String SQL_FILTRO_EMPRESA = "conpag.filtroEmpresa";
	private static final String SQL_ORDER_BY = "conpag.orderBy";
	private static final String SQL_FILTRO_PAGINACAO = "geral.filtroPaginacao";
	private static final String SQL_FILTRO_DATA_EMISSAO = "conpag.filtroDataEmissao";
	private static final String SQL_SELECT_REQUISICAO_ROW_COUNT = "conpag.selectRequisicaoRowCount";
	private static final String SQL_FILTRO_PAGAMENTO = "conpag.filtroPagamento";
	
	@Inject
	private LogController logController;
	
	public ArrayList<ContaPagarDTO> getContasPagar(ContaPagarFiltroDTO filtro) {

		ArrayList<ContaPagarDTO> lista = new ArrayList<>();
		try {
			PreparedStatement ps = getPsContaPagar(SQL_SELECT_ALL, filtro, false);			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				ContaPagarDTO c = new ContaPagarDTO();
				c.setId(rs.getInt("cd_id"));
				c.setDtEmissao(rs.getDate("dt_emissao"));
				c.setFornecedor(rs.getString("fornecedor"));
				c.setEmpresa(rs.getString("empresa"));
				c.setDescricao(rs.getString("descricao"));
				c.setSaldoAnterior(rs.getDouble("saldoAnterior"));
				c.setEmCaixa(rs.getDouble("emCaixa"));
				c.setParaPagar(rs.getDouble("paraPagar"));
				c.setValorPago(rs.getDouble("valorPago"));
				
				
				
				lista.add(c);
			}
			return lista;
		} catch (SQLException e) {
			logController.printLog(e, true);
		}
		return null;
	}

	public int getTotalRegistros(ContaPagarFiltroDTO filtro) {

		try {
			PreparedStatement ps = getPsContaPagar(SQL_SELECT_REQUISICAO_ROW_COUNT, filtro, true);
			
			return super.getRowCount(ps);

		} catch (Exception e) {
			logController.printLog(e, true);
		}

		return 0;
	}
	
	public PreparedStatement getPsContaPagar(String sql, ContaPagarFiltroDTO filtro, boolean isRowCount) throws SQLException{
		int x = 1;
		
		String query = this.queryManager.getQuery(sql);

		if (filtro.getIdEmpresa() > 0) {
			query += this.queryManager.getQuery(SQL_FILTRO_EMPRESA);
		}

		if (filtro.getDtInicial() != null && filtro.getDtFinal() != null) {
			query += this.queryManager.getQuery(SQL_FILTRO_DATA_EMISSAO);
		}

		if(filtro.getIdPagamento() == 1){
			query += this.queryManager.getQuery(SQL_FILTRO_PAGAMENTO);
			query+=" IS NULL ";
		}else if(filtro.getIdPagamento() == 2){
			query += this.queryManager.getQuery(SQL_FILTRO_PAGAMENTO);
			query+=" IS NOT NULL ";
		}
	
		
		if( !isRowCount ){
			query += this.queryManager.getQuery(SQL_ORDER_BY);

			if (filtro.getPagina() > 0) {
				query += this.queryManager.getQuery(SQL_FILTRO_PAGINACAO);
			}			
		}

		PreparedStatement ps = this.connection.prepareStatement(query);

		if (filtro.getIdEmpresa() > 0) {
			ps.setInt(x++, filtro.getIdEmpresa());
		}

		if (filtro.getDtInicial() != null && filtro.getDtFinal() != null) {
			ps.setDate(x++, new java.sql.Date(filtro.getDtInicial().getTime()));
			ps.setDate(x++, new java.sql.Date(filtro.getDtFinal().getTime()));
		}

		if( !isRowCount ){
			if (filtro.getPagina() > 0) {
				ps.setInt(x++, filtro.getLimite()); // limit
				ps.setInt(x++, this.getOffset(filtro.getPagina(), filtro.getLimite())); // offset

			}			
		}
		
		return ps;
	}
	
}
