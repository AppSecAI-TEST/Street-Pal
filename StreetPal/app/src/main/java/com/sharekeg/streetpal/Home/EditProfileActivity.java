package com.sharekeg.streetpal.Home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Settings.SettingsActivity;
import com.sharekeg.streetpal.userinfoforeditingprofile.UsersInfoForEditingProfile;
import com.sharekeg.streetpal.userinfoforlogin.UserInfoForLogin;
import com.sharekeg.streetpal.userinfoforsignup.UsersInfoForSignUp;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditProfileActivity extends AppCompatActivity {
    private EditText etUserName, etEmail, etPhoneNumber, etAge, etWork, etfullName;
    private TextView tvCancel, tvSex,tvDone;
    private ImageView  ivChanceProfilePicture;
    private String mediaPath, email, userName, phone, age, work, fullname;
    View focusView = null;
    ApiInterface apiInterface;
    private ProgressDialog pDialog;
    private Retrofit retrofit;
    private String fileProfiePhotoPath=null;
    private Uri selectedImage;
    private File f;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sharekeg.com:8088/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etUserName = (EditText) findViewById(R.id.userNameEditTextId);
        etEmail = (EditText) findViewById(R.id.mailEditTextId);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumberEditTextId);
        tvSex = (TextView) findViewById(R.id.sexEditTextId);
        etAge = (EditText) findViewById(R.id.ageEditTextId);
        etWork = (EditText) findViewById(R.id.workEditTextId);
        etfullName = (EditText) findViewById(R.id.fullnameEditTextId);
        ivChanceProfilePicture = (ImageView) findViewById(R.id.changeProfileImg);
        tvCancel = (TextView) findViewById(R.id.backTextId);
        tvDone = (TextView) findViewById(R.id.tvDoneId);


//        token = getIntent().getExtras().getString("Token");
                ApiInterface mApiService = retrofit.create(ApiInterface.class);
        Call<UserInfoForLogin> mSerivce = mApiService.displayUserInfo();
        mSerivce.enqueue(new Callback<UserInfoForLogin>() {
            @Override
            public void onResponse(Call<UserInfoForLogin> call, Response<UserInfoForLogin> response) {
                etEmail.setText(response.body().getEmail());
                etUserName.setText(response.body().getName().getFirst());
                tvSex.setText(response.body().getGender());




            }

            @Override
            public void onFailure(Call<UserInfoForLogin> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        });


        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(EditProfileActivity.this)
                        .setTitle(R.string.saveChangesdialoge)
                        .setMessage(R.string.saveChangesMessage)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                attemptEditProfile();
                                Intent i = new Intent(EditProfileActivity.this, SettingsActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(EditProfileActivity.this, SettingsActivity.class);
                                startActivity(i);

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();



            }
        });

        ivChanceProfilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });


    }

    private void attemptEditProfile() {


        boolean mCancel = this.editProfileValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            pDialog = new ProgressDialog(EditProfileActivity.this);
            pDialog.setMessage(getText(R.string.dialog_logging));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


            uploadProfilePhoto(fileProfiePhotoPath);

            editCurrentUser(email, userName, age, work, fullname, phone);


        }


    }

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
                                Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(i);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {

                Snackbar.make(etfullName, R.string.err_toast, Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadProfilePhoto(fileProfiePhotoPath);
                        pDialog.show();
                    }
                }).show();
            }
        });



    }


    private boolean editProfileValidation() {


        userName = etUserName.getText().toString();
        fullname = etfullName.getText().toString();
        email = etEmail.getText().toString();
        phone = etPhoneNumber.getText().toString();
        age = etAge.getText().toString();
        work = etWork.getText().toString();
        boolean cancel = false;
        if (!TextUtils.isEmpty(fullname) && !isPasswordValid(fullname)) {
            etfullName.setError(getText(R.string.loginpass_validation));
            focusView = etfullName;
            cancel = true;

        }
        if (TextUtils.isEmpty(age)) {
            etAge.setError(getText(R.string.signup_age_validation));
            focusView = etAge;
            cancel = true;
        }

        if (TextUtils.isEmpty(work)) {
            etWork.setError(getText(R.string.signup_work_validation));
            focusView = etWork;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhoneNumber.setError(getText(R.string.signup_phonenumber_validation));
            focusView = etPhoneNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(userName)) {
            etUserName.setError(getText(R.string.signup_username_validation));
            focusView = etUserName;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getText(R.string.signup_emptymail_validation));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getText(R.string.signup_invalidmail_validation));
            focusView = etEmail;
            cancel = true;
        }
        return cancel;
    }


    private boolean isEmailValid(String email) {

        return email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 6;
    }


    private void editCurrentUser(final String email, final String userName, final String age, final String work, String password, final String phoneNumber) {

        ApiInterface mApi = retrofit.create(ApiInterface.class);
        Call<UsersInfoForEditingProfile> mycall = mApi.editCurrentUser(new UsersInfoForEditingProfile(email, userName, phoneNumber, age, work, password));
        mycall.enqueue(new Callback<UsersInfoForEditingProfile>() {
            @Override
            public void onResponse(Call<UsersInfoForEditingProfile> call, Response<UsersInfoForEditingProfile> response) {
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UsersInfoForEditingProfile> call, Throwable t) {
                pDialog.dismiss();

                Snackbar.make(etfullName, R.string.txt_connection_Failed, Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptEditProfile();
                        pDialog.show();
                    }
                }).show();
            }
        });

    }


    private void selectImage() {
        final CharSequence[] options = {getApplicationContext().getResources().getString(R.string.option_camera)
                , getApplicationContext().getResources().getString(R.string.option_gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditProfileActivity.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getApplicationContext().getResources().getString(R.string.option_camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals(getApplicationContext().getResources().getString(R.string.option_gallery))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        fileProfiePhotoPath = cursor.getString(columnIndex);
                        cursor.close();
                        selectedImage = imageReturnedIntent.getData();

                        ivChanceProfilePicture.setImageURI(selectedImage);
                    }

                    break;
                case 2:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        fileProfiePhotoPath = cursor.getString(columnIndex);
                        cursor.close();
                        selectedImage = imageReturnedIntent.getData();
                        ivChanceProfilePicture.setImageURI(selectedImage);
                    }
                    break;
            }

        } catch (Exception e) {
            Toast.makeText(this, R.string.smthing_went_wrong, Toast.LENGTH_LONG).show();
        }
    }


}
