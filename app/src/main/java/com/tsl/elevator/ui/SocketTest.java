package com.tsl.elevator.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tsl.elevator.adapters.KoneCallType;
import com.tsl.elevator.adapters.KoneResAdapters;
import com.tsl.elevator.databinding.ActivitySocketTestBinding;
import com.tsl.elevator.managers.CommonCommandsDataManager;
import com.tsl.elevator.managers.PayloadManager;
import com.tsl.elevator.managers.SocketManager;
import com.tsl.elevator.models.actions.ActionsResponse;
import com.tsl.elevator.models.actions.CallTypesItem;
import com.tsl.elevator.models.config.ConfigResponse;
import com.tsl.elevator.models.config.DestinationsItem;
import com.tsl.elevator.models.config.LiftsItem;
import com.tsl.elevator.models.liftcall.ElevatorCallResponse;
import com.tsl.elevator.models.monitoring.CallStatusResponse;
import com.tsl.elevator.models.resources.GroupsItem;
import com.tsl.elevator.models.resources.ResourcesResponse;
import com.tsl.elevator.network.ApiClient;
import com.tsl.elevator.network.ApiInterface;
import com.tsl.elevator.utils.GsonUtil;
import com.tsl.elevator.utils.SocketUtils;

import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SocketTest extends AppCompatActivity {
    private String TAG = SocketTest.class.getSimpleName();

    private Handler handler = new Handler();
    private String authToken = "", userSub = "";
    private ActivitySocketTestBinding binding;
    private Gson mGson;
    private String messageETA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySocketTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mGson = Objects.requireNonNull(GsonUtil.Companion.ins()).getGson();
        generateToken();
        binding.btnConnectSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketManager.getInstance().closeSocketConnection();
                SocketManager.getInstance().init(authToken, callback);
            }
        });

        binding.btnCallElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callElevatorMessage = mGson.toJson(PayloadManager.getInstance().getLiftCallPayload(SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false), SocketUtils.getRequestId(), CommonCommandsDataManager.getInstance().getSourceFloor().getAreaId(), SocketUtils.getTime(), 0, 2, CommonCommandsDataManager.getInstance().getDestinationFloor().getAreaId()));
                SocketManager.getInstance().sendNewMessage(callElevatorMessage);
                appendText(callElevatorMessage, Color.parseColor("#000099"));
            }
        });

        binding.btnClearLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvLogs.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            SocketManager.getInstance().sendPingMessage();
