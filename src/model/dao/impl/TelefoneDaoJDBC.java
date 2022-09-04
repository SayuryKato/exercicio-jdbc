package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.TelefoneDao;
import model.entities.Categoria;
import model.entities.Telefone;

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
				"INSERT INTO telefone (numero) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNumero());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
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
				"UPDATE telefone " +
				"SET numero = ?" +
				"WHERE Id = ?");

			st.setString(1, obj.getNumero());
			st.setInt(2, obj.getId());

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
			st = conn.prepareStatement(
				"DELETE FROM telefone WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
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
				"SELECT * FROM telefone WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Telefone obj = new Telefone();
				obj.setId(rs.getInt("id"));
				obj.setNumero(rs.getString("numero"));
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

	@Override
	public List<Telefone> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT * FROM telefone ORDER BY numero");
			rs = st.executeQuery();

			List<Telefone> list = new ArrayList<>();

			while (rs.next()) {
				Telefone obj = new Telefone();
				obj.setId(rs.getInt("id"));
				obj.setNumero(rs.getString("numero"));
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