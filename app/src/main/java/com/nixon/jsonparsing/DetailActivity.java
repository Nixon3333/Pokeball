package com.nixon.jsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends Activity {
    private static final String TAG = "detailActivity";

    private ImageView imagePoke;
    private TextView descPoke;
    private PokemonListAdapter pokemonListAdapter;
    private Retrofit retrofit;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imagePoke = findViewById(R.id.imagePoke);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        /*Glide.with(this)
                .load("http://pokeapi.co/media/sprites/pokemon/" +id+ ".png")
                .into(imagePoke);*/

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        descPoke = findViewById(R.id.descPoke);
        getDetail(name);


    }

    private void getDetail(String name) {
        DetailService service = retrofit.create(DetailService.class);
        Call<DetailResponse> detailResponseCall = service.getDetails(name);

        detailResponseCall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    DetailResponse detailResponse = response.body();

                    ArrayList<Pokemon> pokemons = detailResponse.getForms();
                    Log.d(TAG, "pokemon: " + pokemons.get(0) );
                    Pokemon p = pokemons.get(0);
                    descPoke.setText(String.valueOf(detailResponse.getWeight()));

                }
            }
                @Override
                public void onFailure (Call < DetailResponse > call, Throwable t){
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }

    }

