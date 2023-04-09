package org.example.classes;

import java.util.List;
import java.util.Map;

public class Solution {

    private final Map<String, List<Order>> pickerToOrders;
    private final double value;
    private int pickingStartTime;

    public Solution(Map<String, List<Order>> pickerAndOrders, double value, int pickingStartTime) {
        this.pickerToOrders = pickerAndOrders;
        this.value = value;
        this.pickingStartTime = pickingStartTime;
    }

    public Map<String, List<Order>> getPickerToOrders() {
        return pickerToOrders;
    }

    public double getValue() {
        return value;
    }

    /*
    Printing solution with given order:
    picker-id order-id picking-start-time
    */
    public void printSolution() {
        for(Map.Entry<String, List<Order>> entry : pickerToOrders.entrySet()) {
            String picker = entry.getKey();
            List<Order> orders = entry.getValue();

            int pickerStartTime = pickingStartTime;

            for (Order order: orders) {
                int minutes = (pickerStartTime % 3600) / 60;
                int hours = pickerStartTime / 3600;
                System.out.println(picker + " " + order.getOrderId() + " " + String.format("%02d:%02d", hours, minutes));
                pickerStartTime += order.getPickingTime();
            }
        }

//        System.out.println("Total cost is: " + value);
    }
}
