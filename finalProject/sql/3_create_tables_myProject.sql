USE coffeeRecipes;

drop tables users, countries, user_info, recipes_type, coffee_type, funnel_types, user_recipes, recipes, comments, infusions,pourover_recipes, french_press_recipes ;

CREATE TABLE users (
id_user INTEGER NOT NULL AUTO_INCREMENT,
login VARCHAR (30) NOT NULL UNIQUE,
password VARCHAR (32) NOT NULL,
role TINYINT UNSIGNED NOT NULL CHECK (role IN (0,1)),

CONSTRAINT pk_users PRIMARY KEY (id_user)
);

CREATE TABLE countries(
id_country  TINYINT UNSIGNED NOT NULL,
country_name VARCHAR (255) NOT NULL,

CONSTRAINT pk_countries PRIMARY KEY (id_country)
); 

CREATE TABLE user_info 
(id_user INTEGER NOT NULL,
name VARCHAR (50) NOT NULL UNIQUE,
email VARCHAR (254) NOT NULL UNIQUE,
information VARCHAR (1000),
country_id TINYINT UNSIGNED NOT NULL,
storagePath VARCHAR (1000) NOT NULL,

CONSTRAINT pk_user_info PRIMARY KEY (id_user), 
FOREIGN KEY (id_user)
REFERENCES users (id_user)
ON DELETE RESTRICT,

CONSTRAINT fk_user_info_country FOREIGN KEY (country_id)
REFERENCES countries (id_country)
ON UPDATE CASCADE
ON DELETE RESTRICT
);

CREATE TABLE recipes_type(
id_recipe_type  TINYINT UNSIGNED NOT NULL,
recipe_type VARCHAR (255) NOT NULL,
CONSTRAINT pk_countries PRIMARY KEY (id_recipe_type)
);

CREATE TABLE coffee_type(
id_coffee_type INTEGER NOT NULL AUTO_INCREMENT,
name VARCHAR (255) NOT NULL ,
country_of_origin_id TINYINT UNSIGNED,
arabic_percent TINYINT UNSIGNED NOT NULL CHECK (arabic_percent between 0 and 100) ,
robusta_percent TINYINT UNSIGNED NOT NULL CHECK (robusta_percent between 0 and 100),
processing_method VARCHAR (255) CHECK (processing_method IN 
('dry', 'washed', 'honey', 'other')),
roaster VARCHAR (255),
roast_degree  VARCHAR (255) CHECK (roast_degree IN 
('light', 'medium', 'dark')),
information VARCHAR (1000),

CONSTRAINT chk_percent CHECK (arabic_percent + robusta_percent = 100),

CONSTRAINT pk_coffee_type PRIMARY KEY (id_coffee_type), 
CONSTRAINT fk_coffee_country FOREIGN KEY (country_of_origin_id)
REFERENCES countries (id_country)
ON UPDATE CASCADE
ON DELETE RESTRICT
);



CREATE TABLE recipes(
id_recipe INTEGER NOT NULL AUTO_INCREMENT,
author_user_id INTEGER NOT NULL,
public BOOL NOT NULL,
date_of_creating DATETIME ON UPDATE CURRENT_TIMESTAMP,
recipe_type_id TINYINT UNSIGNED NOT NULL,
coffee_type_id INTEGER NOT NULL,

CONSTRAINT pk_recipes PRIMARY KEY (id_recipe), 

CONSTRAINT fk_recipes_author_user_id FOREIGN KEY (author_user_id)
REFERENCES user_info (id_user)
ON DELETE NO ACTION,

CONSTRAINT fk_recipes_recipes_type FOREIGN KEY (recipe_type_id)
REFERENCES recipes_type (id_recipe_type)
ON UPDATE CASCADE 
ON DELETE RESTRICT,

CONSTRAINT fk_recipes_coffee_type_id FOREIGN KEY (coffee_type_id)
REFERENCES coffee_type (id_coffee_type)
ON UPDATE CASCADE
ON DELETE RESTRICT
);

