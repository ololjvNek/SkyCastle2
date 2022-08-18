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
        eventList.put("GoldenHead", new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner("8den").setName(Util.fixColors("&8>> &6Golden Head")).setLore(Util.fixColors("&8>> &7Za zabicie gracza dostaniesz &6Golden Head'a &7do ekwipunku!")).toItemStack());
        eventList.put("Przecena", new ItemBuilder(Material.GOLD_INGOT, 1).setName(Util.fixColors("&8>> &6Przecena")).setLore(Util.fixColors("&8>> &7Wszystkie koszty sa przecenione o 50%")).toItemStack());
        eventList.put("Pancernik", new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setName(Util.fixColors("&8>> &6Pancernik")).setLore(Util.fixColors("&8>> &7Kazdy otrzymuje diamentowa zbroje 4/3")).toItemStack());
        eventList.put("Szczescie", new ItemBuilder(Material.EMERALD, 1).setName(Util.fixColors("&8>> &6Szczescie")).setLore(Util.fixColors("&8>> &7Runy maja zwiekszona szanse na drop o 50%")).toItemStack());
        eventList.put("NoFall", new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&8>> &6NoFall")).setLore(Util.fixColors("&8>> &7Nikt nie otrzymuje obrazen od upadku!")).toItemStack());
        eventList.put("Odradzanie", new ItemBuilder(Material.FERMENTED_SPIDER_EYE, 1).setName(Util.fixColors("&8>> &6Odradzanie")).setLore(Util.fixColors("&8>> &7Po kazdej smierci odradzasz sie do &67 sek.")).toItemStack());
        eventList.put("Debug", new ItemBuilder(Material.BARRIER, 1).setName(Util.fixColors("&8>> &6Debug")).setLore(Util.fixColors("&8>> &7Debugowanie developerskie (Niestandardowy event)")).toItemStack());
    }

    public static boolean isEventEnabled(String name, Event event){
        return event.getEvents().contains(name);
    }
}
