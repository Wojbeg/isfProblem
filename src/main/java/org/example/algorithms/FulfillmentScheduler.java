package org.example.algorithms;

import org.example.classes.Order;
import org.example.classes.Solution;
import org.example.utils.Function;

import java.util.*;

/*
    Works similar to earliest deadline first, schedule our time
*/
public class FulfillmentScheduler {

    private final List<Order> orders;
    private final List<String> pickers;
    private final int pickingStartTime;
    private final int pickingEndTime;
    private final Map<String, List<Order>> pickerToOrders;
    private final Map<String, Double> pickerToValue;
    private final Map<String, Integer> pickerToTime;
    private final Function<Order, Double> costFunction;

    public FulfillmentScheduler(List<Order> orders, List<String> pickers, int pickingStartTime, int pickingEndTime, Function<Order, Double> costFunction) {
        this.orders = orders;
        this.pickers = pickers;
        this.pickingStartTime = pickingStartTime;
        this.pickingEndTime = pickingEndTime;
        this.pickerToOrders = new HashMap<>();
        this.pickerToValue = new HashMap<>();
        this.pickerToTime = new HashMap<>();
        this.costFunction = costFunction;
    }

    public Solution scheduleSorted() {
        schedule(true);

        double sum = 0.0;
        for (double value : pickerToValue.values()) {
            sum += value;
        }
        return new Solution(pickerToOrders, sum, pickingStartTime);
    }

    public Solution scheduleNotSorted() {
        schedule(false);

        double sum = 0.0;
        for (double value : pickerToValue.values()) {
            sum += value;
        }
        return new Solution(pickerToOrders, sum, pickingStartTime);
    }

    public Map<String, List<Order>> schedule(boolean shouldSort) {
        // Sort orders by completeBy time, cost function and picking time
        if(shouldSort) {
            Comparator<Order> completeByThenCostComparator = Comparator
                    .comparing(Order::getCompleteBy)
                    .thenComparing((o1, o2) -> costFunction.apply(o1).compareTo(costFunction.apply(o2)))
                    .thenComparing(Order::getPickingTime);

            orders.sort(completeByThenCostComparator);
        }
        // Assign orders to pickers
        for (Order order : orders) {
            String assignedPicker = assignPicker(order);
            if (assignedPicker != null) {
                if (!pickerToOrders.containsKey(assignedPicker)) {
                    pickerToOrders.put(assignedPicker, new ArrayList<>());
                }
                pickerToOrders.get(assignedPicker).add(order);

                if (!pickerToValue.containsKey(assignedPicker)) {
                    pickerToValue.put(assignedPicker, 0.0);
                }
                double cost = costFunction.apply(order);
                pickerToValue.put(assignedPicker, pickerToValue.get(assignedPicker) + cost);

                if(!pickerToTime.containsKey(assignedPicker)) {
                    pickerToTime.put(assignedPicker, pickingStartTime);
                }

                pickerToTime.put(assignedPicker, pickerToTime.get(assignedPicker) + order.getPickingTime());
            }
        }
        return pickerToOrders;
    }

    private String assignPicker(Order order) {
        String bestPicker = null;
        int shortestTime = Integer.MAX_VALUE;

        for (String picker : pickers) {
            if (!pickerToOrders.containsKey(picker)) {
                // Picker has not been assigned any orders yet
                int endTime = pickingStartTime + order.getPickingTime();

                if ((endTime <= order.getCompleteBy())
                        && (endTime <= pickingEndTime)
                ) {
                    // Picker can complete the order before the deadline
                    return picker;
                }
            } else {
                // Picker has already been assigned orders
                int time = pickerToTime.get(picker);
                time += order.getPickingTime();

                if ( (time <= order.getCompleteBy())
                        && (time <= pickingEndTime)
                        && (shortestTime > (time - pickingStartTime))
                        ) {
                    // Picker can complete the order before the deadline and is the best option so far
                    bestPicker = picker;
                    shortestTime = time - pickingStartTime;
                }
            }
        }
        return bestPicker;
    }
}