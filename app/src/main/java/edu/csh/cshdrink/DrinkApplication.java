package edu.csh.cshdrink;

import android.app.Application;

import edu.csh.cshdrink.network.DrinkService;
import edu.csh.cshdrink.network.ServiceGenerator;

public class DrinkApplication extends Application {
    public static DrinkService API;

    @Override
    public void onCreate() {
        super.onCreate();
        createApi();
    }

    public static void createApi() {
        API = ServiceGenerator.createService(DrinkService.class);
    }
}
