package br.com.conpag.entity.dto.sistema;

public class GenericTipoDTO implements Comparable<GenericTipoDTO>{
	
	protected String nome;
	protected long total;
	protected double percentual;

	
	public GenericTipoDTO() {
		super();
	}
	
	public GenericTipoDTO(String nome, long total) {
		super();
		this.nome = nome;
		this.total = total;
	}
	
	public void addTotal( long total ){
		this.total += total;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public double getPercentual() {
		return percentual;
	}
	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

	@Override
	public int compareTo(GenericTipoDTO dto) {
		if ( this.total < dto.getTotal() ){
			return 1;
		}
		else if ( this.total > dto.getTotal() ){
			return -1;
		}
		
		return 0;
	}

}
