SET FOREIGN_KEY_CHECKS = 0;

truncate kts_test.permission; 
insert into kts_test.permission (id, name) values (1, 'REGISTER');
insert into kts_test.permission (id, name) values (2, 'CREATE_MEETING');
insert into kts_test.permission (id, name) values (3, 'CREATE_REPORT');
insert into kts_test.permission (id, name) values (4, 'SEND_BID');
insert into kts_test.permission (id, name) values (5, 'UPDATE_RESIDENT');

truncate kts_test.role; 
insert into kts_test.role (id, name) values (1, 'ADMIN');
insert into kts_test.role (id, name) values (2, 'USER');
insert into kts_test.role (id, name) values (3, 'COMPANY');
insert into kts_test.role (id, name) values (4, 'MANAGER');
insert into kts_test.role (id, name) values (5, 'RESIDENT');
insert into kts_test.role (id, name) values (6, 'APARTMENT_OWNER');

truncate kts_test.role_permissions; 
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 1);
insert into kts_test.role_permissions (role_id, permissions_id) values (4, 2);
insert into kts_test.role_permissions (role_id, permissions_id) values (5, 3);
insert into kts_test.role_permissions (role_id, permissions_id) values (3, 4);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 5);

truncate kts_test.user; 
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'admin@gmail.com', 'admin', 'admin', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'manager@gmail.com', 'manager', 'manager', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'resident@gmail.com', 'vaso', 'vaso', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'mladjo@gmail.com', 'mladen', 'mladen', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'ivan@gmail.com', 'ivan', 'ivan', 5);

insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'nrozzier0@pinterest.com', 'dnertney0', 'prVignkE0MI', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'swenban1@reuters.com', 'msanderson1', 'bVlctTfHcC', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'bfebry2@patch.com', 'aerickssen2', 'A35EL4PGiG', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'agrelka3@google.co.uk', 'kglew3', 'c5uyVAZjHJR3', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'cwitherup4@admin.ch', 'jmcgragh4', 'mXUZJml3', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'moshaughnessy5@stanford.edu', 'hbundey5', 'x9WlLU', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'mbatcock6@upenn.edu', 'sstormont6', 'N63oh0yp', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'abridywater7@omniture.com', 'gkellar7', 'tWYiaUmQc', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'klabb8@apple.com', 'mharbin8', 'NYGRhYS', 1);
insert into kts_test.user (user_type, email, username, password, role_id) values ('ADMIN', 'phellings9@ask.com', 'ajouaneton9', '7V3LMQpgbg1', 1);

insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'dhambya@odnoklassniki.ru', 'rmilkina', '984CJjXrhf', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'wbirkbeckb@uol.com.br', 'ahollowb', 'f2BcG4hi', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'awindowsc@taobao.com', 'gfumagalloc', 'VjOP1qg', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'jreithd@nature.com', 'inoddingsd', 'TqrUYSrnAJ', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'rgrolle@umn.edu', 'rkarolovskye', 'Noc7KTfy2', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'bhayesf@illinois.edu', 'acannyf', 'Pfh8HTA2cW', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'hhasardg@amazon.com', 'bvanderveldeg', '9KivnduLOD2', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'fsygrovesh@mlb.com', 'gspiresh', '5ZPvklt', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'eangricki@thetimes.co.uk', 'elenagheni', 'm4dSeFMgFcf', 2);
insert into kts_test.user (user_type, email, username, password, role_id) values ('USER', 'nkornyakovj@cargocollective.com', 'nkochsj', 'iRCelXqtd1', 2);
	
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'dhambya@odnoklassniki.ru', 'rmilkina', '984CJjXrhf', '41-959-1194', 'District of Columbia', 'Gigazoom', '202-496-5590', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'wbirkbeckb@uol.com.br', 'ahollowb', 'f2BcG4hi', '54-577-9306', 'North Carolina', 'Eazzy', '336-478-1098', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'awindowsc@taobao.com', 'gfumagalloc', 'VjOP1qg', '07-723-4768', 'California', 'Jabberbean', '408-797-7052', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'jreithd@nature.com', 'inoddingsd', 'TqrUYSrnAJ', '55-439-5231', 'Florida', 'Brightbean', '561-630-1295', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'rgrolle@umn.edu', 'rkarolovskye', 'Noc7KTfy2', '65-952-2982', 'New York', 'Thoughtbeat', '585-582-5869', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'bhayesf@illinois.edu', 'acannyf', 'Pfh8HTA2cW', '39-515-6198', 'District of Columbia', 'Yakidoo', '202-972-6182', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'hhasardg@amazon.com', 'bvanderveldeg', '9KivnduLOD2', '85-023-9503', 'Arizona', 'Katz', '602-854-2167', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'fsygrovesh@mlb.com', 'gspiresh', '5ZPvklt', '09-819-0132', 'Ohio', 'Aimbu', '937-488-8276', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'eangricki@thetimes.co.uk', 'elenagheni', 'm4dSeFMgFcf', '37-839-6572', 'California', 'Jaloo', '619-134-6006', 3);
insert into kts_test.user (user_type, email, username, password, pib, location, name, phone_number, role_id) values 
('COMPANY', 'nkornyakovj@cargocollective.com', 'nkochsj', 'iRCelXqtd1', '73-831-7632', 'South Dakota', 'Edgewire', '605-302-2215', 3);

insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'mmccutheonk@twitpic.com', 'rgilvaryk', 'lgcWoOrVa0t', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'amearsl@delicious.com', 'smcteaguel', 'yHrZGFfTDpP', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'apinchbeckm@usa.gov', 'rdallasm', 'ie1wfT0', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'gkonradn@mozilla.com', 'tdoerrenn', '1CL60hj08HV', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'ehurleyo@bloomberg.com', 'ablowneo', '8O3eSQKHTrk', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'pmcarthurp@alexa.com', 'clorainp', 'mtIUYzz3Yd', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'bpeintonq@gmpg.org', 'lgrissettq', 'zPTVal6FI', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'dfrancaisr@jalbum.net', 'cpavelkar', 'CGFTGkx', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'itidmans@nydailynews.com', 'amacnalleys', 'e0Mg1zgwHKin', 4);
insert into kts_test.user (user_type, email, username, password, role_id) values ('MANAGER', 'bbeckerst@drupal.org', 'awindlet', 'd9R5CjHz6s', 4);

insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'alinthead0@craigslist.org', 'dstedman0', 'qKUIKAcUedTl', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'oleitche1@booking.com', 'cgremane1', 'LTKc8CO3', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'ckelso2@naver.com', 'ntiffin2', 'p3xH87ZQ', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'lbrahams3@archive.org', 'svictoria3', 'pMw2Kis0', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'gdruitt4@devhub.com', 'bdakhno4', 'xoygGP4xjtQA', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'mpeeke5@admin.ch', 'llaraway5', 'TunNbXAEOo', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'djablonski6@wikia.com', 'teasterbrook6', '9yjTFNN30DT', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'zramsey7@cam.ac.uk', 'amaccurtain7', 'SoNbzdQt', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'sgolder8@illinois.edu', 'namps8', 'TRzo0ScJ', 5);
insert into kts_test.user (user_type, email, username, password, role_id) values ('RESIDENT', 'cnisius9@zimbio.com', 'lziehms9', 'VW0XXPr', 5);

insert into kts_test.user (user_type, email, username, password, role_id) values ('APARTMENT_OWNER', 'rfoakes0@ox.ac.uk', 'msedgemond0', 'zZXzGKDMJO', 6);
insert into kts_test.user (user_type, email, username, password, role_id) values ('APARTMENT_OWNER', 'gbillo1@marriott.com', 'akondratyuk1', 'OgI0uDPrYye', 6);
insert into kts_test.user (user_type, email, username, password, role_id) values ('APARTMENT_OWNER', 'redmands2@ameblo.jp', 'swhitwam2', 'rjnDlESrrqlI', 6);
insert into kts_test.user (user_type, email, username, password, role_id) values ('APARTMENT_OWNER', 'fadamini3@weather.com', 'abolzen3', '0C0akN', 6);
insert into kts_test.user (user_type, email, username, password, role_id) values ('APARTMENT_OWNER', 'gscritch4@parallels.com', 'dwest4', 'M5TWeM0', 6);

