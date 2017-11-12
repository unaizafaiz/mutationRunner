package sudoku;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException {
        Result result;
        if(args.length == 0) {
            result = JUnitCore.runClasses(BoxCheckerTest.class, CheckerTest.class, ColumnCheckerTest.class, RowCheckerTest.class);
        } else {
            Class<?> runClass = Class.forName(args[0]);
            result = JUnitCore.runClasses(runClass);
        }


        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}