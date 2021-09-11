CREATE TABLE IF NOT EXISTS students (
  id bigint NOT NULL AUTO_INCREMENT,
  email_address varchar(128) NOT NULL,
  first_name varchar(64) NOT NULL,
  last_name varchar(64) NOT NULL,
  username varchar(64) NOT NULL,
  password varchar(128) NOT NULL,
  mobile_number varchar(16) DEFAULT NULL,
  course_count int NOT NULL DEFAULT 0,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by varchar(128) NOT NULL DEFAULT 'System',
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by varchar(128) NOT NULL DEFAULT 'System',
  PRIMARY KEY (id),
  UNIQUE KEY UK_student_username (username)
);

CREATE TABLE IF NOT EXISTS courses (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(128) NOT NULL,
  description varchar(512) NOT NULL,
  student_count int NOT NULL DEFAULT 0,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by varchar(255) NOT NULL DEFAULT 'System',
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by varchar(255) NOT NULL DEFAULT 'System',
  PRIMARY KEY (id),
  UNIQUE KEY UK_course_name (name)
);

CREATE TABLE IF NOT EXISTS student_courses (
  student_id bigint NOT NULL,
  course_id bigint NOT NULL,
  PRIMARY KEY (student_id,course_id),
  CONSTRAINT FK_student_courses_course_id FOREIGN KEY (course_id) REFERENCES courses (id),
  CONSTRAINT FK_student_courses_student_id FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
	userId VARCHAR(255),
	clientId VARCHAR(255),
	scope VARCHAR(255),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);