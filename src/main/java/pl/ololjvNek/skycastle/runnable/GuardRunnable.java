package pl.ololjvNek.skycastle.runnable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.utils.GuardUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class GuardRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for(World world : Bukkit.getWorlds()){
            world.setThundering(false);
            world.setTime(800);
            world.setStorm(false);
            world.setWeatherDuration(99999);
        }
        for(SkyCastle skyCastle : SkyCastleManager.getSkyCastles().values()){
            if(skyCastle.getStatus() == ArenaStatus.STARTED){
                for(Entity entity : skyCastle.getStraznicyBlue()){
                    if(entity.isDead()){
                        skyCastle.getStraznicyBlue().remove(entity);
                        continue;
                    }
                    String teamEntity = entity.getMetadata("team").get(0).value().toString();
                    entity.setCustomName(Util.fixColors(GuardUtil.getHP(entity)));
                    for(Entity nearby : entity.getNearbyEntities(30, 30, 30)){
                        if(nearby instanceof Player){
                            if(skyCastle.getTeamIn(((Player) nearby)).equals(teamEntity)){
                                continue;
                            }
                            ((Creature)entity).setTarget((LivingEntity)nearby);
                        }
                    }
                    if(entity.getLocation().distance(skyCastle.getBlueSpawn()) > 40){
                        entity.teleport(skyCastle.getBlueSpawn());
                    }
                }
                for(Entity entity : skyCastle.getStraznicyRed()){
                    if(entity.isDead()){
                        skyCastle.getStraznicyRed().remove(entity);
                        continue;
                    }
                    String teamEntity = entity.getMetadata("team").get(0).value().toString();
                    entity.setCustomName(Util.fixColors(GuardUtil.getHP(entity)));
                    for(Entity nearby : entity.getNearbyEntities(30, 30, 30)){
                        if(nearby instanceof Player){
                            if(skyCastle.getTeamIn(((Player) nearby)).equals(teamEntity)){
                                continue;
                            }
                            ((Creature)entity).setTarget((LivingEntity)nearby);
                        }
                    }
                    if(entity.getLocation().distance(skyCastle.getRedSpawn()) > 40){
                        entity.teleport(skyCastle.getRedSpawn());
                    }
                }
            }
        }
    }
}
