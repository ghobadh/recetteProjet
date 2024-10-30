##-- docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
##-- connect to mysql using root user

CREATE DATABASE gfsdev;
CREATE DATABASE gfsprod;

CREATE USER 'gfsDevUser'@'localhost' IDENTIFIED BY 'gforce';
CREATE USER 'gfsProdUser'@'localhost' IDENTIFIED BY 'gforce';

CREATE USER 'gfsDevUser'@'%' identified by 'gforce';
CREATE USER 'gfsProdUser'@'%' identified by 'gforce';


GRANT SELECT ON gfsdev.* to 'gfsDevUser'@'localhost';
GRANT INSERT ON gfsdev.* to 'gfsDevUser'@'localhost';
GRANT DELETE ON gfsdev.* to 'gfsDevUser'@'localhost';
GRANT UPDATE ON gfsdev.* to 'gfsDevUser'@'localhost';

GRANT SELECT ON gfsprod.* to 'gfsProdUser'@'localhost';
GRANT INSERT ON gfsprod.* to 'gfsProdUser'@'localhost';
GRANT DELETE ON gfsprod.* to 'gfsProdUser'@'localhost';
GRANT UPDATE ON gfsprod.* to 'gfsProdUser'@'localhost';



GRANT ALL PRIVILEGES ON gfsdev.* TO  'gfsDevUser'@'localhost';
REVOKE ALL PRIVILEGES ON gfsdev.* FROM  'gfsDevUser'@'localhost';