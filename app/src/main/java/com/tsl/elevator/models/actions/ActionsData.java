package com.tsl.elevator.models.actions;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ActionsData {

    @SerializedName("version_minor")
    private int versionMinor;

    @SerializedName("call_types")
    private List<CallTypesItem> callTypes;

    @SerializedName("functions")
    private List<Object> functions;

    @SerializedName("version_major")
    private int versionMajor;

    @SerializedName("action_set")
    private List<Object> actionSet;

    public int getVersionMinor() {
        return versionMinor;
    }

    public List<CallTypesItem> getCallTypes() {
        return callTypes;
    }

    public List<Object> getFunctions() {
        return functions;
    }

    public int getVersionMajor() {
        return versionMajor;
    }

    public List<Object> getActionSet() {
        return actionSet;
    }
}