package com.example.filmatic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmatic.Domain.FilmItem;
import com.example.filmatic.R;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Retrieve movie details from the intent
        FilmItem movieItem = getIntent().getParcelableExtra("movieItem");

        if (movieItem != null) {
            // Populate the UI with movie details
            TextView movieName = findViewById(R.id.MovieName);
            TextView directorName = findViewById(R.id.DirectionName);

            movieName.setText(movieItem.getTitle());
            directorName.setText("Director: " + movieItem.getDirector());
        } else {
            Toast.makeText(this, "Movie details not available", Toast.LENGTH_SHORT).show();
        }



        }
    }
