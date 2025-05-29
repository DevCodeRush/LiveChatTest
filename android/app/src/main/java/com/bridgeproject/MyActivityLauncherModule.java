package com.bridgeproject;

import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.lcw.lsdk.builder.LCWOmniChannelConfigBuilder;
import com.lcw.lsdk.chat.LiveChatMessaging;
import com.lcw.lsdk.data.requests.ChatSDKConfig;
import com.lcw.lsdk.data.requests.OmnichannelConfig;
import com.lcw.lsdk.data.requests.TelemetrySDKConfig;

public class MyActivityLauncherModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    public MyActivityLauncherModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "MyActivityLauncher";
    }

    @ReactMethod
    public void openActivity() throws Exception {
        intiSdk();
    }


    private void intiSdk() throws Exception {
     /*   String orgId = "d0632856-677c-4608-beb4-8f110f2ce523";
        String orgUrl = "https://unqd0632856677c4608beb48f110f2ce-crm5.omnichannelengagementhub.com";
        String widgetId = "b1bd42be-bef0-41e7-b0f6-25f410b7bf48";*/
        String orgId = "ce4db5f6-1c20-ee11-a66d-000d3a0a02f3";
        String orgUrl = "https://m-ce4db5f6-1c20-ee11-a66d-000d3a0a02f3.ca.omnichannelengagementhub.com";
        String widgetId = "323c845c-673a-4d5e-86cd-600596f89b2a";

        OmnichannelConfig omnichannelConfig = new OmnichannelConfig(orgId, orgUrl, widgetId);
        TelemetrySDKConfig telemetryConfig = new TelemetrySDKConfig(false);
        ChatSDKConfig chatSdkConfig = new ChatSDKConfig();
        LCWOmniChannelConfigBuilder lcwOmniChannelConfigBuilder =
                new LCWOmniChannelConfigBuilder.EngagementBuilder(omnichannelConfig, chatSdkConfig, null).build();

        LiveChatMessaging liveChatMessaging = LiveChatMessaging.getInstance();

        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {
            liveChatMessaging.initialize(currentActivity, lcwOmniChannelConfigBuilder, null, "test");
            launchSdk(currentActivity);
        } else {
            // Optional: fallback or log
        }

    }

    private void launchSdk(Activity currentActivity) throws Exception {
        LiveChatMessaging.getInstance().launchLcwBrandedMessaging(currentActivity);
    }
}
