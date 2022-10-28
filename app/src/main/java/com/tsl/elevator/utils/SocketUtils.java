package com.tsl.elevator.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.tsl.elevator.managers.CommonCommandsDataManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class SocketUtils {
    private static int requestId = 11111111;

    public static int getRequestId() {
        return requestId++;
    }

    public static String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getUUIDv4() {
        return UUID.randomUUID().toString();
    }

    public static String getBuildingAndGroupId(boolean isBuilding) {
        String result = "";
        try {
            String[] temp = CommonCommandsDataManager.getInstance().getBuildingId().split(":");
            if (isBuilding)
                result = "building:" + temp[0];
            else
                result = temp[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void appendColoredText(TextView tv, String text, int color) {

        try {
            int start = tv.getText().length();
            tv.append(text + "\n\n");
            int end = tv.getText().length();

            Spannable spannableText = (Spannable) tv.getText();
            spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
