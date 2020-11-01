package com.app.hnbpro;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.firebase.storage.FirebaseStorage;

public class Teste2 extends AppCompatActivity {

    private VideoView videoView;
    private ProgressBar bufferProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video1);

        videoView = findViewById(R.id.videoView);
        bufferProgress = findViewById(R.id.progressBar);

        String str = "https://firebasestorage.googleapis.com/v0/b/projeto-firebase-84b42.appspot.com/o/output.mp4?alt=media&token=88b666b7-405a-4479-bac6-efa0fc7cb8c0";
        Uri uri = Uri.parse(str);
        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        View decorview = getWindow().getDecorView();
        int uiOpcoes = View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorview.setSystemUiVisibility(uiOpcoes);

        //Executar o vídeo

        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                if (i == mediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    bufferProgress.setVisibility(View.VISIBLE);
                } else{
                    bufferProgress.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        videoView.start();
    }
}
