package com.nixon.jsonparsing;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailResponse {
    private String height;

    private String weight;

    private Stats[] stats;

    private String name;

    private Types[] types;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Stats[] getStats() {
        return stats;
    }

    public void setStats(Stats[] stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Types[] getTypes() {
        return types;
    }

    public void setTypes(Types[] types) {
        this.types = types;
    }


}