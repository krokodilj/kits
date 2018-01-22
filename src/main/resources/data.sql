SET FOREIGN_KEY_CHECKS = 0;

truncate kts_prod.role; 
insert into kts_prod.role (id, name) values (1, 'ADMIN');
insert into kts_prod.role (id, name) values (2, 'COMPANY');
insert into kts_prod.role (id, name) values (3, 'MANAGER');
insert into kts_prod.role (id, name) values (4, 'RESIDENT');
insert into kts_prod.role (id, name) values (5, 'OWNER');

truncate kts_prod.permission; 
insert into kts_prod.permission (id, name) values (1, 'REGISTER');
insert into kts_prod.permission (id, name) values (2, 'CREATE_MEETING');
insert into kts_prod.permission (id, name) values (3, 'CREATE_REPORT');
insert into kts_prod.permission (id, name) values (4, 'SEND_BID');
insert into kts_prod.permission (id, name) values (5, 'UPDATE_RESIDENT');
insert into kts_prod.permission (id, name) values (6, 'CREATE_BUILDING');
insert into kts_prod.permission (id, name) values (7, 'CREATE_RESIDENCE');
insert into kts_prod.permission (id, name) values (8, 'CREATE_PROPOSAL');

truncate kts_prod.role_permissions; 
insert into kts_prod.role_permissions (role_id, permissions_id) values (1, 1);
insert into kts_prod.role_permissions (role_id, permissions_id) values (1, 5);
insert into kts_prod.role_permissions (role_id, permissions_id) values (1, 6);
insert into kts_prod.role_permissions (role_id, permissions_id) values (1, 7);
insert into kts_prod.role_permissions (role_id, permissions_id) values (1, 8);
insert into kts_prod.role_permissions (role_id, permissions_id) values (2, 4);
insert into kts_prod.role_permissions (role_id, permissions_id) values (2, 8);
insert into kts_prod.role_permissions (role_id, permissions_id) values (3, 2);
insert into kts_prod.role_permissions (role_id, permissions_id) values (3, 8);
insert into kts_prod.role_permissions (role_id, permissions_id) values (4, 3);
insert into kts_prod.role_permissions (role_id, permissions_id) values (4, 8);
insert into kts_prod.role_permissions (role_id, permissions_id) values (5, 8);

truncate kts_prod.user; 
insert into kts_prod.user (id, user_type, email, username, password) values (1, 'USER', 'admin1@gmail.com', 'admin1', 'admin1');
insert into kts_prod.user (id, user_type, email, username, password) values (2, 'USER', 'admin2@gmail.com', 'admin2', 'admin2');
insert into kts_prod.user (id, user_type, email, username, password) values (3, 'USER', 'admin3@gmail.com', 'admin3', 'admin3');
insert into kts_prod.user (id, user_type, email, username, password) values (4, 'USER', 'admin4@gmail.com', 'admin4', 'admin4');
insert into kts_prod.user (id, user_type, email, username, password) values (5, 'USER', 'admin5@gmail.com', 'admin5', 'admin5');

insert into kts_prod.user_roles (user_id, roles_id) values (1, 1);
insert into kts_prod.user_roles (user_id, roles_id) values (2, 1);
insert into kts_prod.user_roles (user_id, roles_id) values (3, 1);
insert into kts_prod.user_roles (user_id, roles_id) values (4, 1);
insert into kts_prod.user_roles (user_id, roles_id) values (5, 1);

insert into kts_prod.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(6, 'COMPANY', 'company1@gmail.com', 'company1', 'company1', '54-577-9306', 'North Carolina', 'Eazzy', '336-478-1098');
insert into kts_prod.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(7, 'COMPANY', 'company2@gmail.com', 'company2', 'company2', '07-723-4768', 'California', 'Jabberbean', '408-797-7052');
insert into kts_prod.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(8, 'COMPANY', 'company3@gmail.com', 'company3', 'company3', '55-439-5231', 'Florida', 'Brightbean', '561-630-1295');
insert into kts_prod.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(9, 'COMPANY', 'company4@gmail.com', 'company4', 'company4', '65-952-2982', 'New York', 'Thoughtbeat', '585-582-5869');
insert into kts_prod.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(10, 'COMPANY', 'company5@gmail.com', 'company5', 'company5', '41-959-1194', 'District of Columbia', 'Gigazoom', '202-496-5590');

insert into kts_prod.user_roles (user_id, roles_id) values (6, 2);
insert into kts_prod.user_roles (user_id, roles_id) values (7, 2);
insert into kts_prod.user_roles (user_id, roles_id) values (8, 2);
insert into kts_prod.user_roles (user_id, roles_id) values (9, 2);
insert into kts_prod.user_roles (user_id, roles_id) values (10, 2);

insert into kts_prod.user (id, user_type, email, username, password) values (11, 'USER', 'manager1@gmai.com', 'manager1', 'manager1');
insert into kts_prod.user (id, user_type, email, username, password) values (12, 'USER', 'manager2@gmai.com', 'manager2', 'manager2');
insert into kts_prod.user (id, user_type, email, username, password) values (13, 'USER', 'manager3@gmai.com', 'manager3', 'manager3');
insert into kts_prod.user (id, user_type, email, username, password) values (14, 'USER', 'manager4@gmai.com', 'manager4', 'manager4');
insert into kts_prod.user (id, user_type, email, username, password) values (15, 'USER', 'manager5@gmai.com', 'manager5', 'manager5');

