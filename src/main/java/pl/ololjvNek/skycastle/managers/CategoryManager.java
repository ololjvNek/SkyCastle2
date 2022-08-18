package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.Category;
import pl.ololjvNek.skycastle.data.ItemCategory;

import java.util.HashMap;

public class CategoryManager {

    @Getter private static HashMap<String, Category> categories = new HashMap<>();

    public static Category getCategory(String name){
        return categories.get(name);
    }

    public static void loadCategories(){
        FileConfiguration config = Main.getVillagerShopConfig().build();
        for(String categoryName : config.getConfigurationSection("shop.categories").getKeys(false)){
            Category category = new Category(categoryName);
            categories.put(category.getCategoryName(), category);
        }
        for(String itemCategoryName : config.getConfigurationSection("items").getKeys(false)){
            ItemCategory itemCategory = new ItemCategory(itemCategoryName);
            Category category = getCategory(itemCategory.getCategory());
            if(category != null){
                category.getItemCategoryList().add(itemCategory);
            }
        }
    }
}
