INSERT INTO kts_prod.permission (`id`, `name`) VALUES (1, 'REGISTER');
INSERT INTO kts_prod.permission (`id`, `name`) VALUES (2, 'CREATE-REPORT');
INSERT INTO kts_prod.permission (`id`, `name`) VALUES (3, 'SEND-BID');

INSERT INTO kts_prod.role (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (2, 'USER');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (3, 'COMPANY');
INSERT INTO kts_prod.role (`id`, `name`) VALUES (4, 'RESIDENT');

INSERT INTO kts_prod.role_permissions (`role_id`, `permissions_id`) VALUES (1, 1);
INSERT INTO kts_prod.role_permissions (`role_id`, `permissions_id`) VALUES (4, 2);
INSERT INTO kts_prod.role_permissions (`role_id`, `permissions_id`) VALUES (3, 3);

insert into kts_prod.user (user_type, email, username, password, role_id) values ('ADMIN', 'admin@gmail.com', 'admin', 'admin', 1);
insert into kts_prod.user (user_type, email, username, password, role_id) values ('USER', 'manager@gmail.com', 'manager', 'manager', 2);
insert into kts_prod.user (user_type, email, username, password, role_id) values ('RESIDENT', 'resident@gmail.com', 'vaso', 'vaso', 4);
insert into kts_prod.user (user_type, email, username, password, role_id) values ('RESIDENT', 'mladjo@gmail.com', 'mladen', 'mladen', 4);
insert into kts_prod.user (user_type, email, username, password, role_id) values ('RESIDENT', 'ivan@gmail.com', 'ivan', 'ivan', 4);
insert into kts_prod.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'dhambya@odnoklassniki.ru', 'rmilkina', '984CJjXrhf', '41-959-1194', 'District of Columbia', 'Gigazoom', '202-496-5590', 3);

INSERT INTO kts_prod.building (`address`, `apartment_count`, `city`, `country`,
	`description`, `manager_id`) VALUES ('Mise Dimitrijevica 3c', 40, 'Novi Sad', 'Srbija', 'opis', 4);

INSERT INTO kts_prod.residence (`apartment_number`, `floor_number`, `apartment_owner_id`, `building_id`) VALUES (19, 3, 2, 1);

INSERT INTO kts_prod.resident_residence VALUES (1, 3);