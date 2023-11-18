package com.example.filmatic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmatic.Adapter.FilmListAdapter;
import com.example.filmatic.Domain.Datum;
import com.example.filmatic.Domain.FilmItem;
import com.example.filmatic.Domain.ListFilm;
import com.example.filmatic.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpcoming, adapterTopRated, adapterFanFav, adapterBoxOffice;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpcoming, recyclerViewTopRated, recyclerViewFanFav, recyclerViewBoxOffice;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3, mStringRequest4, mStringRequest5;
    private ProgressBar loading1, loading2, loading3, loading4, loading5;
    private ImageView UserProfileButton;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        
        sendRequest1();
        sendRequest2();
        sendRequest3();
        sendRequest4();
        sendRequest5();
        bottomBarButtons();
    }

    private void initView() {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewUpcoming = findViewById(R.id.view3);
        recyclerViewTopRated = findViewById(R.id.view4);
        recyclerViewFanFav = findViewById(R.id.view5);
        recyclerViewBoxOffice = findViewById(R.id.view2);

        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFanFav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBoxOffice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading3);
        loading3 = findViewById(R.id.loading4);
        loading4 = findViewById(R.id.loading5);
        loading5 = findViewById(R.id.loading2);
        UserProfileButton = findViewById(R.id.userProfileBtn);

        searchView = findViewById(R.id.searchBar);
    }


    private void sendRequest1() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading1.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterNewMovies = new FilmListAdapter(items);
                recyclerViewNewMovies.setAdapter(adapterNewMovies);

            }
        }, error -> {
            Log.i("uilover", "sendRequest1: " + error.toString());
            loading1.setVisibility(View.GONE);

        });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=4", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading2.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterUpcoming = new FilmListAdapter(items);
                recyclerViewUpcoming.setAdapter(adapterUpcoming);

            }
        }, error -> {
            Log.i("uilover", "sendRequest1: " + error.toString());
            loading2.setVisibility(View.GONE);

        });
        mRequestQueue.add(mStringRequest2);
    }

    private void sendRequest3() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);
        mStringRequest3 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=5", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading3.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterTopRated = new FilmListAdapter(items);
                recyclerViewTopRated.setAdapter(adapterTopRated);

            }
        }, error -> {
            Log.i("uilover", "sendRequest1: " + error.toString());
            loading3.setVisibility(View.GONE);

        });
        mRequestQueue.add(mStringRequest3);
    }

    private void sendRequest4() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading4.setVisibility(View.VISIBLE);
        mStringRequest4 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=5", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading4.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterFanFav = new FilmListAdapter(items);
                recyclerViewFanFav.setAdapter(adapterFanFav);

            }
        }, error -> {
            Log.i("uilover", "sendRequest1: " + error.toString());
            loading4.setVisibility(View.GONE);

        });
        mRequestQueue.add(mStringRequest4);
    }

    private void sendRequest5() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading5.setVisibility(View.VISIBLE);
        mStringRequest5 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading5.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterBoxOffice = new FilmListAdapter(items);
                recyclerViewBoxOffice.setAdapter(adapterBoxOffice);

            }
        }, error -> {
            loading5.setVisibility(View.GONE);

        });
        mRequestQueue.add(mStringRequest5);
    }

    private void bottomBarButtons() {
        UserProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });
    }


}


