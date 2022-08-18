package pl.ololjvNek.skycastle.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.combats.Combat;
import pl.ololjvNek.skycastle.combats.CombatManager;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player){
            final Player p = e.getEntity();
            final User u = UserManager.getUser(p);
            if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
                if(p.getKiller() instanceof Player){
                    final Player killer = p.getKiller();
                    final User kU = UserManager.getUser(killer);
                    kU.addKills(1);
                    kU.addGameKills(1);
                    final Combat combat = CombatManager.getCombat(killer);
                    if(combat != null){
                        for(Player assisted : combat.getAssisted()){
                            final User assistU = UserManager.getUser(assisted);
                            assistU.addAssists(1);
                            assistU.addGameAssists(1);
                        }
                    }
                    CombatManager.getCombats().remove(combat);
                    UserManager.killPlayer(killer, p);
                }
                e.setDeathMessage(null);
                respawn(p);
                u.addDeaths(1);
                final SkyCastle skyCastle = u.getSkyCastle();
                Util.sendMessage(skyCastle.getPlayers(), "&7Player &c" + p.getName() + " &7died");
                new BukkitRunnable(){
                    public void run(){
                        if(skyCastle.getEvent() != null && skyCastle.getEvent().isEnabled() && skyCastle.getEvent().hasEvent("Respawning")){
                            p.setGameMode(GameMode.CREATIVE);
                            skyCastle.getSpectators().add(p);
                            for(Player inGame : skyCastle.getPlayers()){
                                inGame.hidePlayer(p);
                            }
                            p.teleport(skyCastle.getKapliczka());
                            SkyCastleManager.respawnPlayer(skyCastle, p);
                        }else{
                            p.setGameMode(GameMode.CREATIVE);
                            skyCastle.getSpectators().add(p);
                            for(Player inGame : skyCastle.getPlayers()){
                                inGame.hidePlayer(p);
                            }
                            switch (skyCastle.getTeamIn(p)){
                                case "RED":
                                    skyCastle.getRedTeam().remove(p);
                                    break;
                                case "BLUE":
                                    skyCastle.getBlueTeam().remove(p);
                                    break;
                            }
                            p.getInventory().setItem(8, ItemUtil.leaveGame);
                            p.teleport(skyCastle.getKapliczka());
                        }
                    }
                }.runTaskLater(Main.getPlugin(), 10L);
            }else{
                respawn(p);
            }
        }
    }

    public void respawn(Player player){
        new BukkitRunnable(){
            public void run(){
                if (player.isDead()) {
                    player.spigot().respawn();
                }
            }
        }.runTaskLater(Main.getPlugin(), 5L);
    }
}