insert into kts_prod.user_roles (user_id, roles_id) values (11, 3);
insert into kts_prod.user_roles (user_id, roles_id) values (12, 3);
insert into kts_prod.user_roles (user_id, roles_id) values (13, 3);
insert into kts_prod.user_roles (user_id, roles_id) values (14, 3);
insert into kts_prod.user_roles (user_id, roles_id) values (15, 3);

insert into kts_prod.user (id, user_type, email, username, password) values (16, 'USER', 'resident1@gmail.com', 'resident1', 'resident1');
insert into kts_prod.user (id, user_type, email, username, password) values (17, 'USER', 'resident2@gmail.com', 'resident2', 'resident2');
insert into kts_prod.user (id, user_type, email, username, password) values (18, 'USER', 'resident3@gmail.com', 'resident3', 'resident3');
insert into kts_prod.user (id, user_type, email, username, password) values (19, 'USER', 'resident4@gmail.com', 'resident4', 'resident4');
insert into kts_prod.user (id, user_type, email, username, password) values (20, 'USER', 'resident5@gmail.com', 'resident5', 'resident5');
insert into kts_prod.user (id, user_type, email, username, password) values (21, 'USER', 'resident6@gmail.com', 'resident6', 'resident6');
insert into kts_prod.user (id, user_type, email, username, password) values (22, 'USER', 'resident7@gmail.com', 'resident7', 'resident7');
insert into kts_prod.user (id, user_type, email, username, password) values (23, 'USER', 'resident8@gmail.com', 'resident8', 'resident8');
insert into kts_prod.user (id, user_type, email, username, password) values (24, 'USER', 'resident9@gmail.com', 'resident9', 'resident9');
insert into kts_prod.user (id, user_type, email, username, password) values (25, 'USER', 'resident10@gmail.com', 'resident10', 'resident10');
insert into kts_prod.user (id, user_type, email, username, password) values (26, 'USER', 'resident11@gmail.com', 'resident11', 'resident11');
insert into kts_prod.user (id, user_type, email, username, password) values (27, 'USER', 'resident12@gmail.com', 'resident12', 'resident12');
insert into kts_prod.user (id, user_type, email, username, password) values (28, 'USER', 'resident13@gmail.com', 'resident13', 'resident13');
insert into kts_prod.user (id, user_type, email, username, password) values (29, 'USER', 'resident14@gmail.com', 'resident14', 'resident14');
insert into kts_prod.user (id, user_type, email, username, password) values (30, 'USER', 'resident15@gmail.com', 'resident15', 'resident15');
insert into kts_prod.user (id, user_type, email, username, password) values (31, 'USER', 'resident16@gmail.com', 'resident16', 'resident16');
insert into kts_prod.user (id, user_type, email, username, password) values (32, 'USER', 'resident17@gmail.com', 'resident17', 'resident17');
insert into kts_prod.user (id, user_type, email, username, password) values (33, 'USER', 'resident18@gmail.com', 'resident18', 'resident18');
insert into kts_prod.user (id, user_type, email, username, password) values (34, 'USER', 'resident19@gmail.com', 'resident19', 'resident19');
insert into kts_prod.user (id, user_type, email, username, password) values (35, 'USER', 'resident20@gmail.com', 'resident20', 'resident20');

insert into kts_prod.user_roles (user_id, roles_id) values (16, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (17, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (18, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (19, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (20, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (21, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (22, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (23, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (24, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (25, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (26, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (27, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (28, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (29, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (30, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (31, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (32, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (33, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (34, 4);
insert into kts_prod.user_roles (user_id, roles_id) values (35, 4);

insert into kts_prod.user (id, user_type, email, username, password) values (36, 'USER', 'owner1@gmail.com', 'owner1', 'owner1');
insert into kts_prod.user (id, user_type, email, username, password) values (37, 'USER', 'owner2@gmail.com', 'owner2', 'owner2');
insert into kts_prod.user (id, user_type, email, username, password) values (38, 'USER', 'owner3@gmail.com', 'owner3', 'owner3');
insert into kts_prod.user (id, user_type, email, username, password) values (39, 'USER', 'owner4@gmail.com', 'owner4', 'owner4');
insert into kts_prod.user (id, user_type, email, username, password) values (40, 'USER', 'owner5@gmail.com', 'owner5', 'owner5');
insert into kts_prod.user (id, user_type, email, username, password) values (41, 'USER', 'owner6@gmail.com', 'owner6', 'owner6');
insert into kts_prod.user (id, user_type, email, username, password) values (42, 'USER', 'owner7@gmail.com', 'owner7', 'owner7');
insert into kts_prod.user (id, user_type, email, username, password) values (43, 'USER', 'owner8@gmail.com', 'owner8', 'owner8');
insert into kts_prod.user (id, user_type, email, username, password) values (44, 'USER', 'owner9@gmail.com', 'owner9', 'owner9');
insert into kts_prod.user (id, user_type, email, username, password) values (45, 'USER', 'owner10@gmail.com', 'owner10', 'owner10');

insert into kts_prod.user_roles (user_id, roles_id) values (36, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (37, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (38, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (39, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (40, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (41, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (42, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (43, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (44, 5);
insert into kts_prod.user_roles (user_id, roles_id) values (45, 5);



