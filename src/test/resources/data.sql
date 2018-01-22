SET FOREIGN_KEY_CHECKS = 0;

truncate kts_test.role; 
insert into kts_test.role (id, name) values (1, 'ADMIN');
insert into kts_test.role (id, name) values (2, 'COMPANY');
insert into kts_test.role (id, name) values (3, 'MANAGER');
insert into kts_test.role (id, name) values (4, 'RESIDENT');
insert into kts_test.role (id, name) values (5, 'OWNER');

truncate kts_test.permission; 
insert into kts_test.permission (id, name) values (1, 'REGISTER');
insert into kts_test.permission (id, name) values (2, 'CREATE_MEETING');
insert into kts_test.permission (id, name) values (3, 'CREATE_REPORT');
insert into kts_test.permission (id, name) values (4, 'SEND_BID');
insert into kts_test.permission (id, name) values (5, 'UPDATE_RESIDENT');
insert into kts_test.permission (id, name) values (6, 'CREATE_BUILDING');
insert into kts_test.permission (id, name) values (7, 'CREATE_RESIDENCE');
insert into kts_test.permission (id, name) values (8, 'CREATE_PROPOSAL');
insert into kts_test.permission (id, name) values (9, 'FORWARD_REPORT');
insert into kts_test.permission (id, name) values (10, 'PROPOSAL_VOTE');
insert into kts_test.permission (id, name) values (11, 'CREATE_ANNOUNCEMENT');

truncate kts_test.role_permissions; 
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 1);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 5);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 6);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 7);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 8);
insert into kts_test.role_permissions (role_id, permissions_id) values (1, 9);
insert into kts_test.role_permissions (role_id, permissions_id) values (2, 4);
insert into kts_test.role_permissions (role_id, permissions_id) values (2, 8);
insert into kts_test.role_permissions (role_id, permissions_id) values (2, 9);
insert into kts_test.role_permissions (role_id, permissions_id) values (3, 2);
insert into kts_test.role_permissions (role_id, permissions_id) values (3, 8);
insert into kts_test.role_permissions (role_id, permissions_id) values (3, 9);
insert into kts_test.role_permissions (role_id, permissions_id) values (4, 3);
insert into kts_test.role_permissions (role_id, permissions_id) values (4, 8);
insert into kts_test.role_permissions (role_id, permissions_id) values (4, 9);
insert into kts_test.role_permissions (role_id, permissions_id) values (4, 11);
insert into kts_test.role_permissions (role_id, permissions_id) values (5, 3);
insert into kts_test.role_permissions (role_id, permissions_id) values (5, 8);
insert into kts_test.role_permissions (role_id, permissions_id) values (5, 9);
insert into kts_test.role_permissions (role_id, permissions_id) values (5, 10);

truncate kts_test.user; 
insert into kts_test.user (id, user_type, email, username, password) values (1, 'USER', 'admin1@gmail.com', 'admin1', 'admin1');
insert into kts_test.user (id, user_type, email, username, password) values (2, 'USER', 'admin2@gmail.com', 'admin2', 'admin2');
insert into kts_test.user (id, user_type, email, username, password) values (3, 'USER', 'admin3@gmail.com', 'admin3', 'admin3');
insert into kts_test.user (id, user_type, email, username, password) values (4, 'USER', 'admin4@gmail.com', 'admin4', 'admin4');
insert into kts_test.user (id, user_type, email, username, password) values (5, 'USER', 'admin5@gmail.com', 'admin5', 'admin5');

truncate kts_test.user_roles;
insert into kts_test.user_roles (user_id, roles_id) values (1, 1);
insert into kts_test.user_roles (user_id, roles_id) values (2, 1);
insert into kts_test.user_roles (user_id, roles_id) values (3, 1);
insert into kts_test.user_roles (user_id, roles_id) values (4, 1);
insert into kts_test.user_roles (user_id, roles_id) values (5, 1);

insert into kts_test.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(6, 'COMPANY', 'company1@gmail.com', 'company1', 'company1', '54-577-9306', 'North Carolina', 'Eazzy', '336-478-1098');
insert into kts_test.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(7, 'COMPANY', 'company2@gmail.com', 'company2', 'company2', '07-723-4768', 'California', 'Jabberbean', '408-797-7052');
insert into kts_test.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(8, 'COMPANY', 'company3@gmail.com', 'company3', 'company3', '55-439-5231', 'Florida', 'Brightbean', '561-630-1295');
insert into kts_test.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(9, 'COMPANY', 'company4@gmail.com', 'company4', 'company4', '65-952-2982', 'New York', 'Thoughtbeat', '585-582-5869');
insert into kts_test.user (id, user_type, email, username, password, pib, location, name, phone_number) values 
(10, 'COMPANY', 'company5@gmail.com', 'company5', 'company5', '41-959-1194', 'District of Columbia', 'Gigazoom', '202-496-5590');

