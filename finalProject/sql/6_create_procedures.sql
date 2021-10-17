
delimiter //
CREATE DEFINER = 'coffeeRecipes_app'@'localhost'
PROCEDURE findUsersFromCountry (IN fnd_name VARCHAR (255))
BEGIN
SELECT tab1.id_user, login, password, role FROM users AS tab1
JOIN user_info AS tab2 
ON tab1.id_user = tab2.id_user
WHERE country_id = (SELECT id_country FROM countries WHERE country_name = fnd_name);
END//

delimiter //
CREATE DEFINER = 'coffeeRecipes_app'@'localhost'
PROCEDURE findAllUserSavedRecipes(IN fnd_id INT)
BEGIN
SELECT id_recipe, author_user_id, common, recipe_type, coffee_type_id, date_of_creating FROM recipes WHERE
id_recipe IN (SELECT recipe_id FROM user_recipes WHERE user_id = fnd_id);
END//