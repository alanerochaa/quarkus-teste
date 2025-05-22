BEGIN
   FOR t IN (SELECT table_name FROM user_tables) LOOP
      EXECUTE IMMEDIATE 'DROP TABLE "' || t.table_name || '" CASCADE CONSTRAINTS';
   END LOOP;
END;
/

DROP SEQUENCE contato_seq;
DROP SEQUENCE equipe_seq;
DROP SEQUENCE estacao_seq;
DROP SEQUENCE mensagem_seq;
DROP SEQUENCE notificacao_seq;
DROP SEQUENCE passageiro_seq;
DROP SEQUENCE tarefa_seq;
DROP SEQUENCE tipo_notificacao_seq;
DROP SEQUENCE usuario_seq;

CREATE SEQUENCE contato_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE equipe_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE estacao_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE mensagem_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE notificacao_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE passageiro_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE tarefa_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE tipo_notificacao_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE usuario_seq START WITH 6 INCREMENT BY 1 NOCACHE NOCYCLE;


CREATE TABLE LOGIN (
    id_login        INTEGER PRIMARY KEY,
    email          VARCHAR2(255) NOT NULL UNIQUE,
    senha          VARCHAR2(100) NOT NULL,
    st_login       VARCHAR2(40) NOT NULL CHECK (st_login IN ('ativo', 'inativo')),
    dt_criacao     DATE NOT NULL,
    dt_atualizacao DATE 
);
CREATE TABLE EQUIPE (
    id_equipe            INTEGER PRIMARY KEY,
    nm_equipe           VARCHAR2(50) NOT NULL,
    tp_experiencia      VARCHAR2(100),
    contato_responsavel VARCHAR2(20),
    dt_criacao          DATE NOT NULL,
    dt_atualizacao      DATE
);
CREATE TABLE STATUS_NOTIFICACAO (
    id_status       INTEGER PRIMARY KEY,
    st             VARCHAR2(20) NOT NULL,
    prioridade     VARCHAR2(20),
    dt_criacao     DATE NOT NULL,
    dt_atualizacao DATE
);
CREATE TABLE USUARIO (
    id_usuario        INTEGER PRIMARY KEY,
    nm                VARCHAR2(100) NOT NULL,
    e_admin           CHAR(1) NOT NULL CHECK (e_admin IN ('S', 'N')),
    nivel_interno     VARCHAR2(20),
    nivel_usuario     VARCHAR2(20),
    dt_criacao        DATE NOT NULL,
    dt_atualizacao    DATE,
    LOGIN_id_login    INTEGER NOT NULL,
    EQUIPE_id_equipe  INTEGER,
    FOREIGN KEY (LOGIN_id_login) REFERENCES LOGIN(id_login),
    FOREIGN KEY (EQUIPE_id_equipe) REFERENCES EQUIPE(id_equipe)
);

CREATE TABLE CONTATO_USUARIO (
    id_contato          INTEGER PRIMARY KEY,
    valor_contato      VARCHAR2(200) NOT NULL,
    tp_contato         VARCHAR2(100),
    dt_criacao         DATE NOT NULL,
    dt_atualizacao     DATE,
    USUARIO_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (USUARIO_id_usuario) REFERENCES USUARIO(id_usuario)
);
CREATE TABLE NOTIFICACAO (
    id_notificacao         INTEGER PRIMARY KEY,
    titulo                VARCHAR(100) NOT NULL,
    tp_alerta             VARCHAR(100),
    conteudo_notificacao  VARCHAR2(4000),
    tp_notificacao        VARCHAR2(100) NOT NULL,
    operador              VARCHAR2(200),
    st_notificacao        VARCHAR2(100),
    estacao               VARCHAR2(200),
    criticidade           NUMBER,
    prioridade            NUMBER,
    dt_criacao            DATE NOT NULL,
    dt_atualizacao        DATE,
    comentario_notificacao VARCHAR2(400),
    EQUIPE_id_equipe   INTEGER,
    CONTATO_USUARIO_id_contato INTEGER,
    STATUS_NOTIFICACAO_id_status INTEGER,
    FOREIGN KEY (EQUIPE_id_equipe) REFERENCES EQUIPE(id_equipe),
    FOREIGN KEY (CONTATO_USUARIO_id_contato) REFERENCES CONTATO_USUARIO(id_contato),
    FOREIGN KEY (STATUS_NOTIFICACAO_id_status) REFERENCES STATUS_NOTIFICACAO(id_status)
);

