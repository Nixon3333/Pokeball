package com.nixon.Pokeball;

public class Pokemon {


    private int number;

    private String name;

    private String url;


    public Pokemon() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        try {

            String[] urlParts = url.split("/");

            return Integer.parseInt(urlParts[urlParts.length - 1]);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
