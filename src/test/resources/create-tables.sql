CREATE SCHEMA IF NOT EXISTS desafio2;

-- Pessoa

CREATE SEQUENCE desafio2.seq_pessoa START WITH 1;

CREATE TABLE IF NOT EXISTS desafio2.pessoa (
	id_pessoa INTEGER PRIMARY KEY,
	nome VARCHAR (100) NOT NULL,
	cpf VARCHAR (11) NOT NULL,
	data_nascimento DATE NOT null
);

CREATE UNIQUE INDEX pessoa_cpf_idx ON desafio2.pessoa (cpf);

-- Conta

CREATE SEQUENCE desafio2.seq_conta START WITH 1;

CREATE TABLE IF NOT EXISTS desafio2.conta (
	id_conta INTEGER PRIMARY KEY,
	id_pessoa INTEGER NOT NULL,
	saldo NUMERIC(10, 5) NOT NULL,
	limite_saque_diario NUMERIC(10, 5) NOT NULL,
    flag_ativo BOOLEAN NOT NULL,
    tipo_conta SMALLINT NOT NULL,
    data_criacao DATE NOT NULL,
    versao INTEGER NOT NULL,
    CONSTRAINT fk_pessoa
      FOREIGN KEY(id_pessoa) 
	  REFERENCES desafio2.pessoa(id_pessoa)
);

-- Tipo Transacao

CREATE TABLE IF NOT EXISTS desafio2.tipo_transacao (
	id_tipo_transacao INTEGER PRIMARY KEY,
	nome VARCHAR(20) NOT NULL
);

-- Transacao

CREATE SEQUENCE desafio2.seq_transacao START WITH 1;

CREATE TABLE IF NOT EXISTS desafio2.transacao (
	id_transacao INTEGER PRIMARY KEY,
	id_conta INTEGER NOT NULL,
	valor NUMERIC(10, 5) NOT NULL,
	data_transacao DATE NOT NULL,
	id_tipo_transacao INTEGER NOT NULL,
    CONSTRAINT fk_conta
      FOREIGN KEY(id_conta) 
	  REFERENCES desafio2.conta(id_conta),
	CONSTRAINT fk_tipo_transacao
      FOREIGN KEY(id_tipo_transacao) 
	  REFERENCES desafio2.tipo_transacao(id_tipo_transacao)
);

CREATE INDEX transacao_data_idx ON desafio2.transacao (id_conta, data_transacao);