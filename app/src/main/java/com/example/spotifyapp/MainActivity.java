package com.example.spotifyapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.spotifyapp.ui.bibliotheque.BibliothequeFragment;
import com.example.spotifyapp.ui.home.HomeFragment;
import com.example.spotifyapp.ui.recherche.RechercheFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    LinearLayout nomMusic;
    Button buFav2 , buPlay2;
    ImageView imgMus;
    private MediaPlayer mediaPlayer2;
    public static boolean fav=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , new HomeFragment()).commit();

        // Find All
        {
            nomMusic = findViewById(R.id.txtNomMusicID);
            buFav2 = findViewById(R.id.buFav2ID);
            buPlay2 = findViewById(R.id.buPlay2ID);
            imgMus = findViewById(R.id.imgID);
        }

        // Passer vers Music Activité
        nomMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MainActivity2.class );
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        imgMus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MainActivity2.class );
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });

        mediaPlayer2 = MediaPlayer.create(this ,R.raw.nordo_arbouch);

        // Button for Play and Pause music
        buPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer2.isPlaying())
                {
                    mediaPlayer2.start();
                    buPlay2.setBackgroundResource(R.drawable.ic_baseline_pause);
                }
                else
                {
                    mediaPlayer2.pause();
                    buPlay2.setBackgroundResource(R.drawable.ic_baseline_play);
                }
            }
        });

        // Button for Favorite music
        buFav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav==false)
                {
                    buFav2.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    fav = true;
                }
                else
                {
                    buFav2.setBackgroundResource(R.drawable.ic_favorite_border);
                    fav = false;
                }
            }
        });

    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.navigation_home : selectedFragment = new HomeFragment(); break;
                case R.id.navigation_recherche : selectedFragment = new RechercheFragment(); break;
                case R.id.navigation_bibliotheque : selectedFragment = new BibliothequeFragment(); break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , selectedFragment).commit();

            return true;
        }
    };
}