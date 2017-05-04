package mobileapp.jbr.mydota;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mobileapp.jbr.mydota.Models.RecentMatch;
import mobileapp.jbr.mydota.dummy.DummyContent.DummyItem;

import static android.content.Context.MODE_PRIVATE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MatchesFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;

    private int steamID;
    private List<RecentMatch> recentMatches = new ArrayList<>();
    private ListView listView;
    private MatchesListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MatchesFragment newInstance(int columnCount) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getContext().getSharedPreferences("MyDota", MODE_PRIVATE);

        steamID = prefs.getInt("steamID", 70677728);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        //Get Items in View
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new MatchesListAdapter(getActivity(), recentMatches);
        listView.setAdapter(adapter);

        getMatches();

        listView.setOnItemClickListener(this);
        return view;
    }

    private void getMatches() {
        String url ="https://api.opendota.com/api/players/" + steamID + "/recentMatches?limit=10";
        // Request a jsonobject response from the provided URL.
        JsonArrayRequest JORequest =  new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VolleyRequest", response.toString());
                        try {
                            // Parsing json array response
                            // loop through each json object
                                long startTime = System.nanoTime();

                            ActiveAndroid.beginTransaction();
                            try
                            {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject row = (JSONObject) response.get(i);

                                    RecentMatch recentMatch = new RecentMatch();
                                    recentMatch.match_id = row.getString("match_id");
                                    recentMatch.assists = row.getInt("assists");
                                    recentMatch.deaths = row.getInt("deaths");
                                    recentMatch.kills = row.getInt("kills");
                                    recentMatch.start_time = row.getDouble("start_time");
                                    recentMatch.duration = row.getInt("duration");
                                    recentMatch.game_mode = row.getInt("game_mode");
                                    recentMatch.hero_id = row.getInt("hero_id");
                                    recentMatch.lobby_type = row.getInt("lobby_type");
                                    recentMatch.radiant_win = row.getBoolean("radiant_win");
                                    recentMatch.player_slot = row.getInt("player_slot");
                                    recentMatch.xp_per_min = row.getInt("xp_per_min");
                                    recentMatch.gold_per_min = row.getInt("gold_per_min");
                                    recentMatch.hero_damage = row.getInt("hero_damage");
                                    recentMatch.tower_damage = row.getInt("tower_damage");
                                    recentMatch.hero_healing = row.getInt("hero_healing");
                                    recentMatch.last_hits = row.getInt("last_hits");

                                    recentMatch.save();
                                    recentMatches.add(recentMatch);
                                }
                                ActiveAndroid.setTransactionSuccessful();
                                long endTime = System.nanoTime();
                                Log.d("VolleyRequest", "Got All Matches: " + ((endTime - startTime) / 1000000));
                            }
                        finally {
                            ActiveAndroid.endTransaction();
                                Log.d("ActiveAndroid", "Transaction Ended");
                        }
                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d("VolleyRequest", "Error: " + e.getMessage());
                            Toast.makeText(getContext(),
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("VolleyRequest", "Error: " + error.getMessage());
                        Toast.makeText(getContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(JORequest);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecentMatch itemClicked = (RecentMatch) adapter.getItem(position);

        Intent intent = new Intent(getActivity(), MatchActivity.class);
        intent.putExtra("matchID", itemClicked.match_id);
        startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
