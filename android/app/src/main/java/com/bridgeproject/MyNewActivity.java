package com.bridgeproject;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.lcw.lsdk.builder.LCWOmniChannelConfigBuilder;
import com.lcw.lsdk.chat.LiveChatMessaging;
import com.lcw.lsdk.data.requests.ChatSDKConfig;
import com.lcw.lsdk.data.requests.OmnichannelConfig;
import com.lcw.lsdk.data.requests.TelemetrySDKConfig;

public class MyNewActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            intiSdk();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FrameLayout layout = new FrameLayout(this);

        // Create the button
        Button button = new Button(this);
        button.setText("Click To Launch SDK");
        button.setTextSize(18);

        // Round button shape
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(100);
        shape.setColor(0xFF6200EE);
        button.setBackground(shape);
        button.setTextColor(0xFFFFFFFF);

        // Set layout parameters to bottom right
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.setMargins(0, 0, 40, 40); // right & bottom margin

        button.setLayoutParams(params);

        // Click listener
        button.setOnClickListener(view -> {
            try {
                launchSdk();
                Toast.makeText(getApplicationContext(), "Launching..", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        layout.addView(button);
        setContentView(layout);
    }

    private void intiSdk() throws Exception {
        String orgId = "ce4db5f6-1c20-ee11-a66d-000d3a0a02f3";
        String orgUrl = "https://m-ce4db5f6-1c20-ee11-a66d-000d3a0a02f3.ca.omnichannelengagementhub.com";
        String widgetId = "d6f95e7c-7c08-4eae-b6d3-8c15e7421ee7";
        OmnichannelConfig omnichannelConfig = new OmnichannelConfig(orgId, orgUrl, widgetId);
        TelemetrySDKConfig telemetryConfig = new TelemetrySDKConfig(false);
        ChatSDKConfig chatSdkConfig = new ChatSDKConfig(
            null, telemetryConfig, null, null, null,null
        );
        LCWOmniChannelConfigBuilder lcwOmniChannelConfigBuilder =
                new LCWOmniChannelConfigBuilder.EngagementBuilder(omnichannelConfig, chatSdkConfig, null).build();

        LiveChatMessaging liveChatMessaging = LiveChatMessaging.getInstance();
        liveChatMessaging.initialize(this, lcwOmniChannelConfigBuilder, null, "test");
    }

    private void launchSdk() throws Exception {
        Activity currentActivity = this;
        if (currentActivity != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    LiveChatMessaging.getInstance().launchLcwBrandedMessaging(currentActivity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
