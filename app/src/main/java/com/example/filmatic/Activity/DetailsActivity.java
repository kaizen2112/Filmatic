package com.example.filmatic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.filmatic.Adapter.ImageListAdapter;
import com.example.filmatic.Domain.FilmItem;
import com.example.filmatic.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieDateTxt, movieSummaryTxt, movieActorsInfo, ImdbRating, MetaScore, movieDirector, movieWriter, genre1, genre2, genre3, movieAwards;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ToggleButton btnWatching, btnCompleted, btnOnHold, btnDropped;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference moviesRef;

    private SharedPreferences preferences;
    private String PREF_NAME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        idFilm = getIntent().getIntExtra("id", 0);


        // Modify the preferences key based on the movie ID
        PREF_NAME = "MoviePreferences_" + idFilm;

        initView();
        toggleButtonsAction(mAuth);
        sendRequest();

    }
    private void toggleButtonsAction(FirebaseAuth mAuth) {

        // Use the modified preferences key with the movie ID
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        // Initialize the checked state of the buttons based on preferences
        btnWatching.setChecked(preferences.getBoolean("watching", false));
        btnCompleted.setChecked(preferences.getBoolean("completed", false));
        btnOnHold.setChecked(preferences.getBoolean("onHold", false));
        btnDropped.setChecked(preferences.getBoolean("dropped", false));

        btnWatching.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the state change
            if (isChecked) {
                // Save the movie ID for 'Watching'
                btnWatching.setTextColor(getResources().getColor(R.color.black));
                btnWatching.setTextSize(14);  // Adjust the text size for checked state

                updateMovieStatus(mAuth, "watching", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("watching", true);
                editor.apply();
            } else {
                // Handle when it's unchecked
                btnWatching.setTextColor(getResources().getColor(R.color.white));
                btnWatching.setTextSize(12);  // Adjust the text size for unchecked state

                // Remove the movie ID for 'Watching'
                updateMovieStatus(mAuth, "watching", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("watching", false);
                editor.apply();
            }
        });
        btnCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the state change, e.g., save the movie ID if it's checked
// Handle the state change
            if (isChecked) {
                // Save the movie ID for 'Watching'
                btnCompleted.setTextColor(getResources().getColor(R.color.black));
                btnCompleted.setTextSize(13);  // Adjust the text size for checked state

                updateMovieStatus(mAuth, "completed", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("completed", true);
                editor.apply();
            } else {
                // Handle when it's unchecked
                btnCompleted.setTextColor(getResources().getColor(R.color.white));
                btnCompleted.setTextSize(12);  // Adjust the text size for unchecked state

                // Remove the movie ID for 'Watching'
                updateMovieStatus(mAuth, "completed", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("completed", false);
                editor.apply();
            }
        });
        btnOnHold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the state change, e.g., save the movie ID if it's checked
            // Handle the state change
            if (isChecked) {
                // Save the movie ID for 'Watching'
                btnOnHold.setTextColor(getResources().getColor(R.color.black));
                btnOnHold.setTextSize(14);  // Adjust the text size for checked state

                updateMovieStatus(mAuth, "onHold", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("onHold", true);
                editor.apply();
            } else {
                // Handle when it's unchecked
                btnOnHold.setTextColor(getResources().getColor(R.color.white));
                btnOnHold.setTextSize(12);  // Adjust the text size for unchecked state

                // Remove the movie ID for 'Watching'
                updateMovieStatus(mAuth, "onHold", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("onHold", false);
                editor.apply();
            }
        });
        btnDropped.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the state change, e.g., save the movie ID if it's checked
            // Handle the state change
            if (isChecked) {
                // Save the movie ID for 'Watching'
                btnDropped.setTextColor(getResources().getColor(R.color.black));
                btnDropped.setTextSize(14);  // Adjust the text size for checked state

                updateMovieStatus(mAuth, "dropped", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("dropped", true);
                editor.apply();
            } else {
                // Handle when it's unchecked
                btnDropped.setTextColor(getResources().getColor(R.color.white));
                btnDropped.setTextSize(12);  // Adjust the text size for unchecked state

                // Remove the movie ID for 'Watching'
                updateMovieStatus(mAuth, "dropped", isChecked);

                // Save the state in preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("dropped", false);
                editor.apply();
            }
        });
    }

    private void updateMovieStatus(FirebaseAuth mAuth, String status, boolean isChecked) {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Get the UID of the current user
            String uid = user.getUid();

            // Reference to the current user's data in the Realtime Database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            // Reference to the "movies" node under the user's data
            DatabaseReference moviesRef = userRef.child("movies");

            // Check the state of the toggle button
            if (isChecked) {
                // If checked, add the movie ID under the specified status
                moviesRef.child(status).child(String.valueOf(idFilm)).setValue(true);
            } else {
                // If unchecked, remove the movie ID under the specified status
                moviesRef.child(status).child(String.valueOf(idFilm)).removeValue();
            }
        }
    }





    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, response -> {
            Gson gson= new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            FilmItem item = gson.fromJson(response, FilmItem.class);

            Glide.with(DetailsActivity.this)
                    .load(item.getPoster())
                    .into(pic1);

            Glide.with(DetailsActivity.this)
                    .load(item.getPoster())
                    .into(pic2);

            titleTxt.setText(item.getTitle());
            movieRateTxt.setText(item.getRated());
            movieTimeTxt.setText(item.getRuntime());
            movieDateTxt.setText(item.getReleased());

            ImdbRating.setText(item.getImdbRating());
            MetaScore.setText(item.getMetascore());
            //genre set function
            setGenres(item);

            movieAwards.setText(item.getAwards());
            movieSummaryTxt.setText(item.getPlot());
            movieDirector.setText(item.getDirector());
            movieWriter.setText(item.getWriter());
            movieActorsInfo.setText(item.getActors());
            if(item.getImages() !=null) {
                adapterImgList = new ImageListAdapter(item.getImages());
                recyclerView.setAdapter(adapterImgList);
            }


        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("uilover","onErrorResponse: "+error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }
    private void setGenres(FilmItem item)
    {
        // Set genres
        List<String> genres = item.getGenres();
        if (genres != null) {
            int genreSize = genres.size();

            if (genreSize > 0) {
                genre1.setText(genres.get(0));
            } else {
                genre1.setVisibility(View.GONE); // Hide the TextView if no genre is available
            }

            if (genreSize > 1) {
                genre2.setText(genres.get(1));
            } else {
                genre2.setVisibility(View.GONE); // Hide the TextView if no genre is available
            }
            if (genreSize > 2) {
                genre3.setText(genres.get(2));
            } else {
                genre3.setVisibility(View.GONE); // Hide the TextView if no genre is available
            }
        } else {
            // Hide all genre TextViews if genres array is null
            genre1.setVisibility(View.GONE);
            genre2.setVisibility(View.GONE);
            genre3.setVisibility(View.GONE);
        }

    }


    private void initView() {

        mAuth = FirebaseAuth.getInstance();

        // Get the UID of the current user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Initialize DatabaseReference for the current user
            String uid = user.getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            // Initialize DatabaseReference for the "movies" node under the user's data
            moviesRef = userRef.child("movies");
        }

        // ... your existing code ..



        titleTxt = findViewById(R.id.movieNameText);
        progressBar = findViewById(R.id.detail_loading);
        scrollView = findViewById(R.id.scrollView3);
        pic1 = findViewById(R.id.posterNormalImage);
        pic2 = findViewById(R.id.posterBigImage);
        movieTimeTxt = findViewById(R.id.movieDuration);
        movieRateTxt = findViewById(R.id.movieRating);
        movieDateTxt = findViewById(R.id.movieDate);

        ImdbRating = findViewById(R.id.imdbRatingTextView);
        MetaScore = findViewById(R.id.metascoreRatingTextView);
        genre1 = findViewById(R.id.genre1);
        genre2 = findViewById(R.id.genre2);
        genre3 = findViewById(R.id.genre3);
        movieAwards = findViewById(R.id.movieAwardsInfo);

        movieSummaryTxt = findViewById(R.id.movieSummaryInfo);
        movieDirector = findViewById(R.id.movieDirectorInfo);
        movieWriter = findViewById(R.id.movieWriterInfo);
        movieActorsInfo = findViewById(R.id.movieActorsInfo);
        backImg = findViewById(R.id.backImg1);
        recyclerView = findViewById(R.id.imagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnWatching = findViewById(R.id.btnWatching);
        btnCompleted = findViewById(R.id.btnCompleted);
        btnOnHold = findViewById(R.id.btnOnHold);
        btnDropped = findViewById(R.id.btnDropped);

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        backImg.setOnClickListener(v -> finish());
    }
}