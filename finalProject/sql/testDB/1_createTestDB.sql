CREATE DATABASE `coffeeRecipesTEST` ;

create user 'coffeeRecipes_appTEST'@localhost
        identified by '123password123';

GRANT SELECT,INSERT,UPDATE,DELETE, EXECUTE
ON `coffeeRecipesTEST`.*
TO coffeeRecipes_appTEST@localhost;

GRANT SELECT,INSERT,UPDATE,DELETE
ON `coffeeRecipesTEST`.*
TO coffeeRecipes_user@'%';
#IDENTIFIED BY '123password123';