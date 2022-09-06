package app;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import model.entities.LoacacaoDiaria;
import model.entities.Locacao;
import model.entities.LocacaoLongoPeriodo;
import model.entities.Telefone;
import model.entities.enums.Cor;

public class Programa3 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		
		  
		  metodo1();
		  System.out.println("\nFim do programa!");

	}
	
	public static void metodo1() {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fmt2 =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		CarroDao carroDao = DaoFactory.createCarroDao();
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
		ClienteDao clienteDao = DaoFactory.createClienteDao();
		TelefoneDao telefoneDao = DaoFactory.createTelefoneDao();
		LocacaoDao locacaoDao = DaoFactory.createLocacaoDao();
		LocacaoLongoPeriodo newLocacaoPeriodo = new LocacaoLongoPeriodo();
		LoacacaoDiaria newDiaria = new LoacacaoDiaria();
		
		  Categoria newCategoria = new Categoria();
		  Carro newCarro = new Carro();
		  Cliente newCliente = new Cliente();
		  Telefone newTelefone = new Telefone();
		  Locacao newLocacao = new Locacao();
		  
		  
		  List<Carro> list = new ArrayList<>();
		  List<Categoria> list1 = new ArrayList<>();
		  List<Cliente> list2 = new ArrayList<>();
		  List<Telefone> list3 = new ArrayList<>();
		  List<Locacao> list4 = new ArrayList<>();
		  
		//try {
		Scanner sc = new Scanner(System.in);
		System.out.println("SISTEMA DE LOCAÇÃO DE CARROS:\nMENU PRINCIPAL\n");
		  System.out.println("Entre com a opção:"); 
		  System.out.println("\t1- Categoria\n\t2- Carro\n\t3- Cliente\n\t4- Locação\n\t5- Sair");
		  Integer resposta = sc.nextInt();
		  
		  if(resposta.equals(1)) { // CATEGORIA
			  System.out.println("MENU CATEGORIA\n");
			  metodo3();
			  Integer resposta1 = sc.nextInt();
			  if(resposta1.equals(1)) { //CADRASTAR
				  sc = new Scanner(System.in);
				  System.out.print("Descricao");
				  String descricao = sc.nextLine();
				  System.out.print("Preco: ");
				  Double preco = sc.nextDouble();
				
					  newCategoria = new Categoria(null, descricao , preco);
					  categoriaDao.insert(newCategoria); 
					  System.out.println("Cadrastado com sucesso!");	 
			  }
			  else if(resposta1.equals(2)) { //LISTAR
				  list1 = categoriaDao.findAll(); 
					for (Categoria obj : list1) { 
						System.out.println(obj); }
			  }
			  
			  else if(resposta1.equals(3)) { //EDITAR
				  
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
			  
			  else if(resposta1.equals(4)) { //EXCLUIR
					System.out.println("Digite o ID que será deletado: ");
					int id = sc.nextInt();
					categoriaDao.deleteById(id); 
					System.out.println("Deletado com sucesso!");
			  }
			  else if(resposta1.equals(5)) { //voltar
				  metodo1();
			  }
		  }
		  
		  if(resposta.equals(2)) { // CARRO
			  System.out.println("MENU CARRO\n\tEntre com a opção:\n\t1- Cadrastar\n\t2- "
			  		+ "Listar TODOS\n\t3- Listar por CATEGORIA\n\t4- Editar\n\t5- "
			  		+ "Excluir\n\t6- Voltar");
			  Integer resposta_carro = sc.nextInt();
			  if(resposta_carro.equals(1)) { //CADRASTAR
				  sc = new Scanner(System.in);
				  
				  System.out.print("Modelo");
				  String modelo = sc.nextLine();

				  System.out.print("Placa: ");
				  String placa = sc.nextLine();
				  
				  System.out.print("Cor: ");
				  Cor cor = Cor.valueOf(sc.next());
				  
				  System.out.print("Ano: ");
				  Integer ano = sc.nextInt();
				  
					
					  System.out.print("Data Aquisicao: (DD/MM/YYYY)"); 
					  LocalDate data_Aquisicao = LocalDate.parse(sc.next(), formatacao);
					  
					 
					System.out.println("Categoria Nova? (1-Sim 2-Nao)");
					Integer resposta_cat = sc.nextInt();
					if(resposta_cat.equals(1)) // CATEGORIA NOVA?
					{
						System.out.print("Descricao");
						String descricao = sc.next();
						System.out.print("Preco: ");
						Double preco = sc.nextDouble();
						newCategoria = new Categoria(null, descricao , preco);
						categoriaDao.insert(newCategoria);
						System.out.println("Cadrastado! ID = " +newCategoria.getId());
					}
					else {	   
						System.out.print("Digite o ID que ele pertece: ");
						Integer id = sc.nextInt();
						newCategoria = new Categoria(id, null, null);
					}
				  
					  newCarro = new Carro(null, modelo , placa, cor, ano, data_Aquisicao , newCategoria);
					  System.out.println("Inserido com sucesso!");
					  
					  carroDao.insert(newCarro); 
					  System.out.println("Inserido com sucesso! ");
					  list = carroDao.findByCategoria(newCategoria);
					   
				  
			  }
			  else if(resposta_carro.equals(2)) { //Listar TODOS
				  newCategoria = new Categoria();
				  newCarro = new Carro();
				  list = carroDao.finAll(); 
				  for (Carro obj : list) { System.out.println(obj); }
				  
			  }
			  
			  else if(resposta_carro.equals(3)) { //Listar por CATEGORIA
				  System.out.println("Listando por categoria\nDigite ID da categoria");
				  Integer num_cat = sc.nextInt();
					newCategoria = new Categoria(num_cat, null, null);
					list = carroDao.findByCategoria(newCategoria);
					for (Carro obj : list) {
						System.out.println(obj); 
					}
			  }
			  
			  else if(resposta_carro.equals(4)) { //Editar
				  sc = new Scanner(System.in);
				  System.out.println("Digite um ID: ");
				  Integer id_edit = sc.nextInt();
				  
				  System.out.println("Novo modelo: ");
				  String modelo_novo = sc.next();
				  
				  System.out.println("Nova placa: ");
				  String placa_novo = sc.next();

				  System.out.println("Nova cor: ");
				  Cor cor = Cor.valueOf(sc.next());

				  System.out.print("Nova Data Aquisicao: (DD/MM/YYYY)"); 
				  LocalDate novo_data_Aquisicao = LocalDate.parse(sc.next(), formatacao);
				  
				  //newCategoria = new Categoria(id_edit, null, null);
				  
				  newCarro = carroDao.findById(id_edit); 
					newCarro.setModelo(modelo_novo);
					newCarro.setPlaca(placa_novo);
					newCarro.setCor(cor);
					carroDao.update(newCarro); 
					System.out.println("Atualizado!");
			  }
			  
			  else if(resposta_carro.equals(5)) { //Excluir
				  System.out.println("Digite o ID para deletar: "); 
					int id_excluir = sc.nextInt();
					carroDao.deleteById(id_excluir); 
					System.out.println("Deletado com sucesso!");
			  }
			  
			  else if(resposta_carro.equals(6)) {
				  metodo1();
			  }
		  }
		  
		  else if(resposta.equals(3)) { // CLIENTE
			  System.out.println("MENU CLIENTE\n");
			  metodo3();
			  Integer resposta_cliente = sc.nextInt();
			  
			  if(resposta_cliente.equals(1)) { //CADRASTAR
				  sc = new Scanner(System.in);
				  System.out.print("Nome: ");
				  String nome = sc.nextLine();
				  
				  System.out.print("CPF: ");
				  String cpf = sc.next();	
				  
				  System.out.print("Email: ");
				  String email = sc.next();
				  
					System.out.print("Telefone: ");
					String numero_telefone = sc.next();
									  
				  newCliente = new Cliente(null, nome , cpf, email);
				  clienteDao.insert(newCliente); 
				  System.out.println("Cadrastado com sucesso! Seu ID é: " +newCliente.getId());
				  newTelefone = new Telefone(null, numero_telefone, newCliente);
				  telefoneDao.insert(newTelefone);
			  }
			  else if(resposta_cliente.equals(2)) { //Listar
				  list2 = clienteDao.findAll(); 
					for (Cliente obj : list2) { 
						System.out.println(obj); }
				  
			  }
			  
			  else if(resposta_cliente.equals(3)) { //Editar cliente
				  System.out.print("Digite o ID que será atualizado: ");
				  Integer id_cliente = sc.nextInt();
				  
				  System.out.println("Deseja editar tudo ou somente o telefone? 1- TUDO 2- TELEFONE");
				  Integer edit_resposta = sc.nextInt();
				  if(edit_resposta.equals(1)) { // editar tudo
					  
					  sc = new Scanner(System.in);
					  System.out.print("Novo Nome: ");
					  String nome_novo = sc.nextLine();
						
					  System.out.print("Novo CPF: ");
					  String cpf_novo = sc.next();

					  System.out.print("Novo Email: ");
					  String email_novo = sc.next();
					  
					  System.out.print("Novo Numero: ");
					  String numero_novo = sc.next();

					  Cliente cliente = clienteDao.findById(id_cliente); 
						cliente.setNome(nome_novo);
						cliente.setCpf(cpf_novo);
						cliente.setEmai(email_novo);
						clienteDao.update(cliente); 
						
						newTelefone = telefoneDao.findById(id_cliente); 
						newTelefone.setNumero(numero_novo);
						telefoneDao.update(newTelefone);
						System.out.println("Atualizado com sucesso!");  
					  
				  }
				  else {
					  System.out.print("Novo Numero: ");
					  String numero_novo = sc.next();
					  newTelefone = telefoneDao.findById(id_cliente); 
						newTelefone.setNumero(numero_novo);
						telefoneDao.update(newTelefone); 
						System.out.println("Atualizado!");  
					  
				  }
				  			  }
			  
			  else if(resposta_cliente.equals(4)) { //EXCUIR
				  System.out.print("Digite o ID para deletar");
				  Integer id_delete = sc.nextInt();
				  
				  clienteDao.deleteById(id_delete); 
				  //telefoneDao.deleteById(id_delete); 
				  System.out.println("Deletado com sucesso!");
					
					
			  }
			  else if(resposta_cliente.equals(5)) {
				  metodo1();
			  }
			  
		  }
		  
		  else if(resposta.equals(4)) { //LOCACAO
			  System.out.println("MENU LOCACAO\nEntre com a opcão\n\t1- Nova Locacao\n\t2- Devolucao\n\t"
			  		+ "3- Listar TODAS\n\t4- Listar Locacão por cliente\n\t5- Excluir\n\t6- Voltar");
			  Integer resposta_locacao = sc.nextInt();
			  if(resposta_locacao.equals(1)) { //NOVA LOCACAO
				  sc = new Scanner(System.in);
				  System.out.println("Digite a data Retirada (DD/MM/YYYY HH:mm)");
				  LocalDateTime data_Retirada = LocalDateTime.parse(sc.nextLine(), fmt2);
				  System.out.println("Digite a data Devolucao (DD/MM/YYYY HH:mm)");
				  LocalDateTime data_Devolucao = LocalDateTime.parse(sc.nextLine(), fmt2);
				  System.out.println("ID do cliente");
				  Integer id_locacao = sc.nextInt();
				  newCliente = new Cliente(id_locacao, null, null, null);
				  
				  System.out.println("ID do carro");
				  Integer id_locacao_carro = sc.nextInt();
				  newCarro = new Carro(id_locacao_carro, null, null, null, null, null, null);
				  
				  System.out.println("Digite os dias Previsto para devolucao");
				  int dias_previstos = sc.nextInt();
				  newDiaria = new LoacacaoDiaria(dias_previstos);
				  if(dias_previstos>10) {
					  System.out.println("Porcentagem: ");
					  Integer porcentagem = sc.nextInt();
					  newLocacaoPeriodo = new LocacaoLongoPeriodo(porcentagem);
					  newLocacao = new Locacao(null, data_Retirada,data_Devolucao, newCliente, newCarro, newDiaria, newLocacaoPeriodo);
					  locacaoDao.insert(newLocacao);
					  System.out.println("LOCACAO = " + newLocacao.getId());
					  
				  }
				  else {
					  newLocacaoPeriodo = new LocacaoLongoPeriodo(0);
					  newLocacao = new Locacao(null, data_Retirada,data_Devolucao, newCliente, newCarro, newDiaria, newLocacaoPeriodo);
					  locacaoDao.insert(newLocacao);
					  System.out.println("LOCACAO = " + newLocacao.getId());  
			  }
					  
				  }
			  else if(resposta_locacao.equals(2)) { //DEVOLUCAO
				  System.out.println("Digite o ID da locacao");
				  sc = new Scanner(System.in);
				  Integer id_edit_loc = sc.nextInt();
				  //System.out.print("Digite a data Devolucao (DD/MM/YYYY HH:mm) ");
				  //LocalDateTime data_Devolu = LocalDateTime.parse(sc.nextLine(), fmt2);
					  Categoria dep = categoriaDao.findById(id_edit_loc); Double preco_porDia =
					  dep.getPrecoPorDia(); System.out.println(preco_porDia);
					 
		  }
			  
			  else if(resposta_locacao.equals(3)) { //error
				  list4 = locacaoDao.findAll(); 
					for (Locacao obj : list4) { 
						System.out.println(obj); }
			  }
			  else if(resposta_locacao.equals(4)) { //error
				  System.out.println("Digite ID da locacao");
				  Integer numer_id = sc.nextInt();
					newCliente = new Cliente(numer_id, null, null, null);
					list4 = locacaoDao.findByCliente(newCliente);
					for (Locacao obj : list4) {
						System.out.println(obj); 
					}
			  }
			  
			  else if(resposta_locacao.equals(5)) { //DELETADO
				  System.out.println("Digite o ID para deletar: "); 
				  int id = sc.nextInt();
				  locacaoDao.deleteById(id); 
				  System.out.println("Deletado com sucesso!");
			  }
			  else if(resposta_locacao.equals(6)) {
				  metodo1();
			  }
		  }
		  
		  
		  
		  if(resposta.equals(5)) {
			  System.out.println("Tchau!");
		  }
		  		  
		  sc.close();
		  
		  
		  
		  
		/*}
		catch(DbException e) {
			System.out.println(e.getMessage());
		}
		catch(RuntimeException e) {
			System.out.println("Ops! Não era para isso acontecer '-'.\nVerifique se clicou algo errado.");
		}*/
	}
	public static void metodo3() {
		System.out.println("Entre com a opcão\n\t1- Cadrastar\n\t2- Listar\n\t3- Editar\n\t4- Excluir\n\t5- Voltar");
		
	
	}	
	}

