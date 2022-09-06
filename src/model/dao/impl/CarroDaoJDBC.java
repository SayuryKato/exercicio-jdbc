package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
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
import model.dao.CarroDao;
import model.entities.Carro;
import model.entities.Categoria;
import model.entities.enums.Cor;

public class CarroDaoJDBC implements CarroDao{
	
	private Connection conn;

	public CarroDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Carro obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO carro "
					+ "(mdelo, placa, cor, ano, dataAquisicao, carroId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getModelo());
			st.setString(2, obj.getPlaca());
			st.setString(3, String.valueOf(obj.getCor()));
			st.setInt(4, obj.getAno());
			st.setDate(5, Date.valueOf(obj.getDataAquisicao()));
			st.setInt(6, obj.getCategoria().getId());
			
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
	public void update(Carro obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE carro "
					+ "SET mdelo = ?, placa = ?, cor = ?, ano = ?, dataAquisicao = ? , carroId = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getModelo());
			st.setString(2, obj.getPlaca());
			st.setString(3, obj.getCor().toString());
			st.setInt(4, obj.getAno());
			st.setDate(5, Date.valueOf(obj.getDataAquisicao()));
			st.setInt(6, obj.getCategoria().getId());
			st.setInt(7, obj.getId());
			
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
			st = conn.prepareStatement("DELETE FROM carro WHERE Id = ?");
			
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
	public Carro findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM carro INNER JOIN categoria "
					+ "ON carro.carroId = categoria.id "
					+ "WHERE carro.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Categoria dep = instantiateCategoria(rs);
				Carro obj = instantiateCarro(rs, dep);
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
	Cor cor;
	private Carro instantiateCarro(ResultSet rs, Categoria dep) throws SQLException {
		Carro obj = new Carro();
		obj.setId(rs.getInt("id"));
		obj.setModelo(rs.getString("mdelo"));
		obj.setPlaca(rs.getString("placa"));
		obj.setCor(Cor.valueOf(rs.getString("cor")));
		obj.setAno(rs.getInt("Ano"));
		obj.setDataAquisicao(rs.getDate("DataAquisicao").toLocalDate());
		obj.setCategoria(dep);
		return obj;
	}

	private Categoria instantiateCategoria(ResultSet rs) throws SQLException {
		Categoria dep = new Categoria();
		dep.setId(rs.getInt("carroId"));
		dep.setDescricao(rs.getString("descricao"));
		dep.setPrecoPorDia(rs.getDouble("precoDiario"));
		return dep;
	}

	@Override
	public List<Carro> finAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM carro INNER JOIN categoria "
					+ "WHERE carro.carroId = categoria.id "
					+ "ORDER BY carro.mdelo");
			
			rs = st.executeQuery();
			
			List<Carro> list = new ArrayList<>();
			Map<Integer, Categoria> map = new HashMap<>();
			
			while (rs.next()) {
				
				Categoria dep = map.get(rs.getInt("carroId"));
				
				if (dep == null) {
					dep = instantiateCategoria(rs);
					map.put(rs.getInt("carroId"), dep);
				}
				
				Carro obj = instantiateCarro(rs, dep);
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
	public List<Carro> findByCategoria(Categoria categoria) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT *"
					+ "FROM carro INNER JOIN categoria "
					+ "ON carro.carroId = categoria.id "
					+ "WHERE carroId = ? "
					+ "ORDER BY carro.mdelo");
			
			st.setInt(1, categoria.getId());
			
			rs = st.executeQuery();
			
			List<Carro> list = new ArrayList<>();
			Map<Integer, Categoria> map = new HashMap<>();
			
			while (rs.next()) {
				
				Categoria dep = map.get(rs.getInt("carroId"));
				
				if (dep == null) {
					dep = instantiateCategoria(rs);
					map.put(rs.getInt("carroId"), dep);
				}
				
				Carro obj = instantiateCarro(rs, dep);
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