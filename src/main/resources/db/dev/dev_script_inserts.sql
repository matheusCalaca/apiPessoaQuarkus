INSERT INTO USUARIO (id, cpf, dateOfBirth, email, name, password, lastname)
VALUES (1, '12345678909', null, 'teste@gmail.com', 'matheus ', 'sq/7bDExYjl5OB06IjU1ix8G5U6I+YAdWrw+zDn7a7w=', 'calaça');

INSERT INTO USUARIO (id, cpf, dateOfBirth, email, name, password, lastname)
VALUES (2, '12345678908', null, 'teste2@gmail.com', 'admin', 'sq/7bDExYjl5OB06IjU1ix8G5U6I+YAdWrw+zDn7a7w=', '');

INSERT INTO USUARIO (id, cpf, dateOfBirth, email, name, password, lastname)
VALUES (3, '12345678907', null, 'teste3@gmail.com', 'user', 'sq/7bDExYjl5OB06IjU1ix8G5U6I+YAdWrw+zDn7a7w=', '');


INSERT INTO usuario_roles (USUARIO_id, roles) VALUES (1, 'USER');
INSERT INTO usuario_roles (USUARIO_id, roles) VALUES (2, 'ADMIN');
INSERT INTO usuario_roles (USUARIO_id, roles) VALUES (2, 'USER');
