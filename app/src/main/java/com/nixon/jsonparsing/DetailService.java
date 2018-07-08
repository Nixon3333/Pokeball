package com.nixon.jsonparsing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailService {

    @GET("pokemon/{name}/")
    Call<DetailResponse> getDetails(@Path("name") String name);
}
