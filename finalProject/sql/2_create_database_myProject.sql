CREATE DATABASE `coffeeRecipes` ;

create user 'coffeeRecipes_app'@localhost
        identified by '123password123';

GRANT SELECT,INSERT,UPDATE,DELETE, EXECUTE
ON `coffeeRecipes`.*
TO coffeeRecipes_app@localhost;

GRANT SELECT,INSERT,UPDATE,DELETE
ON `coffeeRecipes`.*
TO coffeeRecipes_user@'%';
#IDENTIFIED BY '123password123';