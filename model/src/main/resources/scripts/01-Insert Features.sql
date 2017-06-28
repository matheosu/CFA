INSERT INTO feature (id, description, name, url, dependency_id)
VALUES (10, 'Gerenciamento de grupos de usuário', 'Grupo de Usuários', 'role', NULL),
  (20, 'Gerenciamento de usuários', 'Usuários', 'user', 10),
  (30, 'Gerenciamento de localização de controladores', 'Localização de Controladores', 'localization', NULL),
  (40, 'Gerenciamento de controladores', 'Controladores', 'controller', 30),
  (50, 'Gerenciamento de restrição de fluxo por usuário', 'Restrições de Fluxo', 'flowrestriction', 20);