package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.providers.EventProvider;
import pl.ololjvNek.skycastle.utils.Base64Util;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;
import pl.ololjvNek.worldmanager.worlds.WorldManager;

public class SkyCastleCommand extends PlayerCommand {

    public SkyCastleCommand() {
        super("skycastle", "skycastle", "skycastle", "core.skycastle");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if(args.length < 1){
            return Util.sendMessage(p, "{PREFIX} &7Correct usage:\n{PREFIX} &c/skycastle set <lobby/redTeam/blueTeam/kapliczka/bluetron/redtron>\n{PREFIX} &c/skycastle setMap <arena> <mapName>\n{PREFIX} &c/skycastle save <world> <mapName>\n{PREFIX} &c/skycastle create <arenaName>");
        }
        if(args[0].equalsIgnoreCase("createVoid")){
            WorldCreator wc = new WorldCreator(args[1]);
            wc.type(WorldType.FLAT);
            wc.generatorSettings("2;0;1;");
            wc.createWorld();
            return Util.sendMessage(p, "{PREFIX} &7Stworzono pusty swiat o nazwie &a" + args[1]);
        }
        if(args[0].equalsIgnoreCase("teleportWorld")){
            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
        }
        if(args[0].equalsIgnoreCase("getallitems")){
            p.getInventory().addItem(ItemUtil.wedka, ItemUtil.runa, ItemUtil.granatPajeczy, ItemUtil.granatOdepchniecia, ItemUtil.granatOslabiajacy, ItemUtil.granatOslepiajacy, ItemUtil.granatPodbicia);
        }
        if(args[0].equalsIgnoreCase("set")){
            switch (args[1].toLowerCase()){
                case "redteam":
                    createArmorStand("SkyCastle_REDTEAM", p);
                    break;
                case "blueteam":
                    createArmorStand("SkyCastle_BLUETEAM", p);
                    break;
                case "kapliczka":
                    createArmorStand("SkyCastle_KAPLICZKA", p);
                    break;
                case "lobby":
                    createArmorStand("SkyCastle_LOBBY", p);
                    break;
                case "tronred":
                    createArmorStand("SkyCastle_REDTRON", p);
                    break;
                case "tronblue":
                    createArmorStand("SkyCastle_BLUETRON", p);
                    break;
                case "generator":
                    createArmorStand("SkyCastle_GENERATOR", p);
                    break;
                case "chest":
                    createArmorStand("SkyCastle_CHEST", p);
                    break;
                case "shop":
                    createArmorStand("SkyCastle_SHOP", p);
                    break;
            }
            return Util.sendMessage(p, "{PREFIX} &7Setted location &c" + args[1].toUpperCase());
        }
        if(args[0].equalsIgnoreCase("disable")){
            SkyCastle skyCastle = SkyCastleManager.getSkyCastle(args[1]);
            skyCastle.setStatus(ArenaStatus.DISABLED);
            return Util.sendMessage(p, "{PREFIX} &7Disabled arena &a" + skyCastle.getName());
        }
        if(args[0].equalsIgnoreCase("event")){
            SkyCastle skyCastle = SkyCastleManager.getSkyCastle(args[1]);
            if(skyCastle.getStatus() == ArenaStatus.DISABLED){
                UserManager.getUser(p).setSkyCastle(skyCastle);
                EventProvider.INVENTORY.open(p);
            }
        }
        if(args[0].equalsIgnoreCase("enable")){
            SkyCastle skyCastle = SkyCastleManager.getSkyCastle(args[1]);
            skyCastle.setStatus(ArenaStatus.WAITING);
            return Util.sendMessage(p, "{PREFIX} &7Enabled arena &a" + skyCastle.getName());
        }
        if(args[0].equalsIgnoreCase("forceWin")){
            User u = UserManager.getUser(p);
            if(u.getSkyCastle() != null){
                String team = u.getSkyCastle().getTeamIn(p);
                if(team.equalsIgnoreCase("BLUE")){
                    u.getSkyCastle().setRedTaking(100);
                }else{
                    u.getSkyCastle().setBlueTaking(100);
                }
            }
            return Util.sendMessage(p, "{PREFIX} &7Wykonano komende przymusowego zwyciestwa arenye");
        }
        if(args[0].equalsIgnoreCase("create")){
            SkyCastle skyCastle = SkyCastleManager.getSkyCastle(args[1]);
            if(skyCastle == null){
                SkyCastleManager.createSkyCastle(args[1]);
                return Util.sendMessage(p, "{PREFIX} &7Created new arena with name &c" + args[1]);
            }else{
                return Util.sendMessage(p, "{PREFIX} &cThat arena is already exists!");
            }
        }
        if(args[0].equalsIgnoreCase("saveGlobalEq")){
            Main.getGlobalConfig().build().set("globalSettings.player.standardArmor", Base64Util.itemStackArrayToBase64(p.getInventory().getArmorContents()));
            Main.getGlobalConfig().build().set("globalSettings.player.standardEquipment", Base64Util.itemStackArrayToBase64(p.getInventory().getContents()));
            Main.getGlobalConfig().saveConfig();
            return Util.sendMessage(p, "{PREFIX} &7Your equipment was saved as Global Equipment of SkyCastle game");
        }
        if(args[0].equalsIgnoreCase("save")){
            World world = Bukkit.getWorld(args[1]);
            String toName = args[2];
            return Util.sendMessage(p, "{PREFIX} &7Message back from saving: &c" + Main.getWorldManager().copyWorldAndChangeToName(world.getName(), toName));
        }
        if(args[0].equalsIgnoreCase("setMap")){
            for(String savedMap : Main.getWorldManager().savedMaps()){
                if(savedMap.equals(args[2])){
                    SkyCastle skyCastle = SkyCastleManager.getSkyCastle(args[1]);
                    if(skyCastle != null){
                        skyCastle.setMapName(args[2]);
                        Main.getArenasConfig().build().set("arenas." + skyCastle.getName() + ".mapName", args[2]);
                        Main.getArenasConfig().saveConfig();
                        return Util.sendMessage(p, "{PREFIX} &7Allocated map &a" + args[2] + " &7to arena &c" + skyCastle.getName());
                    }else{
                        return Util.sendMessage(p, "{PREFIX} &cThat arena doesn't exists!");
                    }
                }
            }
            return Util.sendMessage(p, "{PREFIX} &cI can't find that arena! Type /skycastle savedMaps to get list of saved maps");
        }
        if(args[0].equalsIgnoreCase("savedMaps")){
            return Util.sendMessage(p, "{PREFIX} &7List of saved maps:\n&c" + Main.getWorldManager().savedMaps().toString().replace("[", "").replace("]", ""));
        }
        return true;
    }

    public void createArmorStand(String name, Player player){
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setCustomName(name);
    }
}
