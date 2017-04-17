package com.sharekeg.streetpal.Home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.homefragments.GuideTab;
import com.sharekeg.streetpal.homefragments.HomeTab;
import com.sharekeg.streetpal.homefragments.InfoTab;
import com.sharekeg.streetpal.homefragments.MapTab;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity implements InfoTab.OnFragmentInteractionListener, MapTab.OnFragmentInteractionListener, GuideTab.OnFragmentInteractionListener {
    private String token;
    private Button  btnHome, btnNavigation, btnInformation, btnPosts;
    HomeTab homeTab;
    MapTab mapTab;
    InfoTab infoTab;
    GuideTab guideTab;
    private static String fileProfiePhotoPath = null;
    private int REQUEST_TAKE_profile_PHOTO = 2;
    private int RESULT_LOAD_profile_IMAGE = 2;
    ImageView ivAddUserPhoto;
    ApiInterface apiInterface;
    private Retrofit retrofit;
   private  String userName = "Welcome Mohamed!";

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //  token = getIntent().getExtras().getString("Token");
        btnHome = (Button) findViewById(R.id.btnHome);
        btnNavigation = (Button) findViewById(R.id.btnNavigation);
        btnInformation = (Button) findViewById(R.id.btnInformation);
        btnPosts = (Button) findViewById(R.id.btnPosts);
        ivAddUserPhoto = (ImageView) findViewById(R.id.ivAddUserPhoto);
        ivAddUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Choose Option")
                        .setItems(new String[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takePictureIntent, REQUEST_TAKE_profile_PHOTO);


                                } else {
                                    Intent intent = new Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent, RESULT_LOAD_profile_IMAGE);

                                }

                            }
                        }).create().show();

            }
        });
        homeTab = new HomeTab();
        mapTab = new MapTab();
        infoTab = new InfoTab();
        guideTab = new GuideTab();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rlFragments, homeTab)
                .commit();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeTab();

            }
        });
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationTab();
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoTab();
            }
        });

        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostsTab();
            }
        });



        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
    public String sendUserName() {
        return userName;
    }

    private void openPostsTab() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.rlFragments, guideTab)
                .commit();
    }

    private void openInfoTab() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.rlFragments, infoTab)
                .commit();
    }

    private void openNavigationTab() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.rlFragments, mapTab)
                .commit();
    }

    private void openHomeTab() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.rlFragments, homeTab)
                .commit();


    }


    @Override
    public void onBackPressed() {
        if (!(getSupportFragmentManager()
                .findFragmentById(R.id.rlFragments)
                instanceof HomeTab)) {
            openHomeTab();
        } else
            super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.36/v0/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiInterface mApiService = retrofit.create(ApiInterface.class);
//        Call<UserInfoForLogin> mSerivce = mApiService.getUser(token);
//        mSerivce.enqueue(new Callback<UserInfoForLogin>() {
//            @Override
//            public void onResponse(Call<UserInfoForLogin> call, Response<UserInfoForLogin> response) {
//                Toast.makeText(HomeActivity.this, "UserName " + response.body().getEmail(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoForLogin> call, Throwable t) {
//                Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });


    private void uploadProfilePhoto(final String fileProfiePhotoPath) {


        File file = new File(fileProfiePhotoPath);
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), file);

// Synchronous.
        try {
            Response<RequestBody> response = apiInterface.uploadprofilePhoto(photoBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

// Asynchronous.
        apiInterface.uploadprofilePhoto(photoBody).enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Snackbar.make(ivAddUserPhoto, "Failed to upload Profile-Photo", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadProfilePhoto(fileProfiePhotoPath);
                        //pDialog.show();
                    }
                }).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            if (requestCode == REQUEST_TAKE_profile_PHOTO && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileProfiePhotoPath = cursor.getString(columnIndex);
                cursor.close();

                ivAddUserPhoto.setImageURI(selectedImage);
                Toast.makeText(this, "profile Photo is uploaded Successfully", Toast.LENGTH_LONG).show();
                //            uploadProfilePhoto(fileProfiePhotoPath);


            }
            if (requestCode == RESULT_LOAD_profile_IMAGE && resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor == null)
                    return;

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileProfiePhotoPath = cursor.getString(columnIndex);
                cursor.close();

                ivAddUserPhoto.setImageURI(selectedImage);
                Toast.makeText(this, "profile Photo is uploaded Successfully", Toast.LENGTH_LONG).show();
                //            uploadProfilePhoto(fileProfiePhotoPath);


            }
        } catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        }
    }


}


