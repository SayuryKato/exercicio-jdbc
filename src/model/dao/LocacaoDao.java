package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Locacao;

public interface LocacaoDao {

	void insert(Locacao obj);
	void update(Locacao obj);
	void deleteById(Integer id);
	Locacao findById(Integer id);
	List<Locacao> findAll();
	List<Locacao> findByCliente(Cliente cliente);
}