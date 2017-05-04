package mobileapp.jbr.mydota;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobileapp.jbr.mydota.Models.Hero;
import mobileapp.jbr.mydota.Models.Match;
import mobileapp.jbr.mydota.Models.RecentMatch;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mobileapp.jbr.mydota.MESSAGE";
    private EditText mSteam32IDTextView;

    // temporary string to show the parsed response
    private String jsonResponse;

    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MyDb.db")
                .addModelClass(Hero.class)
                .addModelClass(RecentMatch.class)
                .addModelClass(Match.class)
                .create();

        ActiveAndroid.initialize(dbConfiguration);

        populateHeroes();

        SharedPreferences prefs = getSharedPreferences("MyDota", MODE_PRIVATE);

        mSteam32IDTextView = (EditText) findViewById(R.id.steamID);
        //mSteam32IDTextView.setText(prefs.getInt("steamID", 70677728)); //70677728 is the default value (Exceeds's Account).
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSteam32IDTextView.getText() != null && !mSteam32IDTextView.getText().toString().equals("") && accountFound()){
                    Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                    intent.putExtra("ID", mSteam32IDTextView.getText().toString()); // can be used to send data over activites

                    saveSteamID();

                    startActivity(intent);
                } else{
                    Toast msg = Toast.makeText(getApplicationContext(), "Please enter a steamID", Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
                    msg.show();
                }


            }
        });
    }

    private boolean accountFound() {

        String url ="https://api.opendota.com/api/players/" + mSteam32IDTextView.getText() + "/wl";
        // Request a jsonobject response from the provided URL.
        JsonObjectRequest JORequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            if(response.getString("personaname").equals("")) {
                                isValid = false;
                            } else {
                                isValid = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        AppController.getInstance().addToRequestQueue(JORequest);
        return isValid;
    }

    private void populateHeroes() {
        String url ="https://api.opendota.com/api/heroes";
        // Request a jsonobject response from the provided URL.
        JsonArrayRequest JORequest =  new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VolleyRequest", response.toString());
                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";

                            ActiveAndroid.beginTransaction();
                            try {
                                long startTime = System.nanoTime();

                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject row = (JSONObject) response.get(i);

                                    String preparedURL = row.getString("name");
                                    String heroName = preparedURL.substring(14);

                                    Hero hero = new Hero();
                                    hero.id = row.getInt("id");
                                    hero.name = row.getString("name");
                                    hero.localizedName = row.getString("localized_name");
                                    hero.primaryAttr = row.getString("primary_attr");
                                    hero.attackType = row.getString("attack_type");
                                    hero.url = "https://api.opendota.com/apps/dota2/images/heroes/" + heroName + "_full.png?";
                                    hero.save();
                                }
                                long endTime = System.nanoTime();
                                ActiveAndroid.setTransactionSuccessful();
                                Toast.makeText(getApplicationContext(),
                                        "Successfull", Toast.LENGTH_LONG).show();
                                Log.d("VolleyRequest", "Got All Heroes: " + ((endTime - startTime) / 1000000));
                            }
                            finally {
                                ActiveAndroid.endTransaction();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
    }

    private void saveSteamID() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyDota", MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("steamID", Integer.parseInt(mSteam32IDTextView.getText().toString())); // Storing integer

        editor.commit(); // commit changes
    }
}

