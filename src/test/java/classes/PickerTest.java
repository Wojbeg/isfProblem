package classes;

import org.example.classes.Order;
import org.example.classes.Picker;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PickerTest {

    @Test
    public void testAddOrderToPick() {
        Picker picker = new Picker("1", 0);
        Order order1 = new Order("1", BigDecimal.valueOf(10), Duration.ofSeconds(5), LocalTime.of(12, 30));
        Order order2 = new Order("2", BigDecimal.valueOf(20), Duration.ofSeconds(10), LocalTime.of(13, 0));
        picker.addOrderToPick(order1);
        picker.addOrderToPick(order2);
        assertEquals(2, picker.getOrdersToPick().size());
    }

    @Test
    public void testCanPickOrderReturnsTrue() {
        Picker picker = new Picker("1", 0);
        Order order1 = new Order("1", BigDecimal.valueOf(10), Duration.ofSeconds(5), LocalTime.of(12, 30));
        assertTrue(picker.canPickOrder(order1, LocalTime.of(13, 0).toSecondOfDay()));
    }

    @Test
    public void testGetTotalValue() {
        Picker picker = new Picker("1", 0);
        Order order1 = new Order("1", BigDecimal.valueOf(10), Duration.ofSeconds(5), LocalTime.of(12, 30));
        Order order2 = new Order("2", BigDecimal.valueOf(20), Duration.ofSeconds(10), LocalTime.of(13, 0));
        picker.addOrderToPick(order1);
        picker.addOrderToPick(order2);
        assertEquals(30.0, picker.getTotalValue(), 0.0);
    }

    @Test
    public void testCompareTo() {
        Picker picker1 = new Picker("1", 0);
        Picker picker2 = new Picker("2", 0);
        Picker picker3 = new Picker("3", 0);
        picker1.addOrderToPick(new Order("1", BigDecimal.valueOf(10), Duration.ofSeconds(5), LocalTime.of(12, 30)));
        picker2.addOrderToPick(new Order("2", BigDecimal.valueOf(20), Duration.ofSeconds(10), LocalTime.of(13, 0)));
        picker3.addOrderToPick(new Order("3", BigDecimal.valueOf(15), Duration.ofSeconds(7), LocalTime.of(12, 45)));
        assertTrue(picker1.compareTo(picker2) < 0);
        assertTrue(picker2.compareTo(picker3) > 0);
        assertTrue(picker1.compareTo(picker3) < 0);
    }
}