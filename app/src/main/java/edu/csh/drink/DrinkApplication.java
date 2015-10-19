package edu.csh.drink;

import android.app.Application;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;

import edu.csh.drink.network.DrinkService;
import edu.csh.drink.network.ServiceGenerator;

public class DrinkApplication extends Application {
    public static DrinkService API;
    public static JobManager JOB_MANAGER;

    @Override
    public void onCreate() {
        super.onCreate();
        createApi();
        Configuration configuration = new Configuration.Builder(this)
                .minConsumerCount(1)
                .maxConsumerCount(5)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();

        JOB_MANAGER = new JobManager(this, configuration);
    }

    public static void createApi() {
        API = ServiceGenerator.createService(DrinkService.class);
    }
}
