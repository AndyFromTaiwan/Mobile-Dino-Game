package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ViewScoreActivity extends AppCompatActivity {


    // Tony 1022 store score board info
    private TextView scoreboardName1;
    // extract first 7 char
    private String name1 = "NO";
    private TextView scoreboardScore1;
    private String score1 = "DATA";

    private TextView scoreboardName2;
    private String name2 = "NO";
    private TextView scoreboardScore2;
    private String score2 = "DATA";

    private TextView scoreboardName3;
    private String name3 = "NO";
    private TextView scoreboardScore3;
    private String score3= "DATA";

    private TextView scoreboardName4;
    private String name4 = "NO";
    private TextView scoreboardScore4;
    private String score4= "DATA";

    private TextView scoreboardName5;
    private String name5 = "NO";
    private TextView scoreboardScore5;
    private String score5 = "DATA";

    FirebaseDatabase database;
    DatabaseReference ranksRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);


        // Tony 1022 show score board info
        scoreboardName1 = (TextView) findViewById(R.id.scoreboardName1);
        scoreboardName1.setText(name1);
        scoreboardScore1 = (TextView) findViewById(R.id.scoreboardScore1);
        scoreboardScore1.setText(score1);

        scoreboardName2 = (TextView) findViewById(R.id.scoreboardName2);
        scoreboardName2.setText(name2);
        scoreboardScore2 = (TextView) findViewById(R.id.scoreboardScore2);
        scoreboardScore2.setText(score2);

        scoreboardName3 = (TextView) findViewById(R.id.scoreboardName3);
        scoreboardName3.setText(name3);
        scoreboardScore3 = (TextView) findViewById(R.id.scoreboardScore3);
        scoreboardScore3.setText(score3);

        scoreboardName4 = (TextView) findViewById(R.id.scoreboardName4);
        scoreboardName4.setText(name4);
        scoreboardScore4 = (TextView) findViewById(R.id.scoreboardScore4);
        scoreboardScore4.setText(score4);

        scoreboardName5 = (TextView) findViewById(R.id.scoreboardName5);
        scoreboardName5.setText(name5);
        scoreboardScore5 = (TextView) findViewById(R.id.scoreboardScore5);
        scoreboardScore5.setText(score5);

        //System.out.println("View leaderboard");

        // Write a message to the database
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
                        switch(index) {
                            case "1":
                                scoreboardName1.setText(snapshot.getValue().toString());
                                break;
                            case "2":
                                scoreboardName2.setText(snapshot.getValue().toString());
                                break;
                            case "3":
                                scoreboardName3.setText(snapshot.getValue().toString());
                                break;
                            case "4":
                                scoreboardName4.setText(snapshot.getValue().toString());
                                break;
                            case "5":
                                scoreboardName5.setText(snapshot.getValue().toString());
                                break;
                            default:
                                break;
                        }

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
                        switch(index) {
                            case "1":
                                scoreboardScore1.setText(snapshot.getValue().toString());
                                break;
                            case "2":
                                scoreboardScore2.setText(snapshot.getValue().toString());
                                break;
                            case "3":
                                scoreboardScore3.setText(snapshot.getValue().toString());
                                break;
                            case "4":
                                scoreboardScore4.setText(snapshot.getValue().toString());
                                break;
                            case "5":
                                scoreboardScore5.setText(snapshot.getValue().toString());
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
}
