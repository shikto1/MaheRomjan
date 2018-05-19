package com.walletmix.maheromjan.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.walletmix.maheromjan.Managers.AdmobAddManager;
import com.walletmix.maheromjan.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class KuranTilaoatBanglaShohoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private Unbinder unbinder;
    MediaPlayer mediaPlayer;

    @BindView(R.id.timeOneTv)
    TextView timeOneTv;

    @BindView(R.id.timeTwoTv)
    TextView timeTwoTv;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @BindView(R.id.playPauseButton)
    Button playPauseButton;
    public static boolean completed = false;

    private Handler handler;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuran_tilaoat_bangla_shoho);
        unbinder = ButterKnife.bind(this);
        handler = new Handler();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Showing Add...
        new AdmobAddManager().showInterStitialAd(this);
        new AdmobAddManager().showBannerToActivity(this, R.id.adView);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    String formattedTime = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(seekBar.getProgress()),
                            TimeUnit.MILLISECONDS.toSeconds(seekBar.getProgress()) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(seekBar.getProgress()))
                    );
                    timeOneTv.setText(formattedTime);
                    mediaPlayer.seekTo(seekBar.getProgress());
                } else {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }

            }
        });

    }

    @OnClick(R.id.playPauseButton)
    void clickOnPlayPauseButton(Button b) {
        if (b.getText().equals("PLAY")) {
            play();
            b.setText("PAUSE");
        } else if (b.getText().equals("PAUSE")) {
            b.setText("PLAY");
            pausePlayer();
        }
    }

    public void pausePlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void play() {
        if (completed == true || mediaPlayer == null) {
            completed = false;
            mediaPlayer = MediaPlayer.create(this, R.raw.sura_yasin);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    int mediaMax = mediaPlayer.getDuration();
                    String formattedTime = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(mediaMax),
                            TimeUnit.MILLISECONDS.toSeconds(mediaMax) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaMax))
                    );
                    timeTwoTv.setText(formattedTime);
                    handler.postDelayed(moveSeekBarThread, 1000);
                    mediaPlayer.start();

                }
            });
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            handler.removeCallbacks(moveSeekBarThread);
        }
    }

    private Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()) {
                int mediaPos_new = mediaPlayer.getCurrentPosition();
                int mediaMax_new = mediaPlayer.getDuration();
                seekBar.setMax(mediaMax_new);
                seekBar.setProgress(mediaPos_new);
                String formattedTime = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(mediaPos_new),
                        TimeUnit.MILLISECONDS.toSeconds(mediaPos_new) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPos_new))
                );
                timeOneTv.setText(formattedTime);
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        completed = true;
        timeTwoTv.setText("00:00");
        timeOneTv.setText("00:00");
        playPauseButton.setText("PLAY");
        seekBar.setProgress(0);
        handler.removeCallbacks(moveSeekBarThread);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
