package model.dao;

import db.DB;
import model.dao.impl.CarroDaoJDBC;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static CarroDao createCarroDao() {
		return new CarroDaoJDBC(DB.getConnection());
	}
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
	
	//-------------------------------------------------
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}