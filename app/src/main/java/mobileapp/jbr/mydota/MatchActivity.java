package mobileapp.jbr.mydota;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import mobileapp.jbr.mydota.Models.Match;

public class MatchActivity extends AppCompatActivity {

    private String matchID;
    private TextView txtVictory;
    private TextView txtGameMode;
    private TextView txtKillsRadiant;
    private TextView txtKillsDire;
    private TextView txtTime;
    private TextView txtAvgMMR;
    private TextView txtRegion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();

        txtVictory = (TextView) findViewById(R.id.txtVictory);
        txtGameMode = (TextView) findViewById(R.id.txtGameMode);
        txtKillsDire = (TextView) findViewById(R.id.txtDireKills);
        txtKillsRadiant = (TextView) findViewById(R.id.txtKillsRadiant);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtAvgMMR = (TextView) findViewById(R.id.txtAvgMMR);
        txtRegion = (TextView) findViewById(R.id.txtRegion);

        matchID = intent.getStringExtra("matchID");

        getMatchData();
    }

    private void getMatchData() {
        Match match = Match.findbyMatchID(matchID);

        System.out.println(match);

        if(match.radiant_win) {
            txtVictory.setText("Radiant Victory");
            txtVictory.setTextColor(Color.GREEN);
        } else {
            txtVictory.setText("Dire Victory");
            txtVictory.setTextColor(Color.RED);
        }


    }


}
