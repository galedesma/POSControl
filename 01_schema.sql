-- Crear Tablas
CREATE TABLE point_of_sale
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE paths
(
    id      SERIAL PRIMARY KEY,
    pos1_id INT     NOT NULL,
    pos2_id INT     NOT NULL,
    cost    INTEGER NOT NULL,
    CONSTRAINT fk_pos1 FOREIGN KEY (pos1_id) REFERENCES point_of_sale (id) ON DELETE CASCADE,
    CONSTRAINT fk_pos2 FOREIGN KEY (pos2_id) REFERENCES point_of_sale (id) ON DELETE CASCADE,
    CONSTRAINT unique_pos_pair UNIQUE (pos1_id, pos2_id)
);