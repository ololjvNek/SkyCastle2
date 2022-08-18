package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.events.SkyCastleInKapliczkaEvent;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.Util;

public class SkyCastleInKapliczkaListener implements Listener {

    @EventHandler
    public void onStepIn(SkyCastleInKapliczkaEvent e){
        SkyCastle skyCastle = e.getSkyCastle();
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(skyCastle.getKapliczkaTakenBy().equals(skyCastle.getTeamIn(p))){
            return;
        }
        if(skyCastle.isSpectator(p)){
            return;
        }
        for(Player restIn : e.getRestIn()){
            if(!skyCastle.isTeamMate(restIn, p)){
                if(skyCastle.getTaking() > 0){
                    Util.sendTitle(p, "");
                    Util.sendSubTitle(p, "&cCapturing canceled an enemy got in the chapel!");
                    skyCastle.setTaking(0);
                }
                return;
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
            Util.sendSubTitle(p, "&8>> &7Capturing chapel &c" + skyCastle.getTaking() + "&7/&c30 &8<<");
        }else{
            String team = (skyCastle.getTeamIn(p).equals("RED") ? "&cRED" : "&9BLUE");
            Util.sendTitle(skyCastle.getPlayers(), "&6&lCHAPEL CAPTURED");
            Util.sendSubTitle(skyCastle.getPlayers(), "&8>> &7Chapel got captured by team " + team);
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
