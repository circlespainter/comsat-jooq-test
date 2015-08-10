/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foo.quasar.test;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;

/**
 *
 * @author cartman
 */
public interface MyService {
    
    @Suspendable
    public String jooqTransactionalMethod(String bar) throws InterruptedException, SuspendExecution;
    
    @Suspendable
    public String jdbcTransactionalMethod(String bar) throws InterruptedException, SuspendExecution;
    
}
