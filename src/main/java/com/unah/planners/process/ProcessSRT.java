package com.unah.planners.process;

public class ProcessSRT {
    private String processIdentifier;
    private int arrivalTime;
    private int serviceTime;

    private int positionInTable;

    public int getPosition() {
        return positionInTable;
    }

    public void setPosition(int position) {
        this.positionInTable = position;
    }


    public String getProcessIdentifier() {
        return processIdentifier;
    }

    public void setProcessIdentifier(String processIdentifier) {
        this.processIdentifier = processIdentifier;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }


}
