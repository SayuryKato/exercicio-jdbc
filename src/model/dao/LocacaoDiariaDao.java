package model.dao;

import java.util.List;

import model.entities.LoacacaoDiaria;

public interface LocacaoDiariaDao {
	
	void insert(LoacacaoDiaria obj);
	void update(LoacacaoDiaria obj);
	void deleteById(Integer id);
	LoacacaoDiaria findById(Integer id);
	List<LoacacaoDiaria> findAll();

}
