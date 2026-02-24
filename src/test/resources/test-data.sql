-- Insert Points Of Sale
INSERT INTO point_of_sale (id, name) VALUES ( 1, 'CABA' );
INSERT INTO point_of_sale (id, name) VALUES ( 2, 'GBA_1' );
INSERT INTO point_of_sale (id, name) VALUES ( 3, 'GBA_2' );
INSERT INTO point_of_sale (id, name) VALUES ( 4, 'Santa Fe' );
INSERT INTO point_of_sale (id, name) VALUES ( 5, 'Córdoba' );
INSERT INTO point_of_sale (id, name) VALUES ( 6, 'Misiones' );
INSERT INTO point_of_sale (id, name) VALUES ( 7, 'Salta' );
INSERT INTO point_of_sale (id, name) VALUES ( 8, 'Chubut' );
INSERT INTO point_of_sale (id, name) VALUES ( 9, 'Santa Cruz' );
INSERT INTO point_of_sale (id, name) VALUES ( 10, 'Catamarca' );

-- Insert Paths
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (1, 1, 2, 3);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (2, 1, 3, 3);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (3, 2, 3, 5);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (4, 2, 4, 10);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (5, 1, 4, 11);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (6, 4, 5, 5);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (7, 2, 5, 14);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (8, 6, 7, 32);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (9, 8, 9, 11);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (10, 7, 10, 5);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (11, 3, 8, 10);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (12, 5, 8, 30);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (13, 5, 10, 5);
INSERT INTO paths (id, pos1_id, pos2_id, cost) VALUES (14, 4, 6, 11);