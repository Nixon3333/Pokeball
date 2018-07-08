package com.nixon.jsonparsing;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailResponse {

    @SerializedName("forms")
    @Expose
    private ArrayList<Pokemon> forms = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("height")
    @Expose
    private int height;

    public ArrayList<Pokemon> getForms() {
        return forms;
    }

    public void setForms(ArrayList<Pokemon> forms) {
        this.forms = forms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
