package pl.ololjvNek.skycastle.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuardUtil {

    public static HashMap<UUID, Long> cooldownGuard = new HashMap<>();

    public static boolean isOnCooldown(UUID uuid){
        Long time = cooldownGuard.get(uuid);
        if(time == null){
            return false;
        }else{
            return time > System.currentTimeMillis();
        }
    }

    public static String getHP(Entity entity){
        List<String> hpStr = new ArrayList<>();
        double health = ((LivingEntity)entity).getHealth();
        double maxHealth = ((LivingEntity) entity).getMaxHealth();
        double percentOfHealth = Util.round((health/maxHealth)*100, 2);
        int i = 0;
        while(i < percentOfHealth){
            hpStr.add("&a█");
            i += 10;
        }
        while(i > percentOfHealth && i < 100){
            hpStr.add("&c█");
            i += 10;
        }
        return hpStr.toString().replace("[", "").replace("]", "").replace(", ", "");
    }

    public static void putOnCooldown(UUID uuid, int seconds){
        if(cooldownGuard.get(uuid) == null){
            cooldownGuard.put(uuid, System.currentTimeMillis()+ TimeUtil.SECOND.getTime(seconds));
        }else{
            cooldownGuard.replace(uuid, System.currentTimeMillis()+ TimeUtil.SECOND.getTime(seconds));
        }
    }
}
