package foo.quasar.test;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MyServiceImpl implements MyService {

    @Autowired
    DSLContext dsl;

    @Autowired
    TransactionAwareDataSourceProxy dataSource;

    @Override
    @Suspendable
    @Transactional
    @PreAuthorize("hasPermission(#bar, 'aasdad')")
    public String jooqTransactionalMethod(String bar) throws InterruptedException, SuspendExecution {

        int a = dsl.fetchOne("select 1").into(Integer.class);

        System.out.println("!!!! !!!! !!!! !!!! JOOQ result is: " + a);

        Fiber.sleep(1000);
        return "After sleep on " + Fiber.currentFiber().getName();

    }

    @Override
    @Suspendable
    @Transactional
    @PreAuthorize("hasPermission(#bar, 'aasdad')")
    public String jdbcTransactionalMethod(String bar) throws InterruptedException, SuspendExecution {

        try {
            Connection conn = dataSource.getConnection();

            PreparedStatement stmt = conn.prepareStatement("select 1");

            ResultSet rs = stmt.executeQuery();

            rs.next();

            int a = rs.getInt(1);

            System.out.println("!!!! !!!! !!!! !!!! JDBC result is: " + a);
        } catch (Exception e) {
            throw new RuntimeException("Error querying databse with jdbc");
        }

        Fiber.sleep(1000);
        return "After sleep on " + Fiber.currentFiber().getName();

    }

}
