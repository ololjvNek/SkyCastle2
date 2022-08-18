package pl.ololjvNek.skycastle;

import codecrafter47.bungeetablistplus.api.bukkit.BungeeTabListPlusBukkitAPI;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import pl.ololjvNek.skycastle.combats.CombatRunnable;
import pl.ololjvNek.skycastle.commands.Command;
import pl.ololjvNek.skycastle.commands.CommandManager;
import pl.ololjvNek.skycastle.commands.sc.*;
import pl.ololjvNek.skycastle.configs.ConfigCreator;
import pl.ololjvNek.skycastle.configs.ConfigManager;
import pl.ololjvNek.skycastle.data.Member;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.SavingType;
import pl.ololjvNek.skycastle.listeners.*;
import pl.ololjvNek.skycastle.managers.*;
import pl.ololjvNek.skycastle.mysql.Store;
import pl.ololjvNek.skycastle.mysql.modes.StoreMySQL;
import pl.ololjvNek.skycastle.runnable.*;
import pl.ololjvNek.skycastle.utils.Logger;
import pl.ololjvNek.skycastle.utils.Util;
import pl.ololjvNek.skycastle.variables.*;
import pl.ololjvNek.worldmanager.worlds.WorldManager;

public class Main extends JavaPlugin {

    @Getter private static Main plugin;
    @Getter private static Store store;
    @Getter private static ConfigCreator arenasConfig;
    @Getter private static ConfigCreator globalConfig;
    @Getter private static ConfigCreator shopPermissionConfig;
    @Getter private static ConfigCreator villagerShopConfig;
    @Getter private static ConfigCreator messagesConfig;
    @Getter private static WorldManager worldManager;
    @Getter private static InventoryManager inventoryManager;
    @Getter private static Scoreboard scoreboard;
    @Getter private static SavingType savingType;

    @Getter @Setter private static Entity gameVillager;

    @Setter @Getter private static boolean showTopPoints;

    @Getter private static Location mainLobbyLocation;

