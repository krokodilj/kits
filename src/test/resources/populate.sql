INSERT INTO kts_test.permission (`id`, `name`) VALUES (1, 'REGISTER');

INSERT INTO kts_test.role (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO kts_test.role (`id`, `name`) VALUES (2, 'USER');
INSERT INTO kts_test.role (`id`, `name`) VALUES (3, 'COMPANY');

INSERT INTO kts_test.role_permissions (`role_id`, `permissions_id`) VALUES (1, 1);

INSERT INTO kts_test.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('ADMIN', 'admin@gmail.com', 'admin', 'admin', 'admin.png', 1);
	