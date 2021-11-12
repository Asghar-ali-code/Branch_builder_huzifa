package com.funtash.branchbuilder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Branches {
    @SerializedName("truths")
    @Expose
    public List<Truth> truths = null;
    @SerializedName("categories")
    @Expose
    public List<String> categories = null;

    public Branches() {
    }

    public static class Truth {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("module")
        @Expose
        public String module;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("body")
        @Expose
        public String body;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

}

