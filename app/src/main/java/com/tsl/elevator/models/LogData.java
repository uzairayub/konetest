package com.tsl.elevator.models;

public class LogData {
    private String logMessage;
    private int color;

    public LogData(String logMessage, int color) {
        this.logMessage = logMessage;
        this.color = color;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public int getColor() {
        return color;
    }
}
