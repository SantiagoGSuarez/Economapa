package com.example.economapa;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String EXTRA_EMPRESA_NOME = "empresa_nome";
    public static final String ACTION_STOP_SERVICE = "STOP_FOREGROUND_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_STOP_SERVICE.equals(action)) {
                stopForeground(true);
                stopSelf();
                return START_NOT_STICKY;
            }

            String empresaNome = intent.getStringExtra(EXTRA_EMPRESA_NOME);
            Intent notificationIntent = new Intent(this, InfoProduto.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            Intent stopSelf = new Intent(this, ForegroundService.class);
            stopSelf.setAction(ACTION_STOP_SERVICE);
            PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Economapa")
                    .setContentText("Se dirige a la Empresa: " + empresaNome)
                    .setSmallIcon(R.drawable.baseline_location)  // Asegúrate de tener un ícono en tus recursos
                    .setContentIntent(pendingIntent)
                    .addAction(new NotificationCompat.Action(
                            android.R.drawable.ic_menu_close_clear_cancel, "Detener", pStopSelf
                    ))
                    .build();

            startForeground(1, notification);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
