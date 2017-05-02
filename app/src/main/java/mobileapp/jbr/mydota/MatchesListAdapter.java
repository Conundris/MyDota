package mobileapp.jbr.mydota;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import mobileapp.jbr.mydota.Models.Hero;
import mobileapp.jbr.mydota.Models.Match;

public class MatchesListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Match> matches;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public MatchesListAdapter(Activity activity, List<Match> matches) {
        this.activity = activity;
        this.matches = matches;
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int location) {
        return matches.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Match m = matches.get(position);

        // thumbnail image
        thumbNail.setImageUrl(Hero.findByGUID(m.hero_id).url, imageLoader);

        // title
        title.setText(String.valueOf(m.match_id));

        // rating
        if (determineWinLoss(m.player_slot, m.radiant_win)) {
            rating.setText("Win");
            rating.setTextColor(Color.GREEN);
        } else {
            rating.setText("Loss");
            rating.setTextColor(Color.RED);
        }

        genre.setText(Hero.findByGUID(m.hero_id).localizedName);

        // release year
        year.setText(String.valueOf("2014"));

        return convertView;
    }

    private boolean determineWinLoss(int player_slot, boolean radiant_win) {
        if(player_slot < 5 && radiant_win) {
            return true;
        } else if (player_slot < 5 && !radiant_win) {
            return false;
        } else if (player_slot > 5 && !radiant_win) {
            return true;
        } else if (player_slot > 5 && radiant_win) {
            return false;
        }
        return false;
    }

}
