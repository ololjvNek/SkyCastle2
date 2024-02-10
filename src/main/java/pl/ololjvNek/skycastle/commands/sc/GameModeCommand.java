package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.utils.Util;

public class GameModeCommand extends PlayerCommand {

    public GameModeCommand() {
        super("gamemode", "Gamemode", "gamemode", "core.admin", "gm", "gmc", "gms");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if(getLabel().contains("gmc")){
            p.setGameMode(GameMode.CREATIVE);
            return Util.sendMessage(p, "&c&lGAMEMODE &8>> &7Your game mode has been changed to &acreative");
        }else if(getLabel().contains("gms")){
            p.setGameMode(GameMode.SURVIVAL);
            return Util.sendMessage(p, "&c&lGAMEMODE &8>> &7Your game mode has been changed to &asurvival");
        }
        if(args.length < 1){
            return Util.sendMessage(p, "&8>> &7Correct usage: &c/" + getLabel() + " <creative(c/1)/survival(s/0)/spectator(sp/2)>");
        }
        switch (args[0]) {
            case "c":
            case "creative":
            case "1":
                p.setGameMode(GameMode.CREATIVE);
                return Util.sendMessage(p, "&c&lGAMEMODE &8>> &7Your game mode has been changed to &acreative");
            case "s":
            case "survival":
            case "0":
                p.setGameMode(GameMode.SURVIVAL);
                return Util.sendMessage(p, "&c&lGAMEMODE &8>> &7Your game mode has been changed to &asurvival");
            case "sp":
            case "spectator":
            case "2":
                p.setGameMode(GameMode.SPECTATOR);
                return Util.sendMessage(p, "&c&lGAMEMODE &8>> &7Your game mode has been changed to &aspectator");
        }
        return Util.sendMessage(p, "&8>> &7Correct usage: &c/" + getLabel() + " <creative(c/1)/survival(s/0)/spectator(sp/2)>");
    }
}
