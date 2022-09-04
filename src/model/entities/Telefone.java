package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Telefone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String numero;
	
	public Telefone() {
		
	}

	public Telefone(String numero) {
		this.numero = numero;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Telefone [numero=" + numero + "]";
	}

}
