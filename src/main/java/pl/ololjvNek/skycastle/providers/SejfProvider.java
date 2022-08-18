package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class SejfProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new SejfProvider())
            .size(5, 9)
            .title(Util.fixColors("&cTEAM SAFE"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        setBackground(inventoryContents);
        User u = UserManager.getUser(player);
        if(u.getSkyCastle() != null){
            SkyCastle skyCastle = u.getSkyCastle();
            if(skyCastle.getTeamIn(player).equals("RED")){
                ItemStack sejf = new ItemBuilder(Material.GOLD_BLOCK, 1).setName(Util.fixColors("&8{o} &6Paid in runes &8{o}")).setLore("", Util.fixColors("  &8>> &c" + skyCastle.getRedRunes())).toItemStack();
                ItemStack wplacrune = new ItemBuilder(Material.PRISMARINE_SHARD, 1).setName(Util.fixColors("&8{o} &bPay in rune/s &8{o}")).setLore(Util.fixColors("&7Click &bLPM, &7to pay in &61 rune"), Util.fixColors("&7Click &bPPM, &7to pay all runes from your equipment")).toItemStack();
                ItemStack listawplaconych = new ItemBuilder(Material.CHEST, 1).setName(Util.fixColors("&8{o} &eLista graczy ktorzy wplacili runy &8{o}")).toItemStack();
                ItemStack sklep = new ItemBuilder(Material.NETHER_STAR, 1).setName(Util.fixColors("&8{o} &aSklep za runy &8{o}")).toItemStack();
                inventoryContents.set(2, 2, ClickableItem.empty(sejf));
                inventoryContents.set(1, 4, ClickableItem.of(sklep, e -> {
                    inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                    ItemStack straznikI = new ItemBuilder(Material.MONSTER_EGG, 1, (byte)51).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a1")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &6300 Run")).toItemStack();
                    ItemStack straznikII = new ItemBuilder(Material.IRON_BLOCK, 1).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a2")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &6800 Run")).toItemStack();
                    ItemStack straznikIII = new ItemBuilder(Material.MONSTER_EGG, 1, (byte)56).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a3")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &61500 Run")).toItemStack();

                    ItemStack druzynaSpeedI = new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&cEfekt dla druzyny &bSpeed &2I")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bSpeed &2I &7na &e2 minuty"), Util.fixColors("&7Koszt: &b400 &6run")).toItemStack();
                    ItemStack druzynaSpeedII = new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&cEfekt dla druzyny &bSpeed &2II")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bSpeed &2II &7na &e1 minute i 30 sekund"), Util.fixColors("&7Koszt: &b650 &6run")).toItemStack();
                    ItemStack druzynaSharpness = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName(Util.fixColors("&cZaklecie &bSharpness &2I &cdla druzyny")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma zaklecie:"), Util.fixColors("&bSharpness &2I &7na swych mieczach"), Util.fixColors("&7Koszt: &b850 &6run")).toItemStack();
                    ItemStack druzynaProtection = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setName(Util.fixColors("&cZaklecie &bProtection &2II &cdla druzyny")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma zaklecie:"), Util.fixColors("&bProtection &2II &7na swych zbrojach"), Util.fixColors("&7Koszt: &b1100 &6run")).toItemStack();
                    ItemStack druzynaStrength = new ItemBuilder(Material.REDSTONE, 1).setName(Util.fixColors("&cEfekt dla druzyny &bStrength &2I")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bStrength I &7na &e1 minute i 15 sekund"), Util.fixColors("&7Koszt: &b1400 &6run")).toItemStack();
                    setBackground(inventoryContents);
                    inventoryContents.set(2, 2, ClickableItem.of(druzynaSpeedI, ee -> {
                        if(skyCastle.getRedRunes() < 400){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeRedRune(400);
                        for(Player red : skyCastle.getRedTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*120, 0));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7Cala druzyna otrzymala &bSPEED &2I &7na &a2 minuty");
                        }
                    }));
                    inventoryContents.set(2, 3, ClickableItem.of(druzynaSpeedII, ee -> {
                        if(skyCastle.getRedRunes() < 650){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeRedRune(650);
                        for(Player red : skyCastle.getRedTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*90, 1));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bSPEED &2II &afor 1 minute and 30 seconds");
                        }
                    }));
                    inventoryContents.set(2, 4, ClickableItem.of(druzynaSharpness, ee -> {
                        if(skyCastle.getRedRunes() < 850){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeRedRune(850);
                        for(Player red : skyCastle.getRedTeam()){
                            for(ItemStack is : red.getInventory().getContents()){
                                if(is != null){
                                    if(is.getType() == Material.STONE_SWORD){
                                        ItemMeta im = is.getItemMeta();
                                        im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                                        is.setItemMeta(im);
                                    }
                                }
                            }
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bSharpness &2I &7on swords");
                        }
                    }));
                    inventoryContents.set(2, 5, ClickableItem.of(druzynaProtection, ee -> {
                        if(skyCastle.getRedRunes() < 1100){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeRedRune(1100);
                        for(Player red : skyCastle.getRedTeam()){
                            for(ItemStack is : red.getInventory().getArmorContents()){
                                if(is != null){
                                    ItemMeta im = is.getItemMeta();
                                    im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                                    is.setItemMeta(im);
                                }
                            }
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bProtection &2II &7on armour");
                        }
                    }));
                    inventoryContents.set(2, 6, ClickableItem.of(druzynaStrength, ee -> {
                        if(skyCastle.getRedRunes() < 1400){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeRedRune(1400);
                        for(Player red : skyCastle.getRedTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*75, 0));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&7All your teammates got &bSTRENGTH &2I &7for &a1 minute and 15 seconds");
                        }
                    }));
                    inventoryContents.set(1, 3, ClickableItem.of(straznikI, ee -> {
                        if(skyCastle.getStraznicyRed().size() < 5){
                            if(skyCastle.getRedRunes() < 300){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeRedRune(300);
                            Skeleton ghast = (Skeleton) skyCastle.getRedSpawn().getWorld().spawnEntity(skyCastle.getRedSpawn(), EntityType.SKELETON);
                            ghast.setCustomName("RED");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "RED"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(70);
                            ghast.setHealth(70);
                            skyCastle.getStraznicyRed().add(ghast);
                            Util.sendTitle(skyCastle.getRedTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getRedTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a1");
                        }
                    }));
                    inventoryContents.set(1, 4, ClickableItem.of(straznikII, ee -> {
                        if(skyCastle.getStraznicyRed().size() < 5){
                            if(skyCastle.getRedRunes() < 800){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeRedRune(800);
                            IronGolem ghast = (IronGolem) skyCastle.getRedSpawn().getWorld().spawnEntity(skyCastle.getRedSpawn(), EntityType.IRON_GOLEM);
                            ghast.setCustomName("RED");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "RED"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(100);
                            ghast.setHealth(100);
                            skyCastle.getStraznicyRed().add(ghast);
                            Util.sendTitle(skyCastle.getRedTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getRedTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a2");
                        }
                    }));
                    inventoryContents.set(1, 5, ClickableItem.of(straznikIII, ee -> {
                        if(skyCastle.getStraznicyRed().size() < 5){
                            if(skyCastle.getRedRunes() < 1500){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeRedRune(1500);
                            Ghast ghast = (Ghast) skyCastle.getRedSpawn().getWorld().spawnEntity(skyCastle.getRedSpawn().clone().add(0, 18, 0), EntityType.GHAST);
                            ghast.setCustomName("RED");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "RED"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(150);
                            ghast.setHealth(150);
                            skyCastle.getStraznicyRed().add(ghast);
                            Util.sendTitle(skyCastle.getRedTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getRedTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a3");
                        }
                    }));
                }));
                inventoryContents.set(3, 4, ClickableItem.of(wplacrune, e -> {
                    if(e.isRightClick()){
                        if(Util.isPlayerHasItem(player, ItemUtil.runa)){
                            int amountOfRunes = Util.getRunesFromEq(player);
                            skyCastle.addRedRune(amountOfRunes);
                            u.addGivedRunes(amountOfRunes);
                        }
                    }else if(e.isLeftClick()){
                        if(Util.isPlayerHasItem(player, ItemUtil.runa)){
                            player.getInventory().removeItem(ItemUtil.runa);
                            skyCastle.addRedRune(1);
                            u.addGivedRunes(1);
                        }
                    }
                }));
                inventoryContents.set(2, 6, ClickableItem.of(listawplaconych, e -> {
                    if(skyCastle.getTeamIn(player).equals("RED")){
                        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                        int row = 0,slot = 0;
                        for(Player p : skyCastle.getRedTeam()){
                            User redUser = UserManager.getUser(p);
                            ItemStack is = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(p.getName()).setName(Util.fixColors("&6" + p.getName())).setLore(Util.fixColors("&8{o} &7Aktualnie wplacil: &c" + redUser.getGivedRunes())).toItemStack();
                            inventoryContents.set(row, slot, ClickableItem.empty(is));
                            slot++;
                            if(slot >= 8){
                                row++;
                                slot = 0;
                            }
                        }
                    }else if(skyCastle.getTeamIn(player).equals("BLUE")){
                        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                        int row = 0,slot = 0;
                        for(Player p : skyCastle.getBlueTeam()){
                            User redUser = UserManager.getUser(p);
                            ItemStack is = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(p.getName()).setName(Util.fixColors("&6" + p.getName())).setLore(Util.fixColors("&8{o} &7Aktualnie wplacil: &c" + redUser.getGivedRunes())).toItemStack();
                            inventoryContents.set(row, slot, ClickableItem.empty(is));
                            slot++;
                            if(slot >= 8){
                                row++;
                                slot = 0;
                            }
                        }
                    }
                }));
            }else if(skyCastle.getTeamIn(player).equals("BLUE")){
                ItemStack sejf = new ItemBuilder(Material.GOLD_BLOCK, 1).setName(Util.fixColors("&8{o} &6Paid in runes &8{o}")).setLore("", Util.fixColors("  &8>> &c" + skyCastle.getBlueRunes())).toItemStack();
                ItemStack wplacrune = new ItemBuilder(Material.PRISMARINE_SHARD, 1).setName(Util.fixColors("&8{o} &bPay in runes/y &8{o}")).setLore(Util.fixColors("&7Click &bLPM, &7to pay in &61 rune"), Util.fixColors("&7Click &bPPM, &7to pay all runes from your equipment")).toItemStack();
                ItemStack listawplaconych = new ItemBuilder(Material.CHEST, 1).setName(Util.fixColors("&8{o} &eLista graczy ktorzy wplacili runy &8{o}")).toItemStack();
                ItemStack sklep = new ItemBuilder(Material.NETHER_STAR, 1).setName(Util.fixColors("&8{o} &aSklep za runy &8{o}")).toItemStack();
                inventoryContents.set(2, 2, ClickableItem.empty(sejf));
                inventoryContents.set(1, 4, ClickableItem.of(sklep, e -> {
                    inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                    ItemStack straznikI = new ItemBuilder(Material.MONSTER_EGG, 1, (byte)51).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a1")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &6300 Run")).toItemStack();
                    ItemStack straznikII = new ItemBuilder(Material.IRON_BLOCK, 1).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a2")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &6800 Run")).toItemStack();
                    ItemStack straznikIII = new ItemBuilder(Material.MONSTER_EGG, 1, (byte)56).setName(Util.fixColors("&cStraznik Wyspy &2LvL. &a3")).setLore(Util.fixColors("&7Kliknij, aby przywolac straznika wyspy!"), Util.fixColors("&7Bedzie on strzegl waszej wyspy do konca gry"), Util.fixColors("&7Lub dopoki nie zginie!"), Util.fixColors("&7Maksymalna ilosc straznikow: &b5"), Util.fixColors("&7Koszt straznika: &61500 Run")).toItemStack();

                    ItemStack druzynaSpeedI = new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&cEfekt dla druzyny &bSpeed &2I")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bSpeed &2I &7na &e2 minuty"), Util.fixColors("&7Koszt: &b400 &6run")).toItemStack();
                    ItemStack druzynaSpeedII = new ItemBuilder(Material.FEATHER, 1).setName(Util.fixColors("&cEfekt dla druzyny &bSpeed &2II")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bSpeed &2II &7na &e1 minute i 30 sekund"), Util.fixColors("&7Koszt: &b650 &6run")).toItemStack();
                    ItemStack druzynaSharpness = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName(Util.fixColors("&cZaklecie &bSharpness &2I &cdla druzyny")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma zaklecie:"), Util.fixColors("&bSharpness &2I &7na swych mieczach"), Util.fixColors("&7Koszt: &b850 &6run")).toItemStack();
                    ItemStack druzynaProtection = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setName(Util.fixColors("&cZaklecie &bProtection &2II &cdla druzyny")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma zaklecie:"), Util.fixColors("&bProtection &2II &7na swych zbrojach"), Util.fixColors("&7Koszt: &b1100 &6run")).toItemStack();
                    ItemStack druzynaStrength = new ItemBuilder(Material.REDSTONE, 1).setName(Util.fixColors("&cEfekt dla druzyny &bStrength &2I")).setLore(Util.fixColors("&7Kazdy z twojej druzyny otrzyma efekt:"), Util.fixColors("&bStrength I &7na &e1 minute i 15 sekund"), Util.fixColors("&7Koszt: &b1400 &6run")).toItemStack();
                    setBackground(inventoryContents);
                    inventoryContents.set(2, 2, ClickableItem.of(druzynaSpeedI, ee -> {
                        if(skyCastle.getBlueRunes() < 400){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeBlueRune(400);
                        for(Player red : skyCastle.getBlueTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*120, 0));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7Cala druzyna otrzymala &bSPEED &2I &7na &a2 minuty");
                        }
                    }));
                    inventoryContents.set(2, 3, ClickableItem.of(druzynaSpeedII, ee -> {
                        if(skyCastle.getBlueRunes() < 650){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeBlueRune(650);
                        for(Player red : skyCastle.getBlueTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*90, 1));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bSPEED &2II &afor 1 minute and 30 seconds");
                        }
                    }));
                    inventoryContents.set(2, 4, ClickableItem.of(druzynaSharpness, ee -> {
                        if(skyCastle.getBlueRunes() < 850){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeBlueRune(850);
                        for(Player red : skyCastle.getBlueTeam()){
                            for(ItemStack is : red.getInventory().getContents()){
                                if(is != null){
                                    if(is.getType() == Material.STONE_SWORD){
                                        ItemMeta im = is.getItemMeta();
                                        im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                                        is.setItemMeta(im);
                                    }
                                }
                            }
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bSharpness &2I &7on swords");
                        }
                    }));
                    inventoryContents.set(2, 5, ClickableItem.of(druzynaProtection, ee -> {
                        if(skyCastle.getBlueRunes() < 1100){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeBlueRune(1100);
                        for(Player red : skyCastle.getBlueTeam()){
                            for(ItemStack is : red.getInventory().getArmorContents()){
                                if(is != null){
                                    ItemMeta im = is.getItemMeta();
                                    im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                                    is.setItemMeta(im);
                                }
                            }
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&8>> &7All your teammates got &bProtection &2II &7on armour");
                        }
                    }));
                    inventoryContents.set(2, 6, ClickableItem.of(druzynaStrength, ee -> {
                        if(skyCastle.getBlueRunes() < 1400){
                            Util.sendTitle(player, "&4ERROR");
                            Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                            return;
                        }
                        skyCastle.removeBlueRune(1400);
                        for(Player red : skyCastle.getBlueTeam()){
                            red.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*75, 0));
                            Util.sendTitle(red, "");
                            Util.sendSubTitle(red, "&7All your teammates got &bSTRENGTH &2I &7for &a1 minute and 15 seconds");
                        }
                    }));
                    inventoryContents.set(1, 3, ClickableItem.of(straznikI, ee -> {
                        if(skyCastle.getStraznicyBlue().size() < 5){
                            if(skyCastle.getBlueRunes() < 300){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeBlueRune(300);
                            Skeleton ghast = (Skeleton) skyCastle.getBlueSpawn().getWorld().spawnEntity(skyCastle.getBlueSpawn(), EntityType.SKELETON);
                            ghast.setCustomName("BLUE");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "BLUE"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(70);
                            ghast.setHealth(70);
                            skyCastle.getStraznicyBlue().add(ghast);
                            Util.sendTitle(skyCastle.getBlueTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getBlueTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a1");
                        }
                    }));
                    inventoryContents.set(1, 4, ClickableItem.of(straznikII, ee -> {
                        if(skyCastle.getStraznicyBlue().size() < 5){
                            if(skyCastle.getBlueRunes() < 800){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeBlueRune(800);
                            IronGolem ghast = (IronGolem) skyCastle.getBlueSpawn().getWorld().spawnEntity(skyCastle.getBlueSpawn(), EntityType.IRON_GOLEM);
                            ghast.setCustomName("BLUE");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "BLUE"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(100);
                            ghast.setHealth(100);
                            skyCastle.getStraznicyBlue().add(ghast);
                            Util.sendTitle(skyCastle.getBlueTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getBlueTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a2");
                        }
                    }));
                    inventoryContents.set(1, 5, ClickableItem.of(straznikIII, ee -> {
                        if(skyCastle.getStraznicyBlue().size() < 5){
                            if(skyCastle.getBlueRunes() < 1500){
                                Util.sendTitle(player, "&4ERROR");
                                Util.sendSubTitle(player, "&cYour team don't have enough runes!");
                                return;
                            }
                            skyCastle.removeBlueRune(1500);
                            Ghast ghast = (Ghast) skyCastle.getBlueSpawn().getWorld().spawnEntity(skyCastle.getBlueSpawn().clone().add(0, 18, 0), EntityType.GHAST);
                            ghast.setCustomName("BLUE");
                            ghast.setMetadata("team", new FixedMetadataValue(Main.getPlugin(), "BLUE"));
                            ghast.setCustomNameVisible(false);
                            ghast.setMaxHealth(150);
                            ghast.setHealth(150);
                            skyCastle.getStraznicyBlue().add(ghast);
                            Util.sendTitle(skyCastle.getBlueTeam(), "&6&lISLAND GUARDS");
                            Util.sendSubTitle(skyCastle.getBlueTeam(), "&8>> &7Player &b" + player.getName() + " &7bought island guard &2LvL. &a3");
                        }
                    }));
                }));
                inventoryContents.set(3, 4, ClickableItem.of(wplacrune, e -> {
                    if(e.isRightClick()){
                        if(Util.isPlayerHasItem(player, ItemUtil.runa)){
                            int amountOfRunes = Util.getRunesFromEq(player);
                            skyCastle.addBlueRune(amountOfRunes);
                            u.addGivedRunes(amountOfRunes);
                        }
                    }else if(e.isLeftClick()){
                        if(Util.isPlayerHasItem(player, ItemUtil.runa)){
                            player.getInventory().removeItem(ItemUtil.runa);
                            skyCastle.addBlueRune(1);
                            u.addGivedRunes(1);
                        }
                    }
                }));
                inventoryContents.set(2, 6, ClickableItem.of(listawplaconych, e -> {
                    if(skyCastle.getTeamIn(player).equals("RED")){
                        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                        int row = 0,slot = 0;
                        for(Player p : skyCastle.getRedTeam()){
                            User redUser = UserManager.getUser(p);
                            ItemStack is = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(p.getName()).setName(Util.fixColors("&6" + p.getName())).setLore(Util.fixColors("&8{o} &7Aktualnie wplacil: &c" + redUser.getGivedRunes())).toItemStack();
                            inventoryContents.set(row, slot, ClickableItem.empty(is));
                            slot++;
                            if(slot >= 8){
                                row++;
                                slot = 0;
                            }
                        }
                    }else if(skyCastle.getTeamIn(player).equals("BLUE")){
                        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.AIR)));
                        int row = 0,slot = 0;
                        for(Player p : skyCastle.getBlueTeam()){
                            User redUser = UserManager.getUser(p);
                            ItemStack is = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(p.getName()).setName(Util.fixColors("&6" + p.getName())).setLore(Util.fixColors("&8{o} &7Aktualnie wplacil: &c" + redUser.getGivedRunes())).toItemStack();
                            inventoryContents.set(row, slot, ClickableItem.empty(is));
                            slot++;
                            if(slot >= 8){
                                row++;
                                slot = 0;
                            }
                        }
                    }
                }));
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public void setBackground(InventoryContents inventoryContents){
        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7)));
        inventoryContents.fillRect(1, 1, 3, 7, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        inventoryContents.fillRect(0, 2, 0, 6, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        inventoryContents.fillRect(4, 2, 4, 6, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
    }
}
