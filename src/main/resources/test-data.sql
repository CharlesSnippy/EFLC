-- --USERS
-- INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('admin@gmail.com', '12345', TRUE);
-- INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('javastudy@outlook.com', '12345', TRUE);
-- INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('test1@outlook.com', '12345', TRUE);
-- INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('roleuser@outlook.com', '12345', TRUE);
-- INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('superuser@outlook.com', '12345', TRUE);
--
-- --AUTHORITIES
-- INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin@gmail.com', 'ROLE_ADMIN');
-- INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('roleuser@outlook.com', 'ROLE_USER');
-- INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('superuser@outlook.com', 'ROLE_SUPER_USER');
--
-- --LOG
INSERT INTO LOG (LOGSTRING) VALUES ('TEST LOG 1');
INSERT INTO LOG (LOGSTRING) VALUES ('TEST LOG 2');
INSERT INTO LOG (LOGSTRING) VALUES ('TEST LOG 3');