package com.tsl.elevator.models.liftcall;

import com.google.gson.annotations.SerializedName;

public class ElevatorCallResponse {

    @SerializedName("data")
    private ElevatorCallData elevatorCallData;

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("callType")
    private String callType;

    @SerializedName("buildingId")
    private String buildingId;

    public ElevatorCallData getElevatorCallData() {
        return elevatorCallData;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCallType() {
        return callType;
    }

    public String getBuildingId() {
        return buildingId;
    }
}