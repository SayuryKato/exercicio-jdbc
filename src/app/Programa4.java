package app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.CarroDao;
import model.dao.CategoriaDao;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.dao.TelefoneDao;
import model.entities.Carro;
import model.entities.Categoria;
import model.entities.Cliente;
import model.entities.Department;
import model.entities.LoacacaoDiaria;
import model.entities.Locacao;
import model.entities.LocacaoLongoPeriodo;
import model.entities.Seller;
import model.entities.Telefone;
import model.entities.enums.Cor;

public class Programa4 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		ClienteDao clienteDao = DaoFactory.createClienteDao();
		TelefoneDao telefoneDao = DaoFactory.createTelefoneDao();
		CarroDao carroDao = DaoFactory.createCarroDao();
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
		LocacaoDao locacaoDao = DaoFactory.createLocacaoDao();

		
		//Cliente newCliente = new Cliente(null, "OTAVIO", "00546598714", "otaRIO@gmail.com");
		Cliente newCliente = new Cliente();
		/*
		 * clienteDao.insert(newCliente); System.out.println("Inserted! New id = " +
		 * newCliente.getId());
		 * 
		 * Telefone newTelefone = new Telefone(null, "00546598714", newCliente);
		 * telefoneDao.insert(newTelefone); System.out.println("Inserted! New id = " +
		 * newTelefone.getId());
		 * 
		 * Categoria newCategoria = new Categoria(10, "OTIMO", 250000.00);
		 * categoriaDao.insert(newCategoria);
		 * 
		 * Carro newCarro = new Carro(10, "VAI", "DGYD642", Cor.PRETA, 2012,
		 * LocalDate.now(), newCategoria); carroDao.insert(newCarro);
		 * System.out.println("Inserted! New id = " + newCarro.getId());
		 * 
		 * LocacaoLongoPeriodo newLocacaoP = new LocacaoLongoPeriodo(5); LoacacaoDiaria
		 * newDiaria = new LoacacaoDiaria(10);
		 * 
		 * 
		 * Locacao newLocacao = new Locacao(null, LocalDateTime.now(),
		 * LocalDateTime.now(),newCliente,newCarro, newDiaria, newLocacaoP);
		 * locacaoDao.insert(newLocacao); System.out.println("LOCACAO = " +
		 * newLocacao.getId());
		 */		
		/*
		 * List<Carro> list = carroDao.findByCategoria(newCategoria); list =
		 * carroDao.finAll(); for (Carro obj : list) { System.out.println(obj); }
		 */
		  System.out.println("---------------------------"); 
		 
			

			newCliente = new Cliente(2, null, null, null); 
			List<Locacao> list10 = locacaoDao.findByCliente(newCliente); 
			for (Locacao obj : list10) {
				System.out.println(obj); 
			}
		  
			/*
			 * newCarro = carroDao.findById(5); newCarro.setModelo("FOX");
			 * carroDao.update(newCarro); System.out.println("Update completed");
			 */

	}

}
