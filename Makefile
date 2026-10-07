PGDATA = C:/Users/BRT.AADRA/scoop/apps/postgresql/current/data

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
