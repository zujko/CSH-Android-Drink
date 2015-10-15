package edu.csh.drink;

import android.app.Application;

import edu.csh.drink.network.DrinkService;
import edu.csh.drink.network.ServiceGenerator;

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
