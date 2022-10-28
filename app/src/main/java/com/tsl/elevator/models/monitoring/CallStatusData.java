package com.tsl.elevator.models.monitoring;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CallStatusData {

    @SerializedName("allocated_lift_deck")
    private List<Integer> allocatedLiftDeck;

    @SerializedName("cancel_reason")
    private String cancelReason;

    @SerializedName("eta")
    private String eta;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("subgroup_id")
    private int subgroupId;

    @SerializedName("modified_destination")
    private int modifiedDestination;

    @SerializedName("time")
    private String time;

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public List<Integer> getAllocatedLiftDeck() {
        return allocatedLiftDeck;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getEta() {
        return eta;
    }

    public String getUserId() {
        return userId;
    }

    public int getSubgroupId() {
        return subgroupId;
    }

    public int getModifiedDestination() {
        return modifiedDestination;
    }

    public String getTime() {
        return time;
    }
}