//            handler.postDelayed(runnable, 6 * 1000);
        }
    };

    private void generateToken() {

        Call<JsonObject> call = ApiClient.getAuthTokenClient().create(ApiInterface.class).getNewToken("client_credentials", "application/inventory callgiving/group:4TFxWRCv23D:1 callgiving/group:HxKjGc3knnh:1 callgiving/group:HxKjGc3knnh:1 equipmentstatus/*");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                authToken = response.body().get("access_token").getAsString();
                binding.tvToken.setText(authToken);
                getUserInfo();
                getKoneResources();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void getUserInfo() {

        Call<JsonObject> call = ApiClient.getClient().create(ApiInterface.class).getUserInfo("Bearer " + authToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                userSub = response.body().get("sub").getAsString();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void getKoneResources() {

        Call<ResourcesResponse> call = ApiClient.getClient().create(ApiInterface.class).getKoneRes("application/x-www-form-urlencoded", "Bearer " + authToken);
        call.enqueue(new Callback<ResourcesResponse>() {
            @Override
            public void onResponse(Call<ResourcesResponse> call, retrofit2.Response<ResourcesResponse> response) {
                handleResourceResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResourcesResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void handleResourceResponse(ResourcesResponse response) {
        binding.rvResources.setLayoutManager(new LinearLayoutManager(SocketTest.this));
        binding.rvResources.setAdapter(new KoneResAdapters(response.getBuildings(), new KoneResAdapters.KoneResCallback() {
            @Override
            public void onResourceClicked(List<GroupsItem> groupsItemList) {
                Bundle bundle = new Bundle();
                bundle.putString("groupsList", mGson.toJson(groupsItemList, List.class));
                GroupsDialog groupsDialog = new GroupsDialog();
                groupsDialog.setArguments(bundle);
                groupsDialog.show(getSupportFragmentManager(), "GroupsSheet");
            }
        }));

        SocketManager.getInstance().init(authToken, callback);

    }


    private SocketManager.SocketCallback callback = new SocketManager.SocketCallback() {
        @Override
        public void onSocketConnected() {
            appendText("Socket Connected", Color.parseColor("#009900"));
            handler.postDelayed(runnable, 30000);

            if (messageETA != null && !messageETA.isEmpty()) {
                SocketManager.getInstance().sendNewMessage(messageETA);
                appendText(messageETA, Color.parseColor("#000099"));
            }
        }

        @Override
        public void onConnectionFailed() {
            appendText("Connection failed", Color.parseColor("#996400"));
        }

        @Override
        public void onSocketDisconnected() {
            appendText("Socket Disconnected", Color.parseColor("#990000"));
        }

        @Override
        public void onNewMessageArrive(String message) {
            appendText(message, Color.parseColor("#009900"));
            handleMessageReceived(message);
        }
    };

    private void appendText(String message, int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                logsAdapter.addNewLogMessage(new LogData(message, color));
//                logsAdapter.notifyDataSetChanged();
//                binding.rvLogs.scrollToPosition(logsAdapter.getItemCount());
                SocketUtils.appendColoredText(binding.tvLogs, message, color);
//                final int scrollAmount = binding.tvLogs.getLayout().getLineTop((binding.tvLogs.getLineCount()) - (binding.tvLogs.getHeight()));
//                if (scrollAmount > 0)
//                    binding.tvLogs.scrollTo(0, scrollAmount);
//                else
//                    binding.tvLogs.scrollTo(0, 0);
            }
        });
    }

    private void handleMessageReceived(String message) {
        JsonObject object = mGson.fromJson(message, JsonObject.class);
        if (object.has("callType")) {
            if (object.get("callType").getAsString().equalsIgnoreCase(KoneCallType.config.name())) {
                ConfigResponse configResponse = mGson.fromJson(object, ConfigResponse.class);
                CommonCommandsDataManager.getInstance().setConfigData(configResponse.getConfigData());
                updateDestinationUI(configResponse.getConfigData().getDestinations());
            } else if (object.get("callType").getAsString().equalsIgnoreCase(KoneCallType.actions.name())) {
                ActionsResponse actionsResponse = mGson.fromJson(object, ActionsResponse.class);
                CommonCommandsDataManager.getInstance().setActionsData(actionsResponse.getActionsData());
                updateActionsUI(actionsResponse.getActionsData().getCallTypes());
            } else if (object.get("callType").getAsString().equalsIgnoreCase(KoneCallType.action.name())) {
                ElevatorCallResponse elevatorCallResponse = mGson.fromJson(object, ElevatorCallResponse.class);
                handleElevatorCallResponse(elevatorCallResponse);
            } else if (object.get("callType").getAsString().equalsIgnoreCase(KoneCallType.monitor.name())) {
                CallStatusResponse callStatusResponse = mGson.fromJson(object, CallStatusResponse.class);
                handleCallStatusResponse(callStatusResponse);
            }
        }
    }

    private void handleCallStatusResponse(CallStatusResponse response) {
        if (response.getCallStatusData().getAllocatedLiftDeck().size() < 1) {
            Toast.makeText(SocketTest.this, response.getCallStatusData().getError(), Toast.LENGTH_SHORT).show();
            return;
        }

        String liftId = "";
        List<LiftsItem> liftList = CommonCommandsDataManager.getInstance().getConfigData().getGroups().get(0).getLifts();
        for (int i = 0; i < liftList.size(); i++) {
            LiftsItem lift = liftList.get(i);
            for (int j = 0; j < lift.getDecks().size(); j++) {
                if (lift.getDecks().get(j).getAreaId() == response.getCallStatusData().getAllocatedLiftDeck().get(0)) {
                    liftId = "lift_" + lift.getLiftId();
                    break;
                }
            }
        }

        String topicETA = liftId + "/next_stop_eta";
        String topicStopping = liftId + "/stopping";
        String topicDoors = liftId + "/doors";
        String[] subTopicsArray = new String[]{topicDoors, topicETA, topicStopping};
        messageETA = mGson.toJson(PayloadManager.getInstance().getSiteMonitoringPayload(SocketUtils.getRequestId() + "", SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false), userSub, subTopicsArray));
//        String messageStopping = mGson.toJson(PayloadManager.getInstance().getSiteMonitoringPayload(SocketUtils.getUUIDv4(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false), userSub, topicStopping));
//        String messageDoors = mGson.toJson(PayloadManager.getInstance().getSiteMonitoringPayload(SocketUtils.getUUIDv4(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false), userSub, topicDoors));

        SocketManager.getInstance().sendNewMessage(messageETA);
        appendText(messageETA, Color.parseColor("#000099"));

//        SocketManager.getInstance().sendNewMessage(messageStopping);
//        appendText(messageStopping, Color.parseColor("#000099"));

//        SocketManager.getInstance().sendNewMessage(messageDoors);
//        appendText(messageDoors, Color.parseColor("#000099"));

    }

    private void handleElevatorCallResponse(ElevatorCallResponse response) {
        if (!response.getElevatorCallData().isSuccess()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketTest.this, response.getElevatorCallData().getError(), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        String topic = "call_state/" + response.getElevatorCallData().getSessionId() + "/fixed";
        String message = mGson.toJson(PayloadManager.getInstance().getSiteMonitoringPayload(SocketUtils.getUUIDv4(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false), userSub, new String[]{topic}));
        SocketManager.getInstance().sendNewMessage(message);
        appendText(message, Color.parseColor("#000099"));
    }

    private void updateActionsUI(List<CallTypesItem> callTypesList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter actionsAdapter = new ArrayAdapter(SocketTest.this, android.R.layout.simple_list_item_1, callTypesList);
                binding.spnActions.setAdapter(actionsAdapter);
                binding.spnActions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }

    private void updateDestinationUI(List<DestinationsItem> destinations) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter destinationsAdapter = new ArrayAdapter(SocketTest.this, android.R.layout.simple_list_item_1, destinations);
                binding.spnSource.setAdapter(destinationsAdapter);
                binding.spnDestination.setAdapter(destinationsAdapter);
                binding.spnSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CommonCommandsDataManager.getInstance().setSourceFloor(destinations.get(position));
                        Toast.makeText(SocketTest.this, "Source floor set", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                binding.spnDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CommonCommandsDataManager.getInstance().setDestinationFloor(destinations.get(position));
                        Toast.makeText(SocketTest.this, "destination floor set", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    private static final String SHA_256 = "SHA-256";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getCheckSum(String apikey, String apiSecret, String nonce, String curTime) {
        return encode(encode(apikey, apiSecret) + nonce + curTime);
    }

    private static String encode(String apikey, String apiSecret) {
        if (apikey.isEmpty() || apiSecret.isEmpty()) {
            throw new SecurityException("value or salt can not be empty");
        }
        return encode(apikey + apiSecret);
    }

    private static String encode(String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}