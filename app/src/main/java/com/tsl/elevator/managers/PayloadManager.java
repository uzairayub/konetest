package com.tsl.elevator.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tsl.elevator.adapters.KoneCallType;

public class PayloadManager {
    private JsonObject liftCallPayload;
    private JsonObject siteMonitoringPayload;
    private JsonObject buildingConfigPayload;

    private PayloadManager() {
    }

    public static PayloadManager getInstance() {
        return PayloadManager.SingletonContainer.instance;
    }

    private static class SingletonContainer {
        private static final PayloadManager instance = new PayloadManager();
    }


    public JsonObject getLiftCallPayload(String buildingId, String groupId, int requestId, int sourceFloorId, String time, int terminal, int action, int destinationFloorId) {
        if (liftCallPayload == null)
            liftCallPayload = new JsonObject();
        liftCallPayload.addProperty("type", "lift-call-api-v2");
        liftCallPayload.addProperty("buildingId", buildingId);
        liftCallPayload.addProperty("callType", "action");
        liftCallPayload.addProperty("groupId", groupId);

        JsonObject payload = new JsonObject();
        payload.addProperty("request_id", requestId);
        payload.addProperty("area", sourceFloorId);
        payload.addProperty("time", time);
        payload.addProperty("terminal", terminal);

        JsonObject call = new JsonObject();
        call.addProperty("action", action);
        call.addProperty("destination", destinationFloorId);

//        add call object to payload.
        payload.add("call", call);
//        add payload object to lift call pay load.
        liftCallPayload.add("payload", payload);

        return liftCallPayload;
    }

    public JsonObject getBuildingConfigPayLoad(String requestId, String buildingId, String groupId) {
        CommonCommandsDataManager.getInstance().setRecentConfigRequestId(requestId);
        if (buildingConfigPayload == null)
            buildingConfigPayload = new JsonObject();
        buildingConfigPayload.addProperty("type", "common-api");
        buildingConfigPayload.addProperty("requestId", requestId);
        buildingConfigPayload.addProperty("buildingId", buildingId);
        buildingConfigPayload.addProperty("callType", KoneCallType.config.toString());
        buildingConfigPayload.addProperty("groupId", groupId);
        return buildingConfigPayload;
    }

    public JsonObject getBuildingActionsPayLoad(String requestId, String buildingId, String groupId) {
        CommonCommandsDataManager.getInstance().setRecentActionsRequestId(requestId);
        if (buildingConfigPayload == null)
            buildingConfigPayload = new JsonObject();
        buildingConfigPayload.addProperty("type", "common-api");
        buildingConfigPayload.addProperty("requestId", requestId);
        buildingConfigPayload.addProperty("buildingId", buildingId);
        buildingConfigPayload.addProperty("callType", KoneCallType.actions.toString());
        buildingConfigPayload.addProperty("groupId", groupId);
        return buildingConfigPayload;
    }

    public JsonObject getBuildingPingPayLoad(int requestId, String buildingId, String groupId) {
        if (buildingConfigPayload == null)
            buildingConfigPayload = new JsonObject();
        buildingConfigPayload.addProperty("type", "common-api");
        buildingConfigPayload.addProperty("buildingId", buildingId);
        buildingConfigPayload.addProperty("callType", KoneCallType.ping.toString());
        buildingConfigPayload.addProperty("groupId", groupId);

//        remove requestId from parent object
        if (buildingConfigPayload.has("requestId"))
            buildingConfigPayload.remove("requestId");

        JsonObject payload = new JsonObject();
        payload.addProperty("request_id", requestId);

//        add payload object to lift call pay load.
        buildingConfigPayload.add("payload", payload);
        return buildingConfigPayload;
    }

    public JsonObject getSiteMonitoringPayload(String requestId, String buildingId, String groupId, String userSub, String [] subTopics) {
        if (siteMonitoringPayload == null)
            siteMonitoringPayload = new JsonObject();
        siteMonitoringPayload.addProperty("type", "site-monitoring");
        siteMonitoringPayload.addProperty("requestId", requestId);
        siteMonitoringPayload.addProperty("buildingId", buildingId);
        siteMonitoringPayload.addProperty("callType", KoneCallType.monitor.name());
        siteMonitoringPayload.addProperty("groupId", groupId);


        JsonObject payload = new JsonObject();
        payload.addProperty("sub", userSub);
        payload.addProperty("duration", 300);
        JsonArray array = new JsonArray();
        for(String subtopic : subTopics)
        {
            array.add(subtopic);
        }
        payload.add("subtopics", array);

//        add payload object to site monitoring pay load.
        siteMonitoringPayload.add("payload", payload);
        return siteMonitoringPayload;
    }

}
