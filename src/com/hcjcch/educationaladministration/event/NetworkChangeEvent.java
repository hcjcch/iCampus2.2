package com.hcjcch.educationaladministration.event;

/**
 * Created by hcjcch on 2014/9/2.
 */

public class NetworkChangeEvent {
    private boolean isNetworkConnected;

    public NetworkChangeEvent(boolean isNetworkConnected) {
        this.isNetworkConnected = isNetworkConnected;
    }

    public boolean isNetworkConnected() {
        return isNetworkConnected;
    }
}
