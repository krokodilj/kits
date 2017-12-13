INSERT INTO kts_test.permission (`id`, `name`) VALUES (1, 'REGISTER');
INSERT INTO kts_test.permission (`id`, `name`) VALUES (2, 'CREATE_MEETING');

INSERT INTO kts_test.role (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO kts_test.role (`id`, `name`) VALUES (2, 'USER');
INSERT INTO kts_test.role (`id`, `name`) VALUES (3, 'COMPANY');
INSERT INTO kts_test.role (`id`, `name`) VALUES (4, 'MANAGER');

INSERT INTO kts_test.role_permissions (`role_id`, `permissions_id`) VALUES (1, 1);
INSERT INTO kts_test.role_permissions (`role_id`, `permissions_id`) VALUES (4, 2);

INSERT INTO kts_test.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('ADMIN', 'admin@gmail.com', 'admin', 'admin', 'admin.png', 1);
INSERT INTO kts_test.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('USER', 'manager@gmail.com', 'manager', 'manager', 'manager.png', 4);
	
INSERT INTO kts_test.building (`address`, `apartment_count`, `city`, `country`, `description`, `picture`, `manager_id`) VALUES
    ('3904 Green Hill Road', 4, 'OXFORD JUNCTION', 'Iowa', 'Nice Comfy place', 'comfy.jpg', 1);
INSERT INTO kts_test.building (`address`, `apartment_count`, `city`, `country`, `description`, `picture`, `manager_id`) VALUES
    ('3478 Lightning Point Drive', 15, 'Memphis', 'Tennessee', 'Solid Building', 'BLD.jpg', 2);