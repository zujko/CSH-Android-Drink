package edu.csh.cshdrink.network;

import edu.csh.cshdrink.models.Test;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface DrinkService {
    String BASE_URL = "https://webdrink.csh.rit.edu/api/index.php?request=";
    String LOGIN_URL = "https://webdrink.csh.rit.edu/api/index.php?request=mobileapp/getapikey";
    String REDIRECT_URL = "cshdrink://auth/";

    @GET("test/api")
    Call<Test> getTest(@Query("api_key") String apiKey);


}
