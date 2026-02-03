-- Insertar Puntos de Venta de la consigna
INSERT INTO point_of_sale (name)
VALUES ('CABA'),
       ('GBA_1'),
       ('GBA_2'),
       ('Santa Fe'),
       ('Córdoba'),
       ('Misiones'),
       ('Salta'),
       ('Chubut'),
       ('Santa Cruz'),
       ('Catamarca');

-- Insertar Caminos entre Puntos de Venta
INSERT INTO paths (pos1_id, pos2_id, cost)
VALUES (1, 2, 2),
       (1, 3, 3),
       (2, 3, 5),
       (2, 4, 10),
       (1, 4, 11),
       (4, 5, 5),
       (2, 5, 14),
       (6, 7, 32),
       (8, 9, 11),
       (7, 10, 5),
       (3, 8, 10),
       (5, 8, 30),
       (5, 10, 5),
       (4, 6, 11);