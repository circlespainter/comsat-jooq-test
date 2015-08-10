package foo.quasar.test;

import co.paralleluniverse.fibers.jdbc.FiberDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    DataSource dataSource() {

        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");

        config.setMinimumIdle(1);
        config.setMaximumPoolSize(50);
        config.setIdleTimeout(60000);
        config.setConnectionTimeout(5000);
        config.setMaxLifetime(120000);
        config.setAutoCommit(false);
        config.setConnectionTestQuery("SELECT 1");

        HikariDataSource ds = new HikariDataSource(config);
        
        return new TransactionAwareDataSourceProxy(FiberDataSource.wrap(ds, 50));
        
        
    }

   

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(dataSource());
    }

    @Bean
    DSLContext dsl(org.jooq.Configuration dslConfig) {
        return new DefaultDSLContext(dslConfig);
    }

    @Bean
    ExceptionTranslator exceptionTranslator() {
        return new ExceptionTranslator();
    }

    @Bean
    org.jooq.Configuration dslConfig(ConnectionProvider connectionProvider, ExceptionTranslator exceptionTranslator) {

        DefaultConfiguration ret = new DefaultConfiguration();
        ret.setConnectionProvider(connectionProvider);
        ret.setExecuteListenerProvider(new DefaultExecuteListenerProvider(exceptionTranslator));
        ret.setSQLDialect(SQLDialect.H2);

        return ret;
    }
}
