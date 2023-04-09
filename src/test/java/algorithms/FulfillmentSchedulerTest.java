package algorithms;

import org.example.algorithms.FulfillmentScheduler;
import org.example.classes.Order;
import org.example.classes.Solution;
import org.example.utils.Function;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FulfillmentSchedulerTest {

    private List<Order> createOrders() {

        final Order order1 = new Order("order1", new BigDecimal("10"), Duration.ofSeconds(20), LocalTime.of(10, 0));
        final Order order2 = new Order("order2", new BigDecimal("20"), Duration.ofSeconds(40), LocalTime.of(10, 30));
        final Order order3 = new Order("order3", new BigDecimal("30"), Duration.ofSeconds(60), LocalTime.of(11, 0));
        final Order order4 = new Order("order4", new BigDecimal("40"), Duration.ofSeconds(80), LocalTime.of(12, 0));
        final Order order5 = new Order("order5", new BigDecimal("50"), Duration.ofSeconds(100), LocalTime.of(12, 30));
        final Order order6 = new Order("order6", new BigDecimal("60"), Duration.ofSeconds(120), LocalTime.of(13, 0));


        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5, order6);

        return orders;
    }

    private final int pickingStartTime = 9 * 3600;
    private final int pickingEndTime = 14 * 3600;

    private List<String> createPickers() {
        return Arrays.asList("picker1", "picker2", "picker3");
    }

    private final Function<Order, Double> costFunction = Order::getOrderValue;

    @Test
    public void testScheduleSorted() {
        List<Order> orders = createOrders();
        FulfillmentScheduler scheduler = new FulfillmentScheduler(orders, createPickers(), pickingStartTime, pickingEndTime, costFunction);
        Solution solution = scheduler.scheduleSorted();

        Map<String, List<Order>> pickerToOrders = solution.getPickerToOrders();
        for (Order order : orders) {
            boolean found = false;
            for (List<Order> pickerOrders : pickerToOrders.values()) {
                if (pickerOrders.contains(order)) {
                    found = true;
                    break;
                }
            }
            assertEquals(true, found);
        }

        assertEquals(210.0, solution.getValue(), 0.01);
    }


    @Test
    public void testScheduleNotSorted() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("A", new BigDecimal("10"), Duration.ofSeconds(60), LocalTime.of(10, 0)));
        orders.add(new Order("B", new BigDecimal("5"), Duration.ofSeconds(30), LocalTime.of(11, 0)));
        orders.add(new Order("C", new BigDecimal("7"), Duration.ofSeconds(45), LocalTime.of(12, 0)));

        List<String> pickers = new ArrayList<>();
        pickers.add("Alice");
        pickers.add("Bob");

        FulfillmentScheduler scheduler = new FulfillmentScheduler(
                orders,
                pickers,
                9 * 60 * 60, // 9:00
                13 * 60 * 60, // 13:00
                order -> order.getOrderValue() // Cost function is order value
        );

        Solution solution = scheduler.scheduleNotSorted();

        assertEquals(2, solution.getPickerToOrders().size());
        assertEquals(1, solution.getPickerToOrders().get("Alice").size());
        assertEquals(2, solution.getPickerToOrders().get("Bob").size());
    }

    @Test
    public void testSorted() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("A", new BigDecimal("10"), Duration.ofSeconds(60), LocalTime.of(10, 0)));
        orders.add(new Order("B", new BigDecimal("5"), Duration.ofSeconds(30), LocalTime.of(11, 0)));
        orders.add(new Order("C", new BigDecimal("7"), Duration.ofSeconds(45), LocalTime.of(12, 0)));

        List<String> pickers = new ArrayList<>();
        pickers.add("Alice");
        pickers.add("Bob");

        FulfillmentScheduler scheduler = new FulfillmentScheduler(
                orders,
                pickers,
                9 * 60 * 60, // 9:00
                13 * 60 * 60, // 13:00
                order -> order.getOrderValue() // Cost function is order value
        );

        Solution solution = scheduler.scheduleSorted();

        assertEquals(2, solution.getPickerToOrders().size());
        assertEquals(1, solution.getPickerToOrders().get("Alice").size());
        assertEquals(2, solution.getPickerToOrders().get("Bob").size());
    }
}