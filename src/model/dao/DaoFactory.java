package model.dao;

import db.DB;
import model.dao.impl.CarroDaoJDBC;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.LocacaoDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.TelefoneDaoJDBC;

public class DaoFactory {

	public static CarroDao createCarroDao() {
		return new CarroDaoJDBC(DB.getConnection());
	}
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
	
	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static TelefoneDao createTelefoneDao() {
		return new TelefoneDaoJDBC(DB.getConnection());
	}
	
	public static LocacaoDao createLocacaoDao() {
		return new LocacaoDaoJDBC(DB.getConnection());
	}
	
	//-------------------------------------------------
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}