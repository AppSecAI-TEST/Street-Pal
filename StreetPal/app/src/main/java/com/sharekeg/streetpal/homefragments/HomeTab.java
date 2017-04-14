package com.sharekeg.streetpal.homefragments;


import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Dialogs.DialogActivity;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.Login.LoginActivity;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Registration.ConfirmationActivity;
import com.sharekeg.streetpal.Registration.SignUpActivity;
import com.sharekeg.streetpal.Safe_Places.Safe_palce_Activity;
import com.sharekeg.streetpal.userinfoforlogin.CurrenLocation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTab extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION =99 ;
    private ImageView ivCallForHelp;
    private View myFragmentView;
    private TextView tvWelcomeUser, tvName;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double lat;
    private double lon;
    AlertDialog alert;
    private EditText etName;
    Retrofit retrofit;
    AlertDialog Dilaog;

    public HomeTab() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        myFragmentView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        ivCallForHelp = (ImageView) myFragmentView.findViewById(R.id.ivCallForHelp);
        tvName = (TextView) myFragmentView.findViewById(R.id.tvName);
        tvWelcomeUser = (TextView) myFragmentView.findViewById(R.id.tv_welcome_user);
        HomeActivity activity=(HomeActivity)getActivity();
        String userName= activity.sendUserName();
        tvName.setText(userName);
//        etName = (EditText) getActivity().findViewById(R.id.etName);
//        Name = etName.getText().toString();
//        tvWelcomeUser.setText("Welcome");
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
//                Toast.makeText(getContext(), "Latitude is " + lat, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Longtiude is " + lon, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        ConfigarButton();

        return myFragmentView;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 10:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    ConfigarButton();
//                return;
//        }
//    }

    private void ConfigarButton() {
        ivCallForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = li.inflate(R.layout.activity_dialog, null);
                TextView tvhelp = (TextView) view.findViewById(R.id.tvhelp);
                TextView message = (TextView) view.findViewById(R.id.message);
                Button btncancel = (Button) view.findViewById(R.id.btncancel);
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dilaog.dismiss();
                        alert.dismiss();


                    }
                });


                alert = dialog.create();
                alert.show();
                // opreation after some seconds
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (alert.isShowing()) {
                            alert.dismiss();

                            //   after 10s
                            OpenSavePlace();
                        }
                    }
                };
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        handler.removeCallbacks(runnable);
                    }
                });
                handler.postDelayed(runnable, 10000);

                dialog.setView(view);

                Dilaog = dialog.create();
                Dilaog.show();


                //   currenLocation();


            }

        });

       // locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.INTERNET
//            }, 10);
//        }
//        return;
//    }

    private void currenLocation(double lat, double lon) {
        ApiInterface mApi = retrofit.create(ApiInterface.class);
        Call<CurrenLocation> mycall = mApi.SetLocation(new CurrenLocation(lat, lon));
        mycall.enqueue(new Callback<CurrenLocation>() {
            @Override
            public void onResponse(Call<CurrenLocation> call, Response<CurrenLocation> response) {

            }

            @Override
            public void onFailure(Call<CurrenLocation> call, Throwable t) {

            }
        });
    }


    public void OpenSavePlace() {

        Intent intent = new Intent(getActivity(), Safe_palce_Activity.class);
        startActivity(intent);
    }

}
