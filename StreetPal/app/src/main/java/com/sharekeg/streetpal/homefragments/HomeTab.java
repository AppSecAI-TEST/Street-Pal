package com.sharekeg.streetpal.homefragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.safeplace.SafePlaceActivity;
import com.sharekeg.streetpal.userinfoforlogin.CurrenLocation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTab extends Fragment implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ImageView imageView1, imageView2, imageView3;
    private View myFragmentView;
    private TextView tvWelcomeUser, tvHint, textView1, textView2, textView3;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double lat;
    private StreetPalGuide streetPalGuide;
    private double lon;
    AlertDialog alert;
    private EditText etName;
    Retrofit retrofit;
    AlertDialog Dilaog;
    private RelativeLayout firstCardLayout, secondCardLayout, thirdCardLayout;

    public HomeTab() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        myFragmentView = inflater.inflate(R.layout.fragment_home_tab, container, false);

        tvWelcomeUser = (TextView) myFragmentView.findViewById(R.id.tv_welcome_user);
        tvHint = (TextView) myFragmentView.findViewById(R.id.hint);
        textView1 = (TextView) myFragmentView.findViewById(R.id.textView1);
        textView2 = (TextView) myFragmentView.findViewById(R.id.textView2);
        textView3 = (TextView) myFragmentView.findViewById(R.id.textView3);
        imageView1 = (ImageView) myFragmentView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) myFragmentView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) myFragmentView.findViewById(R.id.imageView3);

        firstCardLayout = (RelativeLayout) myFragmentView.findViewById(R.id.first_card);
        firstCardLayout.setOnClickListener(this);

        secondCardLayout = (RelativeLayout) myFragmentView.findViewById(R.id.second_card);
        secondCardLayout.setOnClickListener(this);

        thirdCardLayout = (RelativeLayout) myFragmentView.findViewById(R.id.third_card);
        thirdCardLayout.setOnClickListener(this);


        return myFragmentView;
    }


    private void startStreetPalGuide() {

        //Starting the street pal fragment when user click call for help button

        StreetPalGuide streetPalGuideFragment = new StreetPalGuide();
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.rlFragments, streetPalGuideFragment)
                .commit();

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_card:
                startStreetPalGuide();

                break;
            case R.id.second_card:
                startStreetPalGuide();

                break;
            case R.id.third_card:
                startListOfChoicesFragment();
                break;

        }
    }

    private void startListOfChoicesFragment() {


        ListOfChoices listOfChoicesFragment = new ListOfChoices();
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.rlFragments, listOfChoicesFragment)
                .commit();




    }
}