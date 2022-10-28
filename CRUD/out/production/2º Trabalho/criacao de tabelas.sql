create schema AppMusica

create table AppMusica.autor (
	idautor int primary key not null,
	Nome varchar(30) not null,
	genero varchar(30) null
)

create table AppMusica.Musica (
	idMusica int primary key not null,
	Nome varchar(30) not null,
	Duracao int not null,
	idautor int not null,
	constraint fkautor
	foreign key (idautor)
	references autor
)
