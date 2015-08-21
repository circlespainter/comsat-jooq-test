package foo.quasar.test;

import co.paralleluniverse.fibers.instrument.MethodDatabase;

/**
 * Created by fabio on 21/08/15.
 */
public class SuspendableClassifier implements co.paralleluniverse.fibers.instrument.SuspendableClassifier {
	@Override
	public MethodDatabase.SuspendableType isSuspendable(MethodDatabase db, String sourceName, String sourceDebugInfo, boolean isInterface, String className, String superClassName, String[] interfaces, String methodName, String methodDesc, String methodSignature, String[] methodExceptions) {
		if (className.contains("foo/quasar/test/MyNonInterfaceService$$EnhancerBySpringCGLIB$$"))
			return MethodDatabase.SuspendableType.SUSPENDABLE;
		return null;
	}
}
