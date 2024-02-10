package pl.ololjvNek.skycastle.utils;

import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.data.Event;
import pl.ololjvNek.skycastle.interfaces.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventList {

    public static HashMap<String, ItemStack> eventList = new HashMap<>();

    static{
        eventList.put("GoldenHead", new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner("8den").setName(Util.fixColors("&8>> &6Golden Head")).setLore(Util.fixColors("&8>> &7You will receive a &6Golden Head &7for killing a player!")).toItemStack());
        eventList.put("Discount", new ItemBuilder(Material.GOLD_INGOT, 1).setName(Util.fixColors("&8>> &6Discount")).setLore(Util.fixColors("&8>> &7All costs are discounted by 50%")).toItemStack());
        eventList.put("Battleship", new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setName(Util.fixColors("&8>> &6Battleship")).setLore(Util.fixColors("&8>> &7Everyone gets diamond armor with 4/3 durability")).toItemStack());
        eventList.put("Luck", new ItemBuilder(Material.EMERALD, 1).setName(Util.fixColors("&8>> &6Luck")).setLore(Util.fixColors("&8>> &7Runes have a 50% increased drop chance")).toItemStack());
        eventList.put("NoFall", new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&8>> &6NoFall")).setLore(Util.fixColors("&8>> &7No one takes fall damage!")).toItemStack());
        eventList.put("Rebirth", new ItemBuilder(Material.FERMENTED_SPIDER_EYE, 1).setName(Util.fixColors("&8>> &6Rebirth")).setLore(Util.fixColors("&8>> &7You respawn after each death for &67 seconds.")).toItemStack());
        eventList.put("Debug", new ItemBuilder(Material.BARRIER, 1).setName(Util.fixColors("&8>> &6Debug")).setLore(Util.fixColors("&8>> &7Developer debugging (Custom event)")).toItemStack());
    }

    public static boolean isEventEnabled(String name, Event event){
        return event.getEvents().contains(name);
    }
}
