package com.nixon.jsonparsing;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {


    public PokemonAdapter( Context context, int resource,  ArrayList<Pokemon> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;

    }
}
