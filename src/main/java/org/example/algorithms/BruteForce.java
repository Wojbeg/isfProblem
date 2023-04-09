package org.example.algorithms;

import org.example.classes.Order;
import org.example.classes.Picker;
import org.example.classes.StoreOld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
This class was made for testing only.
Brute force was a method to verify that my results matched the real ones.
Works well (finds even advanced combinations in a short time) for small amounts of data,
however should not be considered as good solution for bigger data
*/
public class BruteForce {

    public static <T> List<List<T>> generatePermutations(List<T> list) {
        List<List<T>> permutations = new ArrayList<>();
        generatePermutationsHelper(list, 0, permutations);
        return permutations;
    }

    public static <T> void generatePermutationsHelper(List<T> list, int start, List<List<T>> permutations) {
        if (start == list.size()) {
            permutations.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < list.size(); i++) {
                Collections.swap(list, start, i);
                generatePermutationsHelper(list, start + 1, permutations);
                Collections.swap(list, start, i);
            }
        }
    }

    public static void bruteForce(List<Order> orders, StoreOld store) {
        List<List<Order>> permutations = generatePermutations(orders);

        int maxCount = 0;
        List<Picker> bestPickers = null;

        for(List<Order> permutation: permutations) {
            int totalCount = 0;

            List<Picker> tempPickers = new ArrayList<>(store.getPickers().size());
            store.getPickers().stream().forEach(picker -> {
                tempPickers.add(picker.copy());
            });

            for(Order order: permutation) {

                for(Picker picker: tempPickers) {
                    if(picker.canPickOrder(order, store.getPickingEndTime())) {
                        picker.addOrderToPick(order);

                        totalCount++;
                        break;
                    }
                }

            }


            if(totalCount > maxCount) {
                maxCount = totalCount;
                bestPickers = tempPickers;
            }

        }

        if (bestPickers != null) {
            System.out.println("Best permutation with total value " + maxCount + ":");
            for (Picker picker : bestPickers) {
                System.out.println(picker.getOrdersToPick());
            }
        } else {
            System.out.println("No permutation found.");
        }

    }
}
