package com.example.Cloudtakmicenje.models;

public class PayLoad {

        public String serviceType;
        public int userId;
        public String actionType;
        public long timestamp;

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setPayloadSizeMb(long payloadSizeMb) {
        this.payloadSizeMb = payloadSizeMb;
    }

    public long payloadSizeMb;

    public PayLoad(String serviceType, int userId, String actionType, long timestamp, int payloadSizeMb) {
        this.serviceType = serviceType;
        this.userId = userId;
        this.actionType = actionType;
        this.timestamp = timestamp;
        this.payloadSizeMb = payloadSizeMb;
    }

    public String getServiceType() {
        return serviceType;
    }
    public int getUserId() {
        return userId;
    }
    public String getActionType() {
        return actionType;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public long getPayloadSizeMb() {
        return payloadSizeMb;
    }
    @Override
    public String toString() {
        return "PlayLoad{" +
                "serviceType='" + serviceType + '\'' +
                ", userId=" + userId +
                ", actionType='" + actionType + '\'' +
                ", timestamp=" + timestamp +
                ", payloadSizeMb=" + payloadSizeMb +
                '}';
    }
}
