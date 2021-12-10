package com.example.Cloudtakmicenje.calculator;

import com.example.Cloudtakmicenje.models.GetBody;
import com.example.Cloudtakmicenje.models.PayLoad;
import com.example.Cloudtakmicenje.models.ReturnService;
import com.example.Cloudtakmicenje.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private final List<PayLoad> payLoads = Repository.getInstance().getAllPayLoads();
    private final ArrayList<ReturnService> costsPerServices = new ArrayList();
    private final long untilDate;
    private final int id;
    private final List<String> serviceTypes;
    private int dbRequest = 0;
    private int osRequest = 0;
    private long lastStartVm = 0;
    private boolean vmFinished = true;
    private long vmDuration = 0;
    private long network = 0;
    private long mbDb = 0;
    int numExc = 0;


    //free limiti
    private int freeMbDb = 1024;
    private int freeMbOs = 1024;
    private int freeMbNet = 1024;
    private int freeExc = 10;


    public Calculator(long untilDate, int id,List<String> serviceTypes) {
        this.untilDate = untilDate;
        this.id = id;
        this.serviceTypes = serviceTypes;

    }

    public GetBody calcCost(){

        for (PayLoad payLoad:payLoads) {
            if (untilDate < payLoad.getTimestamp()){
                continue;
            }
            if (payLoad.getUserId() == id){

                switch (payLoad.getServiceType()){
                    case "DB":
                        if (freeExc > 0)
                            freeExc--;
                        else dbRequest++;
                        if (payLoad.getActionType().equals("INSERT"))
                            updateDb(payLoad.getPayloadSizeMb());
                        break;

                    case "OBJECT_STORAGE":
                        if (payLoad.getActionType().equals("PUT"))
                            updateOs(payLoad.getPayloadSizeMb());
                        else if(payLoad.getActionType().equals("GET"))
                            updateNetwork(payLoad.getPayloadSizeMb());
                        break;

                    case "VM":
                        if (payLoad.getActionType().equals("START")){
                            lastStartVm = payLoad.getTimestamp();
                            vmFinished = false;
                        }else if(payLoad.getActionType().equals("STOP")){
                            vmDuration += payLoad.getTimestamp()-lastStartVm;
                            vmFinished = true;
                        }
                        break;

                    case "FUNC":
                        if (freeExc > 0)
                            freeExc--;
                        else
                            numExc++;
                        updateNetwork(payLoad.getPayloadSizeMb());
                        break;
                }
            }
        }



        if (!vmFinished){
            vmDuration += untilDate - lastStartVm;
            vmFinished = true;
        }
        double finalCostVm = (vmDuration-36000)*0.01;
        double finalCostDB = Math.ceil((double) dbRequest /10) * 0.01 + mbDb * 0.01;
        double finalCostOS = osRequest *0.01;
        double finalCostEX = Math.ceil((double) numExc /10) * 0.01;
        double finalCostNet = network * 0.01;
        double total = 0;
        if (finalCostDB>0 && (serviceTypes == null || serviceTypes.contains("DB"))){
            total += finalCostDB;
            costsPerServices.add(new ReturnService("DB",finalCostDB));
        }
        if (finalCostVm>0 && (serviceTypes == null || serviceTypes.contains("VM"))){
            total += finalCostVm;
            costsPerServices.add(new ReturnService("VM",finalCostVm));
        }
        if (finalCostOS>0 && (serviceTypes == null || serviceTypes.contains("OBJECT_STORAGE"))){
            total += finalCostOS;
            costsPerServices.add(new ReturnService("OBJECT_STORAGE",finalCostOS));
        }
        if (finalCostEX>0 && (serviceTypes == null || serviceTypes.contains("FUNC"))){
            total += finalCostEX;
            costsPerServices.add(new ReturnService("FUNC",finalCostEX));
        }
        if (finalCostNet>0 && (serviceTypes == null || serviceTypes.contains("NETWORK"))){
            total += finalCostNet;
            costsPerServices.add(new ReturnService("NETWORK",finalCostNet));
        }
        return new GetBody(total,costsPerServices);
    }

    private void updateNetwork(long sizeMb){
        if (freeMbNet >0){
            if (freeMbNet >= sizeMb){
                freeMbNet -= sizeMb;
            }else{
                network += sizeMb - freeMbNet;
                freeMbNet=0;
            }
        }else {
            network += sizeMb;
        }
    }



    private void updateOs(long sizeMb){
        if (freeMbOs >0){
            if (freeMbOs >= sizeMb){
                freeMbOs -= sizeMb;
            }else{
                osRequest += sizeMb - freeMbOs;
            }
        }else{
            osRequest += sizeMb;
        }
    }


    private void updateDb(long sizeMb){
        if (freeMbDb >0){
            if (freeMbDb >= sizeMb){
                freeMbDb -= sizeMb;
            }else{
                mbDb += sizeMb - freeMbDb;
                freeMbDb = 0;
            }
        }else{
            mbDb += sizeMb;
        }
    }
}
