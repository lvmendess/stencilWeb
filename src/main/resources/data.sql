USE Stencil;

INSERT INTO Usuario (nomeUsuario, senhaUsuario, roleUsuario) VALUES ('admin', '$2a$12$Q6psl5QTqsjcZ7pesPT3GeEUSuPXHEV99Fo3YYVELOZT1YDUVSwOO', 'A'),
                                                                    ('aluno1', '$2a$12$Q6psl5QTqsjcZ7pesPT3GeEUSuPXHEV99Fo3YYVELOZT1YDUVSwOO', 'S');

INSERT INTO Turma (nomeTurma) VALUES ('turmaA'), ('turmaB');

#UPDATE Characters SET skin = 'anaLuzia.png' WHERE id=2;
#SELECT * FROM Aluno;