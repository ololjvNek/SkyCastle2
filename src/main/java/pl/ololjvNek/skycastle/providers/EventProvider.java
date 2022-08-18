package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.EventList;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

public class EventProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new EventProvider())
            .size(5, 9)
            .title(Util.fixColors("&cEvent menu"))
            .build();


    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        User u = UserManager.getUser(player);
        SkyCastle skyCastle = u.getSkyCastle();
        ItemBuilder info = new ItemBuilder(Material.GRASS, 1).setName(Util.fixColors("&8>> &7Arena: &a" + skyCastle.getName()));
        ItemBuilder eventy = new ItemBuilder(Material.REDSTONE_BLOCK, 1).setName(Util.fixColors("&8>> &7Set the events"));
        ItemBuilder eventon = new ItemBuilder(Material.STAINED_CLAY, 1, (byte)5).setName(Util.fixColors("&8>> &7Turn event on/off"));
        inventoryContents.set(2, 2, ClickableItem.of(eventy.toItemStack(), e -> {
            inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
            int slot = 0;
            int row = 0;
            for(String eventName : EventList.eventList.keySet()){
                ItemStack is = EventList.eventList.get(eventName).clone();
                ItemBuilder itemBuilder = new ItemBuilder(is).addLoreLine("").addLoreLine(Util.fixColors("&8>> &7Actually: " + (skyCastle.getEvent().hasEvent(eventName) ? "&aENABLED" : "&cDISABLED")));
                inventoryContents.set(row, slot, ClickableItem.of(itemBuilder.toItemStack(), ee -> {
                    if(skyCastle.getEvent().hasEvent(eventName)){
                        skyCastle.getEvent().getEvents().remove(eventName);
                    }else{
                        skyCastle.getEvent().getEvents().add(eventName);
                    }
                }));
                slot++;
                if(slot >= 8){
                    row++;
                    slot = 0;
                }
            }
        }));
        inventoryContents.set(2, 4, ClickableItem.empty(info.toItemStack()));
        inventoryContents.set(2, 6, ClickableItem.of(eventon.toItemStack(), e -> {
            skyCastle.getEvent().setEnabled(!skyCastle.getEvent().isEnabled());
            player.closeInventory();
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        User u = UserManager.getUser(player);
        SkyCastle skyCastle = u.getSkyCastle();
        ItemBuilder info = new ItemBuilder(Material.GRASS, 1).setName(Util.fixColors("&8>> &7Arena: &a" + skyCastle.getName()));
        ItemBuilder eventy = new ItemBuilder(Material.REDSTONE_BLOCK, 1).setName(Util.fixColors("&8>> &7Set the events"));
        ItemBuilder eventon = new ItemBuilder(Material.STAINED_CLAY, 1, (byte)5).setName(Util.fixColors("&8>> &7Start the event"));
        inventoryContents.set(2, 2, ClickableItem.of(eventy.toItemStack(), e -> {
            inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
            int slot = 0;
            int row = 0;
            for(String eventName : EventList.eventList.keySet()){
                ItemStack is = EventList.eventList.get(eventName).clone();
                ItemBuilder itemBuilder = new ItemBuilder(is).addLoreLine("").addLoreLine(Util.fixColors("&8>> &7Actually: " + (skyCastle.getEvent().hasEvent(eventName) ? "&aENABLED" : "&cDISABLED")));
                inventoryContents.set(row, slot, ClickableItem.of(itemBuilder.toItemStack(), ee -> {
                    if(skyCastle.getEvent().hasEvent(eventName)){
                        skyCastle.getEvent().getEvents().remove(eventName);
                    }else{
                        skyCastle.getEvent().getEvents().add(eventName);
                    }
                }));
                slot++;
                if(slot >= 8){
                    row++;
                    slot = 0;
                }
            }
        }));
        inventoryContents.set(2, 4, ClickableItem.empty(info.toItemStack()));
        inventoryContents.set(2, 6, ClickableItem.of(eventon.toItemStack(), e -> {
            player.closeInventory();
            skyCastle.getEvent().setEnabled(true);
            skyCastle.setStatus(ArenaStatus.WAITING);
            u.setSkyCastle(null);
        }));
    }
}
