CREATE TABLE lancamento(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL, 
	data_vencimento DATE NOT NULL, 
	data_pagamento DATE, 
	valor DECIMAL(10,2) NOT NULL, 
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL, 
	codigo_categoria BIGINT(20) NOT NULL, 
	codigo_pessoa BIGINT(20) NOT NULL, 
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo))
	ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
values ('Compra de imovel','2018-05-10','2018-05-10',8000000.00,'compra de imovel casa','DESPESA',6,1 );

INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
values ('Compra de imovel','2018-05-10',NULL,8000000.00,'compra de imovel casa','DESPESA', 6,2 );

INSERT INTO lancamento(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
values ('Compra de imovel','2018-05-10','2018-05-10',8000000.00,'compra de imovel casa','DESPESA', 6,3);