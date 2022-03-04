INSERT INTO users (id, username, nickname, email, password, role, creator_id)
VALUES (0, 'test00001', 'test user', 'test@example.com', 'pass', 'USER', NULL);
INSERT INTO creator (id, name, surname, birth_date, bio, creator_type, avatar)
VALUES (0, 'Creator', 'Test', to_date('1985-05-16', 'YYYY-MM-DD'), 'Biography', 'ARTIST',  'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=');
INSERT INTO users (id, username, nickname, email, password, role, creator_id)
VALUES (1, 'creator00001', 'creator test', 'creator@example.com', 'pass', 'USER', 0);
INSERT INTO followers (user_id, following_id)
VALUES (0, 1);