package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.events.SkyCastleInTronEvent;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.TimeUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class SkyCastleInTronListener implements Listener {

    @EventHandler
    public void onStepIn(SkyCastleInTronEvent e){
        SkyCastle skyCastle = e.getSkyCastle();
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(e.getTeam().equals("BLUE")){
            if(skyCastle.isBlueTeam(p)){
                return;
            }
            if(skyCastle.isSpectator(p)){
                return;
            }
            if(skyCastle.getBlueTaking() < 40){
                Util.sendTitle(skyCastle.getBlueTeam(), "&4&lALERT");
                Util.sendSubTitle(skyCastle.getBlueTeam(), "&8>> &cOur throne is capturing!");
                skyCastle.addBlueTaking(1);
                Util.sendTitle(p, "");
                Util.sendSubTitle(p, "&8>> &7Capturing throne &9BLUE &c" + skyCastle.getBlueTaking() + "&7/&c40 &8<<");
                u.addGameNumbers(1);
            }else{
                skyCastle.setBlueTaking(0);
                for(Player lose : skyCastle.getBlueTeam()){
                    lose.getInventory().clear();
                    lose.getInventory().setArmorContents(new ItemStack[0]);
                    Util.sendTitle(lose, "&c&lLOSE");
                    Util.sendSubTitle(lose, "&8>> &7Yours throne was captured by team &cRED&7!");
                }
                for(Player win : skyCastle.getRedTeam()){
                    win.getInventory().clear();
                    win.getInventory().setArmorContents(new ItemStack[0]);
                    Util.sendTitle(win, "&a&lWIN");
                    Util.sendSubTitle(win, "&8>> &7You have captured &9BLUE&7 throne!");
                    Util.spawnFireworks(win.getLocation(), 3);
                }
                skyCastle.setStatus(ArenaStatus.RESTARTING);
                new BukkitRunnable(){
                    public void run(){
                        for(Player player : skyCastle.getPlayers()){
                            player.getInventory().setArmorContents(new ItemStack[0]);
                            player.getInventory().clear();
                            player.teleport(Main.getMainLobbyLocation());
                            player.getInventory().setItem(4, ItemUtil.menu);
                            player.getInventory().setItem(6, ItemUtil.magicPearl);
                            player.getInventory().setItem(2, ItemUtil.gadzety);
                            for(Player online : Bukkit.getOnlinePlayers()){
                                if(!online.canSee(player)){
                                    online.showPlayer(player);
                                }
                            }
                            User u = UserManager.getUser(player);
                            u.setSkyCastle(null);
                            double points = Util.round((u.getGameKills()*1.8)+(u.getGameAssists()*1.2)+(u.getGameGrenades()*1.3)+(u.getGameKapliczki()*1.6)+(u.getGameNumbers()*1.4), 2);
                            int coinsGained = (int)(points*4.5);
                            Util.sendMessage(player, "&8&m==============&8 ( &6SUMMARY &8) &8&m===============" +
                                    "\n&8&m==&7> &7Kills in game: &6" + u.getGameKills() +
                                    "\n&8&m==&7> &7Assists in game: &6" + u.getGameAssists() +
                                    "\n&8&m==&7> &7Used grenades: &6" + u.getGameGrenades() +
                                    "\n&8&m==&7> &7Captured chapels: &6" + u.getGameKapliczki() +
                                    "\n&8&m==&7> &7Capturing seconds: &6" + u.getGameNumbers() +
                                    "\n&8&m==&7> &7In total you got &6" + points + " &7points in this game!" +
                                    "\n&8&m==&7> &7You got additionl &c" + coinsGained + " &6coins&7!" +
                                    "\n&8&m==============&8 ( &6SUMMARY &8) &8&m===============");
                            u.addCoins(coinsGained);
                            if(points >= 87.5){
                                int rewardStars = 1;
                                double need = 87.5*2;
                                while(points > need){
                                    rewardStars++;
                                    need += 87.5;
                                }
                                u.addStars(rewardStars);
                                Util.sendMessage(Bukkit.getOnlinePlayers(), "&e&lSTARS &8&m=||=&7 Player &c" + u.getLastName() + " &7got &6" + rewardStars + "&e✪ star/s &7for &abest statistics &7in game!");
                                Team team = Main.getScoreboard().getTeam(player.getName());
                                if(team != null){
                                    team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
                                }
                            }
                            if(skyCastle.getScoreRedTeam().hasPlayer(player)){
                                skyCastle.getScoreRedTeam().removePlayer(player);
                            }else if(skyCastle.getScoreBlueTeam().hasPlayer(player)){
                                skyCastle.getScoreBlueTeam().removePlayer(player);
                            }
                        }
                        skyCastle.getPlayers().clear();
                        skyCastle.getRedTeam().clear();
                        skyCastle.getBlueTeam().clear();
                        skyCastle.setTaking(0);
                        skyCastle.setStartTime(TimeUtil.SECOND.getTime(30));
                        skyCastle.setKapliczkaTakenBy("NOTHING");
                        skyCastle.setBlueTaking(0);
                        skyCastle.setRedTaking(0);
                        skyCastle.getSpectators().clear();
                        skyCastle.rebuildMap();
                    }
                }.runTaskLater(Main.getPlugin(), 100L);
            }
        }else if(e.getTeam().equals("RED")){
            if(skyCastle.isRedTeam(p)){
                return;
            }
            if(skyCastle.isSpectator(p)){
                return;
            }
            if(skyCastle.getRedTaking() < 40){
                Util.sendTitle(skyCastle.getRedTeam(), "&4&lALERT");
                Util.sendSubTitle(skyCastle.getRedTeam(), "&8>> &cOur throne is capturing!");
                skyCastle.addRedTaking(1);
                Util.sendTitle(p, "");
                Util.sendSubTitle(p, "&8>> &7Capturing throne &4RED &c" + skyCastle.getRedTaking() + "&7/&c40 &8<<");
                u.addGameNumbers(1);
            }else{
                skyCastle.setBlueTaking(0);
                for(Player lose : skyCastle.getRedTeam()){
                    lose.getInventory().clear();
                    lose.getInventory().setArmorContents(new ItemStack[0]);
                    Util.sendTitle(lose, "&c&lLOSE");
                    Util.sendSubTitle(lose, "&8>> &7Yours throne was captured by team &9BLUE&7!");
                }
                for(Player win : skyCastle.getBlueTeam()){
                    win.getInventory().clear();
                    win.getInventory().setArmorContents(new ItemStack[0]);
                    Util.sendTitle(win, "&a&lWIN");
                    Util.sendSubTitle(win, "&8>> &7You have captured the &4RED&7! throne");
                    Util.spawnFireworks(win.getLocation(), 3);
                }
                skyCastle.setStatus(ArenaStatus.RESTARTING);
                new BukkitRunnable(){
                    public void run(){
                        for(Player player : skyCastle.getPlayers()){
                            player.getInventory().setArmorContents(new ItemStack[0]);
                            player.getInventory().clear();
                            player.teleport(Main.getMainLobbyLocation());
                            player.getInventory().setItem(4, ItemUtil.menu);
                            player.getInventory().setItem(6, ItemUtil.magicPearl);
                            player.getInventory().setItem(2, ItemUtil.gadzety);
                            for(Player online : Bukkit.getOnlinePlayers()){
                                if(!online.canSee(player)){
                                    online.showPlayer(player);
                                }
                            }
                            User u = UserManager.getUser(player);
                            u.setSkyCastle(null);
                            double points = Util.round((u.getGameKills()*1.8)+(u.getGameAssists()*1.2)+(u.getGameGrenades()*1.3)+(u.getGameKapliczki()*1.6)+(u.getGameNumbers()*1.4), 2);
                            int coinsGained = (int)(points*4.5);
                            Util.sendMessage(player, "&8&m==============&8 ( &6SUMMARY &8) &8&m===============" +
                                    "\n&8&m==&7> &7Kills in game: &6" + u.getGameKills() +
                                    "\n&8&m==&7> &7Assists in game: &6" + u.getGameAssists() +
                                    "\n&8&m==&7> &7Used grenades: &6" + u.getGameGrenades() +
                                    "\n&8&m==&7> &7Captured chapels: &6" + u.getGameKapliczki() +
                                    "\n&8&m==&7> &7Capturing seconds: &6" + u.getGameNumbers() +
                                    "\n&8&m==&7> &7In total you got &6" + points + " &7points in this game!" +
                                    "\n&8&m==&7> &7You got additionl &c" + coinsGained + " &6coins&7!" +
                                    "\n&8&m==============&8 ( &6SUMMARY &8) &8&m===============");
                            u.addCoins(coinsGained);
                            if(points >= 87.5){
                                int rewardStars = 1;
                                double need = 87.5*2;
                                while(points > need){
                                    rewardStars++;
                                    need += 87.5;
                                }
                                u.addStars(rewardStars);
                                Util.sendMessage(Bukkit.getOnlinePlayers(), "&e&lSTARS &8&m=||=&7 Player &c" + u.getLastName() + " &7got &6" + rewardStars + "&e✪ star/s &7for &abest statistics &7in game!");
                                Team team = Main.getScoreboard().getTeam(player.getName());
                                if(team != null){
                                    team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
                                }
                            }
                            if(skyCastle.getScoreRedTeam().hasPlayer(player)){
                                skyCastle.getScoreRedTeam().removePlayer(player);
                            }else if(skyCastle.getScoreBlueTeam().hasPlayer(player)){
                                skyCastle.getScoreBlueTeam().removePlayer(player);
                            }
                        }
                        skyCastle.getPlayers().clear();
                        skyCastle.getRedTeam().clear();
                        skyCastle.getBlueTeam().clear();
                        skyCastle.setTaking(0);
                        skyCastle.setStartTime(TimeUtil.SECOND.getTime(30));
                        skyCastle.setKapliczkaTakenBy("NOTHING");
                        skyCastle.setBlueTaking(0);
                        skyCastle.setRedTaking(0);
                        skyCastle.getSpectators().clear();
                        skyCastle.rebuildMap();
                    }
                }.runTaskLater(Main.getPlugin(), 100L);
            }
        }
    }
}