insert into kts_test.user_roles (user_id, roles_id) values (6, 2);
insert into kts_test.user_roles (user_id, roles_id) values (7, 2);
insert into kts_test.user_roles (user_id, roles_id) values (8, 2);
insert into kts_test.user_roles (user_id, roles_id) values (9, 2);
insert into kts_test.user_roles (user_id, roles_id) values (10, 2);

insert into kts_test.user (id, user_type, email, username, password) values (11, 'USER', 'manager1@gmai.com', 'manager1', 'manager1');
insert into kts_test.user (id, user_type, email, username, password) values (12, 'USER', 'manager2@gmai.com', 'manager2', 'manager2');
insert into kts_test.user (id, user_type, email, username, password) values (13, 'USER', 'manager3@gmai.com', 'manager3', 'manager3');
insert into kts_test.user (id, user_type, email, username, password) values (14, 'USER', 'manager4@gmai.com', 'manager4', 'manager4');
insert into kts_test.user (id, user_type, email, username, password) values (15, 'USER', 'manager5@gmai.com', 'manager5', 'manager5');

insert into kts_test.user_roles (user_id, roles_id) values (11, 3);
insert into kts_test.user_roles (user_id, roles_id) values (12, 3);
insert into kts_test.user_roles (user_id, roles_id) values (13, 3);
insert into kts_test.user_roles (user_id, roles_id) values (14, 3);
insert into kts_test.user_roles (user_id, roles_id) values (15, 3);

insert into kts_test.user (id, user_type, email, username, password) values (16, 'USER', 'resident1@gmail.com', 'resident1', 'resident1');
insert into kts_test.user (id, user_type, email, username, password) values (17, 'USER', 'resident2@gmail.com', 'resident2', 'resident2');
insert into kts_test.user (id, user_type, email, username, password) values (18, 'USER', 'resident3@gmail.com', 'resident3', 'resident3');
insert into kts_test.user (id, user_type, email, username, password) values (19, 'USER', 'resident4@gmail.com', 'resident4', 'resident4');
insert into kts_test.user (id, user_type, email, username, password) values (20, 'USER', 'resident5@gmail.com', 'resident5', 'resident5');
insert into kts_test.user (id, user_type, email, username, password) values (21, 'USER', 'resident6@gmail.com', 'resident6', 'resident6');
insert into kts_test.user (id, user_type, email, username, password) values (22, 'USER', 'resident7@gmail.com', 'resident7', 'resident7');
insert into kts_test.user (id, user_type, email, username, password) values (23, 'USER', 'resident8@gmail.com', 'resident8', 'resident8');
insert into kts_test.user (id, user_type, email, username, password) values (24, 'USER', 'resident9@gmail.com', 'resident9', 'resident9');
insert into kts_test.user (id, user_type, email, username, password) values (25, 'USER', 'resident10@gmail.com', 'resident10', 'resident10');
insert into kts_test.user (id, user_type, email, username, password) values (26, 'USER', 'resident11@gmail.com', 'resident11', 'resident11');
insert into kts_test.user (id, user_type, email, username, password) values (27, 'USER', 'resident12@gmail.com', 'resident12', 'resident12');
insert into kts_test.user (id, user_type, email, username, password) values (28, 'USER', 'resident13@gmail.com', 'resident13', 'resident13');
insert into kts_test.user (id, user_type, email, username, password) values (29, 'USER', 'resident14@gmail.com', 'resident14', 'resident14');
insert into kts_test.user (id, user_type, email, username, password) values (30, 'USER', 'resident15@gmail.com', 'resident15', 'resident15');
insert into kts_test.user (id, user_type, email, username, password) values (31, 'USER', 'resident16@gmail.com', 'resident16', 'resident16');
insert into kts_test.user (id, user_type, email, username, password) values (32, 'USER', 'resident17@gmail.com', 'resident17', 'resident17');
insert into kts_test.user (id, user_type, email, username, password) values (33, 'USER', 'resident18@gmail.com', 'resident18', 'resident18');
insert into kts_test.user (id, user_type, email, username, password) values (34, 'USER', 'resident19@gmail.com', 'resident19', 'resident19');
insert into kts_test.user (id, user_type, email, username, password) values (35, 'USER', 'resident20@gmail.com', 'resident20', 'resident20');

