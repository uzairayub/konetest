package com.tsl.elevator.managers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.tsl.elevator.utils.GsonUtil;
import com.tsl.elevator.utils.SocketUtils;

import java.io.IOException;
import java.net.DatagramSocket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kotlin.collections.CollectionsKt;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketManager extends WebSocketListener {
    private static SocketManager singleton = null;
    private WebSocket mWebSocket;
    private boolean isReconnecting;
    private Gson mGson;
    private SocketCallback callback;

    private SocketManager() {
    }

    public static SocketManager getInstance() {
        if (singleton == null) {
            singleton = new SocketManager();
        }
        return singleton;
    }

    public void init(String authToken, SocketCallback callback) {
        String url = "wss://dev.kone.com/stream-v2?accessToken=" + authToken;
        this.callback = callback;
        mGson = Objects.requireNonNull(GsonUtil.Companion.ins()).getGson();
        OkHttpClient socketOkHttpClient = new OkHttpClient.Builder().protocols(Collections.singletonList(Protocol.HTTP_1_1)).build();
        Request request = new Request.Builder().addHeader("Sec-WebSocket-Protocol", "koneapi").url(url).build();
        mWebSocket = socketOkHttpClient.newWebSocket(request, webSocketListener);
    }

    public void reconnectSocket(String mAuthToken, SocketCallback mCallback) {
        isReconnecting = true;
        init(mAuthToken, mCallback);
    }

    public void sendPingMessage() {
        String message = mGson.toJson(PayloadManager.getInstance().getBuildingPingPayLoad(SocketUtils.getRequestId(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false)));
        sendNewMessage(message);
    }

    private void initCommonCommands() {
        String actionsString = mGson.toJson(PayloadManager.getInstance().getBuildingActionsPayLoad(SocketUtils.getUUIDv4(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false)));
        SocketManager.getInstance().sendNewMessage(actionsString);
        String configString = mGson.toJson(PayloadManager.getInstance().getBuildingConfigPayLoad(SocketUtils.getUUIDv4(), SocketUtils.getBuildingAndGroupId(true), SocketUtils.getBuildingAndGroupId(false)));
        SocketManager.getInstance().sendNewMessage(configString);
    }

    public void sendNewMessage(String message) {
        mWebSocket.send(message);
    }

    public void closeSocketConnection() {
        if (mWebSocket != null)
            mWebSocket.close(1000, "REST SOCKET CONNECTION");
    }

    public interface SocketCallback {
        void onSocketConnected();

        void onConnectionFailed();

        void onSocketDisconnected();

        void onNewMessageArrive(String message);
    }


    private WebSocketListener webSocketListener = new WebSocketListener() {
        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosed(webSocket, code, reason);
            callback.onSocketDisconnected();
        }

        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            callback.onConnectionFailed();
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            callback.onNewMessageArrive(text);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            callback.onSocketConnected();
            if (!isReconnecting)
                initCommonCommands();
        }
    };
}
