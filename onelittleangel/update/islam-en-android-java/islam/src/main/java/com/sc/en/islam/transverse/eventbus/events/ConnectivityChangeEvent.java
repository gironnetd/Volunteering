package com.sc.en.islam.transverse.eventbus.events;

public class ConnectivityChangeEvent {
    private boolean isConnected;
    private boolean isWifiConnected;
    private int networkType;

    public ConnectivityChangeEvent() {
    }

    public ConnectivityChangeEvent(int networkType, boolean isConnected, boolean isWifiConnected) {
        this.networkType = networkType;
        this.isConnected = isConnected;
        this.isWifiConnected = isWifiConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isWifiConnected() {
        return isWifiConnected;
    }

    public void setIsWifiConnected(boolean isWifiConnected) {
        this.isWifiConnected = isWifiConnected;
    }

    public int getNetworkType() {
        return networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }
}
