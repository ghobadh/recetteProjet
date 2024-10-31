##-- docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
##-- connect to mysql using root user

CREATE DATABASE gfsdev;
CREATE DATABASE gfsprod;

CREATE USER 'gfsDevUser'@'localhost' IDENTIFIED BY 'gforce';
CREATE USER 'gfsProdUser'@'localhost' IDENTIFIED BY 'gforce';

CREATE USER 'gfsDevUser'@'%' identified by 'gforce';
CREATE USER 'gfsProdUser'@'%' identified by 'gforce';


GRANT SELECT ON gfsdev.* to 'gfsDevUser'@'%';
GRANT INSERT ON gfsdev.* to 'gfsDevUser'@'%';
GRANT DELETE ON gfsdev.* to 'gfsDevUser'@'%';
GRANT UPDATE ON gfsdev.* to 'gfsDevUser'@'%';

GRANT SELECT ON gfsprod.* to 'gfsProdUser'@'%';
GRANT INSERT ON gfsprod.* to 'gfsProdUser'@'%';
GRANT DELETE ON gfsprod.* to 'gfsProdUser'@'%';
GRANT UPDATE ON gfsprod.* to 'gfsProdUser'@'%';



GRANT ALL PRIVILEGES ON gfsdev.* TO  'gfsDevUser'@'localhost';
REVOKE ALL PRIVILEGES ON gfsdev.* FROM  'gfsDevUser'@'localhost';