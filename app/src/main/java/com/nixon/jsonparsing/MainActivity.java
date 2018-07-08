package com.nixon.jsonparsing;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {
    private static final String TAG = "mylog";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private int offset;
    private boolean loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRandom = findViewById(R.id.btnRandom);


        recyclerView = findViewById(R.id.recycleView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
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

        loading = true;
        offset = 0;


        getData(offset);


    }

    public void cbClick(View view) {
        Toast.makeText(this, "Please buy the full version to unlock this functionality :)", Toast.LENGTH_LONG).show();
    }

    public void btnRandomClick(View view) {
        pokemonListAdapter.removePokemonList();
        offset = (int) (Math.random() * 100);
        getData(offset);

    }

    private void getData(int offset) {
        Log.d(TAG, "offset = " + offset);
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getPokemonList(30, offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {

                                        @Override
                                        public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                                            loading = true;
                                            if (response.isSuccessful()) {
                                                PokemonResponse pokemonResponse = response.body();

                                                ArrayList<Pokemon> pokemonList = pokemonResponse.getResults();


                                                pokemonListAdapter.addPokemonList(pokemonList);
                                            } else {
                                                Log.d(TAG, "onResponse: " + response.errorBody());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PokemonResponse> call, Throwable t) {
                                            loading = true;
                                            Log.d(TAG, "onFailure: " + t.getMessage());
                                        }
                                    }
        );
    }


}



