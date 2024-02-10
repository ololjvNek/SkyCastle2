package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.QueueManager;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.sql.SQLException;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        if(QueueManager.getPlayerJoin() >= 100){
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, Util.fixColors("&c[Connection] &8->> &7Please wait 5 seconds before joining we have trouble with mass player joining"));
        }
        if(!SkyCastleManager.isAllSkyCastlesLoaded()){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Util.fixColors("&c[SkyCastle-Manager] &8->> &7Loading arenas... Please wait"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u == null){
            u = UserManager.createUser(p);
        }
        QueueManager.addPlayerJoin(1);
        e.setJoinMessage("");
        Team team = null;
        if(Main.getScoreboard().getTeam(p.getName()) != null){
            team = Main.getScoreboard().getTeam(p.getName());
        }else{
            team = Main.getScoreboard().registerNewTeam(p.getName());
        }
        team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
        team.addPlayer(p);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        p.teleport(Main.getMainLobbyLocation());
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.getInventory().clear();
        p.getInventory().setItem(4, ItemUtil.menu);
        p.getInventory().setItem(6, ItemUtil.magicPearl);
        p.getInventory().setItem(2, ItemUtil.gadgets);
        p.setScoreboard(Main.getScoreboard());
        /*try {
            u.updateWhenDataIsNotCorrect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        e.setQuitMessage("");
        if(u.getSkyCastle() != null){
            SkyCastleManager.leaveGame(u.getSkyCastle(), p);
        }
        u.update();
    }
}
