package com.sqrrow.entity.domain;

import java.util.ArrayList;

public class UCPResponse {
    private ArrayList<String> usecases;
    private ArrayList<String> actors;

    public ArrayList<String> getUsecases() {
        return usecases;
    }

    public void setUsecases(ArrayList<String> usecases) {
        this.usecases = usecases;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
}
