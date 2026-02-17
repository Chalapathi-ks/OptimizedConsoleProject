package core;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer{

    @Override
    public synchronized void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Retry disabled: no in-run retries
    }
}
