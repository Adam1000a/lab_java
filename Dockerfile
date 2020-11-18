FROM mysql:5.7
ADD script.sql /docker-entrypoint-initdb.d

