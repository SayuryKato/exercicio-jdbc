package model.dao;

import java.util.List;

import model.entities.LocacaoDiaria;

public interface LocacaoDiariaDao {
	
	void insert(LocacaoDiaria obj);
	void update(LocacaoDiaria obj);
	void deleteById(Integer id);
	LocacaoDiaria findById(Integer id);
	List<LocacaoDiaria> findAll();

}
