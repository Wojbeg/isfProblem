package org.example;

import org.example.algorithms.GeneticAlgorithm;
import org.example.classes.Order;
import org.example.classes.Store;

import java.util.List;

import static org.example.utils.DataLoader.readOrders;
import static org.example.utils.DataLoader.readStoreConfig;

public class Main {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java ISFOrderScheduler store.json orders.json");
            System.exit(1);
        }

        String storeFilePath = args[0];
        String ordersFilePath = args[1];

        Store store = readStoreConfig(storeFilePath);
        List<Order> orders = readOrders(ordersFilePath);

//        bruteForce(orders, store);

//        FulfillmentScheduler fs = new FulfillmentScheduler(
//                orders,
//                store.getPickers(),
//                store.getPickingStartTime(),
//                store.getPickingEndTime(),
//                (Order o) -> 1.0
//        );
//
//        FulfillmentScheduler fs = new FulfillmentScheduler(
//                orders,
//                store.getPickers(),
//                store.getPickingStartTime(),
//                store.getPickingEndTime(),
//                Order::getOrderValue
//        );
//
//        Solution a =  fs.scheduleSorted();
//
//        a.printSolution();
//
//        System.out.println();
//        System.out.println();
//        System.out.println();

        GeneticAlgorithm ga = new GeneticAlgorithm(
                orders,
                store.getPickers(),
                store.getPickingStartTime(),
                store.getPickingEndTime(),
                (Order o) -> 1.0
        );

//        GeneticAlgorithm ga = new GeneticAlgorithm(
//                orders,
//                store.getPickers(),
//                store.getPickingStartTime(),
//                store.getPickingEndTime(),
//                Order::getOrderValue
//        );
        ga.run();
    }

}