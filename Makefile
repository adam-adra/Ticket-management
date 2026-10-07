PGDATA = C:/Users/BRT.AADRA/scoop/apps/postgresql/current/data
ENDPOINT = http://localhost:8000/api/tickets
HEADER = "Content-Type: application/json"
DATA = '{"title": "Fix the backend", "description": ".env", "priority": "HIGH"}'

.PHONY: db-start db-status db-stop test

db-start:
	pg_ctl -D "$(PGDATA)" status || pg_ctl -D "$(PGDATA)" start

db-status:
	pg_ctl -D "$(PGDATA)" status

db-stop:
	pg_ctl -D "$(PGDATA)" stop

test: db-start
	mvnw.cmd test

db-repl:
	psql -U postgres -d ticketdb

run: db-start
	mvnw.cmd spring-boot:run

post:
	curl -X POST $(ENDPOINT) -H $(HEADER) -d $(DATA)

get:
	curl $(ENDPOINT)

delete:
	curl -X DELETE http://localhost:8000/api/tickets/1 -i
