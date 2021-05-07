test:
	mvn clean test

run:
	mvn spring-boot:run

compile:
	mvn clean install -DskipTests

debug:
	mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

compose-up:
	docker-compose -f -d docker-compose.yml up --build

compose-restart:
	docker-compose down; docker-compose -f docker-compose.yml up --build -d

compose-down:
	docker-compose -f docker-compose.yml down

start: compile compose-down compose-up
