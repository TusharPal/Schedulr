package prototyped.schedulr.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;

import prototyped.schedulr.R;

public class BroadcastReceiverAlarm extends BroadcastReceiver
{
    private AudioManager audioManager;
    private WifiManager wifiManager;
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                                                    .setSmallIcon(R.drawable.ic_launcher)
                                                    .setContentTitle("Schedulr")
                                                    .setContentText(intent.getExtras().getString("profile_name"));
        notificationManager.notify(001, notificationBuilder.build());
    }
}
