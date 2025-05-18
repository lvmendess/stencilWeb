CREATE DATABASE IF NOT EXISTS Characters;
#DROP DATABASE Characters;
USE Characters;

CREATE TABLE IF NOT EXISTS Characters(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL UNIQUE,
    xp INT NOT NULL,
    skin VARCHAR(255) NOT NULL
);

INSERT INTO Characters (name, xp, skin) VALUES ('aluno1', 100, 'C:/Users/user/Documents/BSI/projeto/stencilWeb/stencilweb/src/main/resources/static/images/fabiola.png');
SELECT * FROM Characters;