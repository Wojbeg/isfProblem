package org.example.classes;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.List;

/*
Used in old implementation, inside edf and brute force
*/
@Deprecated
public class StoreOld {
    private List<Picker> pickers;
    private int pickingStartTime;
    private int pickingEndTime;

    @JsonCreator
    public StoreOld(@JsonProperty("pickers") List<String> pickers, @JsonProperty("pickingStartTime")  LocalTime pickingStartTime, @JsonProperty("pickingEndTime")  LocalTime pickingEndTime) {
        this.pickers = generatePickers(pickers, pickingStartTime.toSecondOfDay());
        this.pickingStartTime = pickingStartTime.toSecondOfDay();
        this.pickingEndTime = pickingEndTime.toSecondOfDay();
    }

    private List<Picker> generatePickers(List<String> pickersNames, int startTime) {
        return pickersNames.stream().map(name -> new Picker(name, startTime)).toList();
    }

    public List<Picker> getPickers() {
        return pickers;
    }

    public void setPickers(List<Picker> pickers) {
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