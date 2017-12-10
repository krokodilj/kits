INSERT INTO kts_test.permission (`id`, `name`) VALUES (1, 'register');

INSERT INTO kts_test.role (`id`, `name`) VALUES (1, 'admin');
INSERT INTO kts_test.role (`id`, `name`) VALUES (2, 'manager');

INSERT INTO kts_test.role_permissions (`role_id`, `permissions_id`) VALUES (1, 1);

INSERT INTO kts_test.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('admin', 'admin@gmail.com', 'admin', 'admin', 'admin.png', 1);
	