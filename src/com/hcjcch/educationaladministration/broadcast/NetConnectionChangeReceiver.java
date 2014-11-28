package com.hcjcch.educationaladministration.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.hcjcch.educationaladministration.event.NetworkChangeEvent;
import com.hcjcch.educationaladministration.utils.NetworkJudgment;

import de.greenrobot.event.EventBus;

/**
 * Created by hcjcch on 2014/9/2.
 */

public class NetConnectionChangeReceiver extends BroadcastReceiver {
    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, CONNECTIVITY_CHANGE_ACTION)) {
            boolean isNetworkConnected = NetworkJudgment.isNetworkConnected(context);
            EventBus.getDefault().post(new NetworkChangeEvent(isNetworkConnected));
        }
    }
}