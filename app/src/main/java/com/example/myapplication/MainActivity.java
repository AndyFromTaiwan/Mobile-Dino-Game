package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
//import android.widget.Button;
import android.widget.ImageButton;



public class MainActivity extends AppCompatActivity {

    // score board image buttons
    private ImageButton start = null;
    private ImageButton board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new game and leader board image buttons
        start = (ImageButton) findViewById(R.id.start);
        board = (ImageButton) findViewById(R.id.board);
    }

    // start button onClick: starts a new game function
    public void newGame(View view) {
        startActivity(new Intent(getApplicationContext(), PlayGameActivity.class));
    }

    // board button onClick: views leader board function
    public void viewScore(View view) {
        startActivity(new Intent(getApplicationContext(), ViewScoreActivity.class));
    }
}
