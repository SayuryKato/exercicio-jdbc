package model.entities;

import java.io.Serializable;
import java.util.Objects;


public class Categoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String descricao;
	private Double precoPorDia;
	
	public Categoria() {
		
	}

	public Categoria(Integer id, String descricao, Double precoPorDia) {
		this.id = id;
		this.descricao = descricao;
		this.precoPorDia = precoPorDia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrecoPorDia() {
		return precoPorDia;
	}

	public void setPrecoPorDia(Double precoPorDia) {
		this.precoPorDia = precoPorDia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descricao, id, precoPorDia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(precoPorDia, other.precoPorDia);
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", descricao=" + descricao + ", precoPorDia=" + precoPorDia + "]";
	}
	
	

}
