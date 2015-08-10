package foo.quasar.test;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyNonInterfaceService {

    @Suspendable
    @PreAuthorize("hasPermission(#bar, 'aasdad')")
    public String foo(String bar)  {

        try {
            Fiber.sleep(1000);
        } catch (SuspendExecution|InterruptedException e) {
            throw new AssertionError("should not happen");
        }
        
        
        
        return "After sleep on " + Fiber.currentFiber().getName();

    }
}
