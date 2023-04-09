package org.example.algorithms;

import org.example.classes.Order;
import org.example.classes.Picker;
import org.example.classes.StoreOld;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
This class is no longer in use, it was created as a first approach to solve this problem
(it was tested with min heap (my implementation, deleted in final version) and priority queue).
I was testing what will be better to use with EDF (Earliest Deadline First).
Other algorithms as SJF (Shortest Job Fist), WSJF (Weighted Shortest Job First) or other similar were used as well.

Works only good for small and simple data. It runs fast but can't find optimal solution.
*/
@Deprecated
public class EDF {

    public static void edf(StoreOld store, List<Order> orders) {
        List<Picker> pickers = store.getPickers();

        orders.sort(Comparator.comparing(Order::getCompleteBy));

        PriorityQueue<Picker> pickers_pq = new PriorityQueue<Picker>(pickers.size(), Comparator.naturalOrder());
        pickers_pq.addAll(pickers);

        for (Order order : orders) {
            for (Picker possiblePicker: pickers_pq) {
                if(possiblePicker != null && possiblePicker.canPickOrder(order, store.getPickingEndTime())) {
                    possiblePicker.addOrderToPick(order);
                    pickers_pq.remove(possiblePicker);
                    pickers_pq.add(possiblePicker);
                    break;
                }
            }

        }

        for (int i = 0; i < store.getPickers().size(); i++) {
            System.out.println("Picker " + store.getPickers().get(i) + " orders:");
            for (Order order : store.getPickers().get(i).getOrdersToPick()) {
                System.out.println("Order " + order.getOrderId() + ", value: " + order.getOrderValue() + ", pt: " + order.getPickingTime() + ", completeBy: " + order.getCompleteBy());
            }
        }
    }
}
