package mobileapp.jbr.mydota;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import mobileapp.jbr.mydota.Models.RecentMatch;

public class StatsFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double winrate;
    private RecentMatch minKills;
    private RecentMatch maxKills;
    private RecentMatch minDeaths;
    private RecentMatch maxDeaths;
    private RecentMatch minAssists;
    private RecentMatch maxAssists;
    private RecentMatch minGPM;
    private RecentMatch maxGPM;
    private RecentMatch minXPM;
    private RecentMatch maxXPM;
    private RecentMatch minLastHits;
    private RecentMatch maxLastHits;

    private TextView txtWinRate;

    private TextView txtKills;
    private TextView txtDeaths;
    private TextView txtGPM;
    private TextView txtXPM;

    private TextView txtMinKills;
    private TextView txtMaxKills;
    private TextView txtMinDeaths;
    private TextView txtMaxDeaths;
    private TextView txtMinAssists;
    private TextView txtMaxAssists;
    private TextView txtMinGPM;
    private TextView txtMaxGPM;
    private TextView txtMinXPM;
    private TextView txtMinLastHits;
    private TextView txtMaxLastHits;



    public StatsFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragement newInstance(String param1, String param2) {
        StatsFragement fragment = new StatsFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private void processData() {
        List<RecentMatch> matches = RecentMatch.getAllMatches();

        for (RecentMatch match : matches) {
            if(minAssists == null) minAssists = match;
            if(maxAssists == null) maxAssists = match;
            if(minKills == null) minKills = match;
            if(maxKills == null) maxKills = match;
            if(minDeaths == null) minDeaths = match;
            if(maxDeaths == null) maxDeaths = match;
            if(minGPM == null) minGPM = match;
            if(maxGPM == null) maxGPM = match;
            if(minXPM == null) minXPM = match;
            if(maxXPM == null) maxXPM = match;
            if(minLastHits == null) minLastHits = match;
            if(maxLastHits == null) maxLastHits = match;

            if(match.assists < minAssists.assists) minAssists = match;
            if(match.assists > maxAssists.assists) maxAssists = match;
            if(match.kills < minKills.kills) minKills = match;
            if(match.kills > maxKills.kills) maxKills = match;
            if(match.deaths < minDeaths.deaths) minDeaths = match;
            if(match.deaths > maxDeaths.deaths) maxDeaths = match;
            if(match.gold_per_min < minGPM.gold_per_min) minGPM = match;
            if(match.gold_per_min > maxGPM.gold_per_min) maxGPM = match;
            if(match.xp_per_min < minXPM.xp_per_min) minXPM = match;
            if(match.xp_per_min > maxXPM.xp_per_min) maxXPM = match;
            if(match.last_hits < minLastHits.last_hits) minLastHits = match;
            if(match.last_hits > maxLastHits.last_hits) maxLastHits = match;

        }

        txtKills.setText(String.valueOf(minKills.kills) + " / " + maxKills.kills);
        txtDeaths.setText(String.valueOf(minDeaths.deaths) + " / " + maxDeaths.deaths);
        txtGPM.setText(String.valueOf(minGPM.gold_per_min) + " / " + String.valueOf(maxGPM.gold_per_min));
        txtXPM.setText(String.valueOf(minXPM.xp_per_min) + " / " + String.valueOf(maxXPM.xp_per_min));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats_fragement, container, false);

        txtWinRate = (TextView) view.findViewById(R.id.txtWinRate);

        txtKills = (TextView) view.findViewById(R.id.txtKills);
        txtDeaths = (TextView) view.findViewById(R.id.txtDeaths);
        txtGPM = (TextView) view.findViewById(R.id.txtGPM);
        txtXPM = (TextView) view.findViewById(R.id.txtXPM);


        processData();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
    }*/
}
