FROM openliberty/open-liberty:full-java17-openj9-ubi

COPY src/main/liberty/config /config/
ADD target/RestControllersAdapters-1.0-SNAPSHOT.war /config/dropins/

RUN configure.sh