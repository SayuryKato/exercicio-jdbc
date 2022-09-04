package model.dao;

import java.util.List;

import model.entities.Carro;
import model.entities.Categoria;

public interface CarroDao {
	
	void insert(Carro obj);
	void update(Carro obj);
	void deleteById(Integer id);
	Carro findById(Integer id);
	List<Carro> finAll();
	List<Carro> findByCategoria(Categoria categoria);

}
