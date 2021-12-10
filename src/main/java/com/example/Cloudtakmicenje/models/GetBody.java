package com.example.Cloudtakmicenje.models;

import java.util.ArrayList;

public class GetBody {
    private double totalCosts;
    private ArrayList<ReturnService> costsPerService = new ArrayList<>();

    public GetBody(double totalCosts, ArrayList<ReturnService> costsPerService) {
        this.totalCosts = totalCosts;
        this.costsPerService = costsPerService;
    }

    public double getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(double totalCosts) {
        this.totalCosts = totalCosts;
    }

    public ArrayList<ReturnService> getCostsPerService() {
        return costsPerService;
    }

    public void addToList(ReturnService costsPerService){
        this.costsPerService.add(costsPerService);
    }

}