CREATE TABLE USUARIO_NOTIFICACAO (
    USUARIO_id_usuario INTEGER,
    NOTIFICACAO_id_notificacao INTEGER,
    PRIMARY KEY (USUARIO_id_usuario, NOTIFICACAO_id_notificacao),
    FOREIGN KEY (USUARIO_id_usuario) REFERENCES USUARIO(id_usuario),
    FOREIGN KEY (NOTIFICACAO_id_notificacao) REFERENCES NOTIFICACAO(id_notificacao)
);
CREATE TABLE TIPO_NOTIFICACAO (
    id_tp            INTEGER PRIMARY KEY,
    tp              VARCHAR(50) NOT NULL,
    dt_criacao      DATE NOT NULL,
    dt_atualizacao  DATE,
    NOTIFICACAO_id_notificacao INTEGER NOT NULL,
    FOREIGN KEY (NOTIFICACAO_id_notificacao) REFERENCES NOTIFICACAO(id_notificacao)
);
CREATE TABLE ESTACAO (
    id_estacao      INTEGER PRIMARY KEY,
    nm             VARCHAR2(100) NOT NULL,
    endereco       VARCHAR2(255) NOT NULL,
    telefone       VARCHAR2(20),
    dt_inicio      DATE NOT NULL,
    dt_criacao     DATE NOT NULL,
    dt_atualizacao DATE,
    carro          VARCHAR2(20)
);
CREATE TABLE NOTIFICACAO_ESTACAO (
    NOTIFICACAO_id_notificacao INTEGER,
    ESTACAO_id_estacao INTEGER,
    PRIMARY KEY (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao),
    FOREIGN KEY (NOTIFICACAO_id_notificacao) REFERENCES NOTIFICACAO(id_notificacao),
    FOREIGN KEY (ESTACAO_id_estacao) REFERENCES ESTACAO(id_estacao)
);
CREATE TABLE PASSAGEIRO (
    id_passageiro INTEGER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    documento VARCHAR2(50) NOT NULL UNIQUE,
    descricao VARCHAR2(4000),
    responsavel VARCHAR2(100),
    contato VARCHAR2(20),
    dt_criacao DATE NOT NULL,
    dt_atualizacao DATE
);

