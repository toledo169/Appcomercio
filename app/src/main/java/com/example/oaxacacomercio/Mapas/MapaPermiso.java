package com.example.oaxacacomercio.Mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

// classes needed to initialize map
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
// classes needed to add the location component
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;

import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.R;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.lang.ref.WeakReference;
import java.util.List;


public class MapaPermiso extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    String latitud, longitudinicio;
    String latitufinal, longitudfinal;
    // variables needed to initialize navigation
    private Button button;
    private FloatingActionButton botoninicio;

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        String giro = getIntent().getExtras().getString("giro");
        latitud = getIntent().getExtras().getString("latitud");
        longitudinicio = getIntent().getExtras().getString("longitud");
        latitufinal = getIntent().getExtras().getString("latitud_fin");
        longitudfinal = getIntent().getExtras().getString("longitud_fin");
        setContentView(R.layout.activity_mapa_permiso);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Toolbar toolbar = findViewById(R.id.toolbarperm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(giro);
        mapView = findViewById(R.id.mapapermisos);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        trazarruta(Double.parseDouble(latitud), Double.parseDouble(longitudinicio));
        //   trazarrutaubicacion(Double.parseDouble(latitud),Double.parseDouble(longitudinicio));
    }

    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        //agregarmarcador();
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                addDestinationIconSymbolLayer(style);
                botoninicio = findViewById(R.id.recorridoinicial);
                botoninicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                                MapaPermiso.this, R.style.BottomSheetDialogTheme);
                        View bottomsheetview = LayoutInflater.from(getApplicationContext())
                                .inflate(
                                        R.layout.layout_bottom_sheet_maps,
                                        (LinearLayout) findViewById(R.id.bottomSheetContainermapa)
                                );
                        bottomsheetview.findViewById(R.id.inicioruta).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (PermissionsManager.areLocationPermissionsGranted(MapaPermiso.this)) {
                                    locationComponent = mapboxMap.getLocationComponent();
                                    locationComponent.setCameraMode(CameraMode.TRACKING);
                                    Point destinationPoint = Point.fromLngLat(Double.parseDouble(longitudinicio),Double.parseDouble(latitud));
                                    Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),locationComponent.getLastKnownLocation().getLatitude());
                                    getRuta(originPoint, destinationPoint);
                                    //    boolean simulateRoute = true;
                                    //  NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                    //        .directionsRoute(currentRoute)
                                    //      .shouldSimulateRoute(simulateRoute)
                                    //    .build();
                                    // NavigationLauncher.startNavigation(MapaPermisoActivity.this, options);
                                    //System.out.println("**************************************************************************************");
                                    //System.out.println("La ubicacion de inicio es"+ locationComponent.getLastKnownLocation().getLongitude());
                                    // System.out.println("La latitud de inicio es"+ locationComponent.getLastKnownLocation().getLatitude());
                                    // System.out.println("**************************************************************************************");
                                } else {
                                    permissionsManager = new PermissionsManager(MapaPermiso.this);
                                    permissionsManager.requestLocationPermissions(MapaPermiso.this);
                                }
                                bottomSheetDialog.dismiss();
                            }
                        });
                        bottomsheetview.findViewById(R.id.finalruta).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (PermissionsManager.areLocationPermissionsGranted(MapaPermiso.this)) {
                                    locationComponent = mapboxMap.getLocationComponent();
                                    locationComponent.setCameraMode(CameraMode.TRACKING);
                                    Point destinationPoint = Point.fromLngLat(Double.parseDouble(longitudfinal),Double.parseDouble(latitufinal));
                                    Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),locationComponent.getLastKnownLocation().getLatitude());
                                    getRutafinal(originPoint, destinationPoint);
                                 /*   boolean simulateRoute = true;
                                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                           .directionsRoute(currentRoute)
                                        .shouldSimulateRoute(simulateRoute)
                                      .build();
                                    NavigationLauncher.startNavigation(MapaPermisoActivity.this, options);
                                    System.out.println("**************************************************************************************");
                                    System.out.println("La ubicacion de inicio es"+ locationComponent.getLastKnownLocation().getLongitude());
                                    System.out.println("La latitud de inicio es"+ locationComponent.getLastKnownLocation().getLatitude());
                                    System.out.println("**************************************************************************************");
                                */
                                } else {

                                    permissionsManager = new PermissionsManager(MapaPermiso.this);
                                    permissionsManager.requestLocationPermissions(MapaPermiso.this);
                                }
                                bottomSheetDialog.dismiss();
                            }
                        });
                        bottomSheetDialog.setContentView(bottomsheetview);
                        bottomSheetDialog.show();
                    }

                });

            }
        });
        //
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitudinicio))).title("inicio"));
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitufinal), Double.parseDouble(longitudfinal))).title("final"));

        System.out.println("la latitud es:" + Double.parseDouble(latitud));
        System.out.println("long" + Double.parseDouble(longitudinicio));
        System.out.println("***************************");
        System.out.println("latitud final es:" + Double.parseDouble(latitufinal));
        System.out.println("longitud final es" + Double.parseDouble(longitudfinal));
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            User usuario = new User(this);
            Intent goMain = new Intent(MapaPermiso.this, Ventanas.class);
            goMain.putExtra(HomeFragment.apellido_paternos, usuario.getApellido_paterno());
            goMain.putExtra(HomeFragment.apellido_maternos, usuario.getApellido_materno());
            goMain.putExtra(HomeFragment.nombres, usuario.getNombre());
            goMain.putExtra(HomeFragment.correo, usuario.getCorreoelectronico());
            goMain.putExtra(HomeFragment.cargo, usuario.getCargo());
            goMain.putExtra(HomeFragment.municipio, usuario.getMunicipio());
         //   goMain.putExtra(HomeFragment.fotoperfil, usuario.getImage());
            //   finish();
            startActivity(goMain);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        @SuppressWarnings( {"MissingPermission"})
        public boolean onMapClick(@NonNull LatLng point) {
            Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
            Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                    locationComponent.getLastKnownLocation().getLatitude());
            GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
            if (source != null) {
                source.setGeoJson(Feature.fromGeometry(destinationPoint));
            }
            getRoute(originPoint, destinationPoint);
            button.setEnabled(true);
            return true;
        }*/
    public void trazarruta(Double lat, Double longi) {
        Point destinationPoint = Point.fromLngLat(Double.parseDouble(longitudinicio), Double.parseDouble(latitud));
        Point originPoint = Point.fromLngLat(Double.parseDouble(longitudfinal), Double.parseDouble(latitufinal));
        getRoute(originPoint, destinationPoint);
    }

    private void getRuta(Point origin, Point destination) {

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
    private void getRutafinal(Point origin, Point destination) {

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
    private void getRoute(Point origin, Point destination) {

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
