package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

@Data
public class ItemCategory {

    private String itemNameCategory, category, options;
    private ItemStack guiItem, cost;
    private String permission;

    public ItemCategory(String name){
        FileConfiguration config = Main.getVillagerShopConfig().build();
        itemNameCategory = name;
        permission = config.getString("items." + name + ".permission");
        category = config.getString("items." + name + ".category");
        options = config.getString("items." + name + ".options");
        guiItem = new ItemBuilder(Material.getMaterial(config.getString("items." + name + ".item")), 1).setName(Util.fixColors(config.getString("items." + name + ".name"))).setLore(Util.fixColors(config.getStringList("items." + name + ".lore"))).toItemStack();
        cost = Util.getRunesCost(config.getString("items." + name + ".cost"));
    }
}
