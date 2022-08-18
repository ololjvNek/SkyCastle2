package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {

    private String categoryName;
    private ItemStack categoryItem;
    private List<ItemCategory> itemCategoryList;

    public Category(String name){
        FileConfiguration config = Main.getVillagerShopConfig().build();
        categoryName = name;
        categoryItem = new ItemBuilder(Material.getMaterial(config.getString("shop.categories." + name + ".item")), config.getInt("shop.categories." + name + ".amount"), (byte)config.getInt("shop.categories." + name + ".data")).setName(Util.fixColors(config.getString("shop.categories." + name + ".name"))).toItemStack();
        itemCategoryList = new ArrayList<>();
    }
}
