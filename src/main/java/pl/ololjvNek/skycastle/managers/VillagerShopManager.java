package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.ololjvNek.skycastle.data.VillagerShop;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.HashMap;
import java.util.UUID;

public class VillagerShopManager {

    @Getter private static HashMap<UUID, VillagerShop> villagerShops = new HashMap<>();

    public static VillagerShop createVillagerShop(Location location){
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        Util.setTeg(villager, "PersistenceRequired", 1);
        Util.setTeg(villager, "NoAI", 1);
        Util.setTeg(villager, "Silent", 1);
        Util.setTeg(villager, "Invulnerable", 1);
        villager.setAdult();
        villager.setAgeLock(true);
        ((LivingEntity)villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 255));
        villager.setCustomName(Util.fixColors("&a&lSHOP"));
        villager.setCustomNameVisible(true);
        villager.setRemoveWhenFarAway(false);
        VillagerShop villagerShop = new VillagerShop(villager);
        villagerShops.put(villagerShop.getVillagerUUID(), villagerShop);
        return villagerShop;
    }

    public static VillagerShop getVillagerShop(Entity entity){
        return villagerShops.get(entity.getUniqueId());
    }
}
