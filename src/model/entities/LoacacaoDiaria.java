package model.entities;


public class LoacacaoDiaria extends Locacao{

	private static final long serialVersionUID = 1L;
	
	private Integer diasPrevisto;
	
	
	public LoacacaoDiaria() {

	}
	public LoacacaoDiaria(Integer diasPrevisto) {
		super();
		this.diasPrevisto = diasPrevisto;
	}

	public Integer getDiasPrevisto() {
		return diasPrevisto;
	}
	public void setDiasPrevisto(Integer diasPrevisto) {
		this.diasPrevisto = diasPrevisto;
	}
	@Override
	public String toString() {
		return "LoacacaoDiaria [diasPrevisto=" + diasPrevisto + "]";
	}
	
//	public Integer diasPrevistoDevolucao() {
//		Duration diferenca = Duration.between(getDataRetirada().atStartOfDay(),getDataDevolucao().atStartOfDay());
//		int dias = (int) diferenca.toDays();
//		return dias;
//	}

	
	
	

}
