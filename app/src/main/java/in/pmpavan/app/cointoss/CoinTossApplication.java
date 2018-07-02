package in.pmpavan.app.cointoss;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.google.firebase.analytics.FirebaseAnalytics;

import in.pmpavan.app.cointoss.helpers.Logger;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Pavan on 07/05/17.
 */

public class CoinTossApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initLogger();
        initFireBase();
        initCrashlytics();
        //remove spacing empty comment
    }

    private void initFireBase() {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    private void initCrashlytics() {
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());

    }


    private void initLogger() {
        Logger.setSwitch(true);
    }
}

