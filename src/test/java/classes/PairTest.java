package classes;

import org.example.classes.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTest {

    @Test
    public void testConstructorAndGetters() {
        Pair<String, Integer> pair = new Pair<>("foo", 42);
        assertEquals("foo", pair.l);
        assertEquals(Integer.valueOf(42), pair.r);
    }

    @Test
    public void testToString() {
        Pair<String, Integer> pair = new Pair<>("foo", 42);
        String expected = "Pair{l=foo, r=42}";
        assertEquals(expected, pair.toString());
    }
}