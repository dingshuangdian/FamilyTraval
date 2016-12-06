package www.aiyi.com.myapplicatuon.common;

import android.app.Application;

/**
 * Created by dings on 2016-12-06.
 */

public class AppContext extends Application {
    private static AppContext appContext;

    public AppContext() {
        appContext = this;
    }

    public static synchronized AppContext getInstance() {
        if (appContext == null) {
            appContext = new AppContext();
        }
        return appContext;
    }
}
