package com.nixon.jsonparsing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {
    private static final String TAG = "mylog";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getData();
    }

    private void getData() {
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {

            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    listaPokemonAdapter.adicionalListaPokemon(listaPokemon);
                } else {
                    Log.d(TAG, "ERRRROR" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        }
        );
    }


}



