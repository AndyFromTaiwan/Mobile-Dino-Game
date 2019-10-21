package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameOverActivity extends AppCompatActivity {

    @BindView(R.id.user_score)
    TextView score;

    // Tony highest score
    private TextView highestScoreLabel;
    private int highestScore = 9999999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        highestScoreLabel = (TextView) findViewById(R.id.highestScoreLabel);
        highestScoreLabel.setText("Highest Score: " + highestScore);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        score.setText("Your Score: " + intent.getStringExtra(MainActivity.MESSAGE));

    }
}
