package co.mtaindia.mta.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.mtaindia.mta.Services.NotificationService;

public class BootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, NotificationService.class));
    }
}