CREATE TABLE user_recipes(
user_id INTEGER NOT NULL,
recipe_id INTEGER NOT NULL,

CONSTRAINT pk_user_recipes PRIMARY KEY (user_id, recipe_id), 

CONSTRAINT fk_user_recipes_user_id FOREIGN KEY (user_id)
REFERENCES user_info (id_user)
ON DELETE CASCADE,

CONSTRAINT fk_user_recipes_recipe_id FOREIGN KEY (recipe_id)
REFERENCES recipes (id_recipe)
ON DELETE CASCADE
);

CREATE TABLE comments(
id_comment INTEGER NOT NULL AUTO_INCREMENT,
recipe_id INTEGER NOT NULL,
date_of_creating DATETIME ON UPDATE CURRENT_TIMESTAMP,
comment text NOT NULL,
user_id INTEGER NOT NULL,

CONSTRAINT pk_comments PRIMARY KEY (id_comment), 

CONSTRAINT fk_comments_id_user FOREIGN KEY (user_id)
REFERENCES user_info (id_user)
ON DELETE NO ACTION,

CONSTRAINT fk_comments_id_recipe FOREIGN KEY (recipe_id)
REFERENCES recipes (id_recipe)
ON UPDATE CASCADE
ON DELETE CASCADE
);

CREATE TABLE infusions (
id_infusion INTEGER NOT NULL AUTO_INCREMENT,
recipe_id INTEGER NOT NULL,
time_start SMALLINT UNSIGNED NOT NULL,
water_volume SMALLINT UNSIGNED NOT NULL,
time_end SMALLINT UNSIGNED,
water_temperature TINYINT UNSIGNED NOT NULL,

CONSTRAINT pk_infusions PRIMARY KEY (id_infusion), 

CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
REFERENCES recipes (id_recipe)
ON UPDATE CASCADE
ON DELETE CASCADE
);

CREATE TABLE funnel_types (
id_funnel INTEGER NOT NULL AUTO_INCREMENT,
funnel_name VARCHAR (255) NOT NULL,
CONSTRAINT pk_funnel_type PRIMARY KEY (id_funnel)
);

CREATE TABLE pourover_recipes (
id_recipe INTEGER NOT NULL,
funnel_id INTEGER NOT NULL,
receipt_name VARCHAR (100) UNIQUE NOT NULL,
mass_of_coffee FLOAT4 NOT NULL,
grind_settings FLOAT4 NOT NULL,
coffee_grinder VARCHAR (255) NOT NULL,
total_time SMALLINT UNSIGNED NOT NULL,
discription TEXT,

CONSTRAINT pk_pourover_recipes PRIMARY KEY (id_recipe), 
FOREIGN KEY (id_recipe)
REFERENCES recipes (id_recipe)
ON UPDATE CASCADE
ON DELETE RESTRICT,

CONSTRAINT fk_funnel_id FOREIGN KEY (funnel_id)
REFERENCES funnel_types (id_funnel)
ON UPDATE CASCADE
ON DELETE RESTRICT
);

CREATE TABLE french_press_recipes (
id_recipe INTEGER NOT NULL,
receipt_name VARCHAR (100) UNIQUE NOT NULL,
french_press_volume SMALLINT UNSIGNED,
mass_of_coffee FLOAT4 NOT NULL,
grind_settings TINYINT UNSIGNED NOT NULL,
coffee_grinder VARCHAR (255) NOT NULL,
cap_breaking_time SMALLINT UNSIGNED,
plunger_lowering_time SMALLINT UNSIGNED ,
total_time SMALLINT UNSIGNED,
discription TEXT,

CONSTRAINT french_press_recipes PRIMARY KEY (id_recipe), 
FOREIGN KEY (id_recipe)
REFERENCES recipes (id_recipe)
ON UPDATE CASCADE
ON DELETE RESTRICT
);
