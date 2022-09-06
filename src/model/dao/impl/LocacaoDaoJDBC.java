package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.LocacaoDao;
import model.entities.Carro;
import model.entities.Cliente;
import model.entities.Locacao;
import model.entities.enums.Cor;

public class LocacaoDaoJDBC implements LocacaoDao{
	
private Connection conn;
	
	
	public LocacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Locacao obj) {
			PreparedStatement st = null;
			try {
				st = conn.prepareStatement(
						"INSERT INTO locacao "
						+ "(dataRetirada, dataDevolucao, diasDevolucao, porcentagem, "
						+ "loc_clienteId, loc_carro_Id) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?,?)",
						Statement.RETURN_GENERATED_KEYS);
				
				st.setTimestamp(1, Timestamp.valueOf(obj.getDataRetirada()));
				st.setTimestamp(2, Timestamp.valueOf(obj.getDataDevolucao()));				
				st.setInt(3, obj.getLocacaoDiaria().getDiasPrevisto());		
				st.setInt(4, obj.getLocacaoLongoPeriodo().getPorcentagemDesconto());		
				st.setInt(5, obj.getCliente().getId());		
				st.setInt(6, obj.getCarro().getId());		
				
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
	public void update(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE locacao "
					+ "SET dataRetirada = ?, dataDevolucao = ?, diasDevolucao = ?, porcentagem = ?, "
					+ "loc_clienteId = ?, loc_carro_Id = ? "
					+ "WHERE id = ?");
			st.setTimestamp(1, Timestamp.valueOf(obj.getDataRetirada()));
			st.setTimestamp(2, Timestamp.valueOf(obj.getDataDevolucao()));				
			st.setInt(3, obj.getLocacaoDiaria().getDiasPrevisto());		
			st.setInt(4, obj.getLocacaoLongoPeriodo().getPorcentagemDesconto());		
			st.setInt(5, obj.getCliente().getId());		
			st.setInt(6, obj.getCarro().getId());
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
			st = conn.prepareStatement("DELETE FROM locacao WHERE id = ?");
			
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
	public Locacao findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*,cliente.id, carro.id"
					+ "FROM locacao INNER JOIN cliente, carro "
					+ "ON locacao.loc_clienteId = cliente.id and locacao.loc_carro_Id = carro.id "
					+ "WHERE locacao.id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				
				Carro dep1 = instantiateCarro(rs);
				Cliente dep = instantiateCliente(rs);
				Locacao obj = instantiateLocacao(rs, dep, dep1);
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

	private Locacao instantiateLocacao(ResultSet rs, Cliente dep, Carro dep1) throws SQLException {
		Locacao obj = new Locacao();
		obj.setId(rs.getInt("id"));
		obj.setDataRetirada(rs.getTimestamp("dataRetirada").toLocalDateTime());
		obj.setDataDevolucao(rs.getTimestamp("dataDevolucao").toLocalDateTime());
		obj.getLocacaoDiaria().setDiasPrevisto(rs.getInt("diasDevolucao"));
		obj.getLocacaoLongoPeriodo().setPorcentagemDesconto(rs.getInt("porcentagem"));
	
		obj.setCliente(dep);
		obj.setCarro(dep1);
		return obj;
	}
	/*
	 * private Locacao instantiateLocacaoCliente(ResultSet rs, Cliente dep) throws
	 * SQLException { Locacao obj = new Locacao(); obj.setId(rs.getInt("id"));
	 * obj.setDataRetirada(rs.getTimestamp("dataRetirada").toLocalDateTime());
	 * obj.setDataDevolucao(rs.getTimestamp("dataDevolucao").toLocalDateTime());
	 * obj.getLocacaoDiaria().setDiasPrevisto(rs.getInt("diasDevolucao"));
	 * obj.getLocacaoLongoPeriodo().setPorcentagemDesconto(rs.getInt("porcentagem"))
	 * ;
	 * 
	 * obj.setCliente(dep); return obj; }
	 */

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente dep = new Cliente();
		dep.setId(rs.getInt("loc_clienteId"));
		dep.setNome(rs.getString("nome"));
		dep.setCpf(rs.getString("cpf"));
		dep.setEmai(rs.getString("email"));
		return dep;
	}
	
	private Carro instantiateCarro(ResultSet rs) throws SQLException {
		Carro dep1 = new Carro();
		dep1.setId(rs.getInt("loc_carro_Id"));
		dep1.setModelo(rs.getString("mdelo"));
		dep1.setPlaca(rs.getString("placa"));
		dep1.setCor(Cor.valueOf(rs.getString("cor")));
		dep1.setId(rs.getInt("ano"));
		dep1.setDataAquisicao(rs.getDate("dataAquisicao").toLocalDate());
		return dep1;
	}


	@Override
	public List<Locacao> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*, cliente.id as ID, carro.id as ID "
					+ "FROM locacao INNER JOIN cliente, carro "
					+ "ON locacao.loc_clienteId = cliente.id and locacao.loc_carro_Id = carro.id");
			
			rs = st.executeQuery();
			
			List<Locacao> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			Map<Integer, Carro> map1 = new HashMap<>();
			
			while (rs.next()) {
				
				Cliente dep = map.get(rs.getInt("loc_clienteId"));
				Carro dep1 = map1.get(rs.getInt("loc_carro_Id"));
				
				if (dep == null || dep1 == null) {
					dep = instantiateCliente(rs);
					map.put(rs.getInt("loc_clienteId"), dep);
					
					dep1 = instantiateCarro(rs);
					map1.put(rs.getInt("loc_carro_Id"), dep1);
					
				}
				
				Locacao obj = instantiateLocacao(rs, dep, dep1);
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
	public List<Locacao> findByCliente(Cliente cliente) {
		
		  PreparedStatement st = null; ResultSet rs = null; try { st =
		  conn.prepareStatement( 
				  "SELECT locacao.*,cliente.id FROM locacao INNER JOIN cliente "
							+ "ON locacao.loc_clienteId = cliente.id "
							+ "WHERE loc_clienteId = ? "
							+ "ORDER BY dataRetirada");
		  
		  st.setInt(1, cliente.getId());
		  
		  rs = st.executeQuery();
		  
		  List<Locacao> list = new ArrayList<>(); 
		  Map<Integer, Cliente> map = new HashMap<>(); 
		  Map<Integer, Carro> map1 = new HashMap<>();
		  
		  while (rs.next()) {
		  
		  Cliente dep = map.get(rs.getInt("loc_clienteId")); 
		  Carro dep1 = map1.get(rs.getInt("loc_carro_Id"));
		  
		  if (dep == null) { dep = instantiateCliente(rs);
		  map.put(rs.getInt("loc_clienteId"), dep);
		  
		  dep1 = instantiateCarro(rs); map1.put(rs.getInt("loc_carro_Id"), dep1);
		  
		  }
		  
		  Locacao obj = instantiateLocacao(rs, dep, dep1); list.add(obj); } return
		  list; 
		  } catch (SQLException e) 
		  { throw new DbException(e.getMessage()); 
		  }
		  finally { DB.closeStatement(st); DB.closeResultSet(rs); } }
		 

	/*PreparedStatement st = null;
	ResultSet rs = null;
	try {
		st = conn.prepareStatement(
				"SELECT *"
				+ "FROM locacao INNER JOIN cliente "
				+ "ON locacao.loc_clienteId = cliente.id "
				+ "WHERE loc_clienteId = ? "
				+ "ORDER BY locacao.dataRetirada");
		
		st.setInt(1, cliente.getId());
		
		rs = st.executeQuery();
		
		List<Locacao> list = new ArrayList<>();
		Map<Integer, Cliente> map = new HashMap<>();
		
		while (rs.next()) {
			
			Cliente dep = map.get(rs.getInt("loc_clienteId"));
			
			if (dep == null) {
				dep = instantiateCliente(rs);
				map.put(rs.getInt("loc_clienteId"), dep);
			}
			
			Locacao obj = instantiateLocacaoCliente(rs, dep);
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
*/}


