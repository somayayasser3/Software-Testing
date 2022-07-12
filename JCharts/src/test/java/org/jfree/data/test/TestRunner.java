package org.jfree.data.test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(RangeTest.class);
        if (!result.wasSuccessful())
        {
            System.out.println("Failed Tests: "+result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        else{
            System.out.println(result.wasSuccessful());
        }

    }
}
