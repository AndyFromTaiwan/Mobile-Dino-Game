package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    // 1022 score board image button
    private ImageButton start = null;
    private ImageButton board = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 1022 score board image button
        start = (ImageButton) findViewById(R.id.start);
        board = (ImageButton) findViewById(R.id.board);


    }

    // Tony new game function (for Button)
    public void newGame(View view) {
        startActivity(new Intent(getApplicationContext(), PlayGameActivity.class));
    }

    // Tony view leader board function (for Button)
    public void viewScore(View view) {
        startActivity(new Intent(getApplicationContext(), ViewScoreActivity.class));
    }



}
