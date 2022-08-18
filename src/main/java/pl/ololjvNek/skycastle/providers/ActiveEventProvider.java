package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.EventList;
import pl.ololjvNek.skycastle.utils.Util;

public class ActiveEventProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("events")
            .provider(new ActiveEventProvider())
            .size(3, 9)
            .title(Util.fixColors("&a&lActive events"))
            .build();


    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        User u = UserManager.getUser(player);
        if(u.getSkyCastle() != null){
            int i = 0;
            int row = 0;
            for(String event : u.getSkyCastle().getEvent().getEvents()){
                inventoryContents.set(row, i, ClickableItem.empty(EventList.eventList.get(event)));
                i++;
                if(i >= 8){
                    i = 0;
                    row++;
                }
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
