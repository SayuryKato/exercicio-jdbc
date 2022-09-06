package model.entities;

public class LocacaoLongoPeriodo extends Locacao{

	private static final long serialVersionUID = 1L;
	
	private Integer porcentagemDesconto; 
	
	
	public LocacaoLongoPeriodo() {
	}

	public LocacaoLongoPeriodo(Integer porcentagemDesconto) {
		this.porcentagemDesconto = porcentagemDesconto;
	}

	public Integer getPorcentagemDesconto() {
		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(Integer porcentagemDesconto) {
		this.porcentagemDesconto = porcentagemDesconto;
	}
	
	public Double Desconto(Double porcentagemDesconto, Double preco) {
		return preco + (preco*porcentagemDesconto)/100;
	}

}
