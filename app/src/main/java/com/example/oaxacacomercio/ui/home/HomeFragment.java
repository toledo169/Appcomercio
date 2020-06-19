package com.example.oaxacacomercio.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.oaxacacomercio.R;
import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment {//implements Response.Listener<JSONObject>, Response.ErrorListener{

    private HomeViewModel homeViewModel;
    private TextInputLayout nombre;
    public static final String apellido_paternos = "apellido_paterno";
    public static final String apellido_maternos = "apellido_materno";
    public static final String nombres = "name";
    public static final String correo = "email";
    public static final String cargo = "cargo";
    public static final String municipio = "nombre";

    TextInputLayout apellidop, nomb, correoelect, puesto, lugarn,apellidom;
    //ArrayList<Permisos> listapermisos;
    //ArrayList<Permisos> listaauxiliar;
    //JsonRequest jsonObjectRequest;
    //RequestQueue request;
    //private PendingIntent pendingIntent;
   // private final static String CHANNEL_ID="NOTIFICACION";
   // private final static int NOTIFICACION_ID=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        nomb = (TextInputLayout) root.findViewById(R.id.nombreusuario);
        String nomusuario = getActivity().getIntent().getStringExtra("name");
        nomb.getEditText().setText(" " + nomusuario);
        apellidop = (TextInputLayout) root.findViewById(R.id.apellido);
        apellidom = (TextInputLayout) root.findViewById(R.id.apellidom);
        String usuario = getActivity().getIntent().getStringExtra("apellido_paterno");
        String apmusuario = getActivity().getIntent().getStringExtra("apellido_materno");
        apellidop.getEditText().setText(" " + usuario);
        apellidom.getEditText().setText(" " + apmusuario);
        correoelect = (TextInputLayout) root.findViewById(R.id.correo);
        String coreo = getActivity().getIntent().getStringExtra("email");
        correoelect.getEditText().setText(" " + coreo);

        puesto = (TextInputLayout) root.findViewById(R.id.cargo);
        String puestocargo = getActivity().getIntent().getStringExtra("cargo");
        puesto.getEditText().setText(" " + puestocargo);

        lugarn = (TextInputLayout) root.findViewById(R.id.lugar);
        String nacimiento = getActivity().getIntent().getStringExtra("nombre");
        lugarn.getEditText().setText(" " + nacimiento);
     //   request = Volley.newRequestQueue(getContext());
      //  listapermisos = new ArrayList<>();
      //  cargarwebservice();
        return root;
    }
  /*  private void cargarwebservice() {
        String url = "http://192.168.0.2/api/Usuario/permisomapa/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Lo sentimos");
        sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
        sweetAlertDialog.setContentTextSize(15);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("Intentarlo mas tarde");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                getActivity().finishAffinity();
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
    }

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
                if (listapermisos.size()>0) {
                    Intent intent= new Intent(getActivity().getBaseContext(), PermisosEventualActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID);
                    builder.setContentIntent(pendingIntent);
                    builder.setSmallIcon(R.drawable.ic_event_black_24dp);
                    builder.setContentTitle("Eventos del dia ");
                    builder.setColor(Color.rgb(80, 0, 20));
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    builder.setLights(Color.BLUE, 1000, 1000);
                    builder.setVibrate(new long[]{1000, 1000, 1000, 1000});
                    builder.setDefaults(Notification.DEFAULT_SOUND);
                    builder.setStyle(new NotificationCompat.InboxStyle().addLine(" Hey! Hay " + listapermisos.size() + " eventos programados para el ").addLine("dia de hoy enterate de cuales son"));
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
                    notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
                }
                if (listapermisos.size()<=0){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_event_black_24dp);
                    builder.setContentTitle("Eventos del dia ");
                    builder.setColor(Color.rgb(80, 0, 20));
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    builder.setLights(Color.BLUE, 1000, 1000);
                    builder.setVibrate(new long[]{1000, 1000, 1000, 1000});
                    builder.setDefaults(Notification.DEFAULT_SOUND);
                    builder.setStyle(new NotificationCompat.InboxStyle().addLine(" Hey! lo sentimos, no hay").addLine(" eventos programados para el ").addLine("dia de hoy,intente mañana"));
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
                    notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
                }
            }
            //    recyclerViewzonas.setAdapter(adapter);
            System.out.println("la lista contiene"+listapermisos.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
