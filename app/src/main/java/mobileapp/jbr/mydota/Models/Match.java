package mobileapp.jbr.mydota.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Match")
public class Match
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

    public Match() {}

    //find items by id
    public static Hero findbyMatchID(String id){
        return new Select().from(Hero.class).where("matchID = ?", id).executeSingle();
    }
    //retrieve all items
    public static List<Hero> getAllMatches() {
        return new Select().from(Hero.class).orderBy("id DESC").execute();
    }
}
