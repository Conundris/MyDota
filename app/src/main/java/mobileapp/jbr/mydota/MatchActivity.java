package mobileapp.jbr.mydota;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import mobileapp.jbr.mydota.Models.Match;
import mobileapp.jbr.mydota.Models.RecentMatch;

public class MatchActivity extends AppCompatActivity {

    private String matchID;
    private int steamID;
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

        SharedPreferences prefs = getSharedPreferences("MyDota", MODE_PRIVATE);

        steamID = prefs.getInt("steamID", 70677728);

        txtVictory = (TextView) findViewById(R.id.txtVictory);
        txtKillsDire = (TextView) findViewById(R.id.txtDireKills);
        txtKillsRadiant = (TextView) findViewById(R.id.txtKillsRadiant);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtRegion = (TextView) findViewById(R.id.txtRegion);

        matchID = intent.getStringExtra("matchID");

        getMatchData();
    }

    private void getMatchData() {
        RecentMatch recentMatch = RecentMatch.findbyMatchID(matchID);


        String url = "https://api.opendota.com/api/matches/" + matchID;
        // Request a jsonobject response from the provided URL.
        JsonObjectRequest JORequest =  new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyRequest", response.toString());
                        try {
                            // Parsing json array response
                            // loop through each json object
                            long startTime = System.nanoTime();

                            Match match = new Match();
                            match.match_id = response.getString("match_id");
                            match.radiant_win = response.getBoolean("radiant_win");
                            match.dire_score = response.getInt("dire_score");
                            match.radiant_score = response.getInt("radiant_score");
                            match.lobby_type = response.getInt("lobby_type");
                            match.game_mode = response.getInt("game_mode");
                            match.barracks_status_dire = response.getInt("barracks_status_dire");
                            match.barracks_status_radiant = response.getInt("barracks_status_radiant");
                            match.region = response.getInt("region");
                            match.duration = response.getInt("duration");

                            /*match. = response.getString("match_id");
                            match.match_id = response.getString("match_id");
                            match.match_id = response.getString("match_id");
                            match.match_id = response.getString("match_id");*/

                            txtKillsDire.setText(String.valueOf(match.dire_score));
                            txtKillsRadiant.setText(String.valueOf(match.radiant_score));
                            txtRegion.setText("Europe");

                            txtTime.setText(String.valueOf(timeConversion(match.duration)));


                            match.save();
                            long endTime = System.nanoTime();
                            Toast.makeText(getApplicationContext(),
                                    "Matches Loaded", Toast.LENGTH_LONG).show();
                            Log.d("VolleyRequest", "Got All Matches: " + ((endTime - startTime) / 1000000));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d("VolleyRequest", "Error: " + e.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("VolleyRequest", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(JORequest);



        System.out.println(recentMatch);

        if(recentMatch.radiant_win) {
            txtVictory.setText("Radiant Victory");
            txtVictory.setTextColor(Color.GREEN);
        } else {
            txtVictory.setText("Dire Victory");
            txtVictory.setTextColor(Color.RED);
        }

    }

    private String timeConversion(int totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return totalMinutes + ":" + seconds;
    }

}