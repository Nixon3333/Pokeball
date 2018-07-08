package com.nixon.jsonparsing;

import android.app.Activity;
import android.content.Context;
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
    private TextView namePoke, heightPoke, weightPoke, typePoke, statsPoke, tvId;
    private PokemonListAdapter pokemonListAdapter;
    private Retrofit retrofit;
    private Context context;
    private int id;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imagePoke = findViewById(R.id.imagePoke);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");



        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        namePoke = findViewById(R.id.namePoke);
        heightPoke = findViewById(R.id.heightPoke);
        weightPoke = findViewById(R.id.weightPoke);
        typePoke = findViewById(R.id.typePoke);
        statsPoke = findViewById(R.id.statsPoke);
        tvId = findViewById(R.id.tvId);

        getDetail(name);

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+id+".png")

                .into(imagePoke);


    }

    private void getDetail(String name) {
        DetailService service = retrofit.create(DetailService.class);
        Call<DetailResponse> detailResponseCall = service.getDetails(name);

        detailResponseCall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    DetailResponse detailResponse = response.body();

                    String name = detailResponse.getName();
                    String height = detailResponse.getHeight();
                    String weight = detailResponse.getWeight();
                    Types[] types = detailResponse.getTypes();
                    Stats[] stats = detailResponse.getStats();


                    id = detailResponse.getId();
tvId.setText(String.valueOf(id));





                    namePoke.setText(name);
                    heightPoke.setText(height);
                    weightPoke.setText(weight);
                    typePoke.setText(String.valueOf(types[0].getType()));
                    statsPoke.setText(String.valueOf(stats[0]));


                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}

