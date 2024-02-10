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
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Logger;
import pl.ololjvNek.skycastle.utils.Util;

public class MenuProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new MenuProvider())
            .size(3, 9)
            .title(Util.fixColors("&cSKYCASTLE ARENAS"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        int slot = 0,row = 0;
        for(SkyCastle skyCastle : SkyCastleManager.getSkyCastles().values()){
            ArenaStatus status = skyCastle.getStatus();
            ItemStack is = new ItemBuilder(Material.STAINED_CLAY, 1, (status == ArenaStatus.WAITING ? (byte)5 : (status == ArenaStatus.STARTING ? (byte)4 : (status == ArenaStatus.STARTED ? (byte)14 : (byte)14))))
                    .setName(Util.fixColors("&a" + skyCastle.getName() + " &8|-| &c" + skyCastle.getPlayers().size() + "&7/&a" + skyCastle.getMaxPlayers()))
                    .setLore(Util.fixColors("&8{o} &7Status: " + (status == ArenaStatus.WAITING ? "&aWaiting" : (status == ArenaStatus.STARTING ? "&6Starting" : (status == ArenaStatus.STARTED ? "&4Started" : (status == ArenaStatus.DISABLED ? "&4&lDISABLED" : "&4&lRESTARTING &7: &c" + skyCastle.getRestartPercent() + "%")))))).toItemStack();
            inventoryContents.set(row, slot, ClickableItem.of(is, e->{
                switch (status){
                    case WAITING:
                    case STARTING:
                        SkyCastleManager.joinGame(skyCastle, player);
                        break;
                    case DISABLED:
                        Util.sendTitle(player, "&4&lARENA DISABLED");
                        Util.sendSubTitle(player, "&7Arena is disabled by administrator!");
                        break;
                }
            }));
            slot++;
            if(slot >= 8){
                slot = 0;
                row++;
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        int slot = 0,row = 0;
        for(SkyCastle skyCastle : SkyCastleManager.getSkyCastles().values()){
            ArenaStatus status = skyCastle.getStatus();
            ItemStack is = new ItemBuilder(Material.STAINED_CLAY, 1, (status == ArenaStatus.WAITING ? (byte)5 : (status == ArenaStatus.STARTING ? (byte)4 : (status == ArenaStatus.STARTED ? (byte)14 : (byte)14))))
                    .setName(Util.fixColors("&a" + skyCastle.getName() + " &8|-| &c" + skyCastle.getPlayers().size() + "&7/&a" + skyCastle.getMaxPlayers()))
                    .setLore(Util.fixColors("&8{o} &7Status: " + (status == ArenaStatus.WAITING ? "&aWaiting" : (status == ArenaStatus.STARTING ? "&6Starting" : (status == ArenaStatus.STARTED ? "&4Started" : (status == ArenaStatus.DISABLED ? "&4&lDISABLED" : "&4&lRESTARTING &7: &c" + skyCastle.getRestartPercent() + "%")))))).toItemStack();
            inventoryContents.set(row, slot, ClickableItem.of(is, e->{
                switch (status){
                    case WAITING:
                    case STARTING:
                        SkyCastleManager.joinGame(skyCastle, player);
                        break;
                    case DISABLED:
                        Util.sendTitle(player, "&4&lARENA DISABLED");
                        Util.sendSubTitle(player, "&7Arena got disabled by administrator!");
                        break;
                }
            }));
            slot++;
            if(slot >= 8){
                slot = 0;
                row++;
            }
        }
    }
}
