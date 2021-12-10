INSERT INTO desafio2.pessoa (id_pessoa, nome, cpf, data_nascimento) VALUES (nextval('desafio2.seq_pessoa'), 'Mateus Felipe Ramos', '65961038033', '1987-10-27');
INSERT INTO desafio2.pessoa (id_pessoa, nome, cpf, data_nascimento) VALUES (nextval('desafio2.seq_pessoa'), 'Maria Eduarda Brandao', '87304306076', '1995-04-12');

INSERT INTO desafio2.tipo_transacao (id_tipo_transacao, nome) VALUES (1, 'DEPOSITO');
INSERT INTO desafio2.tipo_transacao (id_tipo_transacao, nome) VALUES (2, 'SAQUE');