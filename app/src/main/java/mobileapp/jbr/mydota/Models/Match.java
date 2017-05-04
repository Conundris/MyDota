package mobileapp.jbr.mydota.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by womble on 03.05.2017.
 */
@Table(name = "Match")
public class Match extends Model
{
    @Column(name = "match_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    public String match_id;
    @Column(name = "barracks_status_dire")
    public int barracks_status_dire;
    @Column(name = "barracks_status_radiant")
    public int barracks_status_radiant;
    //public List<Chat> chat;
    @Column(name = "cluster")
    public int cluster;
    //public Cosmetics cosmetics;
    @Column(name = "dire_score")
    public int dire_score;
    @Column(name = "duration")
    public int duration;
    @Column(name = "engine")
    public int engine;
    @Column(name = "first_blood_time")
    public int first_blood_time;
    @Column(name = "game_mode")
    public int game_mode;
    @Column(name = "human_players")
    public int human_players;
    @Column(name = "leagueid")
    public int leagueid;
    @Column(name = "lobby_type")
    public int lobby_type;
    @Column(name = "match_seq_num")
    public long match_seq_num;
    @Column(name = "negative_votes")
    public int negative_votes;
    //public List<Objective> objectives;
    //public object picks_bans;
    @Column(name = "positive_votes")
    public int positive_votes;
    @Column(name = "radiant_gold_adv")
    public List<Integer> radiant_gold_adv;
    @Column(name = "radiant_score")
    public int radiant_score;
    @Column(name = "radiant_win")
    public boolean radiant_win;
    @Column(name = "radiant_xp_adv")
    public List<Integer> radiant_xp_adv;
    @Column(name = "skill")
    public int skill;
    @Column(name = "start_time")
    public int start_time;
    //public List<Teamfight> teamfights;
    @Column(name = "tower_status_dire")
    public int tower_status_dire;
    @Column(name = "tower_status_radiant")
    public int tower_status_radiant;
    @Column(name = "version")
    public int version;
    @Column(name = "replay_salt")
    public int replay_salt;
    @Column(name = "series_id")
    public int series_id;
    @Column(name = "series_type")
    public int series_type;

    //public List<Player> players;
    @Column(name = "patch")
    public int patch;
    @Column(name = "region")
    public int region;
    //public AllWordCounts all_word_counts;
    //public MyWordCounts my_word_counts;
    @Column(name = "thrown")
    public int thrown;
    @Column(name = "loss")
    public int loss;
    @Column(name = "replay_url")
    public String replay_url;

    public Match() { super(); }

    //find items by id
    public static RecentMatch findbyMatchID(String id){
        return new Select().from(RecentMatch.class).where("matchID = ?", id).executeSingle();
    }
    //retrieve all items
    public static List<RecentMatch> getAllMatches() {
        return new Select().from(RecentMatch.class).orderBy("id DESC").execute();
    }
}