insert into kts_test.user_roles (user_id, roles_id) values (16, 4);
insert into kts_test.user_roles (user_id, roles_id) values (17, 4);
insert into kts_test.user_roles (user_id, roles_id) values (18, 4);
insert into kts_test.user_roles (user_id, roles_id) values (19, 4);
insert into kts_test.user_roles (user_id, roles_id) values (20, 4);
insert into kts_test.user_roles (user_id, roles_id) values (21, 4);
insert into kts_test.user_roles (user_id, roles_id) values (22, 4);
insert into kts_test.user_roles (user_id, roles_id) values (23, 4);
insert into kts_test.user_roles (user_id, roles_id) values (24, 4);
insert into kts_test.user_roles (user_id, roles_id) values (25, 4);
insert into kts_test.user_roles (user_id, roles_id) values (26, 4);
insert into kts_test.user_roles (user_id, roles_id) values (27, 4);
insert into kts_test.user_roles (user_id, roles_id) values (28, 4);
insert into kts_test.user_roles (user_id, roles_id) values (29, 4);
insert into kts_test.user_roles (user_id, roles_id) values (30, 4);
insert into kts_test.user_roles (user_id, roles_id) values (31, 4);
insert into kts_test.user_roles (user_id, roles_id) values (32, 4);
insert into kts_test.user_roles (user_id, roles_id) values (33, 4);
insert into kts_test.user_roles (user_id, roles_id) values (34, 4);
insert into kts_test.user_roles (user_id, roles_id) values (35, 4);

insert into kts_test.user (id, user_type, email, username, password) values (36, 'USER', 'owner1@gmail.com', 'owner1', 'owner1');
insert into kts_test.user (id, user_type, email, username, password) values (37, 'USER', 'owner2@gmail.com', 'owner2', 'owner2');
insert into kts_test.user (id, user_type, email, username, password) values (38, 'USER', 'owner3@gmail.com', 'owner3', 'owner3');
insert into kts_test.user (id, user_type, email, username, password) values (39, 'USER', 'owner4@gmail.com', 'owner4', 'owner4');
insert into kts_test.user (id, user_type, email, username, password) values (40, 'USER', 'owner5@gmail.com', 'owner5', 'owner5');

insert into kts_test.user_roles (user_id, roles_id) values (36, 5);
insert into kts_test.user_roles (user_id, roles_id) values (37, 5);
insert into kts_test.user_roles (user_id, roles_id) values (38, 5);
insert into kts_test.user_roles (user_id, roles_id) values (39, 5);
insert into kts_test.user_roles (user_id, roles_id) values (40, 5);


truncate kts_test.building;
insert into kts_test.building (id, address, apartment_count, city, country, description, manager_id) values (1, 'address', 5, 'city', 'country', 'description', 11);
insert into kts_test.building (id, address, apartment_count, city, country, description, manager_id) values (2, 'address', 5, 'city', 'country', 'description', 12);
insert into kts_test.building (id, address, apartment_count, city, country, description, manager_id) values (3, 'address', 5, 'city', 'country', 'description', 13);
insert into kts_test.building (id, address, apartment_count, city, country, description, manager_id) values (4, 'address', 5, 'city', 'country', 'description', 14);
insert into kts_test.building (id, address, apartment_count, city, country, description, manager_id) values (5, 'address', 5, 'city', 'country', 'description', 15);


truncate kts_test.residence;
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (1, 1, 1, 36, 1);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (2, 2, 1, 36, 1);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (3, 3, 2, 36, 1);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (4, 4, 2, 36, 1);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (5, 1, 1, 37, 2);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (6, 2, 1, 37, 2);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (7, 3, 2, 37, 2);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (8, 4, 2, 37, 2);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (9, 1, 1, 38, 3);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (10, 2, 1, 38, 3);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (11, 3, 2, 38, 3);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (12, 4, 2, 38, 3);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (13, 1, 1, 39, 4);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (14, 2, 1, 39, 4);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (15, 3, 2, 39, 4);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (16, 4, 2, 39, 4);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (17, 1, 1, 40, 5);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (18, 2, 1, 40, 5);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (19, 3, 2, 40, 5);
insert into kts_test.residence (id, apartment_number, floor_number, apartment_owner_id, building_id) values (20, 4, 2, 40, 5);

