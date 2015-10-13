package edu.csh.cshdrink.network;

import edu.csh.cshdrink.models.BulkMachineData;
import edu.csh.cshdrink.models.Test;
import edu.csh.cshdrink.models.UserData;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface DrinkService {
    String BASE_URL = "https://webdrink.csh.rit.edu/api/index.php?request=";
    String LOGIN_URL = "https://webdrink.csh.rit.edu/api/index.php?request=mobileapp/getapikey";
    String REDIRECT_URL = "cshdrink://auth/";

    @GET("test/api")
    Call<Test> getTest(@Query("api_key") String apiKey);

    @GET("machines/stock")
    Call<BulkMachineData> getBulkMachineData();

    @GET("users/info")
    Call<UserData> getUserInfo(@Query("api_key") String apiKey);

    @FormUrlEncoded
    @POST("drops/drop")
    Call<Test> dropDrink(@Field("machine_id") String machineId,
                         @Field("slot_num") String slotNum,
                         @Field("delay") String delay,
                         @Field("api_key") String apiKey);
}
