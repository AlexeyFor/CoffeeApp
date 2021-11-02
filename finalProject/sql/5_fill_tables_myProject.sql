/*passwords are 'user1', '123asd123', 'user'),
('user2', '456asd456', 'user'),
('moder2', '789asd789', 'moder');
If something will be wrong because of encoding, you can fill passwords with 
commented main method in class
src/main/java/by.training.coffeeproject.service.PasswordEncryptedService.java
*/
INSERT INTO users (
 login, password, role) 
VALUES ('user1', 'O]T��,
 {9�w�{$���&W', 'user'),
('user2', '�ϳg��)�V�ו%���&+', 'user'),
('moder2', '+Y��m=*e���.I��', 'moder');

INSERT INTO countries (
id_country,
country_name)
VALUES ('1', 'Belarus'), ('2', 'Russia'),
('3', 'Brazil'), ('4', 'China'), ('5','Nikaragua'),
('6', 'Burundi'), ('7', 'Cambodia'), ('8','Cameroon'),
('9', 'Chile'), ('10', 'Colombia'), ('11','Congo'),
('12', 'Costa Rica'), ('13', 'Cuba'), ('14','Ecuador'),
('15', 'El Salvador'), ('16', 'Ethiopia'), ('17','Guatemala'),
('18', 'Guinea');

INSERT INTO user_info (
id_user, name, email, information, country_id, storagePath)
VALUES ('1', 'Valera', 'Valera@mail.ru', 'contact me on Inst: instNicName', '1', 'D:\Storage'),
('2', 'Mishanya', 'Mishanya@mail.ru', 'contact me on Tg: TgNicName', '2', 'D:\Storage');

INSERT INTO coffee_type (
 name, country_of_origin_id, processing_method, roaster, roast_degree, information, arabic_percent, robusta_percent)
VALUES ('Fine Cup','3', 'dry', 'ROAST.BY', 'dark', 'great coffee', '100', '0'),
 ('Mountain China' ,'4', 'washed', 'ROAST.BY', 'medium', '-','100', '0');
 INSERT INTO coffee_type (
 name,  processing_method, roaster, roast_degree, information, arabic_percent, robusta_percent)
 VALUES ('Professional Exclusive Best Arabica',  'other', 'BARISTA', 'dark', 'mix', '70', '30');
 
INSERT INTO recipes(
 author_user_id, common,  recipe_type, coffee_type_id)
 VALUES ('1', true, 'pourover', '1'), ('1', true, 'french press', '2'),
 ('2', true, 'pourover', '3'), ('2', false, 'french press', '3');
 
 INSERT INTO user_recipes(user_id, recipe_id)
 VALUES ('1','1'), ('1','2'), ('1','3'),
 ('2','3'), ('2','4'), ('2','2');
 
 INSERT INTO infusions (recipe_id, time_start, water_volume, time_end, water_temperature)
 VALUES ('1', '0', '50', '20', '85'), ('1', '45', '130', '75', '85'), ('1', '75', '120', '105', '85'),
 ('2', '0', '500', '15', '92'),
  ('3', '0', '50', '20', '80'), ('3', '45', '130', '75', '80'), ('3', '75', '120', '105', '80'),
  ('4', '0', '450', '15', '85');
 
 INSERT INTO pourover_recipes (
id_recipe, funnel_type, recipe_name, mass_of_coffee, grind_settings, coffee_grinder, total_time, discription)
VALUES ('1', 'Hario V60', 'Brazil_1', '18.0', '29', 'commandante', '240', 'attemption to make dark roasted coffee in filter, not bad'),
('3', 'Original Chemex', 'exp_1', '20.5', '0', '-', '240', 'just because i can, bad');

INSERT INTO french_press_recipes (
id_recipe, recipe_name, french_press_volume, mass_of_coffee, grind_settings, coffee_grinder, 
cap_breaking_time, plunger_lowering_time, total_time, discription)
values ('2', 'Brazil_french_1','1000', '30.0', '17', 'commandante', '240', '600', '660', 
'great cup, really enjoy it. Please, do not preheat the french press, water temp is prety high'),
('4', 'Barista_french_1','1000', '32.0', '18', 'commandante', '240', '600', '660', 
'great cup, really enjoy it. Please, do not preheat the french press, water temp is prety high');

INSERT INTO comments(
recipe_id,  user_id, comment) 
VALUES ('3', '1', 'Are you insane?'), ('3', '2', 'And you?');
