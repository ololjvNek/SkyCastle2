package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

public class WaterBucketEmptyListener implements Listener {

    @EventHandler
    public void onWater(PlayerBucketEmptyEvent e){
        if(e.getBucket() == Material.WATER_BUCKET){
            e.getBlockClicked().getRelative(e.getBlockFace(), 1).setType(Material.STATIONARY_WATER);
            e.getPlayer().getInventory().removeItem(new ItemStack(e.getBucket()));
            e.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET));
        }
    }
}
