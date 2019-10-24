package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;



public class GameOverActivity extends AppCompatActivity {

    // for UI and buttons
    private ImageButton playAgain = null;
    private ImageButton mainMenu = null;
    private ImageButton submit = null;
    private TextView highestScoreLabel;
    private String highestScore = "UNAVAILABLE";

    // for user score display and submission
    @BindView(R.id.user_score)
    TextView score;
    EditText nameInput;
    String userScore;
    String userName;

    // for Firebase accessing and updating
    final int LEADERBOARD_SIZE = 5;
    FirebaseDatabase database;
    DatabaseReference ranksRef;
    String[] names;
    Integer[] scores;
    int rankPointer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // layout initialization
        playAgain = (ImageButton) findViewById(R.id.playAgain);
        mainMenu = (ImageButton) findViewById(R.id.mainMenu);
        submit = (ImageButton) findViewById(R.id.submit);

        highestScoreLabel = (TextView) findViewById(R.id.highestScoreLabel);
        highestScoreLabel.setText("HIGHTEST SCORE: " + highestScore);

        // reads leader board data
        readFirebase();

        // processes user score
        userName = "";
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userScore = intent.getStringExtra(PlayGameActivity.MESSAGE);
        score.setText("YOUR SCORE: " + userScore);
        nameInput = (EditText) findViewById(R.id.nameInput);
    }


    // submit button onClick: user score submission
    public void showToast (View view) {

        userName = nameInput.getText().toString();

        // trims excessive long input
        if (userName.length()>7) {
            userName = userName.substring(0,7);
        }

        // displays username for submission
        Toast.makeText(GameOverActivity.this, userName, Toast.LENGTH_SHORT).show();

        // submits name and score to Firebase
        if (userName!=null && userName.length()>0) {
            submitScore();
        }
    }

    // playAgain button onClick: starts a new game function
    public void playAgain(View view) {
        startActivity(new Intent(getApplicationContext(), PlayGameActivity.class));
    }

    // mainMeau button onClick: return to main menu function
    public void returnMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // disables back button to prevent abnormal game replay
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


    // reads leader board data (rank/name/score) from Firebase
    private void readFirebase() {

        // leader board data recorders
        names = new String[LEADERBOARD_SIZE];
        scores = new Integer[LEADERBOARD_SIZE];

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

        // reads rank 1 to LEADERBOARD_SIZE players' name/score
        for (int i=1; i<=LEADERBOARD_SIZE; i++) {
            String index = String.valueOf(i);

            //System.out.println("ranksRef.child"+index);
            DatabaseReference player = ranksRef.child(index);
            //System.out.println(player);
            DatabaseReference playerName = player.child("name");
            //System.out.println(playerName);
            DatabaseReference playerScore = player.child("score");
            //System.out.println(playerScore);

            playerName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
                    if (snapshot.getValue()!=null) {
                        //System.out.println(index);
                        //System.out.println(snapshot.getKey()+": "+snapshot.getValue());
                        names[Integer.parseInt(index)-1] = snapshot.getValue().toString();

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            playerScore.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
                    if (snapshot.getValue()!=null) {
                        //System.out.println(index);
                        //System.out.println(snapshot.getKey()+": "+snapshot.getValue());
                        scores[Integer.parseInt(index)-1] = Integer.valueOf(snapshot.getValue().toString());

                        // displays highest score on Firebase
                        switch(index) {
                            case "1":
                                highestScoreLabel.setText("HIGHTEST SCORE: " + snapshot.getValue().toString());
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
    }


    // assistant method for submitScore lambda expression
    private String getValidRank() {
        rankPointer++;
        return String.valueOf(rankPointer);
    }

    // submits user name and score to Firebase if enters leader board
    private void submitScore() {
        rankPointer = 0;

        // collects all score records with handling repeated name and button clicks
        HashMap<String,Integer> records = new HashMap<String,Integer>();
        records.put(userName ,Integer.valueOf(userScore));

        for(int i=0; i<LEADERBOARD_SIZE; i++) {
            if(names[i]!=null && scores[i]!=null) {
                // allows only one highest score record for every user
                if(records.containsKey(names[i])){
                    Integer score = records.get(names[i]);
                    if (scores[i]>score) {
                        score = scores[i];
                    }
                    records.put(names[i], score);
                }
                else {
                    records.put(names[i], scores[i]);
                }
            }
        }
        //System.out.println(records);

        // sorts all records by score
        final HashMap<String, Integer> sortedRecords = records.entrySet()
                .stream()
                .sorted((HashMap.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //System.out.println(sortedRecords);

        // updates leader board data on Firebase
        sortedRecords.forEach((k,v)->{
            String i = getValidRank();
            //System.out.println("Rank: "+i + ", Name: "+k + ", Score: "+v);
            DatabaseReference player = ranksRef.child(i);
            DatabaseReference playerName = player.child("name");
            DatabaseReference playerScore = player.child("score");
            playerName.setValue(k);
            playerScore.setValue(v.toString());
        });
    }

}
