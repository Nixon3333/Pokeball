package com.nixon.jsonparsing;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonService {

    @GET("pokemon/?limit=30&offset=30")
    Call<PokemonRespuesta> obtenerListaPokemon();
}
