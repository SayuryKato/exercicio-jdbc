package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Telefone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String numero;
	
	private Cliente cliente;
	
	public Telefone() {
		
	}


	public Telefone(Integer id, String numero, Cliente cliente) {
		this.id = id;
		this.numero = numero;
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		Telefone other = (Telefone) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(id, other.id)
				&& Objects.equals(numero, other.numero);
	}


	@Override
	public String toString() {
		return "Telefone [id=" + id + ", numero=" + numero + ", cliente=" + cliente + "]";
	}
	
	
}
