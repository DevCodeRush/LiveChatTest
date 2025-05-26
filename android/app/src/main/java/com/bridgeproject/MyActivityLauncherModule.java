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
        String orgId = "";
        String orgUrl = "";
        String widgetId = "";
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
