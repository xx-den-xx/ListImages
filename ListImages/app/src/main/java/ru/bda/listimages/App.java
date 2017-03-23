package ru.bda.listimages;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ApiController getController() {
        return ApiController.getInstance();
    }

    public static boolean isPhone(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        return diagonalInches<=6.5;
    }
}
