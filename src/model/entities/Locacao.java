package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Locacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private LocalDateTime dataRetirada;
	private LocalDateTime dataDevolucao;
	
	private Cliente cliente;
	private Carro carro;
	private LoacacaoDiaria locacaoDiaria;
	private LocacaoLongoPeriodo locacaoLongoPeriodo; 
	
	public Locacao() {
		
	}
	
	
	public Locacao(Integer id, LocalDateTime dataRetirada, LocalDateTime dataDevolucao, Cliente cliente, Carro carro,
			LoacacaoDiaria locacaoDiaria, LocacaoLongoPeriodo locacaoLongoPeriodo) {
		this.id = id;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.cliente = cliente;
		this.carro = carro;
		this.locacaoDiaria = locacaoDiaria;
		this.locacaoLongoPeriodo = locacaoLongoPeriodo;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}


	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}


	public LocalDateTime getDataDevolucao() {
		return dataDevolucao;
	}


	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public LoacacaoDiaria getLocacaoDiaria() {
		return locacaoDiaria;
	}

	public void setLocacaoDiaria(LoacacaoDiaria locacaoDiaria) {
		this.locacaoDiaria = locacaoDiaria;
	}
	public LocacaoLongoPeriodo getLocacaoLongoPeriodo() {
		return locacaoLongoPeriodo;
	}
	
	public void setLocacaoLongoPeriodo(LocacaoLongoPeriodo locacaoLongoPeriodo) {
		this.locacaoLongoPeriodo = locacaoLongoPeriodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		return Objects.equals(carro, other.carro) && Objects.equals(cliente, other.cliente)
				&& Objects.equals(dataDevolucao, other.dataDevolucao)
				&& Objects.equals(dataRetirada, other.dataRetirada) && Objects.equals(id, other.id);
	
}
@Override
public String toString() {
StringBuilder stringBuilder = new StringBuilder();
	
	DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	stringBuilder.append("Id: " + this.getId());
	stringBuilder.append(", Data Retirada: " + this.getDataRetirada().format(formatacao));
	stringBuilder.append(", Data Devolucao: " + this.getDataDevolucao().format(formatacao));
	stringBuilder.append(", Cliente: " + this.getCliente());
	stringBuilder.append(" Carro: " + this.getCarro());
	
	return stringBuilder.toString();

}



}
