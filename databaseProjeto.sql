CREATE TABLE categoria (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  descricao varchar(45) DEFAULT NULL,
  precoDiario double not null
);

CREATE TABLE carro (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  mdelo varchar(45) NOT NULL,
  placa varchar(10) NOT NULL,
  cor varchar(10) NOT NULL,
  ano  int not null,
  dataAquisicao date NOT NULL,
  carroId int NOT NULL,
  FOREIGN KEY (carroId) REFERENCES categoria (id)
);

CREATE TABLE cliente (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome varchar(45) NOT NULL,
  cpf varchar(11) NOT NULL,
  email varchar(45) NOT NULL
);

CREATE TABLE locacao (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dataRetirada datetime NOT NULL,
  dataDevolucao datetime default false,
  diasDevolucao int not null,
  porcentagem double,
  loc_clienteId int NOT NULL,
  loc_carro_Id int not null,
  FOREIGN KEY (loc_clienteId) REFERENCES cliente (id),
  FOREIGN KEY (loc_carro_Id) REFERENCES carro (id)
);


CREATE TABLE telefone (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  numero varchar(11) not null,
  telefoneId int not null,
  FOREIGN KEY (telefoneId) REFERENCES cliente (id)
);
