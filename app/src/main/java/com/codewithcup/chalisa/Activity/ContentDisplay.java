package com.codewithcup.chalisa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithcup.chalisa.R;
import com.google.firebase.database.DatabaseReference;

public class ContentDisplay extends AppCompatActivity {

    ImageView omSound, shankSound, bellSound, backPress;
    TextView header, description;
    Spanned ConvertHTML;
    Button backPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_content_display);

        //calling All Method Here
        callingAllMethod();
    }

    private void callingAllMethod() {
        //calling hooks
        callingHooksMethod();

        //display description
        getDescriptionData();
        //calling sound
        CallingSoundMethod();

        //calling back button
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private void getDescriptionData() {

        String _header = getIntent().getStringExtra("title");
        String _desc = getIntent().getStringExtra("description");
        header.setText(_header);
        ConvertHTML  = Html.fromHtml( _desc);
        description.setText(ConvertHTML);
    }

    public void callingHooksMethod() {
        //Hooks
        backPress = findViewById(R.id.back_pressed);
        bellSound = findViewById(R.id.bell_sound);
        omSound = findViewById(R.id.om_sound);
        shankSound = findViewById(R.id.shank_sound);
        header = findViewById(R.id.header_title);
        description = findViewById(R.id.description);


    }

    public void CallingSoundMethod() {

        bellSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(ContentDisplay.this, R.raw.temple_bell);
                player.start();
            }
        });

        shankSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(ContentDisplay.this, R.raw.temple_sankh_sound);
                player.start();
            }
        });

        omSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(ContentDisplay.this, R.raw.temple_om_sound);
                player.start();
            }
        });

    }


    @Override
    public void onBackPressed() {

        // Put your own code here which you want to run on back button click.

       // Toast.makeText(ContentDisplay.this,"Back Button is clicked. ContentDisplay", Toast.LENGTH_LONG).show();

        super.onBackPressed();
    }

}