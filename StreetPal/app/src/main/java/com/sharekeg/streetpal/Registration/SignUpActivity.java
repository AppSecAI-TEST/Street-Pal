package com.sharekeg.streetpal.Registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.EditProfileActivity;
import com.sharekeg.streetpal.userinfoforsignup.UsersInfoForSignUp;
import com.sharekeg.streetpal.Login.LoginActivity;
import com.sharekeg.streetpal.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView IV_profile;
    private Button btnSignUp, btn_UploadNationalCard;
    Retrofit retrofit;
    private Spinner spinner;
    private String name,email, userName, phone, age, nationalId, gender, work, password;
    View focusView = null;
    ApiInterface apiInterface;
    private ProgressDialog pDialog;
    private EditText etEmail, etName, etUserName, etPhone, etAge, etNationalId, etWork, etPassword;
    private static String fileNationaPhotoPath = null;
    private static String fileProfiePhotoPath = null;
    private static final int REQUEST_TAKE_National_Id_PHOTO = 1;
    private static int RESULT_LOAD_National_Card_IMAGE = 1;
    private TextView tvback, TV_upload, TV_sex;
    private int REQUEST_TAKE_profile_PHOTO = 2;
    private int RESULT_LOAD_profile_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // object of retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://sharekeg.com:8088/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etEmail = (EditText) findViewById(R.id.etemail);
        etAge = (EditText) findViewById(R.id.etage);
        etName=(EditText)findViewById(R.id.etName);
        etWork = (EditText) findViewById(R.id.etWork);
        etPassword = (EditText) findViewById(R.id.TV_passward);
        etUserName = (EditText) findViewById(R.id.etuserName);
        etPhone = (EditText) findViewById(R.id.etphone);
        etNationalId = (EditText) findViewById(R.id.etnationalid);
        TV_sex = (TextView) findViewById(R.id.TV_sex);
        IV_profile = (ImageView) findViewById(R.id.IV_profile);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        tvback = (TextView) findViewById(R.id.tvback);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btn_UploadNationalCard = (Button) findViewById(R.id.btn_UploadNationalCard);

        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                attemptSignUp();
                // pDialog.show();


            }
        });
        btn_UploadNationalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setTitle(R.string.text_dialog_choose)
                        .setItems(new String[]{getApplicationContext().getResources().getString(R.string.option_camera)
                                , getApplicationContext().getResources().getString(R.string.option_gallery)}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takePictureIntent, REQUEST_TAKE_National_Id_PHOTO);


                                } else {
                                    Intent intent = new Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent, RESULT_LOAD_National_Card_IMAGE);

                                }

                            }
                        }).create().show();

            }
        });


        IV_profile
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setTitle(R.string.text_dialog_choose)
                                .setItems(new String[]{String.valueOf(R.string.option_camera), String.valueOf(R.string.option_gallery)}, new DialogInterface.OnClickListener() {
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


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Sex, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    private void attemptSignUp() {

        boolean mCancel = this.signupValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage(String.valueOf(R.string.dialog_signing_up));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            Intent i = new Intent(SignUpActivity.this, ConfirmationActivity.class);
            i.putExtra("UserNameWelcomeText", userName);
            startActivity(i);
            pDialog.dismiss();
//            uploadNationalIdPhoto(fileNationaPhotoPath);
//            uploadProfilePhoto(fileProfiePhotoPath);
            //  insertNewUser(name,email, userName, age, nationalId, work, password, phone, gender);


        }


    }

    private boolean signupValidation() {
        name=etName.getText().toString();
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        phone = etPhone.getText().toString();
        gender=TV_sex.getText().toString();
        age = etAge.getText().toString();
        nationalId = etNationalId.getText().toString();
        work = etWork.getText().toString();
        boolean cancel = false;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getText(R.string.loginpass_validation));
            focusView = etPassword;
            cancel = true;

        }
        if (TextUtils.isEmpty(name)) {
            etName.setError(getText(R.string.signup_name_validation));
            focusView = etName;
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
        if (TextUtils.isEmpty(nationalId)) {
            etNationalId.setError(getText(R.string.signup_nationalid_validation));
            focusView = etNationalId;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(getText(R.string.signup_phonenumber_validation));
            focusView = etPhone;
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


    private void insertNewUser(final String name,final String email, final String userName, final String age, final String nationalId, final String work, String password, final String phone, final String gender) {

        ApiInterface mApi = retrofit.create(ApiInterface.class);
        Call<UsersInfoForSignUp> mycall = mApi.insertUserinfo(new UsersInfoForSignUp(name,email, userName, phone, age, nationalId, gender, work, password));
        mycall.enqueue(new Callback<UsersInfoForSignUp>() {
            @Override
            public void onResponse(Call<UsersInfoForSignUp> call, Response<UsersInfoForSignUp> response) {
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UsersInfoForSignUp> call, Throwable t) {
                pDialog.dismiss();

                Snackbar.make(btnSignUp, R.string.txt_connection_Failed, Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptSignUp();
                        pDialog.show();
                    }
                }).show();
            }
        });


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
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Snackbar.make(btnSignUp, R.string.err_toast, Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadProfilePhoto(fileProfiePhotoPath);
                        //pDialog.show();
                    }
                }).show();
            }
        });


    }

    private void uploadNationalIdPhoto(final String fileNationaPhotoPath) {
        File file = new File(fileNationaPhotoPath);

        RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), file);

// Synchronous.
        try {
            Response<RequestBody> response = apiInterface.uploadNationalIdPhoto(photoBody).execute();
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
                Snackbar.make(btnSignUp, "Failed to upload National-Id-Photo", Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadNationalIdPhoto(fileNationaPhotoPath);
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
            if (requestCode == REQUEST_TAKE_National_Id_PHOTO && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileNationaPhotoPath = cursor.getString(columnIndex);
                cursor.close();
                Toast.makeText(this, "National Card  is uploaded Successfully", Toast.LENGTH_LONG).show();


            }
            if (requestCode == RESULT_LOAD_National_Card_IMAGE && resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor == null)
                    return;

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileNationaPhotoPath = cursor.getString(columnIndex);
                cursor.close();
                Toast.makeText(this, "National Card is uploaded Successfully", Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.smthing_went_wrong, Toast.LENGTH_LONG).show();
        }
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

                IV_profile.setImageURI(selectedImage);
                Toast.makeText(this, R.string.uploaded_successfully, Toast.LENGTH_LONG).show();


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

                IV_profile.setImageURI(selectedImage);
                Toast.makeText(this, R.string.uploaded_successfully, Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.smthing_went_wrong, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = spinner.getSelectedItem().toString();
        TV_sex.setText(text);
        TV_sex.getText();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
