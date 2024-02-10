package pl.ololjvNek.skycastle.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static ItemStack menu = new ItemBuilder(Material.COMPASS, 1).setName(Util.fixColors("&4! &9Arena Selection &4!")).toItemStack();

    public static ItemStack leaveGame = new ItemBuilder(Material.FENCE_GATE, 1).setName(Util.fixColors("&cLeave Game")).toItemStack();

    public static ItemStack runa = new ItemBuilder(Material.PRISMARINE_SHARD, 1).setName(Util.fixColors("&b&lRune")).addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10).toItemStack();

    public static ItemStack blindnessGrenade = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lBlindness Grenade")).toItemStack();
    public static ItemStack weakeningGrenade = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lWeakening Grenade")).toItemStack();
    public static ItemStack bounceGrenade = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lBounce Grenade")).toItemStack();
    public static ItemStack webGrenade = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lWeb Grenade")).toItemStack();
    public static ItemStack repulsionGrenade = new ItemBuilder(Material.FIREWORK_CHARGE, 1).setName(Util.fixColors("&7&lRepulsion Grenade")).toItemStack();
    public static ItemStack magicFishingRod = new ItemBuilder(Material.FISHING_ROD, 1).addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10).setInfinityDurability().setName(Util.fixColors("&cMagic Fishing Rod")).setLore(Util.fixColors("&7Shift + Right Click to change the rod mode")).toItemStack();

    public static ItemStack debugStick = new ItemBuilder(Material.STICK, 1).setName(Util.fixColors("&4Debug &cStick")).setLore(Util.fixColors("&c&k&nDebug")).toItemStack();

    //             *    LOBBY    *

    public static ItemStack magicPearl = new ItemBuilder(Material.ENDER_PEARL, 1).setName(Util.fixColors("&5Magic Pearl")).setLore(Util.fixColors("&7Click &bLPM&7 to change the type of pearl")).toItemStack();
    public static ItemStack gadgets = new ItemBuilder(Material.CHEST, 1).setName(Util.fixColors("&eMenu")).toItemStack();


    public static ItemStack getAmountOfItem(ItemStack is, int amount){
        ItemStack cloneis = is.clone();
        cloneis.setAmount(amount);
        return cloneis;
    }
}

