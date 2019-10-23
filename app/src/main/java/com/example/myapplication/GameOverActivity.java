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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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
    String[] names;
    Integer[] scores;

    String userScore;
    String userName;

    int rankPointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = (ImageButton) findViewById(R.id.playAgain);
        mainMenu = (ImageButton) findViewById(R.id.mainMenu);
        submit = (ImageButton) findViewById(R.id.submit);

        highestScoreLabel = (TextView) findViewById(R.id.highestScoreLabel);
        highestScoreLabel.setText("Highest Score: " + highestScore);

        names = new String[5];
        scores = new Integer[5];

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
                    //System.out.println(snapshot.getValue());
                    if (snapshot.getValue()!=null) {
                        //System.out.println(index);
                        //System.out.println(snapshot.getKey()+": "+snapshot.getValue());
                        scores[Integer.parseInt(index)-1] = Integer.valueOf(snapshot.getValue().toString());
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
        //score.setText("Your Score: " + intent.getStringExtra(PlayGameActivity.MESSAGE));
        userScore = intent.getStringExtra(PlayGameActivity.MESSAGE);
        score.setText("Your Score: " + userScore);

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

        if (userName!=null && userName.length()>0) {
            submitScore();
        }
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

    private String getValidRank() {
        rankPointer++;
        return String.valueOf(rankPointer);
    }

    private void submitScore() {
        //TODO
        rankPointer = 0;
        HashMap<String,Integer> records = new HashMap<String,Integer>();
        records.put(userName ,Integer.valueOf(userScore));
        for(int i=0; i<5; i++) {

            if(names[i]!=null && scores[i]!=null) {
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
        System.out.println(records);
        final HashMap<String, Integer> sortedRecords = records.entrySet()
                .stream()
                .sorted((HashMap.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //System.out.println(sortedRecords);


        sortedRecords.forEach((k,v)->{
            String i = getValidRank();
            System.out.println("Rank: "+i + ", Name: "+k + ", Score: "+v);
            DatabaseReference player = ranksRef.child(i);
            DatabaseReference playerName = player.child("name");
            DatabaseReference playerScore = player.child("score");
            playerName.setValue(k);
            playerScore.setValue(v.toString());


        });
    }

}
