package com.tsl.elevator.models.liftcall;

import com.google.gson.annotations.SerializedName;

public class ElevatorCallData {

    @SerializedName("success")
    private boolean success;

    @SerializedName("session_id")
    private int sessionId;

    @SerializedName("request_id")
    private int requestId;

    @SerializedName("error")
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getError() {
        return error;
    }
}