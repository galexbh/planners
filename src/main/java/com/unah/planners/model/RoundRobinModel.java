package com.unah.planners.model;

import javafx.beans.property.IntegerProperty;

public class RoundRobinModel {

    private int processID;
    private int burstTime;
    private int turnaroundTime;
    private int waitingTime;

    public RoundRobinModel(int processID, int burstTime, int turnaroundTime, int waitingTime) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getProcessID() {
        return processID;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
