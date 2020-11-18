FROM java:8
COPY . /usr/src/app
WORKDIR /usr/src/app
COPY Projekt-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/src/app
CMD ["java","-jar", "Projekt-1.0-SNAPSHOT-jar-with-dependencies.jar"]
