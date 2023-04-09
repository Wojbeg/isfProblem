package utils;

import org.example.utils.Function;
import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

public class FunctionTest {

    @Test
    public void testApply() {

        Function<String, Integer> lengthFunction = String::length;

        assertEquals(lengthFunction.apply("hello"), valueOf(5));
        assertEquals(lengthFunction.apply(""), valueOf(0));
        assertEquals(lengthFunction.apply("12345"), valueOf(5));
    }

    @Test
    public void testOrderCount() {

        Function<String, Integer> lengthFunction = String::length;

        assertEquals(lengthFunction.apply("hello"), valueOf(5));
        assertEquals(lengthFunction.apply(""), valueOf(0));
        assertEquals(lengthFunction.apply("12345"), valueOf(5));
    }

}
