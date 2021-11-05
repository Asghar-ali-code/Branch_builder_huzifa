package com.funtash.branchbuilder.APi;

import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Response.ResponseApis;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("obtain_auth_token/")
    Call<ApiToken> getApiToken(
            @Field("password") String password,
            @Field("username") String username
    );
}
