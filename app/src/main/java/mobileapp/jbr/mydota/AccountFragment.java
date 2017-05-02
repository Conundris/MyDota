package mobileapp.jbr.mydota;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import mobileapp.jbr.mydota.Models.Account;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    private TextView textView;
    private ImageView imageView;
    private TextView txtWinsValue;
    private TextView txtLossesValue;
    private TextView txtWinRateValue;

    private int winRate;
    private int lossRate;
    private Account account;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getContext().getSharedPreferences("MyDota", MODE_PRIVATE);

        account = new Account();
        account.steamID = prefs.getInt("steamID", 70677728);

        getData();
    }

    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        System.out.println(textView);
        JsonObjectRequest jsObjProfile = getProfile();
        JsonObjectRequest jsObjMatches = getWinLossRate();
        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(jsObjProfile);
        AppController.getInstance().addToRequestQueue(jsObjMatches);
    }

    private JsonObjectRequest getWinLossRate() {
        String url ="https://api.opendota.com/api/players/" + account.steamID + "/wl";
        // Request a jsonobject response from the provided URL.
        return new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            winRate = response.getInt("win");
                            lossRate = response.getInt("lose");
                            txtWinsValue.setText(String.valueOf(winRate));
                            txtLossesValue.setText(String.valueOf(lossRate));
                            txtWinRateValue.setText(String.valueOf(getWinRate(winRate, lossRate)) + "%");
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
    }

    private double getWinRate(int winRate, int lossRate) {
        int totalMatches = winRate + lossRate;
        return round((double)winRate / (double)totalMatches * 100, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @NonNull
    private JsonObjectRequest getProfile() {
        String url ="https://api.opendota.com/api/players/" + account.steamID;
        // Request a jsonobject response from the provided URL.
        return new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject profile = response.getJSONObject("profile");

                            System.out.println(profile.toString());
                            textView.setText(profile.getString("personaname"));
                            new DownloadImageTask(imageView).execute(profile.getString("avatarfull"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        textView.setText("Did not work.");
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //Get Items in View
        textView = (TextView) view.findViewById(R.id.textView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        txtWinsValue = (TextView) view.findViewById(R.id.txtWinValue);
        txtLossesValue = (TextView) view.findViewById(R.id.txtLossesValue);
        txtWinRateValue = (TextView) view.findViewById(R.id.txtWinRateValue);

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
