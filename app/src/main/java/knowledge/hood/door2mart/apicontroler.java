package knowledge.hood.door2mart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apicontroler {

        private static final String url= "https://emanational-barrel.000webhostapp.com/user/"; //http://localhost/doortomart/user/category_fetch.php
        private static apicontroler clientobject;
        private  static Retrofit retrofit;

        apicontroler(){
            retrofit= new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        public static synchronized apicontroler getInstance()
        {
            if(clientobject==null)
                clientobject= new apicontroler();

            return clientobject;
        }

        public apiset getapi()
        {
            return retrofit.create(apiset.class);
        }



}
