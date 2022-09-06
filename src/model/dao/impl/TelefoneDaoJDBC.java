package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.TelefoneDao;
import model.entities.Carro;
import model.entities.Categoria;
import model.entities.Cliente;
import model.entities.Telefone;
import model.entities.enums.Cor;

public class TelefoneDaoJDBC implements TelefoneDao{
	
	private Connection conn;

	public TelefoneDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	

	@Override
	public void insert(Telefone obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO telefone "
					+ "(numero, telefoneId) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNumero());
			st.setInt(2, obj.getCliente().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
			
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally {
				DB.closeStatement(st);
			}
		}


	@Override
	public void update(Telefone obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE telefone "
					+ "SET numero = ?, telefoneId = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getNumero());
			st.setInt(2, obj.getCliente().getId());
			st.setInt(3, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}


	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM telefone WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Telefone findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT *"
					+ "FROM telefone INNER JOIN cliente "
					+ "ON telefone.telefoneId = cliente.id "
					+ "WHERE telefoneId = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente dep = instantiateCliente(rs);
				Telefone obj = instantiateTelefone(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	private Telefone instantiateTelefone(ResultSet rs, Cliente dep) throws SQLException {
		Telefone obj = new Telefone();
		obj.setId(rs.getInt("id"));
		obj.setNumero(rs.getString("numero"));
		obj.setCliente(dep);
		return obj;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente dep = new Cliente();
		dep.setId(rs.getInt("telefoneId"));
		dep.setNome(rs.getString("nome"));
		dep.setCpf(rs.getString("cpf"));
		dep.setEmai(rs.getString("email"));
		return dep;
	}

	@Override
	public List<Telefone> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT telefone.*,cliente.nome as Nome"
					+ "FROM telefone INNER JOIN cliente "
					+ "ON telefone.telefoneId = cliente.id "
					+ "ORDER BY numero");
			
			rs = st.executeQuery();
			
			List<Telefone> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			
			while (rs.next()) {
				
				Cliente dep = map.get(rs.getInt("telefoneId"));
				
				if (dep == null) {
					dep = instantiateCliente(rs);
					map.put(rs.getInt("telefoneId"), dep);
				}
				
				Telefone obj = instantiateTelefone(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}



	@Override
	public List<Telefone> findByCliente(Cliente cliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT telefone.*,cliente.nome as Nome"
					+ "FROM telefone INNER JOIN cliente "
					+ "ON telefone.telefoneId = cliente.id "
					+ "WHERE telefoneId = ? "
					+ "ORDER BY cliente.nome");
			
			st.setInt(1, cliente.getId());
			
			rs = st.executeQuery();
			
			List<Telefone> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			
			while (rs.next()) {
				
				Cliente dep = map.get(rs.getInt("telefoneId"));
				
				if (dep == null) {
					dep = instantiateCliente(rs);
					map.put(rs.getInt("telefoneId"), dep);
				}
				
				Telefone obj = instantiateTelefone(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}