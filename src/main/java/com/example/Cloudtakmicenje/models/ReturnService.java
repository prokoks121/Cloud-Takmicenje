package com.example.Cloudtakmicenje.models;

public class ReturnService {
    private final String serviceType;

    public String getServiceType() {
        return serviceType;
    }

    public double getCost() {
        return cost;
    }

    private final double cost;

    public ReturnService(String serviceType, double cost) {
        this.serviceType = serviceType;
        this.cost = cost;
    }
}
