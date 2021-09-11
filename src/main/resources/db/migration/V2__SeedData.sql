INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types)
VALUES ('apiclient', 'apiclient', 'read,write,user_info', 'password');

INSERT INTO students (id, first_name, last_name, email_address, username, password, created_by, updated_by)
VALUES (1, 'Phani Kumar', 'N T R', 'ntrphanikumar@gmail.com', 'phani', 'phani', 'seeddata', 'seeddata');

INSERT INTO courses (id, name, description, created_by, updated_by)
VALUES (1, 'DS & Algos Basic', 'Data structures and Algos basic course', 'seeddata', 'seeddata');