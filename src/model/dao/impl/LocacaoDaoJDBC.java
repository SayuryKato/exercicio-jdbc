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
import model.dao.CarroDao;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.entities.Carro;
import model.entities.Cliente;
import model.entities.Locacao;
import model.entities.LocacaoDiaria;
import model.entities.LocacaoLongoPeriodo;
import model.entities.enums.Cor;

public class LocacaoDaoJDBC implements LocacaoDao {

	private Connection conn;

	public LocacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO locacao " + "(dataRetirada, dataDevolucao, diasPrevistosDevolucao, porcentagem, "
							+ "loc_clienteId, loc_carro_Id) " + "VALUES " + "(?, ?, ?, ?, ?,?)",
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
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE locacao " + "SET dataRetirada = ?, dataDevolucao = ?, diasPrevistosDevolucao = ?, porcentagem = ?, "
							+ "loc_clienteId = ?, loc_carro_Id = ? " + "WHERE id = ?");
			st.setTimestamp(1, Timestamp.valueOf(obj.getDataRetirada()));
			st.setTimestamp(2, Timestamp.valueOf(obj.getDataDevolucao()));
			st.setInt(3, obj.getLocacaoDiaria().getDiasPrevisto());
			st.setInt(4, obj.getLocacaoLongoPeriodo().getPorcentagemDesconto());
			st.setInt(5, obj.getCliente().getId());
			st.setInt(6, obj.getCarro().getId());
			st.setInt(7, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
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
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Locacao findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM locacao "
							+ "WHERE id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();
			if (rs.next()) {

				Carro dep1 = instantiateCarro(rs);
				Cliente dep = instantiateCliente(rs);
				System.out.println("teste!");
				Locacao obj = instantiateLocacao(rs);
				System.out.println("teste222!");
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Locacao instantiateLocacao(ResultSet rs) throws SQLException {
	
	  Locacao obj = new Locacao();
	  System.out.println("Batata1");
	  System.out.println(rs.getInt("id"));
	  System.out.println("Batata2");
	  obj.setId(rs.getInt("id"));
	  
	  obj.setDataRetirada(rs.getTimestamp("dataRetirada").toLocalDateTime());
	  obj.setDataDevolucao(rs.getTimestamp("dataDevolucao").toLocalDateTime());
	  Integer porcentagem = rs.getInt("porcentagem"); 
	  if(!rs.wasNull()) {
		  obj.setLocacaoLongoPeriodo(new LocacaoLongoPeriodo());
		  obj.getLocacaoLongoPeriodo().setPorcentagemDesconto(rs.getInt("porcentagem"));
	  }else {		  
		  obj.setLocacaoDiaria(new LocacaoDiaria());
		  obj.getLocacaoDiaria().setDiasPrevisto(rs.getInt("diasPrevistosDevolucao"));
	  }
	  
	  	Integer id_carro = rs.getInt("loc_carro_Id");
	  	Integer id_cliente = rs.getInt("loc_clienteId");
	  	
	  	PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM carro WHERE id = ? ORDER BY id");
		
			st.setInt(1, id_carro);
		
			rs = st.executeQuery();
			obj.setCarro(instantiateCarro(rs));	
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		try {
			st = conn.prepareStatement("SELECT * FROM cliente WHERE id = ? ORDER BY id");
			
			st.setInt(1, id_cliente);
			
			rs = st.executeQuery();
			
			obj.setCliente(instantiateCliente(rs));				
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	  
	  return obj; }
	 

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		while(rs.next()) {			
			Cliente dep = new Cliente();
			dep.setId(rs.getInt("id"));
			dep.setNome(rs.getString("nome"));
			dep.setCpf(rs.getString("cpf"));
			dep.setEmai(rs.getString("email"));
			return dep;
		}
		return null;
	}

	private Carro instantiateCarro(ResultSet rs) throws SQLException {
		while(rs.next()) {			
			Carro dep1 = new Carro();
			dep1.setId(rs.getInt("id"));
			dep1.setModelo(rs.getString("mdelo"));
			dep1.setPlaca(rs.getString("placa"));
			dep1.setCor(Cor.valueOf(rs.getString("cor")));
			dep1.setId(rs.getInt("ano"));
			dep1.setDataAquisicao(rs.getDate("dataAquisicao").toLocalDate());
			return dep1;
		}
		return null;
	}

	@Override
	public List<Locacao> findAll() {
		CarroDao carroDao = DaoFactory.createCarroDao();
		ClienteDao clienteDao = DaoFactory.createClienteDao();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM locacao ORDER BY id");

			rs = st.executeQuery();

			List<Locacao> locacoes = new ArrayList<>();
			Map<Integer, Carro> mapCarro = new HashMap<>();
			Map<Integer, Cliente> mapCliente = new HashMap<>();

			while (rs.next()) {
				Carro carro = mapCarro.get(rs.getInt("loc_carro_Id"));
				if (carro == null) {
					carro = carroDao.findById(rs.getInt("loc_carro_Id"));
					mapCarro.put(rs.getInt("loc_carro_Id"), carro);
				}
				Cliente cliente = mapCliente.get(rs.getInt("loc_clienteId"));
				if (cliente == null) {
					cliente = clienteDao.findById(rs.getInt("loc_clienteId"));
					mapCliente.put(rs.getInt("loc_clienteId"), cliente);
				}
				Locacao locacao = instantiateLocacao(rs);
				locacao.setCarro(carro);
				locacao.setCliente(cliente);
				locacoes.add(locacao);
			}
			return locacoes;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Locacao> findByCliente(Cliente cliente) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM locacao WHERE loc_clienteId = ? ORDER BY id");

			st.setInt(1, cliente.getId());

			rs = st.executeQuery();

			List<Locacao> list = new ArrayList<>();

			while (rs.next()) {
				Locacao obj = instantiateLocacao(rs);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	
	/*
	 * public static Boolean isLocacaoDiaria(LocalDateTime
	 * dataRetirada,LocalDateTime dataDevolucao) { d04.getDayOfMonth() if
	 * (periodoEmDias(dataRetirada, dataDevolucao) <= 10) { return true; } return
	 * false; }
	 */

}
