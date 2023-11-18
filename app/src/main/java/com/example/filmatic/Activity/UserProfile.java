package com.example.filmatic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmatic.Adapter.FilmListAdapter;
import com.example.filmatic.Domain.ListFilm;
import com.example.filmatic.R;
import com.example.filmatic.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class UserProfile extends AppCompatActivity {

    private ImageView backButton;
    private RecyclerView.Adapter adapterFavMovies;
    private RecyclerView recyclerViewFavMovies;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar loading;
    private ImageView signoutButton, Watching, Completed, Onhold, Dropped;
    private TextView usernameid, emailid, seemore;
    private TextView fullnamet, favgent, favmoviet, favdirectort;

    private TextView fullnametxt, favgentxt, favmovietxt, favdirectortxt;
    private EditText fullnameedit, favgenedit, favmovieedit, favdirectoredit;
    private Button saveButton;
    private DatabaseReference userDetailRef;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerContent;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private ActivityMainBinding binding;
    //String username, email, dob, password;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private int flag=0;

    private static final String USER_DATA_PREFS = "UserDataPrefs";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initView();
        buttonWorks();


        showUserInfo();


        sendRequest();
    }

    public void initView() {
        backButton = findViewById(R.id.backImgProfilePage);
        loading = findViewById(R.id.loadingFavMovies);
        recyclerViewFavMovies = findViewById(R.id.viewFav);
        recyclerViewFavMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        signoutButton = findViewById(R.id.btn_signout);

        seemore = findViewById(R.id.seemore);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerContent = findViewById(R.id.drawerContent);

        fullnameedit = findViewById(R.id.fullname);
        favgenedit = findViewById(R.id.favgenre);
        favmovieedit = findViewById(R.id.favmovie);
        favdirectoredit = findViewById(R.id.favdirector);

        fullnamet = findViewById(R.id.fullnametextview);
        favmoviet = findViewById(R.id.favmovietextview);
        favgent = findViewById(R.id.favgenretextview);
        favdirectort = findViewById(R.id.favdirectortextview);

        fullnametxt = findViewById(R.id.fullnametextview2);
        favmovietxt = findViewById(R.id.favmovietextview2);
        favgentxt = findViewById(R.id.favgenretextview2);
        favdirectortxt = findViewById(R.id.favdirectortextview2);

        saveButton = findViewById(R.id.saveButton);

        Watching = findViewById(R.id.watching);
        Completed = findViewById(R.id.completed);
        Onhold = findViewById(R.id.plantowatch);
        Dropped = findViewById(R.id.dropped);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();




        usernameid = findViewById(R.id.user_username);
        emailid = findViewById(R.id.user_email);


        if(user == null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        else {
            //sign in thakle jaja dekhabe tar function
        }
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

    }
    private void showUserInfo() {
        // Check if the user is authenticated

        if (user != null) {
            // Fetch user data from Realtime Database using UID

            reference = db.getReference("Users").child(user.getUid());
            userDetailRef = db.getReference("Users").child(user.getUid()).child("additionalDetails");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Retrieve user data
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String userEmail = snapshot.child("email").getValue(String.class);

                        // Display user data in TextViews
                        usernameid.setText(username);
                        emailid.setText(userEmail);

                        // Check if additional details exist
                        if (snapshot.child("additionalDetails").exists()) {
                            // Additional details exist, retrieve and display them
                            String fullname = snapshot.child("additionalDetails").child("fullname").getValue(String.class);
                            String favmovie = snapshot.child("additionalDetails").child("fav_movie").getValue(String.class);
                            String favgenre = snapshot.child("additionalDetails").child("fav_genre").getValue(String.class);
                            String favdirector = snapshot.child("additionalDetails").child("fav_director").getValue(String.class);

                            fullnametxt.setText(fullname);
                            favmovietxt.setText(favmovie);
                            favgentxt.setText(favgenre);
                            favdirectortxt.setText(favdirector);

                            // Hide EditTexts and Save button
                            fullnameedit.setVisibility(View.GONE);
                            favmovieedit.setVisibility(View.GONE);
                            favgenedit.setVisibility(View.GONE);
                            favdirectoredit.setVisibility(View.GONE);

                            // Show TextViews
                            fullnamet.setVisibility(View.VISIBLE);
                            favmoviet.setVisibility(View.VISIBLE);
                            favgent.setVisibility(View.VISIBLE);
                            favdirectort.setVisibility(View.VISIBLE);

                            fullnametxt.setVisibility(View.VISIBLE);
                            favmovietxt.setVisibility(View.VISIBLE);
                            favgentxt.setVisibility(View.VISIBLE);
                            favdirectortxt.setVisibility(View.VISIBLE);

                            saveButton.setVisibility(View.GONE);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("UserProfile", "Error fetching user data: " + error.getMessage());
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fullname = fullnameedit.getText().toString();
                    String favmovie = favmovieedit.getText().toString();
                    String favgenre = favgenedit.getText().toString();
                    String favdirector = favdirectoredit.getText().toString();

                    userDetailRef.child("fullname").setValue(fullname);
                    userDetailRef.child("fav_movie").setValue(favmovie);
                    userDetailRef.child("fav_genre").setValue(favgenre);
                    userDetailRef.child("fav_director").setValue(favdirector);

                    fullnameedit.setVisibility(View.GONE);
                    favmovieedit.setVisibility(View.GONE);
                    favgenedit.setVisibility(View.GONE);
                    favdirectoredit.setVisibility(View.GONE);

                    fullnametxt.setText(fullname);
                    favmovietxt.setText(favmovie);
                    favgentxt.setText(favgenre);
                    favdirectortxt.setText(favdirector);

                    fullnamet.setVisibility(View.VISIBLE);
                    favmoviet.setVisibility(View.VISIBLE);
                    favgent.setVisibility(View.VISIBLE);
                    favdirectort.setVisibility(View.VISIBLE);

                    fullnametxt.setVisibility(View.VISIBLE);
                    favmovietxt.setVisibility(View.VISIBLE);
                    favgentxt.setVisibility(View.VISIBLE);
                    favdirectortxt.setVisibility(View.VISIBLE);

                    saveButton.setVisibility(View.GONE);



                }
            });
        } else {
            // If the user is not authenticated, redirect to the login screen
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
    }



    public void buttonWorks() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, MainActivity.class));
            }
        });
        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });



        Watching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, WatchingList.class));
            }
        });

        Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, CompletedList.class));
            }
        });

        Onhold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, OnholdList.class));
            }
        });

        Dropped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, DroppedList.class));
            }
        });
    }
    private void openDrawer() {
        // Open the sliding drawer
        drawerLayout.openDrawer(GravityCompat.END);
    }
    private void closeDrawer() {
        // Close the sliding drawer
        drawerLayout.closeDrawer(GravityCompat.END);
    }
    public void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading.setVisibility(View.VISIBLE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                loading.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterFavMovies = new FilmListAdapter(items);
                recyclerViewFavMovies.setAdapter(adapterFavMovies);

            }
        }, error -> {
            Log.i("uilover","sendRequest: "+error.toString());
            loading.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest);
    }

}