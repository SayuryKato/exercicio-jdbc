package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import model.entities.enums.Cor;

public class Carro implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String modelo;
	private String placa;
	private Cor cor;
	private Integer ano;
	private LocalDate dataAquisicao;
	
	private Categoria categoria;
	
	public Carro() {
		
	}

	public Carro(Integer id, String modelo, String placa, Cor cor, Integer ano, LocalDate dataAquisicao, Categoria categoria) {
		this.id = id;
		this.modelo = modelo;
		this.placa = placa;
		this.cor = cor;
		this.ano = ano;
		this.dataAquisicao = dataAquisicao;
		this.categoria = categoria;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public LocalDate getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(LocalDate dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		Carro other = (Carro) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(categoria, other.categoria) && cor == other.cor
				&& Objects.equals(dataAquisicao, other.dataAquisicao) && Objects.equals(id, other.id)
				&& Objects.equals(modelo, other.modelo) && Objects.equals(placa, other.placa);
	}

	@Override
	public String toString() {
		return "Carro [id=" + id + ", modelo=" + modelo + ", placa=" + placa + ", cor=" + cor + ", ano=" + ano
				+ ", dataAquisicao=" + dataAquisicao + ", categoria=" + categoria + "]";
	}
	

}
