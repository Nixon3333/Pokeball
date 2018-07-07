package com.nixon.jsonparsing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("pokemon") ///?limit=30&offset=30
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);


}
