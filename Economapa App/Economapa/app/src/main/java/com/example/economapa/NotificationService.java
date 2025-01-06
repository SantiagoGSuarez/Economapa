package com.example.economapa;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationService extends Service {

    public static final String CHANNEL_ID = "PromotionNotificationChannel";
    private static final int NOTIFICATION_HOUR = 21;
    private static final int NOTIFICATION_MINUTE = 15;

    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        createNotificationChannel();

        // Schedule the service to run at the defined time
        scheduleNextCheck();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkPromotions();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Promotion Notification";
            String description = "Notificaciones de vencimiento de promociones";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void scheduleNextCheck() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, NOTIFICATION_HOUR); // Hora de notificación
        calendar.set(Calendar.MINUTE, NOTIFICATION_MINUTE);    // Minutos de notificación
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void checkPromotions() {
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);

        // Verificar si es la hora y minutos programados
        if (currentHour == NOTIFICATION_HOUR && currentMinute == NOTIFICATION_MINUTE) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ProdutoService produtoService = retrofit.create(ProdutoService.class);
            Call<List<Produto>> call = produtoService.getProduto();

            call.enqueue(new Callback<List<Produto>>() {
                @Override
                public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                    if (response.isSuccessful()) {
                        List<Produto> produtos = response.body();
                        if (produtos != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            for (Produto produto : produtos) {
                                Date dataVencimento = produto.getDataVencimento();
                                if (dataVencimento != null) {
                                    String dataVencimentoString = dateFormat.format(dataVencimento);
                                    Log.d("NotificationService", "Data de vencimento obtenida: " + dataVencimentoString);

                                    try {
                                        Date dataVencimentoFormatado = dateFormat.parse(dataVencimentoString);
                                        if (dataVencimentoFormatado != null) {
                                            Date hoje = new Date();
                                            long diff = dataVencimentoFormatado.getTime() - hoje.getTime();
                                            int daysLeft = (int) (diff / (1000 * 60 * 60 * 24));

                                            if (daysLeft <= 3 && daysLeft >= 0) {
                                                showNotification(produto.getNomeProduto(), daysLeft);
                                            } else if (daysLeft < 0) {
                                                deleteProduct(produto.getIdProduto());
                                            }
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        Log.e("NotificationService", "Error al parsear fecha: " + e.getMessage());
                                        // Manejar errores de parseo de fecha aquí
                                    }
                                } else {
                                    Log.d("NotificationService", "Fecha de vencimiento es null para producto: " + produto.getNomeProduto());
                                    // Manejar el caso donde dataVencimento es null
                                }
                            }
                        }
                    } else {
                        Log.e("NotificationService", "Error en la respuesta del servidor: " + response.code());
                        // Manejar errores de respuesta del servidor aquí
                    }
                }

                @Override
                public void onFailure(Call<List<Produto>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("NotificationService", "Error en la conexión: " + t.getMessage());
                    // Manejar errores de conexión aquí
                }
            });
        } else {
            // Si no es la hora y minutos programados, programar el siguiente check
            scheduleNextCheck();
        }
    }

    private void showNotification(String productName, int daysLeft) {
        String contentText;
        if (daysLeft == 0) {
            contentText = "La promoción del producto " + productName + " vence hoy!";
        } else {
            contentText = "La promoción del producto " + productName + " vence en " + daysLeft + " días.";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_list_alt_24)
                .setContentTitle("Economapa - Promoción cercana a vencerse")
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }

    private void deleteProduct(int productId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProdutoService produtoService = retrofit.create(ProdutoService.class);
        Call<Void> call = produtoService.deleteProduto(productId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Producto eliminado exitosamente
                } else {
                    Log.e("NotificationService", "Error al eliminar producto: " + response.code());
                    // Manejar errores al eliminar producto aquí
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Log.e("NotificationService", "Error en la conexión: " + t.getMessage());
                // Manejar errores de conexión aquí
            }
        });
    }
}
