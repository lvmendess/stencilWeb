CREATE DATABASE IF NOT EXISTS Characters;
#DROP DATABASE Characters;
USE Characters;

CREATE TABLE IF NOT EXISTS Characters(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL UNIQUE,
    xp INT NOT NULL,
    skin VARCHAR(255) NOT NULL
);

INSERT INTO Characters (name, xp, skin) VALUES ('aluno1', 100, 'fabiola.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno2', 100, 'anaLuzia.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno3', 100, 'bernardo.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno5', 100, 'gabrielHenrique.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno6', 100, 'stephany.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno7', 100, 'padraoFem.png');
INSERT INTO Characters (name, xp, skin) VALUES ('aluno8', 100, 'padraoMasc.png');
#UPDATE Characters SET skin = 'anaLuzia.png' WHERE id=2;
SELECT * FROM Characters;