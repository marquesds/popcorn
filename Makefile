createdb:
	docker exec -i popcorn_db_1 mysql -u root -pabc123 < src/main/resources/db/migration/1593371742_create_database.sql

filldb:
	docker exec -i popcorn_db_1 mysql -u root -pabc123 < src/main/resources/db/migration/1593375973_fill_database.sql

testdb:
	docker exec -i popcorn_db_1 mysql -u root -pabc123 < src/main/resources/db/migration/create_test_database.sql

start:
	docker-compose -f docker-compose.yml up -d

stop:
	docker-compose -f docker-compose.yml down

testall:
	ENVIRONMENT=test mvn test