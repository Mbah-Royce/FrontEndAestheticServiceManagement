package com.se3.ase.env;

import okhttp3.MediaType;

public class EnvVariables {
    public static final MediaType JSONb = MediaType.parse("application/json; charset=utf-8");
    public static final String API_BASE_URL = "http://192.168.0.100:11706/BackedTest/webresources";
}
