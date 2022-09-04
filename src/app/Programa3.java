package app;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.CarroDao;
import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.entities.Carro;
import model.entities.Categoria;
import model.entities.Seller;

public class Programa3 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);

		  CarroDao carroDao = DaoFactory.createCarroDao();
		  
		  metodo1();
		  System.out.println("\nFim do programa!");
		   
		  
			/*
			 * System.out.println("\n=== TEST 4: seller insert =====");
			 * System.out.println("Digite uma cor:"); Cor cor = Cor.valueOf(sc.next());
			 * Carro newCarro = new Carro(1, "Gol","KLM2563", cor , 2012, LocalDate.now(),
			 * newCategoria); //Carro newCarro1 = new Carro(1, "Gol","KLM2563",
			 * Cor.valueOf("VERMELHA") , 2012, LocalDate.now(), newCategoria);
			 * carroDao.insert(newCarro); System.out.println("Inserted! New id = "
			 * +newCarro.getId());
			 */


	}
	
	public static void metodo1() {
		Scanner sc = new Scanner(System.in);
		System.out.println("SISTEMA DE LOCAÇÃO DE CARROS:\nMENU PRINCIPAL\n");
		  System.out.println("Entre com a opção:"); 
		  System.out.println("\t1- Categoria\n\t2- Carro\n\t3- Cliente\n\t4- Locação\n\t5- Sair");
		  Integer resposta = sc.nextInt();
		  
		  if(resposta.equals(1)) {
			  System.out.println("MENU CATEGORIA\n");
			  CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
			  metodo3();
			  Integer resposta1 = sc.nextInt();
			  if(resposta1.equals(1)) {
				  sc = new Scanner(System.in);
				  System.out.print("Descricao");
				  String descricao = sc.nextLine();
				  System.out.printf("\ndescricao:"+ descricao + "\n");
				  System.out.print("Preco: ");
				  Double preco = sc.nextDouble();
				
					  Categoria newCategoria = new Categoria(null, descricao , preco);
					  categoriaDao.insert(newCategoria); 
					  System.out.println("Inserted! New id = " +newCategoria.getId());	 
			  }
			  else if(resposta1.equals(2)) {
				  List<Categoria> list = categoriaDao.findAll(); 
					for (Categoria obj : list) { 
						System.out.println(obj); }
			  }
			  
			  else if(resposta1.equals(3)) {
				  
				  System.out.println("Digite o numero do ID que sera atualizado:");
				  int numero = sc.nextInt();
				  sc = new Scanner(System.in);
				  System.out.print("Nova descricao");
				  String descricao_nova = sc.nextLine(); 
				  System.out.print("Novo preco: ");
				  Double preco_novo = sc.nextDouble();
					Categoria categoria = categoriaDao.findById(numero); 
					categoria.setDescricao(descricao_nova);
					categoria.setPrecoPorDia(preco_novo);
					categoriaDao.update(categoria); 
					System.out.println("Atualizado com sucesso!");
			  }
			  
			  else if(resposta1.equals(4)) {
					System.out.println("Digite o ID que será deletado: ");
					int id = sc.nextInt();
					categoriaDao.deleteById(id); 
					System.out.println("Deletado com sucesso!");
			  }
			  
			  
			  
			  if(resposta1.equals(5)) {
				  metodo1();
			  }
		  }
		  
		  if(resposta.equals(5)) {
			  System.out.println("Tchau!");
		  }
		  
		  sc.close();
		  
		}
	
	public static void metodo3() {
		System.out.print("Entre com a opcão\n\t1- Cadrastar\n\t2- Listar\n\t3- Editar\n\t4- Excluir\n\t5- Voltar");
		
	
	}	
	}

