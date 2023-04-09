package org.example.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class Order {
    private final String orderId;
    private final double orderValue;
    private final int pickingTime;
    private final int completeBy;
    @JsonCreator
    public Order(@JsonProperty("orderId") String orderId,
                 @JsonProperty("orderValue") BigDecimal orderValue,
                 @JsonProperty("pickingTime") Duration pickingTime,
                 @JsonProperty("completeBy") LocalTime completeBy) {
        this.orderId = orderId;
        this.orderValue = orderValue.doubleValue();
        this.pickingTime = (int) pickingTime.getSeconds();
        this.completeBy = completeBy.toSecondOfDay();
    }

    public String getOrderId() {
        return orderId;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public int getPickingTime() {
        return pickingTime;
    }

    public int getCompleteBy() {
        return completeBy;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderValue=" + orderValue +
                ", pickingTime=" + pickingTime +
                ", completeBy=" + completeBy +
                '}';
    }
}