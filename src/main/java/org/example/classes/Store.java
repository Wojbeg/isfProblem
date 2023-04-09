package org.example.classes;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.List;

public class Store {
    private List<String> pickers;
    private int pickingStartTime;
    private int pickingEndTime;

    @JsonCreator
    public Store(@JsonProperty("pickers") List<String> pickers, @JsonProperty("pickingStartTime")  LocalTime pickingStartTime, @JsonProperty("pickingEndTime")  LocalTime pickingEndTime) {
        this.pickers = pickers;
        this.pickingStartTime = pickingStartTime.toSecondOfDay();
        this.pickingEndTime = pickingEndTime.toSecondOfDay();
    }

    public List<String> getPickers() {
        return pickers;
    }

    public void setPickers(List<String> pickers) {
        this.pickers = pickers;
    }

    public int getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(int pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public int getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickingEndTime(int pickingEndTime) {
        this.pickingEndTime = pickingEndTime;
    }
}