package pl.lodz.p.edu.adapter.repository.clients;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;

@ApplicationScoped
@DataSourceDefinition(
        name = "java:global/PgDataSource",
        className = "org.postgresql.ds.PGSimpleDataSource",
        url = "jdbc:postgresql://db:5432/nbddb",
        user = "nbd",
        password = "nbdpassword",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED
)
public class DataSourceConfig {
}
