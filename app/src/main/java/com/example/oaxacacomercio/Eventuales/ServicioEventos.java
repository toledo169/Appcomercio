package com.example.oaxacacomercio.Eventuales;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Modelos.Permisos;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ServicioEventos extends Service {
    public Context thiscontext = this;
    ArrayList<Permisos> listapermisos;
    ArrayList<Permisos> listaauxiliar;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    SweetAlertDialog sweetAlertDialog;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final int NOTIFICATION_ID = 101;
    Timer time;

    public int onStartCommand(Intent intent, int flag, int idProcess) {
        time = new Timer();
        time.execute();
        request = Volley.newRequestQueue(thiscontext);
        listapermisos = new ArrayList<>();
        return START_NOT_STICKY;
    }

    public void hilo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutar() {
        time = new Timer();
        time.execute();
        request = Volley.newRequestQueue(thiscontext);
        listapermisos = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(thiscontext,"Finalizado servicio",Toast.LENGTH_LONG).show();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class Timer extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 1; i <= 5; i++) {
                hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            ejecutarservicio();
            // Toast.makeText(thiscontext,"Inicio",Toast.LENGTH_LONG).show();
        }
    }

    public void ejecutarservicio() {
        String url = "http://192.168.10.233/api/Usuario/permisomapa/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Permisos organizacion = null;
                JSONArray json = response.optJSONArray("anuales");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        organizacion = new Permisos();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        organizacion.setGiro(jsonObject.optString("giro"));
                        organizacion.setHorainicio(Time.valueOf(jsonObject.optString("hora_inicio")));
                        organizacion.setHorafin(Time.valueOf(jsonObject.optString("hora_fin")));
                        organizacion.setLatitud(jsonObject.optDouble("latitud"));
                        organizacion.setLongitud(jsonObject.optDouble("longitud"));
                        organizacion.setLatitudfinal(jsonObject.optDouble("latitud_fin"));
                        organizacion.setLongitudfinal(jsonObject.optDouble("longitud_fin"));
                        listapermisos.add(organizacion);
                        if (listapermisos.size() > 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(thiscontext)
                                                .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                                                .setLargeIcon((((BitmapDrawable) getResources()
                                                        .getDrawable(R.drawable.logom)).getBitmap()))
                                                .setContentTitle("Municipio de Oaxaca")
                                                .setContentText("Eventos del dia")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .setLights(Color.BLUE, 1000, 1000)
                                                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                                                .setDefaults(Notification.DEFAULT_SOUND)
                                                .setStyle(new NotificationCompat.InboxStyle().
                                                        addLine(" Hey! Hay " + listapermisos.size() + " eventos programados para el ")
                                                        .addLine("dia de hoy enterate de cuales son"))
                                                .setTicker("Alerta!");
                                Intent notIntent =
                                        new Intent(thiscontext, PermisosEventualActivity.class);
                                PendingIntent contIntent =
                                        PendingIntent.getActivity(
                                                thiscontext, 0, notIntent, 0);
                                mBuilder.setContentIntent(contIntent);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                            } else {
                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(thiscontext)
                                                .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                                                .setLargeIcon((((BitmapDrawable) getResources()
                                                        .getDrawable(R.drawable.logom)).getBitmap()))
                                                .setContentTitle("Municipio de Oaxaca")
                                                .setContentText("Eventos del dia")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .setLights(Color.BLUE, 1000, 1000)
                                                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                                                .setDefaults(Notification.DEFAULT_SOUND)
                                                .setStyle(new NotificationCompat.InboxStyle().addLine(" Hey! Hay " + listapermisos.size() + " eventos programados para el ").addLine("dia de hoy enterate de cuales son"))
                                                .setTicker("Alerta!");
                                Intent notIntent =
                                        new Intent(thiscontext, PermisosEventualActivity.class);
                                PendingIntent contIntent =
                                        PendingIntent.getActivity(
                                                thiscontext, 0, notIntent, 0);
                                mBuilder.setContentIntent(contIntent);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                            }
                        }
                    }
                    System.out.println("la lista contiene" + listapermisos.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    sweetAlertDialog = new SweetAlertDialog(thiscontext, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Lo sentimos");
                    sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("Volver a intentarlo");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //stopService(new Intent(thiscontext, Serviciodatos.class));
                            startActivity(new Intent(thiscontext, Ventanas.class));
                        }
                    });
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.show();
                    //             mDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sweetAlertDialog = new SweetAlertDialog(thiscontext, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        startActivity(new Intent(thiscontext, Ventanas.class));
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();

            }
        });
        request.add(jsonObjectRequest);
    }

}
