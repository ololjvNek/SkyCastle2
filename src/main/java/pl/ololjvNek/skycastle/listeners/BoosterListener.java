package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.antilogout.CombatAPI;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.DataUtil;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.TimeUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoosterListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() != null){
            if(e.getItem().isSimilar(ItemUtil.granatOslepiajacy)){
                e.getPlayer().getInventory().removeItem(ItemUtil.granatOslepiajacy);
                Snowball snowball = (Snowball) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.SNOWBALL);
                snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                snowball.setShooter(e.getPlayer());
                snowball.setCustomName("OSLEPIENIE");
                UserManager.getUser(e.getPlayer()).addGameGrenades(1);
            }
            if(e.getItem().isSimilar(ItemUtil.granatOslabiajacy)){
                e.getPlayer().getInventory().removeItem(ItemUtil.granatOslabiajacy);
                Snowball snowball = (Snowball) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.SNOWBALL);
                snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                snowball.setShooter(e.getPlayer());
                snowball.setCustomName("OSLABIENIE");
                UserManager.getUser(e.getPlayer()).addGameGrenades(1);
            }
            if(e.getItem().isSimilar(ItemUtil.granatOdepchniecia)){
                e.getPlayer().getInventory().removeItem(ItemUtil.granatOdepchniecia);
                Snowball snowball = (Snowball) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.SNOWBALL);
                snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                snowball.setShooter(e.getPlayer());
                snowball.setCustomName("ODEPCHNIECIE");
                UserManager.getUser(e.getPlayer()).addGameGrenades(1);
            }
            if(e.getItem().isSimilar(ItemUtil.granatPajeczy)){
                e.getPlayer().getInventory().removeItem(ItemUtil.granatPajeczy);
                Snowball snowball = (Snowball) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.SNOWBALL);
                snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                snowball.setShooter(e.getPlayer());
                snowball.setCustomName("PAJECZYNA");
                UserManager.getUser(e.getPlayer()).addGameGrenades(1);
            }
            if(e.getItem().isSimilar(ItemUtil.granatPodbicia)){
                e.getPlayer().getInventory().removeItem(ItemUtil.granatPodbicia);
                Snowball snowball = (Snowball) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.SNOWBALL);
                snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));
                snowball.setShooter(e.getPlayer());
                snowball.setCustomName("PODBICIE");
                UserManager.getUser(e.getPlayer()).addGameGrenades(1);
            }
        }
    }


    @EventHandler
    public void onHit(ProjectileHitEvent e){
        if(e.getEntity() instanceof FishHook){
            Player shooter = (Player) e.getEntity().getShooter();
            User u = UserManager.getUser(shooter);
            FishHook fishHook = (FishHook) e.getEntity();
            if(fishHook.getNearbyEntities(1, 1, 1).isEmpty() || fishHook.getNearbyEntities(1, 1, 1).get(0) == null){
                return;
            }
            Player shooted = (Player) fishHook.getNearbyEntities(1, 1, 1).get(0);

            if(u.getWedkaCooldown() > System.currentTimeMillis()){
                Util.sendMessage(shooter, "&cNext time you can use Magic Fishing Rod is &a" + DataUtil.secondsToString(u.getWedkaCooldown()));
                return;
            }
            if(u.getWedka().equals("PRZYCIAGANIE")){
                shooted.setVelocity(shooter.getLocation().getDirection().multiply(-2.3D).setY(1.1));
            }else if(u.getWedka().equals("DOSKOK")){
                shooter.setVelocity(shooter.getLocation().getDirection().multiply(2.3D).setY(1.1));
            }else if(u.getWedka().equals("ODPYCHANIE")){
                shooted.setVelocity(shooter.getLocation().getDirection().multiply(2.3D).setY(1.1));
            }
            fishHook.remove();
            u.setWedkaCooldown(System.currentTimeMillis()+ TimeUtil.SECOND.getTime(10));
        }
        if(e.getEntity() instanceof Snowball){
            if(e.getEntity().getCustomName() != null){
                if(e.getEntity().getCustomName().equals("OSLEPIENIE")){
                    Player shooter = (Player) e.getEntity().getShooter();
                    User u = UserManager.getUser(shooter);
                    for(Entity entity : shooter.getWorld().getNearbyEntities(e.getEntity().getLocation(), 4, 3, 4)){
                        if(entity instanceof Player){
                            Player shooted = (Player) entity;
                            if(u.getSkyCastle().isTeamMate(shooter, shooted)){
                                continue;
                            }
                            shooted.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 2));
                            Util.sendTitle(shooted, "");
                            Util.sendSubTitle(shooted, "&8>> &7&lYOU'RE BLINDED! &8<<");
                        }
                    }
                }
                if(e.getEntity().getCustomName().equals("OSLABIENIE")){
                    Player shooter = (Player) e.getEntity().getShooter();
                    User u = UserManager.getUser(shooter);
                    for(Entity entity : shooter.getWorld().getNearbyEntities(e.getEntity().getLocation(), 4, 3, 4)){
                        if(entity instanceof Player){
                            Player shooted = (Player) entity;
                            if(u.getSkyCastle().isTeamMate(shooter, shooted)){
                                continue;
                            }
                            shooted.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*15, 2));
                            Util.sendTitle(shooted, "");
                            Util.sendSubTitle(shooted, "&8>> &7&lYOU'RE WEAK! &8<<");
                        }
                    }
                }
                if(e.getEntity().getCustomName().equals("PODBICIE")){
                    Player shooter = (Player) e.getEntity().getShooter();
                    User u = UserManager.getUser(shooter);
                    for(Entity entity : shooter.getWorld().getNearbyEntities(e.getEntity().getLocation(), 4, 3, 4)){
                        if(entity instanceof Player){
                            Player shooted = (Player) entity;
                            if(u.getSkyCastle().isTeamMate(shooter, shooted) && !(shooted.getUniqueId().toString().equals(shooter.getUniqueId().toString()))){
                                continue;
                            }
                            shooted.setVelocity(shooted.getLocation().getDirection().zero().normalize().setY(2.0D).multiply(3));
                            Util.sendTitle(shooted, "");
                            Util.sendSubTitle(shooted, "&8>> &7&lYOU WAS PUSHED UP! &8<<");
                        }
                    }
                }
                if(e.getEntity().getCustomName().equals("ODEPCHNIECIE")){
                    Player shooter = (Player) e.getEntity().getShooter();
                    User u = UserManager.getUser(shooter);
                    for(Entity entity : shooter.getWorld().getNearbyEntities(e.getEntity().getLocation(), 4, 3, 4)){
                        if(entity instanceof Player){
                            Player shooted = (Player) entity;
                            if(u.getSkyCastle().isTeamMate(shooter, shooted)){
                                continue;
                            }
                            shooted.setVelocity(shooter.getLocation().getDirection().multiply(4));
                            Util.sendTitle(shooted, "");
                            Util.sendSubTitle(shooted, "&8>> &7&lYOU WAS PUSHED AWAY! &8<<");
                        }
                    }
                }
                if(e.getEntity().getCustomName().equals("PAJECZYNA")){
                    Player shooter = (Player) e.getEntity().getShooter();
                    User u = UserManager.getUser(shooter);
                    for(Entity entity : shooter.getWorld().getNearbyEntities(e.getEntity().getLocation(), 4, 3, 4)){
                        if(entity instanceof Player){
                            Player shooted = (Player) entity;
                            if(u.getSkyCastle().isTeamMate(shooter, shooted)){
                                continue;
                            }
                            HashMap<Location, Material> last = new HashMap<>();
                            for(Location loc : Util.sphere(shooted.getLocation(), 2, 2, false, true, 0)){
                                if(loc.getWorld().getBlockAt(loc).getType() == Material.AIR){
                                    last.put(loc,loc.getWorld().getBlockAt(loc).getType());
                                    loc.getWorld().getBlockAt(loc).setType(Material.WEB);
                                }
                            }
                            new BukkitRunnable(){
                                public void run(){
                                    for(Location loc : last.keySet()){
                                        loc.getWorld().getBlockAt(loc).setType(last.get(loc));
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(), 100L);
                            Util.sendTitle(shooted, "");
                            Util.sendSubTitle(shooted, "&8>> &7&lYOU GOT COBWEBED! &8<<");
                        }
                    }
                }
            }
        }
    }
}
