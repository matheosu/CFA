CREATE TABLE feature (
  id            BIGINT       NOT NULL CONSTRAINT feature_pkey PRIMARY KEY,
  description   VARCHAR(100),
  name          VARCHAR(50)  NOT NULL CONSTRAINT unique_feature_name UNIQUE,
  url           VARCHAR(255) NOT NULL CONSTRAINT unique_feature_url UNIQUE,
  dependency_id BIGINT CONSTRAINT dependency_fk REFERENCES feature
);

CREATE TABLE "user" (
  id                 BIGINT       NOT NULL CONSTRAINT user_pkey PRIMARY KEY,
  active             BOOLEAN,
  email              VARCHAR(400) NOT NULL  CONSTRAINT unique_user_email UNIQUE,
  name               VARCHAR(200) NOT NULL,
  password           VARCHAR(500) NOT NULL,
  datecreated        TIMESTAMP,
  dateupdated        TIMESTAMP,
  usercreatedid      BIGINT CONSTRAINT usercreated_fk REFERENCES "user",
  userupdatedid      BIGINT CONSTRAINT userupdated_fk REFERENCES "user",
  version            INTEGER
);

CREATE TABLE flow_restriction (
  id            BIGINT      NOT NULL CONSTRAINT flow_restriction_pkey PRIMARY KEY,
  description   VARCHAR(120),
  name          VARCHAR(80) NOT NULL,
  period        VARCHAR(255),
  restriction   VARCHAR(255),
  value         REAL        NOT NULL,
  datecreated   TIMESTAMP,
  dateupdated   TIMESTAMP,
  usercreatedid BIGINT CONSTRAINT usercreated_fk REFERENCES "user",
  userupdatedid BIGINT CONSTRAINT userupdated_fk REFERENCES "user",
  version       INTEGER
);

CREATE TABLE role (
  id            BIGINT       NOT NULL CONSTRAINT role_pkey PRIMARY KEY,
  description   VARCHAR(200),
  name          VARCHAR(100) NOT NULL,
  datecreated   TIMESTAMP,
  dateupdated   TIMESTAMP,
  usercreatedid BIGINT CONSTRAINT usercreated_fk REFERENCES "user",
  userupdatedid BIGINT CONSTRAINT userupdated_fk REFERENCES "user",
  version       INTEGER
);

ALTER TABLE "user"
    ADD flowrestriction_id BIGINT CONSTRAINT flowrestriction_fk REFERENCES flow_restriction,
    ADD role_id            BIGINT CONSTRAINT role_fk REFERENCES role;

CREATE TABLE role_features (
  role_id    BIGINT NOT NULL CONSTRAINT role_fk REFERENCES role,
  feature_id BIGINT NOT NULL CONSTRAINT feature_fk REFERENCES feature
);

CREATE TABLE localization (
  id            BIGINT      NOT NULL CONSTRAINT localization_pkey PRIMARY KEY,
  description   VARCHAR(200),
  name          VARCHAR(80) NOT NULL,
  datecreated   TIMESTAMP,
  dateupdated   TIMESTAMP,
  usercreatedid BIGINT CONSTRAINT usercreated_fk REFERENCES "user",
  userupdatedid BIGINT CONSTRAINT userupdated_fk REFERENCES "user",
  version       INTEGER
);

CREATE TABLE controller (
  id              BIGINT NOT NULL CONSTRAINT controller_pkey PRIMARY KEY,
  model           VARCHAR(50),
  uuid            VARCHAR(30),
  localization_id BIGINT CONSTRAINT localization_fk REFERENCES localization,
  datecreated     TIMESTAMP,
  dateupdated     TIMESTAMP,
  usercreatedid   BIGINT CONSTRAINT usercreated_fk REFERENCES "user",
  userupdatedid   BIGINT CONSTRAINT userupdated_fk REFERENCES "user",
  version         INTEGER
);

CREATE TABLE "sessionUser" (
  id              BIGINT NOT NULL CONSTRAINT "sessionUser_pkey" PRIMARY KEY,
  logindate       TIMESTAMP,
  logoutdate      TIMESTAMP,
  hostname        VARCHAR(255),
  ipaddr          VARCHAR(255),
  locale          VARCHAR(255),
  macaddr         VARCHAR(255),
  operationsystem VARCHAR(255),
  useragent       VARCHAR(255),
  user_id         BIGINT CONSTRAINT user_fk REFERENCES "user"
);

CREATE TABLE flow (
  id            BIGINT NOT NULL CONSTRAINT flow_pkey PRIMARY KEY,
  measure       REAL   NOT NULL,
  registrantion TIMESTAMP,
  controller_id BIGINT CONSTRAINT controller_fk REFERENCES controller,
  user_id       BIGINT CONSTRAINT user_fk REFERENCES "user"
);


