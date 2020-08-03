BEGIN;

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (id bigserial PRIMARY KEY, name VARCHAR(255));
INSERT INTO users (name) VALUES
('Arnold S.'),
('Jean R.'),
('Keanu R.'),
('Margot R.'),
('Gerard D.'),
('Brad P.'),
('Silvester S.'),
('Willis B.');

DROP TABLE IF EXISTS lot CASCADE;
CREATE TABLE lot (id bigserial PRIMARY KEY, name VARCHAR(255), currentRate  INTEGER, owner_last_bet BIGINT REFERENCES users (id), version INTEGER);
INSERT INTO lot (name, currentRate, version) VALUES
('vase', 0, 0),
('sphere', 0, 0),
('maul', 0, 0),
('ring', 0, 0);

COMMIT;