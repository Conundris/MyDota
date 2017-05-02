package mobileapp.jbr.mydota.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.Map;

@Table(name = "Hero")
public class Hero extends Model {

    @Column(name = "heroId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "localizedName")
    public String localizedName;
    @Column(name = "primaryAttr")
    public String primaryAttr;
    @Column(name = "attackType")
    public String attackType;
    @Column(name = "url")
    public String url;

    public Hero(){
        super();
    }

    //find items by id
    public static Hero findByGUID(int id){
        return new Select().from(Hero.class).where("heroId = ?", id).executeSingle();
    }
    //retrieve all items
    public static List<Hero> getAllFeedItems() {
        return new Select().from(Hero.class).orderBy("id DESC").execute();
    }
}