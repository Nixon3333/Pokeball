package com.nixon.jsonparsing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pokemon, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Pokemon p = dataset.get(i);
        viewHolder.textView.setText(p.getName());

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")

                .into(viewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionalListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
