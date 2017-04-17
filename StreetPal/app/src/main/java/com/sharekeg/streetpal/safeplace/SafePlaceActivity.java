package com.sharekeg.streetpal.safeplace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SafePlaceActivity extends AppCompatActivity {
    private RecyclerView recylcerView;
    private Button btnCallVolunteer,btnMarkSafe;
    private ProgressDialog progressDialoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_palce_);
        progressDialoge = new ProgressDialog(this);
        progressDialoge.setMessage("Loading...");
        progressDialoge.setCancelable(false);
        progressDialoge.show();
        recylcerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recylcerView.setLayoutManager(new LinearLayoutManager(this));
        btnMarkSafe=(Button)findViewById(R.id.btnMarkSafe);
        btnMarkSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SafePlaceActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        btnCallVolunteer = (Button) findViewById(R.id.btnCallVolunteer);
        btnCallVolunteer.setEnabled(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface myAPI = retrofit.create(ApiInterface.class);
        Call<List<Guide>> myCall = myAPI.getGuide();
        myCall.enqueue(new Callback<List<Guide>>() {
            @Override
            public void onResponse(Call<List<Guide>> call, Response<List<Guide>> response) {
                progressDialoge.dismiss();
                List<Guide> myResponse = response.body();
                GuideAdapter adapter = new GuideAdapter(SafePlaceActivity.this, myResponse);

                recylcerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Guide>> call, Throwable t) {
                progressDialoge.dismiss();
                Toast.makeText(SafePlaceActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

