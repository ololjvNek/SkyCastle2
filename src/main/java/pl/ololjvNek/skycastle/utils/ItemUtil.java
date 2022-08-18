package pl.ololjvNek.skycastle.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static ItemStack menu = new ItemBuilder(Material.COMPASS, 1).setName(Util.fixColors("&4! &9Wybor areny &4!")).toItemStack();

    public static ItemStack leaveGame = new ItemBuilder(Material.FENCE_GATE, 1).setName(Util.fixColors("&cWyjdz z gry")).toItemStack();

    public static ItemStack runa = new ItemBuilder(Material.PRISMARINE_SHARD, 1).setName(Util.fixColors("&b&lRuna")).addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10).toItemStack();

    public static ItemStack granatOslepiajacy = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lGranat Oslepiajacy")).toItemStack();
    public static ItemStack granatOslabiajacy = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lGranat Oslabiajacy")).toItemStack();
    public static ItemStack granatPodbicia = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lGranat Podbijajacy")).toItemStack();
    public static ItemStack granatPajeczy = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lGranat Pajeczy")).toItemStack();
    public static ItemStack granatOdepchniecia = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lGranat Odepchniecia")).toItemStack();
    public static ItemStack wedka = new ItemBuilder(Material.FISHING_ROD, 1).addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10).setInfinityDurability().setName(Util.fixColors("&cMagiczna Wedka")).setLore(Util.fixColors("&7Kliknij Shift+PPM, aby zmienic tryb wedki")).toItemStack();

    public static ItemStack debugStick = new ItemBuilder(Material.STICK, 1).setName(Util.fixColors("&4Debug &cStick")).setLore(Util.fixColors("&c&k&nDebug")).toItemStack();

    //             *    LOBBY    *

    public static ItemStack magicPearl = new ItemBuilder(Material.ENDER_PEARL, 1).setName(Util.fixColors("&5Magiczna Perla")).setLore(Util.fixColors("&7Kliknij &bLPM&7, aby zmienic typ perly")).toItemStack();
    public static ItemStack gadzety = new ItemBuilder(Material.CHEST, 1).setName(Util.fixColors("&eMenu")).toItemStack();


    public static ItemStack getAmountOfItem(ItemStack is, int amount){
        ItemStack cloneis = is.clone();
        cloneis.setAmount(amount);
        return cloneis;
    }
}
