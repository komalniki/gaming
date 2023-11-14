docker network create game-mysql

docker container run --name mysqldb --network game-mysql -e MYSQL_ROOT_PASSWORD=root -p 3307:3306 -e MYSQL_DATABASE=game -d mysql:8

docker image build -t game-jdbc .

docker container run --network game-mysql --name game-jdbc-container -p 8087:8080 -d game-jdbc