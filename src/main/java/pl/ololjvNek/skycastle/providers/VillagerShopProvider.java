package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.Category;
import pl.ololjvNek.skycastle.data.ItemCategory;
import pl.ololjvNek.skycastle.managers.CategoryManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

public class VillagerShopProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("VillagerShop")
            .provider(new VillagerShopProvider())
            .size(3, 9)
            .title(Util.fixColors("&cSKLEP"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        int i = 0;
        int row = 0;
        for(Category category : CategoryManager.getCategories().values()){
            inventoryContents.set(row, i, ClickableItem.of(category.getCategoryItem(), e -> {
                inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                int i2 = 0;
                int row2 = 0;
                for(ItemCategory itemCategory : category.getItemCategoryList()){
                    if((itemCategory.getPermission() == null || itemCategory.getPermission().isEmpty())) {
                        inventoryContents.set(row2, i2, ClickableItem.of(itemCategory.getGuiItem().clone(), e2 -> {
                            if (Util.isPlayerHasItem(player, itemCategory.getCost())) {
                                player.getInventory().removeItem(itemCategory.getCost());
                                Util.categoryOptions(player, itemCategory);
                                Util.sendTitle(player, "&8>> &aPoprawnie zakupiono przedmiot/ulepszenie!");
                            } else {
                                player.closeInventory();
                                Util.sendTitle(player, "&cNie posiadasz wystarczajaco run!");
                            }
                        }));
                    }else if(itemCategory.getPermission() != null && !player.hasPermission(itemCategory.getPermission())){
                        ItemBuilder ib = new ItemBuilder(itemCategory.getGuiItem().clone());
                        ib.addLoreLine("");
                        ib.addLoreLine(Util.fixColors("&8>> &cTen schemat nie zostal przez Ciebie odblokowany!"));
                        ib.addLoreLine(Util.fixColors("&8>> &cOdblokuj go w lobby otwierajac menu i sklep!"));
                        inventoryContents.set(row2, i2, ClickableItem.empty(ib.toItemStack()));
                    }else if(itemCategory.getPermission() != null && player.hasPermission(itemCategory.getPermission())){
                        inventoryContents.set(row2, i2, ClickableItem.of(itemCategory.getGuiItem().clone(), e2 -> {
                            if (Util.isPlayerHasItem(player, itemCategory.getCost())) {
                                player.getInventory().removeItem(itemCategory.getCost());
                                Util.categoryOptions(player, itemCategory);
                                Util.sendTitle(player, "&8>> &aPoprawnie zakupiono przedmiot/ulepszenie!");
                            } else {
                                player.closeInventory();
                                Util.sendTitle(player, "&cNie posiadasz wystarczajaco run!");
                            }
                        }));
                    }
                    i2++;
                    if(i2 >= 9){
                        i2 = 0;
                        row2++;
                    }
                }
                inventoryContents.set(2, 8, ClickableItem.of(new ItemBuilder(Material.FENCE_GATE).setName(Util.fixColors("&8>> &cPowrot")).toItemStack(), ee-> {
                    player.closeInventory();
                    INVENTORY.open(player);
                }));
            }));
            i++;
            if(i >= 8){
                i = 0;
                row++;
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
