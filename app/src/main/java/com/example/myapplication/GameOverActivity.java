package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

// Tony
import android.view.View;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class GameOverActivity extends AppCompatActivity {

    @BindView(R.id.user_score)
    TextView score;

    // 1021 1740
    // Tony user input name
    String userName;
    EditText nameInput;
    Button submitButton;

    // Tony play again
    Button playAgainButton;


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


        // 1021 1740
        // Tony store user input name
        nameInput = (EditText) findViewById(R.id.nameInput);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameInput.getText().toString();

                // testing user input name
                showToast(userName);

            }
        });

        // 1021 1740
        // Tony play again
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

    }

    // testing function for storing user input name
    private void showToast (String text) {
        Toast.makeText(GameOverActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    // 1021 1740
    // Tony replay function
    public void playAgain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    // 1021 1740
    // Disable Return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }



}
