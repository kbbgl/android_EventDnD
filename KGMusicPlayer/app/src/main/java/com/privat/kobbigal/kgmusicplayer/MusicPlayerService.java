package com.privat.kobbigal.kgmusicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by kobbigal on 11/23/17.
 */

public class MusicPlayerService extends Service implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

    MediaPlayer mediaPlayer = new MediaPlayer();
    final int NOTIF_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.reset();

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play)
                .setContentText("Playing Bob Dylan")
                .setContentTitle("Music Player");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("playing", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        startForeground(NOTIF_ID, notification);

        mediaPlayer.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!mediaPlayer.isPlaying()){
            try {
                mediaPlayer.setDataSource(intent.getStringExtra("link"));
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer!=null){
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
