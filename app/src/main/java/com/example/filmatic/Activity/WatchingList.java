package com.example.filmatic.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmatic.Adapter.SpecificFilmListAdapter;
import com.example.filmatic.Domain.Datum;
import com.example.filmatic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class WatchingList extends AppCompatActivity {

    private DatabaseReference moviesRef;
    private RecyclerView recyclerView;
    private SpecificFilmListAdapter adapter;
    private RequestQueue mRequestQueue;
    private FirebaseAuth mAuth;
    private ImageView backbtn;
    private TextView noMoviesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watching_list);

        initView();
        mAuth = FirebaseAuth.getInstance();
        loadSpecificMovies();
        btnaction();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerViewWatchingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backbtn = findViewById(R.id.backImg2);
        noMoviesTextView = findViewById(R.id.noMoviesTextView);
    }

    private void loadSpecificMovies() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            moviesRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("movies").child("watching");

            moviesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        List<Integer> specificIds = new ArrayList<>();
                        for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                            specificIds.add(Integer.valueOf(idSnapshot.getKey()));
                        }
                        if (specificIds.isEmpty()) {
                            // No movies added
                            showNoMoviesAddedText();
                        } else {
                            // Movies added, fetch details
                            noMoviesTextView.setVisibility(View.GONE);
                            fetchMoviesDetails(specificIds);

                        }

                    }
                    else {
                        // No movies added
                        showNoMoviesAddedText();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors if needed
                }
            });
        }
    }

    private void fetchMoviesDetails(List<Integer> specificIds) {
        mRequestQueue = Volley.newRequestQueue(this);
        for (Integer movieId : specificIds) {
            String apiUrl = "https://moviesapi.ir/api/v1/movies/" + movieId;
            StringRequest mStringRequest = new StringRequest(Request.Method.GET, apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            Datum movie = gson.fromJson(response, Datum.class);
                            updateRecyclerView(movie);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle errors if needed
                        }
                    });

            mRequestQueue.add(mStringRequest);
        }
    }
    private void showNoMoviesAddedText() {
        // Show your "No movies added" text message here.
        // You can use a TextView or any other UI element to display the message.
        // For example:

         noMoviesTextView.setVisibility(View.VISIBLE);
    }

    private void updateRecyclerView(Datum movie) {
        if (adapter == null) {
            List<Datum> moviesList = new ArrayList<>();
            moviesList.add(movie);
            adapter = new SpecificFilmListAdapter(moviesList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.addData(movie);
        }
    }
    private void btnaction() {
        backbtn.setOnClickListener(v -> finish());
    }
}
