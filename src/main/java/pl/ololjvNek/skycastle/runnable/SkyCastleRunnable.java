package pl.ololjvNek.skycastle.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.events.SkyCastleInKapliczkaEvent;
import pl.ololjvNek.skycastle.events.SkyCastleInTronEvent;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.*;
import pl.ololjvNek.worldmanager.worlds.WorldManager;

import java.util.List;

public class SkyCastleRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            BoardUtil.update(p);
        }
        for(SkyCastle skyCastle : SkyCastleManager.getSkyCastles().values()){
            if(skyCastle.getStatus() == ArenaStatus.STARTED){
                if(skyCastle.getEvent() == null || !skyCastle.getEvent().isEnabled() || !skyCastle.getEvent().hasEvent("Debug")){
                    SkyCastleManager.tryToEndGame(skyCastle);
                }
                if(!skyCastle.getKapliczkaTakenBy().equals("NOTHING")){
                    switch (skyCastle.getKapliczkaTakenBy()){
                        case "BLUE":
                            for(Player player : skyCastle.getBlueTeam()){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 0));
                            }
                            break;
                        case "RED":
                            for(Player player : skyCastle.getRedTeam()){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 0));
                            }
                            break;
                    }
                }
                List<Player> inKapliczka = Util.getListOfPlayersInArea(skyCastle.getKapliczka(), 7);
                if(inKapliczka.size() > 0){
                    for(Player p : inKapliczka){
                        inKapliczka.remove(p);
                        Bukkit.getPluginManager().callEvent(new SkyCastleInKapliczkaEvent(p, skyCastle, inKapliczka));
                        inKapliczka.add(p);
                    }
                }
                List<Player> inTron = Util.getListOfPlayersInArea(skyCastle.getBlueTron(), 1.5);
                if(inTron.size() > 0){
                    for(Player p : inTron){
                        inTron.remove(p);
                        Bukkit.getPluginManager().callEvent(new SkyCastleInTronEvent(p, skyCastle, inTron, "BLUE"));
                        inTron.add(p);
                    }
                }
                List<Player> inTron2 = Util.getListOfPlayersInArea(skyCastle.getRedTron(), 1.5);
                if(inTron2.size() > 0){
                    for(Player p : inTron2){
                        inTron2.remove(p);
                        Bukkit.getPluginManager().callEvent(new SkyCastleInTronEvent(p, skyCastle, inTron2, "RED"));
                        inTron2.add(p);
                    }
                }
            }
        }
        /*for(Player p : Bukkit.getOnlinePlayers()){
            BoardUtil.update(p);
            User u = UserManager.getUser(p);
            if(u == null){
                continue;
            }
            if(u.getSkyCastle() == null || u.getSkyCastle().getStatus() != ArenaStatus.STARTED){
                continue;
            }
            if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
                SkyCastle skyCastle = u.getSkyCastle();
                if(skyCastle.isSpectator(p)){
                    continue;
                }
                if(skyCastle.getEvent() == null || !skyCastle.getEvent().isEnabled() || !skyCastle.getEvent().hasEvent("Debug")){
                    SkyCastleManager.tryToEndGame(skyCastle);
                }
                if(!skyCastle.getKapliczkaTakenBy().equals("NOTHING")){
                    switch (skyCastle.getKapliczkaTakenBy()){
                        case "BLUE":
                            for(Player player : skyCastle.getBlueTeam()){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*2, 0));
                            }
                            break;
                        case "RED":
                            for(Player player : skyCastle.getRedTeam()){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*2, 0));
                            }
                            break;
                    }
                }
                if(p.getLocation().distance(skyCastle.getBlueTron()) < 1.5){
                    if(skyCastle.isBlueTeam(p)){
                        return;
                    }
                    if(skyCastle.isSpectator(p)){
                        return;
                    }
                    if(skyCastle.getBlueTaking() < 40){
                        Util.sendTitle(skyCastle.getBlueTeam(), "&4&lALARM");
                        Util.sendSubTitle(skyCastle.getBlueTeam(), "&8>> &cWasz tron jest przejmowany!");
                        skyCastle.addBlueTaking(1);
                        Util.sendTitle(p, "");
                        Util.sendSubTitle(p, "&8>> &7Przejmowanie tronu &9NIEBIESKICH &c" + skyCastle.getBlueTaking() + "&7/&c100 &8<<");
                        u.addGameNumbers(1);
                    }else{
                        skyCastle.setBlueTaking(0);
                        for(Player lose : skyCastle.getBlueTeam()){
                            lose.getInventory().clear();
                            lose.getInventory().setArmorContents(new ItemStack[0]);
                            Util.sendTitle(lose, "&c&lPRZEGRANA");
                            Util.sendSubTitle(lose, "&8>> &7Wasz tron zostal przejety przez druzyne &cCZERWONA&7!");
                        }
                        for(Player win : skyCastle.getRedTeam()){
                            win.getInventory().clear();
                            win.getInventory().setArmorContents(new ItemStack[0]);
                            Util.sendTitle(win, "&a&lZWYCIESTWO");
                            Util.sendSubTitle(win, "&8>> &7Przejeliscie tron druzyny &9NIEBIESKICH&7!");
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
                                    Util.sendMessage(player, "&8&m==============&8 ( &6PODSUMOWANIE &8) &8&m===============" +
                                            "\n&8&m==&7> &7Laczna ilosc zabojstw: &6" + u.getGameKills() +
                                            "\n&8&m==&7> &7Laczna ilosc asyst: &6" + u.getGameAssists() +
                                            "\n&8&m==&7> &7Uzyte granaty: &6" + u.getGameGrenades() +
                                            "\n&8&m==&7> &7Przejete kapliczki: &6" + u.getGameKapliczki() +
                                            "\n&8&m==&7> &7Ilosc sekund przejmowania w sumie: &6" + u.getGameNumbers() +
                                            "\n&8&m==&7> &7W sumie zdobyles &6" + points + " &7punktow w tej grze!" +
                                            "\n&8&m==&7> &7Otrzymujesz dodatkowo &c" + coinsGained + " &6coinsow&7!" +
                                            "\n&8&m==============&8 ( &cdineron.net &8) &8&m===============");
                                    u.addCoins(coinsGained);
                                    if(points >= 87.5){
                                        int rewardStars = 1;
                                        double need = 87.5*2;
                                        while(points > need){
                                            rewardStars++;
                                            need += 87.5;
                                        }
                                        u.addStars(rewardStars);
                                        Util.sendMessage(Bukkit.getOnlinePlayers(), "&e&lGWIAZDKI &8&m=||=&7 Gracz &c" + u.getLastName() + " &7zdobyl &6" + rewardStars + "&e✪ gwiazdke/i &7za &awspaniale statystyki &7w grze!");
                                        Team team = Main.getScoreboard().getTeam(player.getName());
                                        team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
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
                if(p.getLocation().distance(skyCastle.getRedTron()) < 1.5){
                    if(skyCastle.isRedTeam(p)){
                        return;
                    }
                    if(skyCastle.isSpectator(p)){
                        return;
                    }
                    if(skyCastle.getRedTaking() < 40){
                        Util.sendTitle(skyCastle.getRedTeam(), "&4&lALARM");
                        Util.sendSubTitle(skyCastle.getRedTeam(), "&8>> &cWasz tron jest przejmowany!");
                        skyCastle.addRedTaking(1);
                        Util.sendTitle(p, "");
                        Util.sendSubTitle(p, "&8>> &7Przejmowanie tronu &4CZERWONYCH &c" + skyCastle.getRedTaking() + "&7/&c100 &8<<");
                        u.addGameNumbers(1);
                    }else{
                        skyCastle.setBlueTaking(0);
                        for(Player lose : skyCastle.getRedTeam()){
                            lose.getInventory().clear();
                            lose.getInventory().setArmorContents(new ItemStack[0]);
                            Util.sendTitle(lose, "&c&lPRZEGRANA");
                            Util.sendSubTitle(lose, "&8>> &7Wasz tron zostal przejety przez druzyne &9NIEBIESKA&7!");
                        }
                        for(Player win : skyCastle.getBlueTeam()){
                            win.getInventory().clear();
                            win.getInventory().setArmorContents(new ItemStack[0]);
                            Util.sendTitle(win, "&a&lZWYCIESTWO");
                            Util.sendSubTitle(win, "&8>> &7Przejeliscie tron druzyny &4CZERWONEJ&7!");
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
                                    Util.sendMessage(player, "&8&m==============&8 ( &6PODSUMOWANIE &8) &8&m===============" +
                                            "\n&8&m==&7> &7Laczna ilosc zabojstw: &6" + u.getGameKills() +
                                            "\n&8&m==&7> &7Laczna ilosc asyst: &6" + u.getGameAssists() +
                                            "\n&8&m==&7> &7Uzyte granaty: &6" + u.getGameGrenades() +
                                            "\n&8&m==&7> &7Przejete kapliczki: &6" + u.getGameKapliczki() +
                                            "\n&8&m==&7> &7Ilosc sekund przejmowania w sumie: &6" + u.getGameNumbers() +
                                            "\n&8&m==&7> &7W sumie zdobyles &6" + points + " &7punktow w tej grze!" +
                                            "\n&8&m==&7> &7Otrzymujesz dodatkowo &c" + coinsGained + " &6coinsow&7!" +
                                            "\n&8&m==============&8 ( &cdineron.net &8) &8&m===============");
                                    u.addCoins(coinsGained);
                                    if(points >= 87.5){
                                        int rewardStars = 1;
                                        double need = 87.5*2;
                                        while(points > need){
                                            rewardStars++;
                                            need += 87.5;
                                        }
                                        u.addStars(rewardStars);
                                        Util.sendMessage(Bukkit.getOnlinePlayers(), "&e&lGWIAZDKI &8&m=||=&7 Gracz &c" + u.getLastName() + " &7zdobyl &6" + rewardStars + "&e✪ gwiazdke/i &7za &awspaniale statystyki &7w grze!");
                                        Team team = Main.getScoreboard().getTeam(player.getName());
                                        team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
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
                if(p.getLocation().distance(skyCastle.getKapliczka()) < 7){
                    if(skyCastle.getKapliczkaTakenBy().equals(skyCastle.getTeamIn(p))){
                        return;
                    }
                    if(skyCastle.isSpectator(p)){
                        return;
                    }
                    for(Entity entity : skyCastle.getKapliczka().getWorld().getNearbyEntities(skyCastle.getKapliczka(), 7, 7, 7)){
                        if(entity instanceof Player){
                            Player inKap = (Player) entity;
                            if(inKap.getUniqueId().toString().equalsIgnoreCase(p.getUniqueId().toString())){
                                continue;
                            }
                            if(!skyCastle.isTeamMate(inKap, p)){
                                if(skyCastle.getTaking() > 0){
                                    Util.sendTitle(p, "");
                                    Util.sendSubTitle(p, "&cPrzejmowanie przerwane wrog pojawil sie w kapliczce!");
                                    skyCastle.setTaking(0);
                                }
                                return;
                            }
                        }
                    }
                    if(skyCastle.getTaking() < 30){
                        for(Location location : Util.getNumberOfBlocks(p,skyCastle.getKapliczka(), 2)){
                            switch (skyCastle.getTeamIn(p)) {
                                case "RED":
                                    location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                    location.getWorld().getBlockAt(location).setData((byte) 14);
                                    break;
                                case "BLUE":
                                    location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                    location.getWorld().getBlockAt(location).setData((byte) 11);
                                    break;
                            }
                        }
                        u.addGameNumbers(1);
                        skyCastle.addTaking(1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*3, 2));
                        Util.sendTitle(p, "");
                        Util.sendSubTitle(p, "&8>> &7Przejmowanie kapliczki &c" + skyCastle.getTaking() + "&7/&c30 &8<<");
                    }else{
                        String team = (skyCastle.getTeamIn(p).equals("RED") ? "&cCZERWONA" : "&9NIEBIESKA");
                        Util.sendTitle(skyCastle.getPlayers(), "&6&lKAPLICZKA PRZEJETA");
                        Util.sendSubTitle(skyCastle.getPlayers(), "&8>> &7Kapliczka zostala przejeta przez druzyne " + team);
                        skyCastle.setKapliczkaTakenBy(skyCastle.getTeamIn(p));
                        u.addGameKapliczki(1);
                        for(Location location : Util.sphere(skyCastle.getKapliczka(), 8, 7, false, true, 0)){
                            if(location.getWorld().getBlockAt(location).getType() == Material.BEDROCK){
                                switch (skyCastle.getTeamIn(p)){
                                    case "RED":
                                        location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                        location.getWorld().getBlockAt(location).setData((byte)14);
                                        break;
                                    case "BLUE":
                                        location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                        location.getWorld().getBlockAt(location).setData((byte)11);
                                        break;
                                }
                            }else if(location.getWorld().getBlockAt(location).getType() == Material.STAINED_CLAY){
                                switch (skyCastle.getTeamIn(p)){
                                    case "RED":
                                        location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                        location.getWorld().getBlockAt(location).setData((byte)14);
                                        break;
                                    case "BLUE":
                                        location.getWorld().getBlockAt(location).setType(Material.STAINED_CLAY);
                                        location.getWorld().getBlockAt(location).setData((byte)11);
                                        break;
                                }
                            }
                        }
                        skyCastle.setTaking(0);
                    }
                    for(Entity entity : skyCastle.getKapliczka().getWorld().getNearbyEntities(skyCastle.getKapliczka(), 7, 7, 7)){
                        if(entity instanceof Player){
                            return;
                        }
                    }
                    if(skyCastle.getTaking() > 0){
                        skyCastle.setTaking(0);
                    }
                }
            }
        }*/
    }
}
