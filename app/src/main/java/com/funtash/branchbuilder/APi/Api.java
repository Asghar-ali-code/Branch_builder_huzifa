package com.funtash.branchbuilder.APi;

import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.Model.Register;
import com.funtash.branchbuilder.Response.ResponseApis;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("obtain_auth_token/")
    Call<ApiToken> getApiToken(
            @Field("password") String password,
            @Field("username") String username
    );
    @FormUrlEncoded
    @POST("register/")
    Call<Register> registerUser(
            @Field("email") String email,
            @Field("password") String password

    );
    @GET("branches/")
    Call<Branches> gettingBranches(
            @Header("Authorization") String Authorization

   // @Query("Authorization")
    );

}
