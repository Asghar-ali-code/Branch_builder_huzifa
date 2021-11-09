package com.funtash.branchbuilder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultNotification {

    @SerializedName("noti")
    @Expose
    public Noti noti;


    public class Noti {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("on")
        @Expose
        public Boolean on;
        @SerializedName("starthour")
        @Expose
        public Integer starthour;
        @SerializedName("endhour")
        @Expose
        public Integer endhour;
        @SerializedName("delay")
        @Expose
        public Integer delay;
        @SerializedName("created_at")
        @Expose
        public String createdAt;

    }
}
