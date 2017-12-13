INSERT INTO kts_prod.permission (`id`, `name`) VALUES (1, 'REGISTER');
INSERT INTO kts_prod.permission (`id`, `name`) VALUES (2, 'CREATE_REPORT');

INSERT INTO kts_prod.role (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (2, 'USER');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (3, 'COMPANY');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (4, 'RESIDENT');

INSERT INTO kts_prod.role_permissions (`role_id`, `permissions_id`) VALUES (1, 1);
INSERT INTO kts_prod.role_permissions (`role_id`, `permissions_id`) VALUES (4, 2);

INSERT INTO kts_prod.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('ADMIN', 'admin@gmail.com', 'admin', 'admin', 'admin.png', 1);
INSERT INTO kts_prod.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('RESIDENT', 'resident@gmail.com', 'vaso', 'vaso', 'resident.png', 4);
INSERT INTO kts_prod.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('RESIDENT', 'mladjo@gmail.com', 'mladen', 'mladen', 'mladen.png', 4);
INSERT INTO kts_prod.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('USER', 'manager@gmail.com', 'manager', 'manager', 'manager.png', 2);
INSERT INTO kts_prod.user (`user_type`, `email`, `username`, `password`, `picture`, `role_id`) VALUES
	('RESIDENT', 'ivan@gmail.com', 'ivan', 'ivan', 'ivan.png', 4);
	
INSERT INTO kts_prod.building (`address`, `apartment_count`, `city`, `country`,
	`description`, `picture`, `manager_id`) VALUES ('Mise Dimitrijevica 3c', 40, 'Novi Sad', 'Srbija', 'opis', 'slika.png', 4);

INSERT INTO kts_prod.residence (`apartment_number`, `floor_number`, `apartment_owner_id`, `building_id`) VALUES (19, 3, 2, 1);

INSERT INTO kts_prod.resident_residence VALUES (1, 3);