package com.funtash.branchbuilder.APi;

import com.funtash.branchbuilder.Model.ApiToken;
import com.funtash.branchbuilder.Model.Branches;
import com.funtash.branchbuilder.Model.CreateTruth;
import com.funtash.branchbuilder.Model.DefaultNotification;
import com.funtash.branchbuilder.Model.Register;
import com.funtash.branchbuilder.Model.UpdateNotifications;
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
    @FormUrlEncoded
    @POST("create_truth/")
    Call<CreateTruth> createTruth(
            @Field("category") String category,
            @Field("title") String title,
            @Field("body") String body,
            @Header("Authorization") String Authorization

    );
    @GET("noti/")
    Call<DefaultNotification> defaultNoti(
            @Header("Authorization") String Authorization

    );
    @FormUrlEncoded
    @POST("update_noti/")
    Call<UpdateNotifications> updateNoti(
            @Field("id") String id,
            @Field("on") Boolean on,
            @Field("starthour") String starthour,
            @Field("endhour") String endhour,
            @Field("delay") String delay

    );

}