truncate kts_test.resident_residence;
insert into kts_test.resident_residence (residence_id, resident_id) values (1, 16);
insert into kts_test.resident_residence (residence_id, resident_id) values (1, 17);
insert into kts_test.resident_residence (residence_id, resident_id) values (2, 18);
insert into kts_test.resident_residence (residence_id, resident_id) values (2, 19);
insert into kts_test.resident_residence (residence_id, resident_id) values (3, 20);
insert into kts_test.resident_residence (residence_id, resident_id) values (3, 21);
insert into kts_test.resident_residence (residence_id, resident_id) values (4, 22);
insert into kts_test.resident_residence (residence_id, resident_id) values (4, 23);
insert into kts_test.resident_residence (residence_id, resident_id) values (5, 24);
insert into kts_test.resident_residence (residence_id, resident_id) values (5, 25);
insert into kts_test.resident_residence (residence_id, resident_id) values (6, 26);
insert into kts_test.resident_residence (residence_id, resident_id) values (6, 27);
insert into kts_test.resident_residence (residence_id, resident_id) values (7, 28);
insert into kts_test.resident_residence (residence_id, resident_id) values (8, 29);
insert into kts_test.resident_residence (residence_id, resident_id) values (9, 30);
insert into kts_test.resident_residence (residence_id, resident_id) values (10, 31);
insert into kts_test.resident_residence (residence_id, resident_id) values (11, 32);
insert into kts_test.resident_residence (residence_id, resident_id) values (12, 33);
insert into kts_test.resident_residence (residence_id, resident_id) values (13, 34);
insert into kts_test.resident_residence (residence_id, resident_id) values (14, 35);

truncate kts_test.report;
insert into kts_test.report (id, description, status, current_holder_id, location_id, sender_id) values (1, "report1", "OPEN", 1, 1, 16);
insert into kts_test.report (id, description, status, current_holder_id, location_id, sender_id) values (2, "report2", "OPEN", 2, 2, 18);
insert into kts_test.report (id, description, status, current_holder_id, location_id, sender_id) values (3, "report3", "OPEN", 3, 3, 20);
insert into kts_test.report (id, description, status, current_holder_id, location_id, sender_id) values (4, "report4", "OPEN", 4, 4, 22);
insert into kts_test.report (id, description, status, current_holder_id, location_id, sender_id) values (5, "report5", "OPEN", 5, 5, 24);

truncate kts_test.forward;
insert into kts_test.forward (id, forwarded_report_id, forwarded_to_id) values (1, 1, 11);
insert into kts_test.forward (id, forwarded_report_id, forwarded_to_id) values (2, 2, 12);
insert into kts_test.forward (id, forwarded_report_id, forwarded_to_id) values (3, 3, 13);
insert into kts_test.forward (id, forwarded_report_id, forwarded_to_id) values (4, 4, 14);
insert into kts_test.forward (id, forwarded_report_id, forwarded_to_id) values (5, 5, 15);

truncate kts_test.bid;
insert into kts_test.bid (id, description, price, status, company_id, report_bid_id) values (1, "bid1", 1, "OPEN", 6, 1);
insert into kts_test.bid (id, description, price, status, company_id, report_bid_id) values (2, "bid2", 1, "OPEN", 7, 2);
insert into kts_test.bid (id, description, price, status, company_id, report_bid_id) values (3, "bid3", 1, "OPEN", 8, 3);
insert into kts_test.bid (id, description, price, status, company_id, report_bid_id) values (4, "bid4", 1, "OPEN", 9, 4);
insert into kts_test.bid (id, description, price, status, company_id, report_bid_id) values (5, "bid5", 1, "CLOSED", 10, 5);

truncate kts_test.proposal;
insert into kts_test.proposal (id, content, status, suggested_at, building_id, proposer_id) values (1, "proposal", "OPEN", "2018-01-23 14:48:46", 1, 16);
insert into kts_test.proposal (id, content, status, suggested_at, building_id, proposer_id) values (2, "proposal", "OPEN", "2018-01-23 14:48:46", 2, 18);
insert into kts_test.proposal (id, content, status, suggested_at, building_id, proposer_id) values (3, "proposal", "OPEN", "2018-01-23 14:48:46", 3, 20);
insert into kts_test.proposal (id, content, status, suggested_at, building_id, proposer_id) values (4, "proposal", "OPEN", "2018-01-23 14:48:46", 4, 22);
insert into kts_test.proposal (id, content, status, suggested_at, building_id, proposer_id) values (5, "proposal", "OPEN", "2018-01-23 14:48:46", 5, 24);

truncate kts_test.proposal_vote;
truncate kts_test.proposal_votes;

truncate kts_test.announcement;
insert into kts_test.announcement (id, content, posted_at, building_id, poster_id) values (1, "announcement", "2018-01-23 14:48:46", 1, 16);
insert into kts_test.announcement (id, content, posted_at, building_id, poster_id) values (2, "announcement", "2018-01-23 14:48:46", 2, 24);
insert into kts_test.announcement (id, content, posted_at, building_id, poster_id) values (3, "announcement", "2018-01-23 14:48:46", 3, 30);
insert into kts_test.announcement (id, content, posted_at, building_id, poster_id) values (4, "announcement", "2018-01-23 14:48:46", 4, 34);
