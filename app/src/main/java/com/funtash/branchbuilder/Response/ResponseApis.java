package com.funtash.branchbuilder.Response;

import com.funtash.branchbuilder.APi.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponseApis {
private  static  final  String Url="https://www.changewants.com/zappapi/";
    private static ResponseApis clinetAllPorts;
    private static Retrofit retrofitAllPorts;

    ResponseApis() {
        retrofitAllPorts = new Retrofit.Builder().baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized ResponseApis getInstance() {
        if (clinetAllPorts == null)
            clinetAllPorts = new ResponseApis();
        return clinetAllPorts;
    }

    public Api getApiAllPorts() {
        return retrofitAllPorts.create(Api.class);
    }
}
