-- TODO: check this script and add new tables depending on your needs

DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users
(
  id                IDENTITY PRIMARY KEY,
  email             VARCHAR NOT NULL,
  password          VARCHAR NOT NULL,
  avatar_url        VARCHAR,
  nickname          VARCHAR NOT NULL,
  first_name        VARCHAR,
  last_name         VARCHAR,
  description       VARCHAR,
  is_active         BOOLEAN DEFAULT TRUE,
  registered_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles
(
  user_id           BIGINT,
  role              VARCHAR,
  CONSTRAINT user_role_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);