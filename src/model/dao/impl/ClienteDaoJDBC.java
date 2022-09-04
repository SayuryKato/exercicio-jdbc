package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.ClienteDao;
import model.entities.Cliente;
import model.entities.Telefone;

public class ClienteDaoJDBC implements ClienteDao{
	
	private Connection conn;

	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Cliente obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Cliente obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> findByTelefone(Telefone telefone) {
		// TODO Auto-generated method stub
		return null;
	}

}