    public void onEnable(){
        plugin = this;
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        pl.ololjvNek.worldmanager.Main.setPluginAs(plugin);
        worldManager = new WorldManager();
        arenasConfig = ConfigManager.createConfig("arenas");
        arenasConfig.saveDefaultConfig();
        globalConfig = ConfigManager.createConfig("config");
        globalConfig.saveDefaultConfig();
        villagerShopConfig = ConfigManager.createConfig("villagershop");
        villagerShopConfig.saveDefaultConfig();
        shopPermissionConfig = ConfigManager.createConfig("shop");
        shopPermissionConfig.saveDefaultConfig();
        messagesConfig = ConfigManager.createConfig("messages");
        messagesConfig.saveDefaultConfig();
        FileConfiguration configg = globalConfig.build();
        savingType = SavingType.valueOf(globalConfig.build().getString("globalSettings.saving"));
        if(savingType == SavingType.MYSQL) {
            store = new StoreMySQL(configg.getString("globalSettings.mysql.host"), configg.getInt("globalSettings.mysql.port"), configg.getString("globalSettings.mysql.user"), configg.getString("globalSettings.mysql.password"), configg.getString("globalSettings.mysql.db"), "");
            if (store.connect()) {
                store.update("CREATE TABLE IF NOT EXISTS `users`(`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `uuid` text NOT NULL, `lastName` text NOT NULL, `coins` int(11) NOT NULL, `stars` int(11) NOT NULL, `points` int(11) NOT NULL, `team` text NOT NULL, `kills` int(11) NOT NULL, `deaths` int(11) NOT NULL, `assists` int(11) NOT NULL, `discordTag` text NOT NULL, `addons` text NOT NULL)");
                store.update("CREATE TABLE IF NOT EXISTS `teams`(`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `teamTag` text NOT NULL);");
                store.update("CREATE TABLE IF NOT EXISTS `members`(`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `uuid` text NOT NULL, `name` text NOT NULL, `teamTag` text NOT NULL, `mention` text NOT NULL);");
            } else {
                savingType = SavingType.FLAT;
            }
        }
        TeamManager.loadTeams();
        UserManager.loadUsers();
        TeamManager.loadMembers();
        SkyCastleManager.loadSkyCastles();
        CategoryManager.loadCategories();
        PermissionShopManager.loadShops();
        registerCommand(new DebugCommand());
        registerCommand(new SkyCastleCommand());
        registerCommand(new SejfCommand());
        registerCommand(new WedkaCommand());
        registerCommand(new TeamCommand());
        registerCommand(new ChatCommand());
        registerCommand(new NagrodaCommand());
        registerCommand(new SkullCommand());
        registerCommand(new RejestrujCommand());
        registerCommand(new GameModeCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BoosterListener(), this);
        Bukkit.getPluginManager().registerEvents(new GeneratorListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuardsListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncChatPlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new WaterBucketEmptyListener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyCastleInKapliczkaListener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyCastleInTronListener(), this);
        Logger.info("DATAFOLDER: " + getDataFolder().getPath());
        Logger.info("WORLD FOLDER: " + Bukkit.getWorld("world").getWorldFolder().getPath());
        if(Bukkit.getPluginManager().getPlugin("BungeeTabListPlus") != null){
            BungeeTabListPlusBukkitAPI.registerVariable(getPlugin(), new PointsVariable());
            BungeeTabListPlusBukkitAPI.registerVariable(getPlugin(), new WhatTopVariable("whattop"));
            BungeeTabListPlusBukkitAPI.registerVariable(getPlugin(), new StarsVariable("stars"));
            for(int i = 1; i < 15; i++){
                BungeeTabListPlusBukkitAPI.registerVariable(getPlugin(), new TopVariable("top-" + i, i));
            }
            for(int i = 1; i < 15; i++){
                BungeeTabListPlusBukkitAPI.registerVariable(getPlugin(), new TTopVariable("ttop-" + i, i));
            }
        }else{
            Bukkit.getLogger().info("Plugin can't detect BungeeTabListPlus in your plugins so variables will not registered");
        }
        new SkyCastleRunnable().runTaskTimer(this, 20L, 20L);
        new GuardRunnable().runTaskTimer(this, 60L, 60L);
        new CombatRunnable().runTaskTimer(this, 20L, 20L);
        new QueueRunnable().runTaskTimer(this, 100L, 100L);
        new DiscoArmorRunnable().runTaskTimer(this, 120L, 10L);
        new AnnouncementsRunnable().runTaskTimer(this, (20*60)*2L, (20*60)*2L);
        if(savingType == SavingType.MYSQL){
            new BukkitRunnable(){
                public void run(){
                    long start = System.currentTimeMillis();
                    for(User u : UserManager.getUsers().values()){
                        if(u.isOnline()){
                            u.update();
                        }
                    }
                    for(Member member : TeamManager.getMembers().values()){
                        member.update();
                    }
                }
            }.runTaskTimer(this, (20*60)*5L, (20*60)*5L);
        }
        showTopPoints = true;
        mainLobbyLocation = Bukkit.getWorld("worldvoid").getSpawnLocation();
        if(gameVillager == null){
            setupVillager(new Location(Bukkit.getWorld("worldvoid"), -4, 27, 0));
        }
    }

    public void setupVillager(Location location){
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        Util.setTeg(villager, "PersistenceRequired", 1);
        Util.setTeg(villager, "NoAI", 1);
        Util.setTeg(villager, "Silent", 1);
        Util.setTeg(villager, "Invulnerable", 1);
        villager.setAdult();
        villager.setAgeLock(true);
        ((LivingEntity)villager).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 255));
        villager.setCustomName(Util.fixColors("&a&l" + globalConfig.getConfig().getString("globalSettings.playVillagerName")));
        villager.setCustomNameVisible(true);
        villager.setRemoveWhenFarAway(false);
        gameVillager = villager;
    }

    public void onDisable(){
        if(gameVillager != null){
            gameVillager.remove();
        }
        if(savingType == SavingType.FLAT){
            for(User u : UserManager.getUsers().values()){
                u.update();
            }
        }
        for(SkyCastle skyCastle : SkyCastleManager.getSkyCastles().values()){
            Main.getWorldManager().deleteWorld(skyCastle.getName());
        }
    }

    public void registerCommand(Command command){
        CommandManager.register(command);
    }


}
