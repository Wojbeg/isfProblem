package classes;

import org.example.classes.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void testGetters() {
        String orderId = "123";
        BigDecimal orderValue = new BigDecimal("100.00");
        Duration pickingTime = Duration.ofMinutes(30);
        LocalTime completeBy = LocalTime.of(18, 0);

        Order order = new Order(orderId, orderValue, pickingTime, completeBy);

        assertEquals(orderId, order.getOrderId());
        assertEquals(orderValue.doubleValue(), order.getOrderValue(), 0.001);
        assertEquals(pickingTime.getSeconds(), order.getPickingTime());
        assertEquals(completeBy.toSecondOfDay(), order.getCompleteBy());
    }

    @Test
    public void testToString() {
        String orderId = "123";
        BigDecimal orderValue = new BigDecimal("100.00");
        Duration pickingTime = Duration.ofMinutes(30);
        LocalTime completeBy = LocalTime.of(18, 0);

        Order order = new Order(orderId, orderValue, pickingTime, completeBy);

        String expectedString = "Order{orderId='123', orderValue=100.0, pickingTime=1800, completeBy=64800}";
        assertEquals(expectedString, order.toString());
    }
}