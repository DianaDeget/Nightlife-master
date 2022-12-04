package com.nordesk.nightlife.model;

public enum EventStatus {
    Planning("Planning"),
    Preparing("Preparing"),
    Live("Live"),
    Over("Over");

    private String status;

    EventStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
