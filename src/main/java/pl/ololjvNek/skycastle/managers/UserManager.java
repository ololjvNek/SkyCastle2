package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.data.saving.Flat;
import pl.ololjvNek.skycastle.enums.SavingType;
import pl.ololjvNek.skycastle.utils.Logger;
import pl.ololjvNek.skycastle.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UserManager {

    @Getter private static HashMap<UUID, User> users = new HashMap<>();

    public static User createUser(Player player){
        User u = new User(player);
        users.put(u.getUUID(), u);
        RankingManager.getUserRankings().add(u);
        RankingManager.getStarsRankings().add(u);
        Logger.info("[UserManager] Created new player " + u.getUUID().toString());
        return u;
    }

    public static User getUser(Player player){
        return users.get(player.getUniqueId());
    }

    public static User getUser(UUID uuid){
        return users.get(uuid);
    }

    public static void killPlayer(Player killer, Player death){
        double pointsGet = 0;
        User kU = getUser(killer);
        User dU = getUser(death);
        if(kU.getPoints() > dU.getPoints()){
            pointsGet = (kU.getPoints()/dU.getPoints())*1.6;
        }else if(kU.getPoints() < dU.getPoints()){
            pointsGet = (dU.getPoints()/kU.getPoints())*2.2;
        }else{
            pointsGet = ThreadLocalRandom.current().nextInt(40)+12;
        }
        kU.addPoints((int)pointsGet);
        dU.removePoints((int)pointsGet);
        Util.sendTitle(killer, "&a&lKILL");
        Util.sendSubTitle(killer, "&7You killed &c" + death.getName() + " &8(&a+" + pointsGet + "&8)");
        Util.sendTitle(death, "&c&lDEATH");
        Util.sendSubTitle(death, "&7You got killed by &c" + killer.getName() + " &8(&c-" + pointsGet + "&8)");
    }

    public static User getUserByLastName(String lastName){
        for(User u : users.values()){
            if(u.getLastName().equals(lastName)){
                return u;
            }
        }
        return null;
    }

    public static void loadUsers(){
        if(Main.getSavingType() == SavingType.FLAT){
            for(FileConfiguration fileConfiguration : Flat.getPlayersFromFile()){
                User u = new User(fileConfiguration);
                users.put(u.getUUID(), u);
            }
        }else{
            ResultSet rs = Main.getStore().query("SELECT * FROM `users`");
            try{
                while(rs.next()){
                    User u = new User(rs);
                    users.put(u.getUUID(), u);
                    RankingManager.getUserRankings().add(u);
                    RankingManager.getStarsRankings().add(u);
                }
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        RankingManager.sortUserRankings();
        RankingManager.sortStarsRankings();
        Logger.info("[UserManager] Loaded " + users.size() + " players");
    }
}