truncate kts_test.building; 
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('3904 Green Hill Road', 4, 'OXFORD JUNCTION', 'Iowa', 'Nice Comfy place', 1);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('3478 Lightning Point Drive', 15, 'Memphis', 'Tennessee', 'Solid Building', 2);
insert into kts_test.building (address, apartment_count, city, country,	description, manager_id) values ('Mise Dimitrijevica 3c', 40, 'Novi Sad', 'Srbija', 'opis', 4);
    
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('7171 Rowland Lane', 1, 'Guanajay', 'Cuba', 'Persistent', 36);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('86 Nevada Way', 2, 'Curahpacul Satu', 'Indonesia', 'core', 36);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('2847 Glacier Hill Hill', 3, 'Rancaerang', 'Indonesia', 'access', 37);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('1 Bayside Alley', 4, 'Aurora', 'United States', 'Multi-layered', 38);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('1488 Westridge Park', 5, 'Sîr ed Danniyé', 'Lebanon', 'frame', 39);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('916 Marcy Pass', 6, 'Orléans', 'France', 'leading edge', 40);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('5 Forster Terrace', 7, 'Jinxiang', 'China', 'dynamic', 40);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('9827 Onsgard Park', 8, 'Pardubice', 'Czech Republic', 'middleware', 40);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('05764 Corry Court', 9, 'Yuanhou', 'China', 'Profit-focused', 41);
insert into kts_test.building (address, apartment_count, city, country, description, manager_id) values ('519 Carpenter Plaza', 10, 'Singa', 'Peru', 'Configurable', 42);

truncate kts_test.residence; 
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (19, 3, 3, 3);

insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (2, 1, 56, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (5, 1, 56, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (8, 2, 57, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (11, 2, 59, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (19, 3, 59, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (6, 2, 60, 1);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (1, 1, 57, 2);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (4, 1, 56, 2);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (9, 9, 58, 3);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (10, 3, 58, 4);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (11, 3, 60, 4);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (4, 1, 59, 5);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (13, 3, 56, 5);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (26, 5, 60, 5);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (15, 3, 57, 6);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (16, 3, 59, 6);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (27, 6, 59, 6);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (30, 6, 58, 6);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (3, 1, 59, 7);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (5, 1, 60, 7);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (11, 2, 56, 7);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (22, 4, 57, 8);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (35, 5, 58, 8);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (36, 5, 59, 8);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (5, 1, 60, 9);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (8, 2, 57, 9);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (2, 1, 57, 10);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (5, 1, 58, 10);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (10, 2, 59, 10);
insert into kts_test.residence (apartment_number, floor_number, apartment_owner_id, building_id) values (11, 2, 60, 10);

truncate kts_test.resident_residence; 
insert into kts_test.resident_residence (residence_id, resident_id) values (1, 4);
insert into kts_test.resident_residence (residence_id, resident_id) values (1, 1);
insert into kts_test.resident_residence (residence_id, resident_id) values (2, 6);
insert into kts_test.resident_residence (residence_id, resident_id) values (3, 14);
insert into kts_test.resident_residence (residence_id, resident_id) values (3, 18);
insert into kts_test.resident_residence (residence_id, resident_id) values (3, 11);
insert into kts_test.resident_residence (residence_id, resident_id) values (4, 25);
insert into kts_test.resident_residence (residence_id, resident_id) values (5, 6);
insert into kts_test.resident_residence (residence_id, resident_id) values (6, 44);
insert into kts_test.resident_residence (residence_id, resident_id) values (7, 34);
insert into kts_test.resident_residence (residence_id, resident_id) values (8, 16);
insert into kts_test.resident_residence (residence_id, resident_id) values (8, 28);
insert into kts_test.resident_residence (residence_id, resident_id) values (9, 31);
insert into kts_test.resident_residence (residence_id, resident_id) values (9, 33);
insert into kts_test.resident_residence (residence_id, resident_id) values (10, 19);
insert into kts_test.resident_residence (residence_id, resident_id) values (11, 49);
insert into kts_test.resident_residence (residence_id, resident_id) values (12, 41);
insert into kts_test.resident_residence (residence_id, resident_id) values (13, 3);
insert into kts_test.resident_residence (residence_id, resident_id) values (14, 7);
insert into kts_test.resident_residence (residence_id, resident_id) values (15, 18);
insert into kts_test.resident_residence (residence_id, resident_id) values (16, 48);
insert into kts_test.resident_residence (residence_id, resident_id) values (17, 27);
insert into kts_test.resident_residence (residence_id, resident_id) values (17, 37);
insert into kts_test.resident_residence (residence_id, resident_id) values (18, 29);
insert into kts_test.resident_residence (residence_id, resident_id) values (19, 13);
insert into kts_test.resident_residence (residence_id, resident_id) values (20, 20);
insert into kts_test.resident_residence (residence_id, resident_id) values (21, 10);
insert into kts_test.resident_residence (residence_id, resident_id) values (21, 16);
insert into kts_test.resident_residence (residence_id, resident_id) values (22, 52);
insert into kts_test.resident_residence (residence_id, resident_id) values (24, 42);
insert into kts_test.resident_residence (residence_id, resident_id) values (26, 23);
insert into kts_test.resident_residence (residence_id, resident_id) values (28, 38);
insert into kts_test.resident_residence (residence_id, resident_id) values (29, 12);
insert into kts_test.resident_residence (residence_id, resident_id) values (30, 9);
insert into kts_test.resident_residence (residence_id, resident_id) values (22, 34);
insert into kts_test.resident_residence (residence_id, resident_id) values (11, 30);
insert into kts_test.resident_residence (residence_id, resident_id) values (21, 9);
insert into kts_test.resident_residence (residence_id, resident_id) values (19, 43);
insert into kts_test.resident_residence (residence_id, resident_id) values (5, 53);
insert into kts_test.resident_residence (residence_id, resident_id) values (7, 15);

truncate kts_test.forward;
insert into kts_test.forward (forwarded_report_id, forwarded_to_id, forwarder_id) 
values (1, 3, null);

truncate kts_test.report; 
insert into kts_test.report (description, status, current_holder_id, location_id, sender_id) values ('Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.', 
'OPEN', 1, 2, 34);
insert into kts_test.report (description, status, location_id, sender_id) values ('Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 
'OPEN', 9, 60);
insert into kts_test.report (description, status, location_id, sender_id) values ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis. Sed ante. Vivamus tortor. Duis mattis egestas metus.', 
'CLOSED', 3, 31);
insert into kts_test.report (description, status, location_id, sender_id) values ('Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.nPraesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', 
'CLOSED', 7, 13);
insert into kts_test.report (description, status, location_id, sender_id) values ('Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat.', 
'CLOSED', 4, 58);
insert into kts_test.report (description, status, location_id, sender_id) values ('In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis. Sed ante. Vivamus tortor. Duis mattis egestas metus.', 
'OPEN', 7, 16);
insert into kts_test.report (description, status, location_id, sender_id) values ('Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 
'OPEN', 2, 34);
insert into kts_test.report (description, status, location_id, sender_id) values ('Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.', 
'CLOSED', 9, 60);
insert into kts_test.report (description, status, location_id, sender_id) values ('Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.', 
'CLOSED', 1, 25);
insert into kts_test.report (description, status, location_id, sender_id) values ('Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', 
'CLOSED', 4, 60);

truncate kts_test.meeting; 
insert into kts_test.meeting (location, record, starts_at, building_id) values ('Florida', 'Haliaetus vocifer', '2016-12-19 09:24:43', 4);
insert into kts_test.meeting (location, record, starts_at, building_id) values ('Ohio', 'Oryx gazella', '2017-03-25 15:05:35', 8);
insert into kts_test.meeting (location, record, starts_at, building_id) values ('Pennsylvania', 'Threskionis aethiopicus', '2016-12-18 10:12:19', 6);
insert into kts_test.meeting (location, record, starts_at, building_id) values ('Oklahoma', 'Echimys chrysurus', '2017-04-21 15:52:13', 5);
insert into kts_test.meeting (location, record, starts_at, building_id) values ('California', 'Tauraco porphyrelophus', '2017-07-14 17:10:42', 7);


SET FOREIGN_KEY_CHECKS = 1;
