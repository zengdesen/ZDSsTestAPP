package none.zds.zdsstestapp;

import android.app.Application;
import android.content.Context;

import none.zds.zdsstestapp.util.Toasts;

public class App extends Application {
    public static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        sContext = this;
        Toasts.register(this);
    }
}
