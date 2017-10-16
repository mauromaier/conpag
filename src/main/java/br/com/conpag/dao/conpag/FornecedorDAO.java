package br.com.conpag.dao.conpag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorDTO;
import br.com.conpag.entity.dto.sistema.conpag.FornecedorFiltroDTO;


public class FornecedorDAO extends BaseDAO{
	
	
	private static final String SQL_SELECT_ALL = "fornecedor.selectAll";
	private static final String SQL_FILTRO_FORNECEDOR = "fornecedor.filtroFornecedor";
	private static final String SQL_FILTRO_MUNICIPIO = "fornecedor.filtroMunicipio";
	private static final String SQL_FILTRO_CNPJ_CPF = "fornecedor.filtroCnpjCpf";
	private static final String SQL_ORDER_BY = "fornecedor.orderBy";
	private static final String SQL_FILTRO_PAGINACAO = "fornecedor.filtroPaginacao";
	private static final String SQL_SELECT_REQUISICAO_ROW_COUNT = "fornecedor.selectRequisicaoRowCount";
	
	@Inject
	private LogController logController;
	
	public ArrayList<FornecedorDTO> getFornecedores(FornecedorFiltroDTO filtro) {

		ArrayList<FornecedorDTO> lista = new ArrayList<>();
		try {
			PreparedStatement ps = getPsFornecedor(SQL_SELECT_ALL, filtro, false);			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				FornecedorDTO f = new FornecedorDTO();
				f.setId(rs.getInt("cd_id"));
				f.setNomeRazao(rs.getString("nomeRazao"));
				f.setCnpjCpf(rs.getString("cnpjCpf"));
				f.setFone(rs.getString("fone"));
				f.setEmail(rs.getString("email"));
				f.setBanco(rs.getString("banco"));
				f.setMunicipio(rs.getString("municipio"));
				
				
				lista.add(f);
			}
			return lista;
		} catch (SQLException e) {
			logController.printLog(e, true);
		}
		return null;
	}

	public int getTotalRegistros(FornecedorFiltroDTO filtro) {

		try {
			PreparedStatement ps = getPsFornecedor(SQL_SELECT_REQUISICAO_ROW_COUNT, filtro, true);
			
			return super.getRowCount(ps);

		} catch (Exception e) {
			logController.printLog(e, true);
		}

		return 0;
	}
	
	public PreparedStatement getPsFornecedor(String sql, FornecedorFiltroDTO filtro, boolean isRowCount) throws SQLException{
		int x = 1;
		
		String query = this.queryManager.getQuery(sql);

		if (filtro.getIdFornecedor() > 0) {
			query += this.queryManager.getQuery(SQL_FILTRO_FORNECEDOR);
		}

		if (filtro.getCnpjCpf() != null && filtro.getCnpjCpf().length() > 0) {
			query += this.queryManager.getQuery(SQL_FILTRO_CNPJ_CPF);
		}

		if (filtro.getIdMunicipio() > 0) {
			query += this.queryManager.getQuery(SQL_FILTRO_MUNICIPIO);
		}
		
		if( !isRowCount ){
			query += this.queryManager.getQuery(SQL_ORDER_BY);

			if (filtro.getPagina() > 0) {
				query += this.queryManager.getQuery(SQL_FILTRO_PAGINACAO);
			}			
		}

		PreparedStatement ps = this.connection.prepareStatement(query);

		if (filtro.getIdFornecedor() > 0) {
			ps.setInt(x++, filtro.getIdFornecedor());
		}

		if (filtro.getCnpjCpf() != null && filtro.getCnpjCpf().length() > 0) {
			ps.setString(x++, filtro.getRazaoNome());
		}

		if (filtro.getCnpjCpf() != null && filtro.getCnpjCpf().length() > 0) {
			ps.setString(x++, filtro.getCnpjCpf());
		}

		if (filtro.getIdMunicipio() > 0) {
			ps.setInt(x++, filtro.getIdMunicipio());
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
