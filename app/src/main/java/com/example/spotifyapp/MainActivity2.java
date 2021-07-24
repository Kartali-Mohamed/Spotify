package com.example.spotifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class MainActivity2 extends AppCompatActivity {

    private Button buFav , buPrev , buPlay , buNext , buRep , buBack;
    private TextView txtTemps , txtDurée ;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private boolean rep = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Find All
        {
            buFav = findViewById(R.id.buFavID);
            buPrev = findViewById(R.id.buPrevID);
            buPlay = findViewById(R.id.buPlayID);
            buNext = findViewById(R.id.buNextID);
            buBack = findViewById(R.id.buBackID);
            buRep = findViewById(R.id.buRepID);
            txtTemps = findViewById(R.id.txtTempsID);
            txtDurée = findViewById(R.id.txtDuréeID);
            seekBar = findViewById(R.id.seekBarID);
        }

        mediaPlayer = MediaPlayer.create(this ,R.raw.nordo_arbouch);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);


        // Click Listener All
        {
            // Button for Start and Pause music
            buPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();
                        buPlay.setBackgroundResource(R.drawable.ic_pause);
                    }
                    else
                    {
                        mediaPlayer.pause();
                        buPlay.setBackgroundResource(R.drawable.ic_play);
                    }
                }
            });


            // Check Favorite button
            if (MainActivity.fav==false)
            {
                buFav.setBackgroundResource(R.drawable.ic_favorite_border);
            }
            else
            {
                buFav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            }

            //  Button for Favorite music
            buFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MainActivity.fav==false)
                    {
                        buFav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                        MainActivity.fav = true;
                    }
                    else
                    {
                        buFav.setBackgroundResource(R.drawable.ic_favorite_border);
                        MainActivity.fav = false;
                    }
                }
            });

            // Button for Back
            buBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity2.this , MainActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideDown(MainActivity2.this);
                }
            });

            // Button for Repeat music
            buRep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rep == false)
                    {
                        mediaPlayer.setLooping(true);
                        buRep.setBackgroundResource(R.drawable.ic_repeat_one);
                        rep=true;
                    }
                    else
                    {
                        mediaPlayer.setLooping(false);
                        buRep.setBackgroundResource(R.drawable.ic_repeat);
                        rep=false;
                    }
                }
            });


            // Modifer TextDurée
            final String duréeMusic = millesSecondToString(mediaPlayer.getDuration());
            txtDurée.setText(duréeMusic);

            // Modifer SeekBar
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                    if (isFromUser){
                        mediaPlayer.seekTo(progress);
                        seekBar.setProgress(progress);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            // Modifer TextTemps et SeekBar
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null)
                    {
                        try {
                            final double current = mediaPlayer.getCurrentPosition();
                            final String timeReal = millesSecondToString((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtTemps.setText(timeReal);
                                    seekBar.setProgress((int) current);
                                }
                            });

                            if (timeReal.equals(duréeMusic))
                            {
                                if (rep == false)
                                {
                                    buPlay.setBackgroundResource(R.drawable.ic_play);
                                }
                            }

                            Thread.sleep(1000);
                        }
                        catch (Exception e) { }
                    }
                }
            }).start();
        }

    }

    public String millesSecondToString(int temps)
    {
        String tempsOnString = "";
        int minute = temps / 1000 / 60;
        int second = temps / 1000 % 60;
        tempsOnString = minute + ":";
        if (second < 10){
            tempsOnString += "0";
        }
        tempsOnString += second;
        return tempsOnString;
    }
}