CREATE TABLE MENSAGEM (
    id_mensagem INTEGER PRIMARY KEY,
    conteudo VARCHAR2(4000) NOT NULL,
    contato VARCHAR2(20),
    dt_criacao DATE NOT NULL,
    dt_atualizacao DATE,
    USUARIO_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (USUARIO_id_usuario) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE TAREFA (
    id_tarefa INTEGER PRIMARY KEY,
    titulo VARCHAR2(200) NOT NULL,
    descricao VARCHAR2(4000),
    status VARCHAR2(50) NOT NULL,
    dt_criacao DATE NOT NULL,
    dt_atualizacao DATE,
    USUARIO_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (USUARIO_id_usuario) REFERENCES USUARIO(id_usuario)
);

--INSERTS--

--LOGIN(5)--
INSERT INTO LOGIN (id_login, email, senha, st_login, dt_criacao)
VALUES (1, 'user1@example.com', 'senha1', 'ativo', DATE '2025-01-01');

INSERT INTO LOGIN (id_login, email, senha, st_login, dt_criacao)
VALUES (2, 'user2@example.com', 'senha2', 'ativo', TO_DATE('2025-01-02', 'YYYY-MM-DD'));

INSERT INTO LOGIN (id_login, email, senha, st_login, dt_criacao)
VALUES (3, 'user3@example.com', 'senha3', 'inativo', TO_DATE('2025-01-03', 'YYYY-MM-DD'));

INSERT INTO LOGIN (id_login, email, senha, st_login, dt_criacao)
VALUES (4, 'user4@example.com', 'senha4', 'ativo', TO_DATE('2025-01-04', 'YYYY-MM-DD'));

INSERT INTO LOGIN (id_login, email, senha, st_login, dt_criacao)
VALUES (5, 'user5@example.com', 'senha5', 'inativo', TO_DATE('2025-01-05', 'YYYY-MM-DD'));

--EQUIPE(5)--
INSERT INTO EQUIPE (id_equipe, nm_equipe, tp_experiencia, contato_responsavel, dt_criacao)
VALUES (1, 'Equipe Alfa', 'Nível Sênior', 'Carlos', DATE '2025-02-01');

INSERT INTO EQUIPE (id_equipe, nm_equipe, tp_experiencia, contato_responsavel, dt_criacao)
VALUES (2, 'Equipe Beta', 'Nível Pleno', 'Mariana', DATE '2025-02-02');

INSERT INTO EQUIPE (id_equipe, nm_equipe, tp_experiencia, contato_responsavel, dt_criacao)
VALUES (3, 'Equipe Gama', 'Especializada Java', 'Pedro', DATE '2025-02-03');

INSERT INTO EQUIPE (id_equipe, nm_equipe, tp_experiencia, contato_responsavel, dt_criacao)
VALUES (4, 'Equipe Delta', 'Especializada BD', 'Roberta', DATE '2025-02-04');

INSERT INTO EQUIPE (id_equipe, nm_equipe, tp_experiencia, contato_responsavel, dt_criacao)
VALUES (5, 'Equipe Omega', 'Suporte Geral', 'Juliana', DATE '2025-02-05');

--STATUS_NOTIFICACAO(5)--
INSERT INTO STATUS_NOTIFICACAO (id_status, st, prioridade, dt_criacao)
VALUES (1, 'Aberto', 'Alta', TO_DATE('2025-05-01', 'YYYY-MM-DD'));

INSERT INTO STATUS_NOTIFICACAO (id_status, st, prioridade, dt_criacao)
VALUES (2, 'Em Análise', 'Média', TO_DATE('2025-05-02', 'YYYY-MM-DD'));

INSERT INTO STATUS_NOTIFICACAO (id_status, st, prioridade, dt_criacao)
VALUES (3, 'Em Teste', 'Média', TO_DATE('2025-05-03', 'YYYY-MM-DD'));

INSERT INTO STATUS_NOTIFICACAO (id_status, st, prioridade, dt_criacao)
VALUES (4, 'Fechado', 'Baixa', TO_DATE('2025-05-04', 'YYYY-MM-DD'));

INSERT INTO STATUS_NOTIFICACAO (id_status, st, prioridade, dt_criacao)
VALUES (5, 'Cancelado', 'Baixa', TO_DATE('2025-05-05', 'YYYY-MM-DD'));

--USUARIO(5)--
INSERT INTO USUARIO (id_usuario, nm, e_admin, nivel_interno, nivel_usuario, dt_criacao, LOGIN_id_login, EQUIPE_id_equipe)
VALUES(1, 'Ana Silva', 'S', 'Sênior', 'Alto', TO_DATE('2025-03-01', 'YYYY-MM-DD'), 1, 1);

INSERT INTO USUARIO (id_usuario, nm, e_admin, nivel_interno, nivel_usuario, dt_criacao, LOGIN_id_login, EQUIPE_id_equipe)
VALUES(2, 'Bruno Santos', 'N', 'Pleno', 'Médio', TO_DATE('2025-03-02', 'YYYY-MM-DD'), 2, 2);

INSERT INTO USUARIO (id_usuario, nm, e_admin, nivel_interno, nivel_usuario, dt_criacao, LOGIN_id_login, EQUIPE_id_equipe)
VALUES(3, 'Carla Souza', 'N', 'Júnior', 'Baixo', TO_DATE('2025-03-03', 'YYYY-MM-DD'), 3, 3);

INSERT INTO USUARIO (id_usuario, nm, e_admin, nivel_interno, nivel_usuario, dt_criacao, LOGIN_id_login, EQUIPE_id_equipe)
VALUES(4, 'Daniel Costa', 'N', 'Sênior', 'Alto', TO_DATE('2025-03-04', 'YYYY-MM-DD'), 4, 4);

INSERT INTO USUARIO (id_usuario, nm, e_admin, nivel_interno, nivel_usuario, dt_criacao, LOGIN_id_login, EQUIPE_id_equipe)
VALUES(5, 'Eva Pereira', 'S', 'Coordenador', 'Alto', TO_DATE('2025-03-05', 'YYYY-MM-DD'), 5, 5);


--CONTATO_USUARIO(5)--
INSERT INTO CONTATO_USUARIO (id_contato, valor_contato, tp_contato, dt_criacao, USUARIO_id_usuario)
VALUES (1, '(11)99999-0001', 'Celular', TO_DATE('2025-04-01', 'YYYY-MM-DD'), 1);

INSERT INTO CONTATO_USUARIO (id_contato, valor_contato, tp_contato, dt_criacao, USUARIO_id_usuario)
VALUES (2, 'ana.silva@corp.com', 'E-mail', TO_DATE('2025-04-02', 'YYYY-MM-DD'), 2);

INSERT INTO CONTATO_USUARIO (id_contato, valor_contato, tp_contato, dt_criacao, USUARIO_id_usuario)
VALUES (3, '(21)2222-3333', 'Telefone Fixo', TO_DATE('2025-04-03', 'YYYY-MM-DD'), 3);

INSERT INTO CONTATO_USUARIO (id_contato, valor_contato, tp_contato, dt_criacao, USUARIO_id_usuario)
VALUES (4, 'bruno@corp.com', 'E-mail', TO_DATE('2025-04-04', 'YYYY-MM-DD'), 4);

INSERT INTO CONTATO_USUARIO (id_contato, valor_contato, tp_contato, dt_criacao, USUARIO_id_usuario)
VALUES (5, '(31)99999-5555', 'Celular', TO_DATE('2025-04-05', 'YYYY-MM-DD'), 5);

--NOTIFICACAO(5)--
INSERT INTO NOTIFICACAO (id_notificacao, titulo, tp_alerta, conteudo_notificacao, tp_notificacao, operador, st_notificacao, estacao, criticidade, prioridade, dt_criacao, EQUIPE_id_equipe, CONTATO_USUARIO_id_contato, STATUS_NOTIFICACAO_id_status)
VALUES (1, 'Alerta Descarrilhamento', 'Infra', 'Sensor de detecção de desalinhamento do veículo com o trilho ativado, indicando perda de contato ou posição inadequada', 'Automática', 'ScriptMonitor', 'Em aberto', 'Estação Central', 5, 1, TO_DATE('2025-07-01', 'YYYY-MM-DD'), 1, 1, 1);

INSERT INTO NOTIFICACAO (id_notificacao, titulo, tp_alerta, conteudo_notificacao, tp_notificacao, operador, st_notificacao, estacao, criticidade, prioridade, dt_criacao, EQUIPE_id_equipe, CONTATO_USUARIO_id_contato, STATUS_NOTIFICACAO_id_status)
VALUES (2, 'Alerta Superaquecimento', 'Sistema', 'Sensor indicando temperatura > 40°C', 'Automática', 'ScriptMonitor', 'Em aberto', 'Estação Leste', 4, 2, TO_DATE('2025-07-02', 'YYYY-MM-DD'), 2, 3, 2);

INSERT INTO NOTIFICACAO (id_notificacao, titulo, tp_alerta, conteudo_notificacao, tp_notificacao, operador, st_notificacao, estacao, criticidade, prioridade, dt_criacao, EQUIPE_id_equipe, CONTATO_USUARIO_id_contato, STATUS_NOTIFICACAO_id_status)
VALUES (3, 'Falha Sinalização', 'Sistema', 'Sistema de controle reportando ausência de sinal, sinalização incorreta', 'Manual', 'CarlosAdmin', 'Em análise', 'Estação Norte', 4, 3, TO_DATE('2025-07-03', 'YYYY-MM-DD'), 3, 4, 2);

INSERT INTO NOTIFICACAO (id_notificacao, titulo, tp_alerta, conteudo_notificacao, tp_notificacao, operador, st_notificacao, estacao, criticidade, prioridade, dt_criacao, EQUIPE_id_equipe, CONTATO_USUARIO_id_contato, STATUS_NOTIFICACAO_id_status)
VALUES (4, 'Falha no sistema de alimentação elétrica', 'Infra', 'Monitoramento de voltagem/corrente reportando valores fora da faixa operacional', 'Automática', 'SensorXYZ', 'Em teste', 'Estação Sul', 3, 2, TO_DATE('2025-07-04', 'YYYY-MM-DD'), 2, 2, 3);

INSERT INTO NOTIFICACAO (id_notificacao, titulo, tp_alerta, conteudo_notificacao, tp_notificacao, operador, st_notificacao, estacao, criticidade, prioridade, dt_criacao, EQUIPE_id_equipe, CONTATO_USUARIO_id_contato, STATUS_NOTIFICACAO_id_status)
VALUES (5, 'Desgaste no trilho', 'Infra', 'Reportando medições de desgaste', 'Manual', 'AnaAdmin', 'Fechado', 'Estação Oeste', 2, 1, TO_DATE('2025-07-05', 'YYYY-MM-DD'), 4, 5, 4);



--USUARIO_NOTIFICACAO(10)--
INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (1, 1);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (1, 2);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (2, 3);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (2, 4);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (3, 1);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (3, 5);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (4, 2);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (4, 3);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (5, 4);

INSERT INTO USUARIO_NOTIFICACAO (USUARIO_id_usuario, NOTIFICACAO_id_notificacao)
VALUES (5, 5);

--TIPO_NOTIFICACAO(5)--
INSERT INTO TIPO_NOTIFICACAO (id_tp, tp, dt_criacao, NOTIFICACAO_id_notificacao)
VALUES(1, 'Crítica', TO_DATE('2025-08-01', 'YYYY-MM-DD'), 1);

INSERT INTO TIPO_NOTIFICACAO (id_tp, tp, dt_criacao, NOTIFICACAO_id_notificacao)
VALUES (2, 'Sistema', TO_DATE('2025-08-02', 'YYYY-MM-DD'), 2);

INSERT INTO TIPO_NOTIFICACAO (id_tp, tp, dt_criacao, NOTIFICACAO_id_notificacao)
VALUES (3, 'Infra', TO_DATE('2025-08-03', 'YYYY-MM-DD'), 3);

INSERT INTO TIPO_NOTIFICACAO (id_tp, tp, dt_criacao, NOTIFICACAO_id_notificacao)
VALUES (4, 'Infra', TO_DATE('2025-08-04', 'YYYY-MM-DD'), 4);

INSERT INTO TIPO_NOTIFICACAO (id_tp, tp, dt_criacao, NOTIFICACAO_id_notificacao)
VALUES (5, 'Sistema', TO_DATE('2025-08-05', 'YYYY-MM-DD'), 5);

--ESTAÇAO(5)--
INSERT INTO ESTACAO (id_estacao, nm, endereco, telefone, dt_inicio, dt_criacao, carro)
VALUES (1, 'Estação Central', 'Av. Principal, 100', '(11)3000-0001', TO_DATE('2025-06-01', 'YYYY-MM-DD'), TO_DATE('2025-06-01', 'YYYY-MM-DD'), 'Carro 001');

INSERT INTO ESTACAO (id_estacao, nm, endereco, telefone, dt_inicio, dt_criacao, carro)
VALUES (2, 'Estação Leste', 'Rua da Leste, 500', '(11)3000-0002', TO_DATE('2025-06-02', 'YYYY-MM-DD'), TO_DATE('2025-06-02', 'YYYY-MM-DD'), 'Carro 002');

INSERT INTO ESTACAO (id_estacao, nm, endereco, telefone, dt_inicio, dt_criacao, carro)
VALUES (3, 'Estação Norte', 'Rua Norte, 123', '(11)3000-0003', TO_DATE('2025-06-03', 'YYYY-MM-DD'), TO_DATE('2025-06-03', 'YYYY-MM-DD'), 'Carro 003');

INSERT INTO ESTACAO (id_estacao, nm, endereco, telefone, dt_inicio, dt_criacao, carro)
VALUES (4, 'Estação Sul', 'Av. Sul, 789', '(11)3000-0004', TO_DATE('2025-06-04', 'YYYY-MM-DD'), TO_DATE('2025-06-04', 'YYYY-MM-DD'), 'Carro 004');

INSERT INTO ESTACAO (id_estacao, nm, endereco, telefone, dt_inicio, dt_criacao, carro)
VALUES(5, 'Estação Oeste', 'Travessa Oeste, 55', '(11)3000-0005', TO_DATE('2025-06-05', 'YYYY-MM-DD'), TO_DATE('2025-06-05', 'YYYY-MM-DD'), 'Carro 005');

--NOTIFICACAO_ESTACAO(10)--
INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (1, 1);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (1, 2);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (2, 2);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (2, 3);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (3, 3);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (3, 4);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (4, 2);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (4, 4);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (5, 4);

INSERT INTO NOTIFICACAO_ESTACAO (NOTIFICACAO_id_notificacao, ESTACAO_id_estacao)
VALUES (5, 5);

--PASSAGEIRO--
INSERT INTO PASSAGEIRO (id_passageiro, nome, documento, descricao, responsavel, contato, dt_criacao, dt_atualizacao)
VALUES (1, 'Ana Silva', '123.456.789-00', 'Deficiente Visual', NULL, '(11) 99999-8888', SYSDATE, SYSDATE);

INSERT INTO PASSAGEIRO (id_passageiro, nome, documento, descricao, responsavel, contato, dt_criacao, dt_atualizacao)
VALUES (2, 'Pedro Oliveira', '98765432-1', 'Cadeirante', 'Maria Oliveira', '(21) 98765-4321', SYSDATE, SYSDATE);

INSERT INTO PASSAGEIRO (id_passageiro, nome, documento, descricao, responsavel, contato, dt_criacao, dt_atualizacao)
VALUES (3, 'Carla Souza', '55.666.777-8', 'Gestante', NULL, '(15) 91234-5678', SYSDATE, SYSDATE);

INSERT INTO PASSAGEIRO (id_passageiro, nome, documento, descricao, responsavel, contato, dt_criacao, dt_atualizacao)
VALUES (4, 'Lucas Pereira', '22.333.444-5', 'Portador de necessidades especiais', NULL, '(41) 98888-7777', SYSDATE, SYSDATE);

INSERT INTO PASSAGEIRO (id_passageiro, nome, documento, descricao, responsavel, contato, dt_criacao, dt_atualizacao)
VALUES (5, 'Mariana Costa', '11.222.333-6', 'Levando uma Bicicleta', 'João Costa', '(31) 97777-6666', SYSDATE, SYSDATE);

--Menssagem--
INSERT INTO MENSAGEM (id_mensagem, conteudo, contato, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (1, 'Atenção, passageiros! O trem com destino a [Destino do Trem] está atrasado devido a [Motivo do Atraso]. Agradecemos a compreensão.', '(XX) XXXX-XXXX', SYSDATE, NULL, 1);

INSERT INTO MENSAGEM (id_mensagem, conteudo, contato, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (2, 'Prezados passageiros, informamos que a plataforma de embarque do trem para [Destino do Trem] foi alterada para a plataforma [Número da Plataforma].', '(XX) XXXX-XXXX', SYSDATE, NULL, 2);

INSERT INTO MENSAGEM (id_mensagem, conteudo, contato, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (3, 'Alerta! A linha [Nome da Linha] está temporariamente interditada entre as estações [Estação Inicial] e [Estação Final] devido a [Motivo da Interdição]. Utilize as linhas alternativas.', '(XX) XXXX-XXXX', SYSDATE, SYSDATE, 3);

INSERT INTO MENSAGEM (id_mensagem, conteudo, contato, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (4, 'Informamos que a estação [Nome da Estação] está operando com velocidade reduzida devido a falha elétrica. Pedimos cautela ao circular pela estação e plataformas.', '(XX) XXXX-XXXX', SYSDATE, NULL, 4);

INSERT INTO MENSAGEM (id_mensagem, conteudo, contato, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (5, 'Atenção, passageiros! Informamos que a operação da linha [Nome da Linha] foi normalizada. Agradecemos a paciência.', '(XX) XXXX-XXXX', SYSDATE, SYSDATE, 5);

--Tarefa--
INSERT INTO TAREFA (id_tarefa, titulo, descricao, status, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (1, 'ALERTA: Trem com Atraso na Linha Vermelha', 'Passageiros devem verificar os painéis de informação para estimativa de chegada. Considerar rotas alternativas se o atraso for significativo.', 'Pendente', SYSDATE, NULL, 1);

INSERT INTO TAREFA (id_tarefa, titulo, descricao, status, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (2, 'ALERTA: Falha no Sistema de Escadas Rolantes - Estação Sé', 'Técnicos já foram acionados para reparo. Utilizar elevadores ou escadas fixas com cautela. Informações serão atualizadas.', 'Em Andamento', SYSDATE, NULL, 2);

INSERT INTO TAREFA (id_tarefa, titulo, descricao, status, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (3, 'ALERTA: Plataforma 3 Temporariamente Interditada', 'A interdição é devido a manutenção emergencial. Dirigir-se às plataformas alternativas indicadas pela sinalização e pelos funcionários.', 'Em Andamento', SYSDATE, NULL, 3);

INSERT INTO TAREFA (id_tarefa, titulo, descricao, status, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (4, 'ALERTA: Operação Lenta na Linha Azul', 'Devido a condições climáticas adversas, a velocidade dos trens foi reduzida. Planejar a viagem com tempo adicional.', 'Pendente', SYSDATE, NULL, 4);

INSERT INTO TAREFA (id_tarefa, titulo, descricao, status, dt_criacao, dt_atualizacao, USUARIO_id_usuario)
VALUES (5, 'ALERTA: Normalização da Linha Verde', 'Informamos que a operação na Linha Verde foi normalizada após a resolução de problema anterior. Agradecemos a compreensão.', 'Concluída', SYSDATE, SYSDATE, 5);