package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.saving.Flat;
import pl.ololjvNek.skycastle.enums.SavingType;
import pl.ololjvNek.skycastle.managers.TeamManager;
import pl.ololjvNek.skycastle.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
public class User {

    private java.util.UUID UUID;
    private String lastName, teamTag, wedka,discordTag;
    private boolean magicPearl,vision,nagroda;
    private Team team;
    private int coins, stars, givedRunes, points,kills,deaths,assists,gameKills,gameAssists,gameGrenades,gameKapliczki,gameNumbers,nagrodaCode;
    private long wedkaCooldown;
    private Disco discoArmor;
    private SkyCastle skyCastle;
    private List<String> addons;

    public User(Player player){
        UUID = player.getUniqueId();
        lastName = player.getName();
        coins = 0;
        discordTag = null;
        kills = 0;
        deaths = 0;
        assists = 0;
        gameNumbers = 0;
        gameKapliczki = 0;
        gameKills = 0;
        gameAssists = 0;
        gameGrenades = 0;
        nagrodaCode = 0;
        stars = 0;
        teamTag = "";
        wedka = "PRZYCIAGANIE";
        points = 0;
        givedRunes = 0;
        team = null;
        vision = true;
        wedkaCooldown = 0L;
        discoArmor = new Disco(getUUID());
        skyCastle = null;
        magicPearl = false;
        nagroda = false;
        addons = new ArrayList<>();
        if(Main.getSavingType() == SavingType.MYSQL){
            ResultSet rs = Main.getStore().query("SELECT * FROM `users` WHERE `uuid`='" + getUUID().toString() + "'");
            try {
                if(rs.next()){
                    updateWhenDataIsNotCorrect();
                    return;
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            insert();
        }else{
            update();
        }
    }

    public User(ResultSet rs) throws SQLException {
        UUID = java.util.UUID.fromString(rs.getString("uuid"));
        lastName = rs.getString("lastName");
        coins = rs.getInt("coins");
        stars = rs.getInt("stars");
        teamTag = rs.getString("team");
        points = rs.getInt("points");
        kills = rs.getInt("kills");
        deaths = rs.getInt("deaths");
        assists = rs.getInt("assists");
        nagroda = rs.getBoolean("nagroda");
        discordTag = rs.getString("discordTag");
        addons = Util.compareStringToList(rs.getString("addons"), ";");
        gameKills = 0;
        gameAssists = 0;
        gameGrenades = 0;
        gameKapliczki = 0;
        gameNumbers = 0;
        nagrodaCode = 0;
        discoArmor = new Disco(getUUID());
        skyCastle = null;
        wedka = "PRZYCIAGANIE";
        wedkaCooldown = 0L;
        givedRunes = 0;
        team = TeamManager.getTeam(getTeamTag());
        magicPearl = false;
        vision = true;
    }

    public User(FileConfiguration fileConfiguration){
        UUID = java.util.UUID.fromString(fileConfiguration.getString("uuid"));
        lastName = fileConfiguration.getString("lastName");
        coins = fileConfiguration.getInt("coins");
        stars = fileConfiguration.getInt("stars");
        teamTag = fileConfiguration.getString("team");
        points = fileConfiguration.getInt("points");
        kills = fileConfiguration.getInt("kills");
        deaths = fileConfiguration.getInt("deaths");
        assists = fileConfiguration.getInt("assists");
        nagroda = fileConfiguration.getBoolean("nagroda");
        discordTag = fileConfiguration.getString("discordTag");
        addons = Util.compareStringToList(fileConfiguration.getString("addons"), ";");
        gameKills = 0;
        gameAssists = 0;
        gameGrenades = 0;
        gameKapliczki = 0;
        gameNumbers = 0;
        nagrodaCode = 0;
        discoArmor = new Disco(getUUID());
        skyCastle = null;
        wedka = "PRZYCIAGANIE";
        wedkaCooldown = 0L;
        givedRunes = 0;
        team = TeamManager.getTeam(getTeamTag());
        magicPearl = false;
        vision = true;

    }

    public void setStarsOnTag(Player p){
        org.bukkit.scoreboard.Team team = null;
        if(Main.getScoreboard().getTeam(p.getName()) != null){
            team = Main.getScoreboard().getTeam(p.getName());
        }else{
            team = Main.getScoreboard().registerNewTeam(p.getName());
        }
        team.setSuffix(Util.fixColors("&8 &6" + getStars() + " &e&l✪"));
        team.addPlayer(p);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        p.setScoreboard(Main.getScoreboard());
    }

    public boolean hasAddon(String addon){
        return getAddons().contains(addon);
    }

    public boolean isOnline(){
        return Bukkit.getPlayer(getUUID()) != null;
    }

    public void addKills(int index){
        kills += index;
    }

    public void addPoints(int index){ points += index;}

    public void removePoints(int index) { points -= index; }

    public void removeCoins(int index){ coins -= index;}

    public void addGameKapliczki(int index){
        gameKapliczki += index;
    }

    public void addGameNumbers(int index){
        gameNumbers += index;
    }

    public void addGameGrenades(int index){
        gameGrenades += index;
    }

    public void addDeaths(int index){
        deaths += index;
    }

    public void addAssists(int index){
        assists = index;
    }

    public void addGameKills(int index){
        gameKills += index;
    }

    public void addGameAssists(int index){
        gameAssists += index;
    }

    public void addGivedRunes(int index){
        givedRunes += index;
    }

    public void addStars(int index){
        stars += index;
    }

    public void addCoins(int index) {coins += index;}

    public void removeStars(int index){
        stars -= index;
    }

    public double getKDA(){
        double kd = 0.00;
        double kills = getKills();
        double deaths = getDeaths();
        double assists = getAssists();
        kd = (kills+assists)/deaths;
        return Util.round(kd, 2);
    }

    public void updateWhenDataIsNotCorrect() throws SQLException{
        ResultSet rs = Main.getStore().query("SELECT * FROM `users` WHERE `uuid`='" + getUUID().toString() + "'");
        if(rs.next()){
            coins = rs.getInt("coins");
            stars = rs.getInt("stars");
            teamTag = rs.getString("team");
            points = rs.getInt("points");
            kills = rs.getInt("kills");
            deaths = rs.getInt("deaths");
            assists = rs.getInt("assists");
            nagroda = rs.getBoolean("nagroda");
            discordTag = rs.getString("discordTag");
            addons = Util.compareStringToList(rs.getString("addons"), ";");
        }
    }

    public void insert(){
        if(Main.getSavingType() == SavingType.MYSQL){
            Main.getStore().update("INSERT INTO `users`(`id`, `uuid`, `lastName`, `coins`, `stars`, `points`, `team`, `kills`, `deaths`, `assists`, `nagroda`, `discordTag`, `addons`) VALUES (NULL, '" + getUUID().toString() + "', '" + getLastName() + "', '" + getCoins() + "', '" + getStars() + "', '" + getPoints() + "', '" + getTeamTag() + "', '" + getKills() + "', '" + getDeaths() + "', '" + getAssists() + "', '" + (isNagroda() ? 1 : 0) + "', '" + (getDiscordTag() == null ? "" : getDiscordTag()) + "', '" + Util.compareListToString(getAddons(), ";") + "');");
        }
    }

    public void update(){
        if(Main.getSavingType() == SavingType.FLAT){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("coins", getCoins());
            hashMap.put("uuid", getUUID().toString());
            hashMap.put("lastName", getLastName());
            hashMap.put("kills", getKills());
            hashMap.put("deaths", getDeaths());
            hashMap.put("assists", getAssists());
            hashMap.put("nagroda", isNagroda());
            hashMap.put("discordTag", getDiscordTag());
            hashMap.put("addons", Util.compareListToString(getAddons(), ";"));
            Flat.saveFlat(getUUID().toString(), hashMap);
        }else{
            Main.getStore().update(false, "UPDATE `users` SET `coins` = '" + getCoins() + "', `points` = '" + getPoints() + "', `team` = '" + getTeamTag() + "', `stars` = '" + getStars() + "', `kills` = '" + getKills() + "', `deaths` = '" + getDeaths() + "', `assists` = '" + getAssists() + "', `nagroda` = '" + (isNagroda() ? 1 : 0)  + "', `discordTag` = '" + getDiscordTag() + "', `addons` = '" + Util.compareListToString(getAddons(), ";") + "' WHERE `uuid` = '" + getUUID().toString() + "';");
            Bukkit.getLogger().info("[UserManager] Saved user " + getUUID().toString() + " to database");
        }
    }
}
