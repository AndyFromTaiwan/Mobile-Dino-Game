package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

// Tony
import android.view.View;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOverActivity extends AppCompatActivity {

    @BindView(R.id.user_score)
    TextView score;

    EditText nameInput;

    private ImageButton playAgain = null;
    private ImageButton mainMenu = null;
    private ImageButton submit = null;

    private TextView highestScoreLabel;
    //Andy
    private String highestScore = "NO DATA";

    FirebaseDatabase database;
    DatabaseReference ranksRef;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = (ImageButton) findViewById(R.id.playAgain);
        mainMenu = (ImageButton) findViewById(R.id.mainMenu);
        submit = (ImageButton) findViewById(R.id.submit);

        highestScoreLabel = (TextView) findViewById(R.id.highestScoreLabel);
        highestScoreLabel.setText("Highest Score: " + highestScore);

        database = FirebaseDatabase.getInstance();
        ranksRef = database.getReference("ranks");

        ranksRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
        });

        //Andy
        for (int i=1; i<=5; i++) {
            String index = String.valueOf(i);

            //System.out.println("ranksRef.child"+index);
            DatabaseReference player = ranksRef.child(index);
            //System.out.println(player);
            DatabaseReference playerName = player.child("name");
            DatabaseReference playerScore = player.child("score");
            //System.out.println(playerName);
            //System.out.println(playerScore);

            playerName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
                    //System.out.println(snapshot.getValue());
                    if (snapshot.getValue()!=null) {
                        //System.out.println(index);
                        //System.out.println(snapshot.getKey()+": "+snapshot.getValue());
                        //TODO add data and sorting
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            playerScore.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
                    //System.out.println(snapshot.getValue());
                    if (snapshot.getValue()!=null) {
                        //TODO add data and sorting
                        //System.out.println(index);
                        //System.out.println(snapshot.getKey()+": "+snapshot.getValue());
                        switch(index) {
                            case "1":
                                highestScoreLabel.setText("Highest Score: " + snapshot.getValue().toString());
                                break;
                            default:
                                break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        //Andy
        userName = "";


        ButterKnife.bind(this);
        Intent intent = getIntent();
        score.setText("Your Score: " + intent.getStringExtra(PlayGameActivity.MESSAGE));

        nameInput = (EditText) findViewById(R.id.nameInput);
    }

    // testing function for storing user input name
    public void showToast (View view) {

        userName = nameInput.getText().toString();
        // testing user input name
        // Andy
        if (userName.length()>7) {
            userName = userName.substring(0,7);
        }

        Toast.makeText(GameOverActivity.this, userName, Toast.LENGTH_SHORT).show();
    }

    // 1021 1740
    // Tony replay function
    public void playAgain(View view) {
        startActivity(new Intent(getApplicationContext(), PlayGameActivity.class));
    }

    // 1021 2320
    // Tony return menu function
    public void returnMenu(View view) {
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
