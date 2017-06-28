INSERT INTO "user" (id, datecreated, dateupdated, usercreatedid, userupdatedid, version, active, email, name, password, flowrestriction_id, role_id)
VALUES (1, now(), now(), NULL , NULL , 1, TRUE, 'admin@cfa.com', 'Administrator', 'XoE+cBi3D/c/*', NULL, NULL);

INSERT INTO role (id, datecreated, dateupdated, usercreatedid, userupdatedid, version, description, name)
VALUES
  (1, now(), now(), 1, 1, 1, 'Administradores', 'Administradores'),
  (2, now(), now(), 1, 1, 1, 'Gerenciadores', 'Gerenciadores'),
  (3, now(), now(), 1, 1, 1, 'Usuários', 'Usuários');

UPDATE "user"
SET role_id = 1, usercreatedid=1, userupdatedid=1
WHERE id = 1;

INSERT INTO role_features (role_id, feature_id)
VALUES (1, 10),(1, 20),(1, 30),(1, 40),(1, 50), (2,20),(2,50);