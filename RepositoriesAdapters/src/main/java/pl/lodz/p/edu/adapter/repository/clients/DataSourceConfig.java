package pl.lodz.p.edu.adapter.repository.clients;

import jakarta.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(name = "java:global/PgDataSource", className = "org.postgresql.ds.PGSimpleDataSource",
url = "jdbc:postgresql://localhost:5432/nbddb", user = "nbd", password = "nbdpassword")
public class DataSourceConfig {
}
