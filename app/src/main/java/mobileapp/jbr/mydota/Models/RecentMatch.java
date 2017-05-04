package mobileapp.jbr.mydota.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "RecentMatch")
public class RecentMatch extends Model
{
    @Column(name = "assists")
    public int assists;
    @Column(name = "duration")
    public int duration;
    @Column(name = "player_slot")
    public int player_slot;
    @Column(name = "deaths")
    public int deaths;
    @Column(name = "radiant_win")
    public boolean radiant_win;
    @Column(name = "matchID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    public String match_id;
    @Column(name = "game_mode")
    public int game_mode;
    @Column(name = "start_time")
    public double start_time;
    @Column(name = "kills")
    public int kills;
    @Column(name = "hero_id")
    public int hero_id;
    @Column(name = "lobby_type")
    public int lobby_type;
    @Column(name = "version")
    public int version;
    @Column(name = "xp_per_min")
    public int xp_per_min;
    @Column(name = "gold_per_min")
    public int gold_per_min;
    @Column(name = "hero_damage")
    public int hero_damage;
    @Column(name = "tower_damage")
    public int tower_damage;
    @Column(name = "hero_healing")
    public int hero_healing;
    @Column(name = "last_hits")
    public int last_hits;

    public RecentMatch() { super(); }

    //find items by id
    public static RecentMatch findbyMatchID(String id){
        return new Select().from(RecentMatch.class).where("matchID = ?", id).executeSingle();
    }
    //retrieve all items
    public static List<RecentMatch> getAllMatches() {
        return new Select().from(RecentMatch.class).orderBy("id DESC").execute();
    }
}
