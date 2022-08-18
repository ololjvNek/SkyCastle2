package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.providers.ActiveEventProvider;
import pl.ololjvNek.skycastle.utils.*;
import pl.ololjvNek.worldmanager.worlds.WorldManager;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SkyCastleManager {

    @Getter private static HashMap<String, SkyCastle> skyCastles = new HashMap<>();

    public static SkyCastle createSkyCastle(String name){
        FileConfiguration config = Main.getArenasConfig().build();
        config.set("arenas." + name + ".mapName", "test");
        config.set("arenas." + name + ".maxPlayers", 20);
        config.set("arenas." + name + ".minPlayers", 10);
        config.set("arenas." + name + ".startTime", 30);
        config.set("arenas." + name + ".endTime", 600);
        Main.getArenasConfig().saveConfig();
        ConfigurationSection section = Main.getArenasConfig().build().getConfigurationSection("arenas." + name);
        SkyCastle skyCastle = new SkyCastle(name, section.getString("mapName"), section.getInt("maxPlayers"), section.getInt("minPlayers"), section.getInt("startTime"), section.getInt("endTime"));
        skyCastles.put(skyCastle.getName(), skyCastle);
        Main.getWorldManager().restoreWorldWithNames(skyCastle.getMapName(), skyCastle.getName());
        return skyCastle;
    }

    public static SkyCastle getSkyCastle(String name){
        return skyCastles.get(name);
    }

    public static void leaveGame(SkyCastle skyCastle, Player player){
        if(skyCastle.getStatus() == ArenaStatus.WAITING || skyCastle.getStatus() == ArenaStatus.STARTING){
            skyCastle.getPlayers().remove(player);
            UserManager.getUser(player).setSkyCastle(null);
            player.teleport(Main.getMainLobbyLocation());
            player.getInventory().clear();
            player.getInventory().setItem(4, ItemUtil.menu);
            BoardUtil.update(player);
        }else if(skyCastle.getStatus() == ArenaStatus.STARTED && skyCastle.isSpectator(player)){
            skyCastle.getPlayers().remove(player);
            skyCastle.getSpectators().remove(player);
            switch (skyCastle.getTeamIn(player)){
                case "RED":
                    skyCastle.getRedTeam().remove(player);
                    break;
                case "BLUE":
                    skyCastle.getBlueTeam().remove(player);
                    break;
            }
            UserManager.getUser(player).setSkyCastle(null);
            player.teleport(Main.getMainLobbyLocation());
            player.getInventory().clear();
            player.getInventory().setItem(4, ItemUtil.menu);
            for(Player players : Bukkit.getOnlinePlayers()){
                if(!players.canSee(player)){
                    players.showPlayer(player);
                }
            }
        }
    }

    public static void joinGame(SkyCastle skyCastle, Player player){
        skyCastle.getPlayers().add(player);
        UserManager.getUser(player).setSkyCastle(skyCastle);
        if(skyCastle.getLobbySpawn() != null){
            player.teleport(skyCastle.getLobbySpawn());
        }
        player.closeInventory();
        player.getInventory().clear();
        player.getInventory().setItem(8, ItemUtil.leaveGame);
        Util.sendMessage(skyCastle.getPlayers(), "&b&lSKY&a&lCASTLE &8{o} &7Player &c" + player.getName() + " &ejoined to the game &8(&c" + skyCastle.getPlayers().size() + "&7/&c" + skyCastle.getMaxPlayers() + "&8)");
        if(skyCastle.getEvent() != null && skyCastle.getEvent().isEnabled()){
            new BukkitRunnable(){
                public void run(){
                    ActiveEventProvider.INVENTORY.open(player);
                }
            }.runTaskLater(Main.getPlugin(), 30L);
        }
        if(skyCastle.getPlayers().size() > skyCastle.getMinPlayers() && skyCastle.getStatus() == ArenaStatus.WAITING){
            String skyCastleName = skyCastle.getName();
            skyCastle.setStatus(ArenaStatus.STARTING);
            skyCastle.setStartTime(TimeUtil.SECOND.getTime(30));
            skyCastle.setStartTime(System.currentTimeMillis()+skyCastle.getStartTime());

            new BukkitRunnable(){
                final SkyCastle skyCastle = SkyCastleManager.getSkyCastle(skyCastleName);
                public void run(){
                    if(skyCastle.getPlayers().size() >= 1){
                        if(skyCastle.getStartTime() > System.currentTimeMillis()){
                            Util.sendActionBar(skyCastle.getPlayers(), Util.fixColors("&7Game will start until &a" + DataUtil.secondsToString(skyCastle.getStartTime())));
                            int secondsToStart = DataUtil.convertStrToInt(DataUtil.secondsToString(skyCastle.getStartTime()));
                            if(secondsToStart < 6){
                                Util.sendTitle(skyCastle.getPlayers(), "&cStart in &a" + secondsToStart);
                                skyCastle.getPlayers().forEach(player -> player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5f, 0.5f));
                            }
                        }else{
                            if(skyCastle.getLobbySpawn() != null && skyCastle.getBlueSpawn() != null && skyCastle.getRedSpawn() != null && skyCastle.getKapliczka() != null && skyCastle.getRedTron() != null && skyCastle.getBlueTron() != null){
                                this.cancel();
                                for(Player inGame : skyCastle.getPlayers()){
                                    if(!skyCastle.isInAnyTeam(inGame)){
                                        int sizeOfRed = skyCastle.getRedTeam().size();
                                        int sizeOfBlue = skyCastle.getBlueTeam().size();
                                        if(sizeOfRed > sizeOfBlue){
                                            skyCastle.getBlueTeam().add(inGame);
                                        }else if(sizeOfBlue > sizeOfRed){
                                            skyCastle.getRedTeam().add(inGame);
                                        }else{
                                            int random = ThreadLocalRandom.current().nextInt(2);
                                            switch (random){
                                                case 1:
                                                    if(skyCastle.getTeamIn(inGame).equals("NULL")){
                                                        skyCastle.getRedTeam().add(inGame);
                                                    }
                                                    break;
                                                case 0:
                                                    if(skyCastle.getTeamIn(inGame).equals("NULL")){
                                                        skyCastle.getBlueTeam().add(inGame);
                                                    }
                                                    break;
                                            }
                                        }
                                    }
                                    inGame.teleport(skyCastle.getLobbySpawn());
                                }
                                skyCastle.setStatus(ArenaStatus.STARTED);
                                skyCastle.setEndTime(System.currentTimeMillis()+skyCastle.getEndTime());
                                Util.sendActionBar(skyCastle.getPlayers(), Util.fixColors("&aGame is starting! &cLoading..."));
                                new BukkitRunnable(){
                                    public void run(){
                                        Util.sendActionBar(skyCastle.getPlayers(), Util.fixColors("&aGame is starting! &cTeleporting players..."));
                                        for(Player teamRed : skyCastle.getRedTeam()){
                                            teamRed.teleport(skyCastle.getRedSpawn());
                                            teamRed.setDisplayName(ChatColor.DARK_RED + teamRed.getName());
                                            ItemStack[] copyOfGlobalArmor = skyCastle.getGlobalArmor();
                                            for(int i = 0; i < copyOfGlobalArmor.length; i++){
                                                if(copyOfGlobalArmor[i].getType() == Material.LEATHER_HELMET
                                                        || copyOfGlobalArmor[i].getType() == Material.LEATHER_CHESTPLATE
                                                || copyOfGlobalArmor[i].getType() == Material.LEATHER_LEGGINGS
                                                        || copyOfGlobalArmor[i].getType() == Material.LEATHER_BOOTS){
                                                    copyOfGlobalArmor[i] = new ItemBuilder(copyOfGlobalArmor[i].getType(), copyOfGlobalArmor[i].getAmount()).setLeatherArmorColor(Color.RED).toItemStack();
                                                }
                                            }
                                            teamRed.getInventory().setArmorContents(copyOfGlobalArmor);
                                            teamRed.getInventory().setContents(skyCastle.getGlobalEquipment());
                                            skyCastle.getScoreRedTeam().addPlayer(teamRed);
                                            if(UserManager.getUser(teamRed).hasAddon("FishingRod")){
                                                teamRed.getInventory().addItem(ItemUtil.wedka);
                                            }
                                        }
                                        for(Player teamBlue : skyCastle.getBlueTeam()){
                                            teamBlue.teleport(skyCastle.getBlueSpawn());
                                            teamBlue.setDisplayName(ChatColor.BLUE + teamBlue.getName());

                                            ItemStack[] copyOfGlobalArmor = skyCastle.getGlobalArmor();
                                            for(int i = 0; i < copyOfGlobalArmor.length; i++){
                                                if(copyOfGlobalArmor[i].getType() == Material.LEATHER_HELMET
                                                        || copyOfGlobalArmor[i].getType() == Material.LEATHER_CHESTPLATE
                                                        || copyOfGlobalArmor[i].getType() == Material.LEATHER_LEGGINGS
                                                        || copyOfGlobalArmor[i].getType() == Material.LEATHER_BOOTS){
                                                    copyOfGlobalArmor[i] = new ItemBuilder(copyOfGlobalArmor[i].getType(), copyOfGlobalArmor[i].getAmount()).setLeatherArmorColor(Color.BLUE).toItemStack();
                                                }
                                            }
                                            teamBlue.getInventory().setArmorContents(copyOfGlobalArmor);
                                            teamBlue.getInventory().setContents(skyCastle.getGlobalEquipment());
                                            skyCastle.getScoreBlueTeam().addPlayer(teamBlue);
                                            if(UserManager.getUser(teamBlue).hasAddon("FishingRod")){
                                                teamBlue.getInventory().addItem(ItemUtil.wedka);
                                            }
                                        }
                                        for(Location loc : skyCastle.getChests()){
                                            if(loc.getBlock().getType() != Material.CHEST){
                                                loc.getBlock().setType(Material.CHEST);
                                                Chest chest = (Chest) loc.getBlock().getState();
                                                ItemStack[] randomBooster = new ItemStack[] {ItemUtil.granatPajeczy,ItemUtil.granatOdepchniecia,ItemUtil.granatOslabiajacy, ItemUtil.granatOslepiajacy,ItemUtil.granatPodbicia};
                                                ItemStack randomedBooster = randomBooster[ThreadLocalRandom.current().nextInt(randomBooster.length)];
                                                chest.getBlockInventory().addItem(randomedBooster);
                                                for(int i = 0; i < 5; i++){
                                                    int randomSlot = ThreadLocalRandom.current().nextInt(chest.getBlockInventory().getSize());
                                                    if(chest.getBlockInventory().getItem(randomSlot) == null){
                                                        chest.getBlockInventory().setItem(randomSlot, new ItemStack(Material.GOLD_NUGGET, 1));
                                                    }
                                                }
                                                Logger.info("Chest filled");
                                            }else if(loc.getBlock().getType() == Material.CHEST){
                                                Chest chest = (Chest) loc.getBlock().getState();
                                                ItemStack[] randomBooster = new ItemStack[] {ItemUtil.granatPajeczy,ItemUtil.granatOdepchniecia,ItemUtil.granatOslabiajacy, ItemUtil.granatOslepiajacy,ItemUtil.granatPodbicia};
                                                ItemStack randomedBooster = randomBooster[ThreadLocalRandom.current().nextInt(randomBooster.length)];
                                                chest.getBlockInventory().addItem(randomedBooster);
                                                for(int i = 0; i < 5; i++){
                                                    int randomSlot = ThreadLocalRandom.current().nextInt(chest.getBlockInventory().getSize());
                                                    if(chest.getBlockInventory().getItem(randomSlot) == null){
                                                        chest.getBlockInventory().setItem(randomSlot, new ItemStack(Material.GOLD_NUGGET, 1));
                                                    }
                                                }
                                                Logger.info("Chest filled");
                                            }
                                        }
                                        skyCastle.getCreatedWorld().setPVP(true);
                                        skyCastle.getCreatedWorld().setDifficulty(Difficulty.EASY);
                                        skyCastle.getCreatedWorld().setTime(0);
                                        skyCastle.getCreatedWorld().setAnimalSpawnLimit(0);
                                        skyCastle.getCreatedWorld().setWeatherDuration(0);
                                        skyCastle.getCreatedWorld().setStorm(false);
                                        skyCastle.getCreatedWorld().setThundering(false);
                                        skyCastle.getCreatedWorld().setMonsterSpawnLimit(0);
                                        skyCastle.getCreatedWorld().setAutoSave(false);

                                    }
                                }.runTaskLater(Main.getPlugin(), 30L);
                            }else{
                                boolean lobby = skyCastle.getLobbySpawn() != null;
                                boolean redteam = skyCastle.getRedSpawn() != null;
                                boolean blueteam = skyCastle.getBlueSpawn() != null;
                                boolean kapliczka = skyCastle.getKapliczka() != null;
                                boolean redtron = skyCastle.getRedTron() != null;
                                boolean bluetron = skyCastle.getBlueTron() != null;
                                Logger.warning("There is a problem with reading a locations. Check console log");
                                Logger.warning("Debug locations:", "Lobby: " + lobby, "RedTeam: " + redteam, "BlueTeam: " + blueteam, "kapliczka: " + kapliczka, "redTron: " + redtron, "blueTron: " + bluetron);
                                this.cancel();
                            }
                        }
                    }else{
                        this.cancel();
                        skyCastle.setStatus(ArenaStatus.WAITING);
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 20L, 20L);
        }
    }

    public static void respawnPlayer(SkyCastle skyCastle, Player toRespawn){
        if(skyCastle.getTeamIn(toRespawn).equals("RED")){
            new BukkitRunnable(){
                int i = 7;
                public void run(){
                    if(i > 0){
                        Util.sendTitle(toRespawn, "&cODRADZANIE");
                        Util.sendSubTitle(toRespawn, "&8>> &7Odrodzisz sie za &6" + i + " &7sek.");
                        i--;
                    }else{
                        this.cancel();
                        toRespawn.teleport(skyCastle.getRedSpawn());
                        toRespawn.setGameMode(GameMode.SURVIVAL);
                        toRespawn.setHealth(20);
                        toRespawn.setDisplayName(ChatColor.DARK_RED + toRespawn.getName());
                        ItemStack[] copyOfGlobalArmor = skyCastle.getGlobalArmor();
                        for(int i = 0; i < copyOfGlobalArmor.length; i++){
                            if(copyOfGlobalArmor[i].getType() == Material.LEATHER_HELMET
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_CHESTPLATE
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_LEGGINGS
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_BOOTS){
                                copyOfGlobalArmor[i] = new ItemBuilder(copyOfGlobalArmor[i].getType(), copyOfGlobalArmor[i].getAmount()).setLeatherArmorColor(Color.RED).toItemStack();
                            }
                        }
                        toRespawn.getInventory().setArmorContents(copyOfGlobalArmor);
                        toRespawn.getInventory().setContents(skyCastle.getGlobalEquipment());
                        skyCastle.getSpectators().remove(toRespawn);
                        skyCastle.getPlayers().forEach(player -> {
                            if(!player.canSee(toRespawn)){
                                player.showPlayer(toRespawn);
                            }
                        });
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 20L, 20L);
        }
        if(skyCastle.getTeamIn(toRespawn).equals("BLUE")){
            new BukkitRunnable(){
                int i = 7;
                public void run(){
                    if(i > 0){
                        Util.sendTitle(toRespawn, "&cODRADZANIE");
                        Util.sendSubTitle(toRespawn, "&8>> &7Odrodzisz sie za &6" + i + " &7sek.");
                        i--;
                    }else{
                        this.cancel();
                        toRespawn.teleport(skyCastle.getBlueSpawn());
                        toRespawn.setGameMode(GameMode.SURVIVAL);
                        toRespawn.setHealth(20);
                        toRespawn.setDisplayName(ChatColor.DARK_RED + toRespawn.getName());
                        ItemStack[] copyOfGlobalArmor = skyCastle.getGlobalArmor();
                        for(int i = 0; i < copyOfGlobalArmor.length; i++){
                            if(copyOfGlobalArmor[i].getType() == Material.LEATHER_HELMET
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_CHESTPLATE
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_LEGGINGS
                                    || copyOfGlobalArmor[i].getType() == Material.LEATHER_BOOTS){
                                copyOfGlobalArmor[i] = new ItemBuilder(copyOfGlobalArmor[i].getType(), copyOfGlobalArmor[i].getAmount()).setLeatherArmorColor(Color.BLUE).toItemStack();
                            }
                        }
                        toRespawn.getInventory().setArmorContents(copyOfGlobalArmor);
                        toRespawn.getInventory().setContents(skyCastle.getGlobalEquipment());
                        skyCastle.getSpectators().remove(toRespawn);
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 20L, 20L);
        }
    }

    public static void endGame(SkyCastle skyCastle, List<Player> winners, List<Player> losers){
        skyCastle.setBlueTaking(0);
        skyCastle.setRedTaking(0);
        skyCastle.setTaking(0);
        for(Player lose : losers){
            lose.getInventory().clear();
            lose.getInventory().setArmorContents(new ItemStack[4]);
            Util.sendTitle(lose, "&c&lPRZEGRANA");
            Util.sendSubTitle(lose, "&8>> &7Zostaliscie pokonani przez przeciwna &cdruzyne&7!");
        }
        for(Player win : winners){
            win.getInventory().clear();
            win.getInventory().setArmorContents(new ItemStack[4]);
            Util.sendTitle(win, "&a&lZWYCIESTWO");
            Util.sendSubTitle(win, "&8>> &7Pokonaliscie cala przeciwna &cdruzyne&7!");
            Util.spawnFireworks(win.getLocation(), 3);
        }
        skyCastle.setStatus(ArenaStatus.RESTARTING);
        new BukkitRunnable(){
            public void run(){
                for(Player player : skyCastle.getPlayers()){
                    player.getInventory().clear();
                    player.teleport(Main.getMainLobbyLocation());
                    player.getInventory().setItem(4, ItemUtil.menu);
                    player.getInventory().setItem(6, ItemUtil.magicPearl);
                    player.getInventory().setItem(2, ItemUtil.gadzety);
                    for(Player online : Bukkit.getOnlinePlayers()){
                        if(!online.canSee(player)){
                            online.showPlayer(player);
                        }
                    }
                    User u = UserManager.getUser(player);
                    u.setSkyCastle(null);
                    double points = Util.round((u.getGameKills()*1.8)+(u.getGameAssists()*1.2)+(u.getGameGrenades()*1.3)+(u.getGameKapliczki()*1.6)+(u.getGameNumbers()*1.4), 2);
                    int coinsGained = (int)(points*4.5);
                    Util.sendMessage(player, "&8&m==============&8 ( &6PODSUMOWANIE &8) &8&m===============" +
                            "\n&8&m==&7> &7Laczna ilosc zabojstw: &6" + u.getGameKills() +
                            "\n&8&m==&7> &7Laczna ilosc asyst: &6" + u.getGameAssists() +
                            "\n&8&m==&7> &7Uzyte granaty: &6" + u.getGameGrenades() +
                            "\n&8&m==&7> &7Przejete kapliczki: &6" + u.getGameKapliczki() +
                            "\n&8&m==&7> &7Ilosc sekund przejmowania w sumie: &6" + u.getGameNumbers() +
                            "\n&8&m==&7> &7W sumie zdobyles &6" + points + " &7punktow w tej grze!" +
                            "\n&8&m==&7> &7Otrzymujesz dodatkowo &c" + coinsGained + " &6coinsow&7!" +
                            "\n&8&m==============&8 ( &cdineron.net &8) &8&m===============");
                    u.addCoins(coinsGained);
                    if(points >= 87.5){
                        int rewardStars = 1;
                        double need = 87.5*2;
                        while(points > need){
                            rewardStars++;
                            need += 87.5;
                        }
                        u.addStars(rewardStars);
                        Util.sendMessage(Bukkit.getOnlinePlayers(), "&e&lGWIAZDKI &8&m=||=&7 Gracz &c" + u.getLastName() + " &7zdobyl &6" + rewardStars + "&e✪ gwiazdke/i &7za &awspaniale statystyki &7w grze!");
                        Team team = Main.getScoreboard().getTeam(player.getName());
                        team.setSuffix(Util.fixColors("&8 &6" + u.getStars() + " &e&l✪"));
                    }
                    u.setGameAssists(0);
                    u.setGameKills(0);
                    u.setGameGrenades(0);
                    u.setGameKapliczki(0);
                    u.setGameNumbers(0);
                    if(skyCastle.getScoreRedTeam().hasPlayer(player)){
                        skyCastle.getScoreRedTeam().removePlayer(player);
                    }else if(skyCastle.getScoreBlueTeam().hasPlayer(player)){
                        skyCastle.getScoreBlueTeam().removePlayer(player);
                    }
                }
                skyCastle.getPlayers().clear();
                skyCastle.getRedTeam().clear();
                skyCastle.getBlueTeam().clear();
                skyCastle.setTaking(0);
                skyCastle.setStartTime(TimeUtil.SECOND.getTime(30));
                skyCastle.setKapliczkaTakenBy("NOTHING");
                skyCastle.setBlueTaking(0);
                skyCastle.setRedTaking(0);
                skyCastle.getSpectators().clear();
                skyCastle.rebuildMap();
            }
        }.runTaskLater(Main.getPlugin(), 100L);
    }

    public static boolean isAllSkyCastlesLoaded(){
        int i = 0;
        for(SkyCastle skyCastle : skyCastles.values()){
            if(skyCastle.isLoaded()){
                i++;
            }
        }
        return i == skyCastles.size();
    }

    public static void tryToEndGame(SkyCastle skyCastle){
        if(skyCastle.getBlueTeam().size() < 1){
            endGame(skyCastle, skyCastle.getRedTeam(), skyCastle.getBlueTeam());
        }
        if(skyCastle.getRedTeam().size() < 1){
            endGame(skyCastle, skyCastle.getBlueTeam(), skyCastle.getRedTeam());
        }
    }

    public static void loadSkyCastles(){
        if(Main.getArenasConfig().build().getConfigurationSection("arenas") == null){
            return;
        }
        Logger.info("[SkyCastle-Manager] Rozpoczynam konfiguracje aren...");
        for(String s : Main.getArenasConfig().build().getConfigurationSection("arenas").getKeys(false)){
            ConfigurationSection section = Main.getArenasConfig().build().getConfigurationSection("arenas." + s);
            SkyCastle skyCastle = new SkyCastle(s, section.getString("mapName"), section.getInt("maxPlayers"), section.getInt("minPlayers"), section.getInt("startTime"), section.getInt("endTime"));
            skyCastles.put(skyCastle.getName(), skyCastle);
            Logger.info("[SkyCastle-Manager] Setuping: " + skyCastle.getName() + " / map: " + skyCastle.getMapName() + " (id: " + skyCastles.size() + ")");
            skyCastle.setupGame();
        }
    }
}
