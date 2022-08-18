package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.PermissionShop;

import java.util.HashMap;

public class PermissionShopManager {

    @Getter private static HashMap<String, PermissionShop> permissionShops = new HashMap<>();


    public static void loadShops(){
        if(Main.getShopPermissionConfig().getConfig().getConfigurationSection("shops") == null){
            return;
        }
        for(String s : Main.getShopPermissionConfig().getConfig().getConfigurationSection("shops").getKeys(false)){
            PermissionShop permissionShop = new PermissionShop(s);
            permissionShops.put(permissionShop.getName(), permissionShop);
        }
    }
}
