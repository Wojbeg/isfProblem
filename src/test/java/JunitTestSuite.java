import algorithms.BruteForceTest;
import algorithms.FulfillmentSchedulerTest;
import classes.OrderTest;
import classes.PairTest;
import classes.PickerTest;
import classes.StoreTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.BinaryHeapTest;
import utils.FunctionTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BinaryHeapTest.class,
        FunctionTest.class,
        OrderTest.class,
        PairTest.class,
        PickerTest.class,
        StoreTest.class,
        BruteForceTest.class,
        FulfillmentSchedulerTest.class,
})

public class JunitTestSuite {
}  