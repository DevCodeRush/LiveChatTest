package com.bridgeproject;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.lcw.lsdk.builder.LCWOmniChannelConfigBuilder;
import com.lcw.lsdk.chat.LiveChatMessaging;
import com.lcw.lsdk.data.requests.ChatSDKConfig;
import com.lcw.lsdk.data.requests.OmnichannelConfig;
import com.lcw.lsdk.data.requests.TelemetrySDKConfig;

import java.util.Objects;

public class MyNewActivity extends AppCompatActivity {
    private EditText editOrgId, editOrgUrl, editWidgetId, editAuth;
    private String orgId = "d0632856-677c-4608-beb4-8f110f2ce523";
    String orgUrl = "https://unqd0632856677c4608beb48f110f2ce-crm5.omnichannelengagementhub.com";
    String widgetId = "b1bd42be-bef0-41e7-b0f6-25f410b7bf48";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new);

        editOrgId = findViewById(R.id.editOrgId);
        editOrgUrl = findViewById(R.id.editOrgUrl);
        editWidgetId = findViewById(R.id.editWidgetId);
        editAuth = findViewById(R.id.editAuth);
        Button launchButton = findViewById(R.id.launchButton);

        try {
            intiSdk();
        } catch (Exception e) {
            Log.d("intiSdk", e.getLocalizedMessage());
        }

        launchButton.setOnClickListener(view -> {
            orgId = editOrgId.getText().toString().trim();
            orgUrl = editOrgUrl.getText().toString().trim();
            widgetId = editWidgetId.getText().toString().trim();

            if (orgId.isEmpty() || orgUrl.isEmpty() || widgetId.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                launchSdk();
                Toast.makeText(getApplicationContext(), "Launching...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("launchSdk", e.getLocalizedMessage());
            }
        });
    }

    private void intiSdk() throws Exception {
        editOrgId.setText(orgId);
        editOrgUrl.setText(orgUrl);
        editWidgetId.setText(widgetId);
        OmnichannelConfig omnichannelConfig = new OmnichannelConfig(orgId, orgUrl, widgetId);
        TelemetrySDKConfig telemetryConfig = new TelemetrySDKConfig(false);
        ChatSDKConfig chatSdkConfig = new ChatSDKConfig(
                null, telemetryConfig, null, null, null, null
        );
        LCWOmniChannelConfigBuilder lcwOmniChannelConfigBuilder =
                new LCWOmniChannelConfigBuilder.EngagementBuilder(omnichannelConfig, chatSdkConfig, null).build();

        LiveChatMessaging liveChatMessaging = LiveChatMessaging.getInstance();
        liveChatMessaging.initialize(this, lcwOmniChannelConfigBuilder, null, "test");
    }

    private void launchSdk() throws Exception {
        Activity currentActivity = this;
        LiveChatMessaging.getInstance().launchLcwBrandedMessaging(currentActivity);
    }

    @Override
    protected void  onPause() {
        super.onPause();
        LiveChatMessaging.getInstance().unmount();
    }
}
