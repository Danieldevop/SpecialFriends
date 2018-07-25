package us.happycart.specialfriends;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

public class SpecialFriendsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
