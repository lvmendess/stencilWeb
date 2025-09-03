USE Stencil;

INSERT INTO Usuario (nomeUsuario, senhaUsuario, roleUsuario) VALUES ('admin', '$2a$10$zoVFgbD/KpH60BDOF19qF.hdUJsiKcfEWwme3rnuQxfiEHbC9ILFS', 'A');

INSERT INTO Aluno (nomeAluno, nick, xp, skin, ofensiva) VALUES ('aluno1', '', 100, 'fabiola.png', 0),
('aluno2', '', 100, 'anaLuzia.png', 0),
('aluno3', '', 100, 'bernardo.png', 0),
('aluno5', '', 100, 'gabrielHenrique.png', 0),
('aluno6', '', 100, 'stephany.png', 0),
('aluno7', '', 100, 'padraoFem.png', 0),
('aluno8', '', 100, 'padraoMasc.png', 0);

INSERT INTO Turma (nomeTurma) VALUES ('turmaA'), ('turmaB');

#UPDATE Characters SET skin = 'anaLuzia.png' WHERE id=2;
#SELECT * FROM Aluno;