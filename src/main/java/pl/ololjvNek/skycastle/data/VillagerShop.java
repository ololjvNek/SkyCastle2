package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.UUID;

@Data
public class VillagerShop {

    private UUID villagerUUID;
    private Entity entity;
    private Location villagerLocation;

    public VillagerShop(Entity entity){
        villagerUUID = entity.getUniqueId();
        this.entity = entity;
        villagerLocation = entity.getLocation();
    }
}
