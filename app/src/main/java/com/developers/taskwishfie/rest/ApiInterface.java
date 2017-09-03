package com.developers.taskwishfie.rest;

import com.developers.taskwishfie.model.Result;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Amanjeet Singh on 3/9/17.
 */

public interface ApiInterface {

    @GET("/v2/59ac290e100000cd0bf9c238")
    Observable<Result> getPage1();

    @GET("/v2/59ac28a9100000ce0bf9c236")
    Observable<Result> getPage2();

    @GET("/v2/59ac293b100000d60bf9c239")
    Observable<Result> getPage3();

}
