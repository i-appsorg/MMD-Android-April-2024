package com.MamaDevalayam.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OnBootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hi Gowri", Toast.LENGTH_LONG).show();
        Log.e("data","data");
        Intent i = new Intent("com.examle.FirebaseMessagingReceiveService");
        i.setClass(context, MyFirebaseMessagingService.class);
        context.startService(i);
    }
}
