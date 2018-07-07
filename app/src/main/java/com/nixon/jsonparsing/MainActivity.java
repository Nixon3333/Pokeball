package com.nixon.jsonparsing;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private int offset;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            aptoParaCargar = false;
                            offset += 30;
                            getData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;

        getData(offset);
    }

    private void getData(int offset) {
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(30, offset);

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {

            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoParaCargar = true;
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
                aptoParaCargar = true;
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        }
        );
    }


}



