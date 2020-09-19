INSERT INTO users (id, name, email)
VALUES (1, 'Petras', 'Petras@gmail.com');

INSERT INTO giftees (name, user_id)
VALUES ('Antanas', 1);

INSERT into occasions (name, date, giftee_id, created_date, modified_date)
VALUES ('Bernvakaris', 20200930, 1, '2020-09-17 18:47:52.69', '2020-09-30 18:47:52.69');

INSERT INTO gifts (name, description, created_date, modified_date, occasion_id)
VALUES ('Candle', 'A simple gift', '2020-09-17 18:47:52.69', '2020-09-30 18:47:52.69', 1);

INSERT into hibernate_sequence (next_val) VALUES (1), (1), (1);