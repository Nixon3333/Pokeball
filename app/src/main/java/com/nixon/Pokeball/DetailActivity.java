package com.nixon.Pokeball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nixon.Pokeball.pokemonAPI.DetailService;
import com.nixon.Pokeball.stats.Stats;
import com.nixon.Pokeball.stats.Types;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends Activity {
    private static final String TAG = "detailActivity";

    private ImageView imagePoke;
    private TextView namePoke, heightPoke, weightPoke, typePoke, statsPoke;
    private Retrofit retrofit;
    private int id;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imagePoke = findViewById(R.id.imagePoke);
        namePoke = findViewById(R.id.namePoke);
        heightPoke = findViewById(R.id.heightPoke);
        weightPoke = findViewById(R.id.weightPoke);
        typePoke = findViewById(R.id.typePoke);
        statsPoke = findViewById(R.id.statsPoke);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        getDetail(name);


    }


    private void getDetail(String name) {
        DetailService service = retrofit.create(DetailService.class);
        Call<DetailResponse> detailResponseCall = service.getDetails(name);

        detailResponseCall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {


                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    DetailResponse detailResponse = response.body();

                    String name = detailResponse.getName();
                    String height = detailResponse.getHeight();
                    String weight = detailResponse.getWeight();
                    Types[] types = detailResponse.getTypes();
                    Stats[] stats = detailResponse.getStats();


                    id = detailResponse.getId();


                    Glide.with(getApplicationContext())
                            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                                    + id + ".png")
                            .into(imagePoke);


                    namePoke.setText(name);
                    heightPoke.setText(getString(R.string.height) + height);
                    weightPoke.setText(getString(R.string.weight) + weight);
                    typePoke.setText(getString(R.string.type) + String.valueOf(types[0].getType()));
                    statsPoke.setText(String.valueOf(stats[5]) + "\n" +
                            String.valueOf(stats[4]) + "\n" +
                            String.valueOf(stats[3]));


                }

            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                namePoke.setText(R.string.no_internet);

            }
        });

    }

}

