package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.VillagerShopManager;
import pl.ololjvNek.skycastle.utils.Base64Util;
import pl.ololjvNek.skycastle.utils.Logger;
import pl.ololjvNek.skycastle.utils.TimeUtil;
import pl.ololjvNek.skycastle.utils.Util;
import pl.ololjvNek.worldmanager.worlds.WorldManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class SkyCastle {

    private String name, mapName,kapliczkaTakenBy;
    private World createdWorld;
    private ArenaStatus status;
    private Event event;
    private int maxPlayers, minPlayers, taking,blueTaking,redTaking,blueRunes,redRunes;
    private long startTime, endTime;
    private double restartPercent;
    private Team scoreRedTeam, scoreBlueTeam;
    private List<Player> redTeam,blueTeam,players,spectators;
    private List<Entity> straznicyBlue,straznicyRed;
    private Location lobbySpawn,redSpawn,blueSpawn,kapliczka,redTron,blueTron;
    private List<Location> generators, chests, placedBlocks;
    private List<BlockState> blockStates;
    private ItemStack[] globalArmor;
    private ItemStack[] globalEquipment;

    public SkyCastle(String name, String mapName, int maxPlayers, int minPlayers, int startTime, int endTime){
        this.name = name;
        this.mapName = mapName;
        this.status = ArenaStatus.WAITING;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.startTime = TimeUtil.SECOND.getTime(startTime);
        this.endTime = TimeUtil.SECOND.getTime(endTime);
        this.blockStates = new ArrayList<>();
        this.placedBlocks = new ArrayList<>();
        this.createdWorld = null;
        this.restartPercent = 0.00;
        this.redTeam = new ArrayList<Player>();
        this.blueTeam = new ArrayList<Player>();
        this.spectators = new ArrayList<>();
        this.straznicyBlue = new ArrayList<>();
        this.straznicyRed = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.redSpawn = null;
        this.redTron = null;
        this.scoreBlueTeam = Main.getScoreboard().registerNewTeam(name + "BLUE");
        this.scoreRedTeam = Main.getScoreboard().registerNewTeam(name + "RED");
        this.blueRunes = 0;
        this.redRunes = 0;
        this.blueTron = null;
        this.taking = 0;
        this.lobbySpawn = null;
        this.blueSpawn = null;
        this.kapliczka = null;
        this.generators = new ArrayList<>();
        this.blueTaking = 0;
        this.redTaking = 0;
        this.kapliczkaTakenBy = "NOTHING";
        this.event = new Event(this, new ArrayList<>());
        try {
            this.globalArmor = Base64Util.itemStackArrayFromBase64(Main.getGlobalConfig().build().getString("globalSettings.player.standardArmor"));
            this.globalEquipment = Base64Util.itemStackArrayFromBase64(Main.getGlobalConfig().build().getString("globalSettings.player.standardEquipment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRedRune(int index){
        redRunes += index;
    }

    public void addBlueRune(int index){
        blueRunes += index;
    }

    public void removeBlueRune(int index){
        blueRunes -= index;
    }

    public void removeRedRune(int index){
        redRunes -= index;
    }

    public void addTaking(int index){
        taking += index;
    }

    public void addBlueTaking(int index){
        blueTaking += index;
    }

    public void addRedTaking(int index){
        redTaking += index;
    }

    public String getTeamIn(Player player){
        if(redTeam.contains(player)){
            return "RED";
        }else if(blueTeam.contains(player)){
            return "BLUE";
        }
        return "NULL";
    }

    public boolean isSpectator(Player p){
        return spectators.contains(p);
    }

    public boolean isRedTeam(Player p){
        return redTeam.contains(p);
    }

    public boolean isBlueTeam(Player p){
        return blueTeam.contains(p);
    }

    public boolean isTeamMate(Player p1, Player p2){
        if(isRedTeam(p1) && isRedTeam(p2)){
            return true;
        }
        return isBlueTeam(p1) && isBlueTeam(p2);
    }

    public boolean isInAnyTeam(Player player){
        for(Player red : redTeam){
            if(red.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())){
                return true;
            }
        }
        for(Player red : blueTeam){
            if(red.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())){
                return true;
            }
        }
        return false;
    }

    public void rebuildMap(){
        for(Location location : Util.sphere(getKapliczka(), 8,7,false,true,0)){
            if(location.getBlock().getType() == Material.STAINED_CLAY){
                location.getBlock().setType(Material.BEDROCK);
            }
        }
        new BukkitRunnable(){
            int i = 0;
            int i2 = 0;
            public void run(){
                double fullAmount = (blockStates.size()-1)+(placedBlocks.size()-1);
                if(i < (blockStates.size()-1)){
                    BlockState blockState = blockStates.get(i);
                    blockState.update(true, false);
                    i++;
                }else{
                    if(i2 < (placedBlocks.size()-1)){
                        Location location = placedBlocks.get(i2);
                        location.getBlock().setType(Material.AIR);
                        i2++;
                    }else{
                        blockStates.clear();
                        placedBlocks.clear();
                        setStatus(ArenaStatus.WAITING);
                        restartPercent = 0.00;
                        this.cancel();
                    }
                }
                restartPercent = Util.round((((i+i2)/fullAmount)*100), 2);
            }
        }.runTaskTimer(Main.getPlugin(), 5L, 5L);
    }

    private int trials = 0;
    private boolean loaded = false;

    public void setupGame(){
        if(mapName == null){
            return;
        }
        getScoreBlueTeam().setPrefix(ChatColor.BLUE + "");
        getScoreRedTeam().setPrefix(ChatColor.DARK_RED + "");
        if(trials > 0){
            if(createdWorld != null){
                Main.getWorldManager().deleteWorld(getName());
                createdWorld = null;
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(createdWorld == null){
            Main.getWorldManager().restoreWorldWithNames(getMapName(), getName());
            createdWorld = Bukkit.getWorld(getName());
        }
        new BukkitRunnable(){
            public void run(){
                if(createdWorld == null){
                    createdWorld = Bukkit.getWorld(getName());
                }
                for(Entity entity : createdWorld.getEntities()){
                    if(entity instanceof ArmorStand){
                        if(entity.getCustomName() != null){
                            Location toSave = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
                            switch (entity.getCustomName()){
                                case "SkyCastle_LOBBY":
                                    setLobbySpawn(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_REDTEAM":
                                    setRedSpawn(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_BLUETEAM":
                                    setBlueSpawn(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_KAPLICZKA":
                                    setKapliczka(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_REDTRON":
                                    setRedTron(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_BLUETRON":
                                    setBlueTron(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_GENERATOR":
                                    getGenerators().add(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_CHEST":
                                    getChests().add(toSave);
                                    entity.remove();
                                    break;
                                case "SkyCastle_SHOP":
                                    VillagerShopManager.createVillagerShop(toSave);
                                    entity.remove();
                                    break;
                            }
                        }
                    }
                }
                if(getLobbySpawn() != null && getRedSpawn() != null && getBlueSpawn() != null && getKapliczka() != null && getBlueTron() != null && getRedTron() != null){
                    Logger.info("[SkyCastle-Manager] Pomyslnie zaladowano arene " + getName() + " z mapa " + getMapName());
                    loaded = true;
                }else{
                    Logger.info("[SkyCastle-Manager] Niepowodzenie przy ladowaniu areny: " + getName() + " z mapa " + getMapName(), "Ponowna proba zaladowania areny... (" + trials + "/5)");
                    trials += 1;
                    if(trials > 5){
                        Logger.info("[SkyCastle-Manager] Blad przy ladowaniu areny: " + getName() + " z mapa " + getMapName(), "Przekroczono limit kolejnych prob, arena zostanie pominieta");
                        loaded = true;
                        return;
                    }
                    setupGame();
                }
            }
        }.runTaskLater(Main.getPlugin(), 60L);
    }

}
