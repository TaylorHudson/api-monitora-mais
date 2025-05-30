INSERT INTO monitoring (id, name, allow_monitors_same_time, teacher_registration)
VALUES
    (1, 'Matemática Básica', false, '0000000'),
    (2, 'Estrutura de Dados', false, '1111111'),
    (3, 'Projeto 2', false, '1111111'),
    (4, 'Banco de Dados', false, '0000000');

INSERT INTO monitoring_students (monitoring_id, student_registration)
VALUES
    (1, '202315020036'),
    (2, '202315020036'),
    (3, '202315020036'),
    (4, '202315020036');