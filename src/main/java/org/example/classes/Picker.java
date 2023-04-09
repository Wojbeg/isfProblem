package org.example.classes;

import java.util.ArrayList;
import java.util.List;

public class Picker implements Comparable<Picker>  {
    private String id;
    private List<Order> ordersToPick;

    private final int start;
    private int currentPickTime;

    public Picker(String id, int start) {
        this.id = id;
        this.ordersToPick = new ArrayList<>();
        currentPickTime = start;
        this.start = start;
    }

    public Picker copy() {
        return new Picker(this.id, this.start);
    }

    public String getId() {
        return id;
    }

    public int getCurrentPickTime() {
        return currentPickTime;
    }

    public void setCurrentPickTime(int currentPickTime) {
        this.currentPickTime = currentPickTime;
    }

    private void addToCurrentPickTime(int time) {
        this.currentPickTime += time;
    }

    public List<Order> getOrdersToPick() {
        return ordersToPick;
    }

    public void addOrderToPick(Order order) {
        ordersToPick.add(order);
        addToCurrentPickTime(order.getPickingTime());
    }

    public boolean canPickOrder(Order order, int endTime) {
        int finishTime = getCurrentPickTime() + order.getPickingTime();
        return (finishTime <= order.getCompleteBy()) && (finishTime <= endTime);
    }

    public double getTotalValue() {
        return ordersToPick.stream()
                .mapToDouble(Order::getOrderValue)
                .sum();
    }

    @Override
    public int compareTo(Picker o) {
        return Integer.compare(this.currentPickTime, o.currentPickTime);
    }


}