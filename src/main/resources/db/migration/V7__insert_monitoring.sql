INSERT INTO monitoring (name, allow_monitors_same_time, teacher_registration, topics)
VALUES
    ('Matemática Básica', false, '0000000', 'Aritmética, Álgebra'),
    ('Estrutura de Dados', false, '1111111', 'Listas, Árvores, Grafos'),
    ('Projeto 2', false, '1111111', 'Engenharia de Software, UML'),
    ('Banco de Dados', false, '0000000', 'Modelagem, SQL, Normalização');

INSERT INTO monitoring_students (monitoring_id, student_registration)
VALUES
    (1, '202315020036'),
    (2, '202315020036'),
    (3, '202315020036'),
    (4, '202315020036');
