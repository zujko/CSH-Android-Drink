package edu.csh.cshdrink.network;

public interface DrinkService {

    String BASE_URL = "https://webdrink.csh.rit.edu/api/index.php?request=";
    String LOGIN_URL = "https://webdrink.csh.rit.edu/api/index.php?request=mobileapp/getapikey";
    String REDIRECT_URL = "cshdrink://auth/";

}
