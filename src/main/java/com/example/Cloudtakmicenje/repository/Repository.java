package com.example.Cloudtakmicenje.repository;

import com.example.Cloudtakmicenje.models.PayLoad;

import java.util.ArrayList;

public class Repository {
    private static final Object object = new Object();
    private static volatile Repository instance = null;

    private final ArrayList<PayLoad> payLoads = new ArrayList<>();


    public void addPayLoad(PayLoad payLoad){
        payLoads.add(payLoad);
    }
    public ArrayList<PayLoad> getAllPayLoads() {
        return payLoads;
    }

    public static Repository getInstance() {
        if (instance != null) {
            return instance;
        }

        synchronized (object) {
            if (instance == null) {
                instance = new Repository();
            }

            return instance;
        }
    }
}