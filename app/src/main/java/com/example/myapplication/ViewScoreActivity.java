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



public class ViewScoreActivity extends AppCompatActivity {


    // stores and displays top 5 player score board info
    // default values "NO DATA" if Firebase is unavailable
    private TextView scoreboardName1;
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
    private String score3 = "DATA";

    private TextView scoreboardName4;
    private String name4 = "NO";
    private TextView scoreboardScore4;
    private String score4 = "DATA";

    private TextView scoreboardName5;
    private String name5 = "NO";
    private TextView scoreboardScore5;
    private String score5 = "DATA";

    // Firebase
    FirebaseDatabase database;
    DatabaseReference ranksRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        displayLeaderBoardTop5();
        readFirebaseTop5();
    }


    // initializes and shows score board info
    private void displayLeaderBoardTop5() {

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
    }

    // reads leader board data (rank/name/score) from Firebase
    private void readFirebaseTop5() {

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


        // reads rank 1 to 5 players' name/score
        for (int i=1; i<=5; i++) {
            String index = String.valueOf(i);
            //System.out.println("ranksRef.child"+index);
            DatabaseReference player = ranksRef.child(index);
            //System.out.println(player);
            DatabaseReference playerName = player.child("name");
            //System.out.println(playerName);
            DatabaseReference playerScore = player.child("score");
            //System.out.println(playerScore);

            // updates and displays name information if available
            playerName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
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

            // updates and displays score information if available
            playerScore.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot);
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
