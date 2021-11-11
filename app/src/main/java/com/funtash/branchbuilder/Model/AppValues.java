package com.funtash.branchbuilder.Model;

import java.util.ArrayList;

public class AppValues {
    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        AppValues.i = i;
    }

    public  static  int i;
    public static String getTruthTitle() {
        return truthTitle;
    }

    public static void setTruthTitle(String truthTitle) {
        AppValues.truthTitle = truthTitle;
    }

    public static String getTruthBody() {
        return truthBody;
    }

    public static void setTruthBody(String truthBody) {
        AppValues.truthBody = truthBody;
    }

    public  static  String truthTitle;
    public  static  String truthBody;
    public static ArrayList<String> getArrayList() {
        return arrayList;
    }

    public static void setArrayList(ArrayList<String> arrayList) {
        AppValues.arrayList = arrayList;
    }

    public  static ArrayList<String> arrayList;
}
