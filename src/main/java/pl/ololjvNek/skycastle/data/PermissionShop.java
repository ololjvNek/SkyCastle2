package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import pl.ololjvNek.skycastle.Main;

import java.util.List;

@Data
public class PermissionShop {

    private FileConfiguration shopConfig = Main.getShopPermissionConfig().getConfig();

    private String name,permission,addon;
    private int cost;

    private Material guiItem;
    private int guiAmount;
    private byte guiData;
    private String guiName;
    private List<String> guiLore;


    public PermissionShop(String name){
        this.name = name;
        this.permission = shopConfig.getString("shops." + name + ".permission");
        this.addon = shopConfig.getString("shops." + name + ".addon");
        this.cost = shopConfig.getInt("shops." + name + ".cost");

        this.guiItem = Material.getMaterial(shopConfig.getString("shops." + name + ".guiItem"));
        this.guiAmount = shopConfig.getInt("shops." + name + ".guiAmount");
        this.guiData = (byte)shopConfig.getInt("shops." + name + ".guiData");
        this.guiName = shopConfig.getString("shops." + name + ".guiName");
        this.guiLore = shopConfig.getStringList("shops." + name + ".guiLore");
    }
}
